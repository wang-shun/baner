package com.ztkx.cbpay.business.serviceimp.PAB.FBS006;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.PayExgStatusEnum;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.BSellExgCtrlData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 根据后端响应结果，单笔修改交易状态
 * 1.更新付汇总控表  只修改状态
 * 2.更新商户订单表         购汇状态为服务方响应的状态       只修改状态
 * @author zhangxiaoyun
 * 2016-4-18 19:34:39
 * <p>Company:ztkx</p>
 */
public class UpdatePayExgStatusBuss implements BusinessService {

	private Logger logger = Logger.getLogger(UpdatePayExgStatusBuss.class);
	
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		int count = Integer.parseInt(context.getFieldStr("totalCount", CommonContext.SCOPE_GLOBAL));
		String PROCESS_STATUS = context.getFieldStr("PROCESS_STATUS", CommonContext.SCOPE_GLOBAL);
		String PROCESS_MSG = context.getFieldStr("PROCESS_MSG", CommonContext.SCOPE_GLOBAL);
		String BUSS_STATUS = context.getFieldStr("BUSS_STATUS", CommonContext.SCOPE_GLOBAL);
		String FAIL_REASON = context.getFieldStr("FAIL_REASON", CommonContext.SCOPE_GLOBAL);
		String paybatno = context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL);
		String paydate = context.getFieldStr("paydate", CommonContext.SCOPE_GLOBAL);
		
		Connection connection = null;
		BSellExgCtrlData bSellExgCtrlData = null;			//付汇总控表操作句柄
		BMerchantOrderData bMerchantOrderData = null;	//商户订单表B_MERCHANT_ORDER操作句柄
		try {
			logger.info("start update table B_SELL_EXG_CTRL");
			if(PayExgStatusEnum.T.getStatus().equals(PROCESS_STATUS)){
				
				bSellExgCtrlData = new BSellExgCtrlData();
				//初始化链接
				connection = bSellExgCtrlData.getConnection();
				connection.setAutoCommit(false);
				/**
				 * 1.更新付汇总控表
				 */
				String sqlStatement = "update b_sell_exg_ctrl set  process_msg = ?, process_status = ?, fail_reason = ?, buss_status = ? where paybatno = ? and paydate = ?";
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("process_msg", PROCESS_MSG);
				map.put("process_status", PROCESS_STATUS);
				map.put("fail_reason", FAIL_REASON);
				map.put("buss_status", BUSS_STATUS);
				map.put("paybatno", paybatno);
				map.put("paydate", paydate);
				
				int res = bSellExgCtrlData.executeUpdate(sqlStatement, map);
				if(res != 1){
					logger.error("update b_sell_exg_ctrl table error paybatno ["+paybatno+"] paydate ["+paydate+"]");
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
					throw new ServiceException("update b_sell_exg_ctrl table error paybatno ["+paybatno+"] paydate ["+paydate+"]");
				}
				logger.info("update b_sell_exg_ctrl table succ");
				
				
				/**
				 * 3.更新商户订单表
				 */
				logger.info("start update table b_merchant_order status");
				String sqlstatement = "update b_merchant_order set  paybatstatus = ?  where paybatdate = ? and paybatno = ?";
				List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
				LinkedHashMap<String, String> updateData = new LinkedHashMap<String, String>();
				
				updateData.put("paybatstatus", PayExgStatusEnum.T.getCode());
				updateData.put("paybatdate",paydate);
				updateData.put("paybatno", paybatno);
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
				DataHandlerUtil.commitConn(connection);
			}else{
				logger.warn("buy foregin currency status is ["+PROCESS_STATUS+"] with no need to update ");
			}
			
		} catch (Exception e) {
			
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			logger.error("buss service exec exception ,roll back data");
			DataHandlerUtil.rollbakConn(connection);
			throw new ServiceException("buss service exec exception ,roll back data",e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bSellExgCtrlData);
			DataHandlerUtil.releaseSource(bMerchantOrderData);
		}
		return context;
	}
}
