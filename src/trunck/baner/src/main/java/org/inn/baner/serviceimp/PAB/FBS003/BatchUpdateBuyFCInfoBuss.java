package org.inn.baner.serviceimp.PAB.FBS003;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.BuyFCStatusEnum;
import com.ztkx.cbpay.business.handledata.BBuyExgCtrlData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.db.DBUtil;

/**
 * 根据后端响应结果，批量修改交易状态
 * 1.更新购汇总控表
 * 2.更新商户订单表         购汇状态为服务方响应的状态
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class BatchUpdateBuyFCInfoBuss implements BusinessService {

	private Logger logger = Logger.getLogger(BatchUpdateBuyFCInfoBuss.class);
	
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		
		if(listMapData == null || listMapData.size()<=0){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			logger.error("business service exec exception ");
			throw new ServiceException("business service exec exception");
		}
		Connection connection = null;
		BBuyExgCtrlData bBuyExgCtrlData = null;			//购汇总控表b_buy_exg_ctrl操作句柄
		BMerchantOrderData bMerchantOrderData = null;	//商户订单表B_MERCHANT_ORDER操作句柄
		String client_exchange_rate = context.getFieldStr("CLIENT_EXCHANGE_RATE",CommonContext.SCOPE_GLOBAL);//成交汇率
		try {
			/**
			 * 1.更新购汇总控表
			 */
			logger.info("start update table b_buy_exg_ctrl ");
						
			bBuyExgCtrlData = new BBuyExgCtrlData();
			//初始化链接
			connection  = bBuyExgCtrlData.getConnection();
			connection.setAutoCommit(false);
			
			String sqlStatement = "update b_buy_exg_ctrl set register_date = ?, price = ?, client_exchange_rate = ?, "
					+ "discount_type = ?, dis_amt = ?, amt = ?, tran_amt = ?, sell_acct_no = ?, buy_acct_no = ?, "
					+ "sale_amount = ?, buy_amount = ?, RATE_TIME = ?, exceed_flag = ?, process_status = ?, process_msg = ? where buybatno = ? and buydate = ? ";
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("register_date", context.getFieldStr("REGISTER_DATE",CommonContext.SCOPE_GLOBAL));
			map.put("price", context.getFieldStr("PRICE",CommonContext.SCOPE_GLOBAL));	//市场价
			map.put("client_exchange_rate", client_exchange_rate);
			map.put("discount_type", context.getFieldStr("DISCOUNT_TYPE",CommonContext.SCOPE_GLOBAL));
			map.put("dis_amt", context.getFieldStr("DIS_AMT",CommonContext.SCOPE_GLOBAL));
			map.put("amt", context.getFieldStr("AMT",CommonContext.SCOPE_GLOBAL));
			map.put("tran_amt", context.getFieldStr("TRAN_AMT",CommonContext.SCOPE_GLOBAL));
			map.put("sell_acct_no", context.getFieldStr("SELL_ACCT_NO",CommonContext.SCOPE_GLOBAL));
			map.put("buy_acct_no", context.getFieldStr("BUY_ACCT_NO",CommonContext.SCOPE_GLOBAL));
			map.put("sale_amount", context.getFieldStr("SALE_AMOUNT",CommonContext.SCOPE_GLOBAL));
			map.put("buy_amount", context.getFieldStr("BUY_AMOUNT",CommonContext.SCOPE_GLOBAL));
			map.put("RATE_TIME", context.getFieldStr("DATE_TIME",CommonContext.SCOPE_GLOBAL));
			map.put("exceed_flag", context.getFieldStr("EXCEED_FLAG",CommonContext.SCOPE_GLOBAL));
			map.put("process_status", context.getFieldStr("PROCESS_STATUS",CommonContext.SCOPE_GLOBAL));
			map.put("process_msg", context.getFieldStr("PROCESS_MSG",CommonContext.SCOPE_GLOBAL));
			map.put("buybatno", context.getFieldStr("SEQ_NO",CommonContext.SCOPE_GLOBAL));
			map.put("buydate", context.getFieldStr("BUYDATE", CommonContext.SCOPE_GLOBAL));
			
			bBuyExgCtrlData.executeUpdate(sqlStatement, map);
			
			logger.info("update b_buy_exg_ctrl table succ");
			
			
			/**
			 * 3.更新商户订单表
			 */
			logger.info("start update table b_merchant_order status");
			String sqlstatement = "update b_merchant_order set  buybatstatus = ? , buybatrate = ? ,upordownmount=? where orderdate=? and BUYBATDATE = ? and BUYBATNO = ? and merchantid=? and orderid=?";
			
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
			
			//组织修改条件
			for (int i = 0; i < listMapData.size(); i++) {
				LinkedHashMap<String, String> updateData = new LinkedHashMap<String, String>();
				BMerchantOrder rowData = listMapData.get(i);
				String PROCESS_STATUS = context.getFieldStr("PROCESS_STATUS", CommonContext.SCOPE_GLOBAL);
				if(BuyFCStatusEnum.O.getStatus().equals(PROCESS_STATUS)){
					//待交割
					updateData.put("buybatstatus", BuyFCStatusEnum.O.getCode());
				}else if(BuyFCStatusEnum.T.getStatus().equals(PROCESS_STATUS)){
					updateData.put("buybatstatus", BuyFCStatusEnum.T.getCode());
				}else if(BuyFCStatusEnum.F.getStatus().equals(PROCESS_STATUS)){
					updateData.put("buybatstatus", BuyFCStatusEnum.F.getCode());
				}
				
				updateData.put("buybatrate", client_exchange_rate);
				//计算收损益金额
				BigDecimal accetpRate = rowData.getAcceptancerate();//	收单汇率
				BigDecimal clientRate = new BigDecimal(client_exchange_rate);	//成交汇率
				BigDecimal orderMount =rowData.getOrderamount();	//订单金额
				BigDecimal upordownMount = accetpRate.subtract(clientRate).multiply(orderMount);//损益金额
				updateData.put("upordownmount", upordownMount.toString());
				
				updateData.put("orderdate", rowData.getOrderdate());
				updateData.put("buybatdate",context.getFieldStr("BUYDATE", CommonContext.SCOPE_GLOBAL));;
				updateData.put("buybatno", context.getFieldStr("SEQ_NO", CommonContext.SCOPE_GLOBAL));
				updateData.put("merchantid", rowData.getMerchantid());
				updateData.put("orderid", rowData.getOrderid());
				
				list.add(updateData);
			}
			
			
			bMerchantOrderData = new BMerchantOrderData();
			bMerchantOrderData.setConnection(connection);
			
			int res = bMerchantOrderData.batchExecuteUpdate(sqlstatement, list);
			
			logger.info("update row size is ["+res+"]");
			
			if(res==listMapData.size()){
				logger.info("update table b_merchant_order status succ");
			}else{
				logger.error("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
//				throw new ServiceException("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
			}
			DBUtil.commit(connection);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("roll back data exception",e1);
			}
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("buss service exec exception ,roll back data");
			throw new ServiceException("buss service exec exception ,roll back data",e);
		}finally{
			//释放资源
			bBuyExgCtrlData.relaceResource();
			bMerchantOrderData.relaceResource();
		}
		return context;
	}
}
