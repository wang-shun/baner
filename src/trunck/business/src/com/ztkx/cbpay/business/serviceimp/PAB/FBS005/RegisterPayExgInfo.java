package com.ztkx.cbpay.business.serviceimp.PAB.FBS005;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.bean.BSellExgCtrl;
import com.ztkx.cbpay.business.bean.BSellExgDet;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.BuyExgChannelEnum;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.BSellExgCtrlData;
import com.ztkx.cbpay.business.handledata.BSellExgDetData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.container.initdata.PlatParamsData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 5.登记付汇总控表和
 * 6.登记付汇明细表
 * 7.修改订单的付汇状态
 * @author zhangxiaoyun
 * 2016-3-24 15:16:09
 * <p>Company:ztkx</p>
 */
public class RegisterPayExgInfo implements BusinessService{

	Logger logger = Logger.getLogger(RegisterPayExgInfo.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		/**
		 * 获取数据
		 */
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		BigDecimal totalAmt =(BigDecimal)context.getObj("totalAmt", CommonContext.SCOPE_GLOBAL);//总金额
		String TradeSn = context.getFieldStr("TradeSn",CommonContext.SCOPE_GLOBAL);//付汇批次号
		String paymentdate = context.getFieldStr("paymentdate", CommonContext.SCOPE_GLOBAL);//付汇日期
		String paymenttime = context.getFieldStr("paymenttime", CommonContext.SCOPE_GLOBAL);//付汇批批次号
		Map<String,String> merchantInfo = (Map<String,String>)context.getObj("merchantInfo");//商户信息
		List<BSellExgDet> sellExgDetList = (List<BSellExgDet>)context.getObj("sellExgDetList");//付汇明细信息
		
		String currencyType = listMapData.get(0).getCurrency();//交易币种
		String remark = context.getFieldStr("REMARK", CommonContext.SCOPE_GLOBAL);//交易附言
				
		BSellExgCtrlData sellExgCtrl = null;
		BSellExgDetData sellExgDet = null;
		BMerchantOrderData orderHandler = null;
		Connection conn = null;
		
		try{
			//登记付汇总控表和
			BSellExgCtrl bSellExgCtrl = new BSellExgCtrl();
			bSellExgCtrl.setPaybatno(TradeSn);
			bSellExgCtrl.setPaydate(paymentdate);
			bSellExgCtrl.setPaytime(paymenttime);
			bSellExgCtrl.setQuotechnl(BuyExgChannelEnum.PAB.getCode());
			bSellExgCtrl.setRemitCcy(currencyType);//获取订单金额
			bSellExgCtrl.setRemitAmt(totalAmt);
			bSellExgCtrl.setCostType("OUR");	//付款人全部承担
			bSellExgCtrl.setPayerAcctNo(BServerParamData.getInstance().getPIAAccount(context.getSDO().serverId, currencyType).getParavalue());//付款人账号
			bSellExgCtrl.setPayerClientName(PlatParamsData.getInstance().getParamValue(BusinessConstantField.PLA_NAME));//付款人姓名
			bSellExgCtrl.setPayerAddress(PlatParamsData.getInstance().getParamValue(BusinessConstantField.PLA_ADDRESS));//付款人地址
			bSellExgCtrl.setPayeeAcctNo(merchantInfo.get("PAYEE_ACCT_NO"));//收款人账号
			bSellExgCtrl.setPayeeClientName(merchantInfo.get("MERCHANTNAME"));//收款人姓名
			bSellExgCtrl.setPayeeAddress(merchantInfo.get("ADDRESS"));//收款人地址
			bSellExgCtrl.setPayeeAcctOpenBranchId(merchantInfo.get("OPEN_BRANCH_ID"));//收款人开户行行号
			bSellExgCtrl.setFileName(context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL));
			bSellExgCtrl.setTot_num(listMapData.size());
//			bSellExgCtrl.setProcessStatus(BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_01);
			bSellExgCtrl.setRemark(remark);
			logger.info("start register B_SELL_EXG_CTRL table");
			sellExgCtrl = new BSellExgCtrlData();
			conn = sellExgCtrl.getConnection();
			DataHandlerUtil.setAutoCommit(conn, false);
			sellExgCtrl.setRelaseConn(false);
			sellExgCtrl.insertData(bSellExgCtrl);
			
			//登记付汇明细表
			logger.info("start register B_SELL_EXG_DET table");
			sellExgDet = new BSellExgDetData();
			sellExgDet.setConnection(conn);
			sellExgDet.setRelaseConn(false);
			sellExgDet.batchInsertData(sellExgDetList);
			
			/**
			 * 3.更新商户订单表
			 * 修改订单的付汇状态
			 */
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
			String sqlstatement = "update b_merchant_order set  paybatstatus = ?,  paybatno = ?, paybatdate = ?  where  merchantid = ?   and orderid = ? and orderdate = ? and paybatstatus=?";
			for (BMerchantOrder order : listMapData) {
				LinkedHashMap<String, String> rowData = new LinkedHashMap<String, String>();	//每一条更新条件
				rowData.put("paybatstatus", BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_01);
				rowData.put("paybatno", TradeSn);
				rowData.put("paybatdate", paymentdate);
				rowData.put("merchantid", order.getMerchantid());
				rowData.put("orderid", order.getOrderid());
				rowData.put("orderdate",order.getOrderdate());
				rowData.put("paybatstatus_where", BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_00);
				list.add(rowData);
			}
			logger.info("start update table b_merchant_order status");
			orderHandler = new BMerchantOrderData();
			orderHandler.setConnection(conn);
			orderHandler.setRelaseConn(false);
			
			int res = orderHandler.batchExecuteUpdate(sqlstatement, list);
			if(res==listMapData.size()){
				logger.info("update table b_merchant_order status succ");
			}else{
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				logger.error("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
				throw new ServiceException("update table b_merchant_order status faile . need update data row is ["+listMapData.size()+"] actual update data row is ["+res+"]");
			}
			DataHandlerUtil.commitConn(conn);
		}catch(ServiceException e){
			logger.error("business service exe  fail",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			DataHandlerUtil.rollbakConn(conn);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(sellExgCtrl);
			DataHandlerUtil.releaseSource(sellExgDet);
			DataHandlerUtil.releaseSource(orderHandler);
		}
		return context;
	}
}
