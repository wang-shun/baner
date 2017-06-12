package com.ztkx.cbpay.business.serviceimp.PAB.FBS005;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.BSellExgCtrlData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 根据后端响应结果，批量修改交易状态
 * 1.更新付汇总控表
 * 2.更新商户订单表        付汇状态为服务方响应的状态
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class BatchUpdatePayExgInfoBuss implements BusinessService {

	private Logger logger = Logger.getLogger(BatchUpdatePayExgInfoBuss.class);
	
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		String paymentdate = context.getFieldStr("paymentdate", CommonContext.SCOPE_GLOBAL);//付汇日期
		String TradeSn = context.getFieldStr("TradeSn",CommonContext.SCOPE_GLOBAL);//付汇批次号
		String PROCESS_STATUS = context.getFieldStr("PROCESS_STATUS",CommonContext.SCOPE_GLOBAL);
		Connection connection = null;
		BSellExgCtrlData sellExgCtrlData = null;
		BMerchantOrderData bMerchantOrderData = null;	//商户订单表B_MERCHANT_ORDER操作句柄
		
		/**
		 * 和平安银行技术沟通过，他们说只要没有返回错误就算交易成功。
		 * 应以上逻辑修改当前类交易流程
		 */
		try {
			/**
			 * 1.更新付汇总控表
			 * 因为服务端响应没有状态，所以目前先不更新购汇总控表状态
			 */
//			logger.info("start update table b_sell_exg_ctrl ");
//			String sqlStatement = "update B_SELL_EXG_CTRL t set t.process_status=? ,t.process_msg=? where t.paybatno=? and t.paydate=?";
//			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//			
//			map.put("process_status", PayExgStatusEnum.O.getStatus());
//			map.put("process_msg", PayExgStatusEnum.O.getStatusDesc());
//			map.put("paybatno", TradeSn);
//			map.put("paydate", paymentdate);
			
			sellExgCtrlData = new BSellExgCtrlData();
			sellExgCtrlData.setRelaseConn(false);
			connection = sellExgCtrlData.getConnection();
			DataHandlerUtil.setAutoCommit(connection, false);
//			sellExgCtrlData.executeUpdate(sqlStatement, map);
			
			
			/**
			 * 3.更新商户订单表
			 */
			logger.info("start update table b_merchant_order status");
			String sqlstatement = "update b_merchant_order set  paybatstatus = ?  where merchantid = ?  and orderid = ? and orderdate = ? and paybatstatus=?";
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
			
			//组织修改条件
			for (BMerchantOrder rowData:listMapData) {
				
				LinkedHashMap<String, String> updateData = new LinkedHashMap<String, String>();
//				if(PayExgStatusEnum.O.getStatus().equals(PROCESS_STATUS)){
//					//待交割
//					updateData.put("paybatstatus", PayExgStatusEnum.O.getCode());
//				}else if(PayExgStatusEnum.T.getStatus().equals(PROCESS_STATUS)){
//					updateData.put("paybatstatus", PayExgStatusEnum.T.getCode());
//				}else if(PayExgStatusEnum.F.getStatus().equals(PROCESS_STATUS)){
//					updateData.put("paybatstatus", PayExgStatusEnum.F.getCode());
//				}
				updateData.put("paybatstatus", BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_02);
				updateData.put("merchantid", rowData.getMerchantid());
				updateData.put("orderid", rowData.getOrderid());
				updateData.put("orderdate", rowData.getOrderdate());
				updateData.put("paybatstatus_where", BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_01);
				list.add(updateData);
			}
			bMerchantOrderData = new BMerchantOrderData();
			bMerchantOrderData.setRelaseConn(false);
			bMerchantOrderData.setConnection(connection);
			
			int res = bMerchantOrderData.batchExecuteUpdate(sqlstatement, list);
			
			logger.info("update row size is ["+res+"]");
			
			if(res==listMapData.size()){
				logger.info("update table b_merchant_order status succ");
			}else{
				logger.error("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw new ServiceException("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
			}
			//提交事务
			DataHandlerUtil.commitConn(connection);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			logger.error("buss service exec exception ,roll back data");
			DataHandlerUtil.rollbakConn(connection);
			throw new ServiceException("buss service exec exception ,roll back data",e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(sellExgCtrlData);
			DataHandlerUtil.releaseSource(bMerchantOrderData);
		}
		return context;
	}
}
