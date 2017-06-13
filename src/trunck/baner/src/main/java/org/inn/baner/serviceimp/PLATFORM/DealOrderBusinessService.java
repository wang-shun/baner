package org.inn.baner.serviceimp.PLATFORM;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.AccountTransferStatusEnum;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

public class DealOrderBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(DealOrderBusinessService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("DealOrderBusinessService is begin ..");
		BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
		bMerchantOrderData.getConnection();
		BMerchantOrder bMerchantOrderBean = new BMerchantOrder();
		try{
			bMerchantOrderBean.setMerchantid(context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrderid(context.getFieldStr("orderId", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrdertime(context.getFieldStr("tranTime", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setClientip(context.getFieldStr("clientIP", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setPurchaserid(context.getFieldStr("purchaserId", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setValidunit(context.getFieldStr("validUnit", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setValidnum(context.getFieldStr("validNum", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrderdesc(context.getFieldStr("orderDesc", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrderamount(new BigDecimal(context.getFieldStr("totalAmount", CommonContext.SCOPE_GLOBAL)));
			bMerchantOrderBean.setCurrency(context.getFieldStr("currency", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setPagereturnurl(context.getFieldStr("pageReturnUrl", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOfflinenotifyurl(context.getFieldStr("offlineNotifyUrl", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrderstatus(BusinessConstantField.ORDER_WF);
			bMerchantOrderBean.setAcceptancycurrency(context.getFieldStr("acceptancyCurrency", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setUserid(context.getFieldStr("userid", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setOrderdate(context.getFieldStr("tranDate", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setBuybatstatus(BusinessConstantField.PURCHASESTATUS_00);
			bMerchantOrderBean.setPaybatstatus(BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_00);
			bMerchantOrderBean.setTradeType(context.getFieldStr("tradeType", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setTradeCode(context.getFieldStr("tradeCode", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setIsRef(context.getFieldStr("isRef", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setProductName(context.getFieldStr("productName", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setProductId(context.getFieldStr("productId", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setProductDesc(context.getFieldStr("productDesc", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setShowUrl(context.getFieldStr("showUrl", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setInvoiceNo(context.getFieldStr("invoiceNo", CommonContext.SCOPE_GLOBAL));
			bMerchantOrderBean.setRcvDate(TimeUtil.getCurrentTime("yyyyMMdd"));
			bMerchantOrderBean.setRcvTime(TimeUtil.getCurrentTime("HHmmss"));
			bMerchantOrderBean.setMountchangestatus(AccountTransferStatusEnum.INITSTATUS.getStatus());	//账户划转初始状态
			if(context.getErrorCode()==null){//判断上一步有没有报错，报错的话下面参数无值
				bMerchantOrderBean.setMerchantpoundage(new BigDecimal(context.getFieldStr("merchantPoundage", CommonContext.SCOPE_GLOBAL)));
				bMerchantOrderBean.setAcceptancemount(new BigDecimal((String)context.getObj("acceptanceMount", CommonContext.SCOPE_GLOBAL)));
				bMerchantOrderBean.setAcceptancerate(new BigDecimal((String)context.getObj("acceptanceRate", CommonContext.SCOPE_GLOBAL)));
				bMerchantOrderBean.setPurchaseamount(new BigDecimal(context.getFieldStr("purchaseMount", CommonContext.SCOPE_GLOBAL)));
			}
			bMerchantOrderData.insertData(bMerchantOrderBean);
			DataHandlerUtil.commitConn(bMerchantOrderData.connection);
			logger.info("DealOrderBusinessService commit succ");
		}catch(Exception e){
			logger.error("insert order is error", e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bMerchantOrderData);
		}
		logger.info("DealOrderBusinessService is succ");
		return context;
	}

}
