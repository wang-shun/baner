package com.ztkx.cbpay.business.serviceimp.PAB.FBS004;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.BuyFCStatusEnum;
import com.ztkx.cbpay.business.handledata.BBuyExgCtrlData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.db.DBUtil;

/**
 * 根据后端响应结果，单笔修改交易状态
 * 1.更新购汇总控表  只修改状态
 * 2.更新商户订单表         购汇状态为服务方响应的状态       只修改状态
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class UpdateBuyFCStatusBuss implements BusinessService {

	private Logger logger = Logger.getLogger(UpdateBuyFCStatusBuss.class);
	
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		int count = Integer.parseInt(context.getFieldStr("totalCount", CommonContext.SCOPE_GLOBAL));
		String PROCESS_STATUS = context.getFieldStr("PROCESS_STATUS", CommonContext.SCOPE_GLOBAL);
		String buybatno = context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL);
		String buydate = context.getFieldStr("BUYDATE", CommonContext.SCOPE_GLOBAL);
		String DATE_TIME = context.getFieldStr("DATE_TIME", CommonContext.SCOPE_GLOBAL);
		
		Connection connection = null;
		BBuyExgCtrlData bBuyExgCtrlData = null;			//购汇总控表b_buy_exg_ctrl操作句柄
		BMerchantOrderData bMerchantOrderData = null;	//商户订单表B_MERCHANT_ORDER操作句柄
		try {
			logger.info("start update table b_buy_exg_ctrl ");
			if(BuyFCStatusEnum.T.getStatus().equals(PROCESS_STATUS)){
				
				bBuyExgCtrlData = new BBuyExgCtrlData();
				//初始化链接
				connection  = bBuyExgCtrlData.getConnection();
				connection.setAutoCommit(false);
				
				/**
				 * 1.更新购汇总控表
				 */
				String sqlStatement = "update b_buy_exg_ctrl set  process_status = ?, process_msg = ? where buybatno = ? and buydate = ? and rate_time= ?";
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("process_status", context.getFieldStr("PROCESS_STATUS",CommonContext.SCOPE_GLOBAL));
				map.put("process_msg", context.getFieldStr("PROCESS_MSG",CommonContext.SCOPE_GLOBAL));
				map.put("buybatno", buybatno);
				map.put("buydate", buydate);
				map.put("rate_time", DATE_TIME);	//银行端响应回来的汇率时间，对每一笔购汇交易唯一
				int res = bBuyExgCtrlData.executeUpdate(sqlStatement, map);
				if(res != 1){
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
					logger.error("update b_buy_exg_ctrl table error buybatno ["+buybatno+"] buydate ["+buydate+"]");
					throw new ServiceException("update table b_buy_exg_ctrl status faile . need update data row is ["+1+"] actual update data row is ["+res+"]");
				}
				logger.info("update b_buy_exg_ctrl table succ");
				
				
				/**
				 * 3.更新商户订单表
				 */
				logger.info("start update table b_merchant_order status");
				String sqlstatement = "update b_merchant_order set  buybatstatus = ?  where BUYBATDATE = ? and BUYBATNO = ?";
				List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
				LinkedHashMap<String, String> updateData = new LinkedHashMap<String, String>();
				
				updateData.put("buybatstatus", BuyFCStatusEnum.T.getCode());
				updateData.put("buybatdate",buydate);
				updateData.put("buybatno", buybatno);
				list.add(updateData);
				bMerchantOrderData = new BMerchantOrderData();
				bMerchantOrderData.setConnection(connection);
				res = bMerchantOrderData.batchExecuteUpdate(sqlstatement, list);
				logger.info("update row size is ["+res+"]");
				if(res==count){
					logger.info("update table b_merchant_order status succ");
				}else{
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
					logger.error("update table b_merchant_order status faile . need update data row is ["+count+"] actual update data row is ["+res+"]");
					throw new ServiceException("update table b_merchant_order status faile . need update data row is ["+count+"] actual update data row is ["+res+"]");
				}
				DBUtil.commit(connection);
				
			}else{
				logger.warn("buy foregin currency status is ["+PROCESS_STATUS+"] with no need to update ");
			}
			
		} catch (Exception e) {
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("buss service exec exception ,roll back data");
			DataHandlerUtil.rollbakConn(connection);
			throw new ServiceException("buss service exec exception ,roll back data",e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bBuyExgCtrlData);
			DataHandlerUtil.releaseSource(bMerchantOrderData);
		}
		return context;
	}
}
