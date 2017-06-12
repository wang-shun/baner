package com.ztkx.cbpay.business.serviceimp.PAB;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.AccountTransferStatusEnum;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;

public class PABBusiUtil {
	static Logger logger = Logger.getLogger(PABBusiUtil.class);
	
	/**
	 * 获取平安银行批次号
	 * @return
	 */
	public static String getTradeSn(){
		String EX_SEQNOPRE = PlatDateParamData.getInstance().getPlatDate();
		String seqPre = "ms"+EX_SEQNOPRE;
		String TradeSn = seqPre+FlowNoPoolManager.getInstance().getSequence();
		return TradeSn;
	}
	
	/**
	 * 检查订单购汇状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void checkBuyExgStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String purchasestatus = order.getBuybatstatus();
		if(purchasestatus.equals(BusinessConstantField.PURCHASESTATUS_00)){
			logger.info("order Foreign exchange purchasing  status is ["+purchasestatus+"] status info is 未购汇");
		}else if(purchasestatus.equals(BusinessConstantField.PURCHASESTATUS_05)){
			logger.info("order Foreign exchange purchasing  status is ["+purchasestatus+"] status info is 上一次购汇失败");
		}
		else{
			//订单状态错误不能完成购汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000502, context);
			logger.error("order already start purchase foreign currency,can not repeat purchase foreign currency");
			throw new ServiceException("order already start purchase foreign currency,can not repeat purchase foreign currency");
		}
	}
	
	/**
	 * 付汇的时候检查订单购汇状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void payExgcheckBuyExgStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String purchasestatus = order.getBuybatstatus();
		if(purchasestatus.equals(BusinessConstantField.PURCHASESTATUS_06)){
			logger.info("order Foreign exchange purchasing  status is ["+purchasestatus+"] status info is 购汇成功");
		}else{
			//订单状态错误不能完成付汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000503, context);
			logger.error("order buy exg not finish");
			throw new ServiceException("order buy exg not finish");
		}
	}
	
	/**
	 * 检查订单付汇状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void checkPayExgStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String purchasestatus = order.getPaybatstatus();
		if(purchasestatus.equals(BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_00)){
			logger.info("order Foreign exchange pay  status is ["+purchasestatus+"] status info is 未付汇");
		}else{
			//订单状态错误不能完成购汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000504, context);
			logger.error("order already start pay foreign currency,can not repeat pay foreign currency");
			throw new ServiceException("order already start pay foreign currency,can not repeat pay foreign currency");
		}
	}

	/**
	 * 购汇前检查订单账户划转状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void buyExgCheckAcTStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String atStatus = order.getMountchangestatus();
		if(atStatus.equals(AccountTransferStatusEnum.BUYBEFSUCC.getStatus())){
			logger.info("order account transfer status is ["+atStatus+"] status info is "+AccountTransferStatusEnum.BUYBEFSUCC.getStatusDesc());
		}else{
			//订单状态错误不能完成购汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000505, context);
			logger.error("order status error ");
			throw new ServiceException("order Didn't finish the payment");
		}
	}
	
	/**
	 * 付汇前检查订单账户划转状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void payExgCheckAcTStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String atStatus = order.getMountchangestatus();
		if(atStatus.equals(AccountTransferStatusEnum.PAYBEFSUCC.getStatus())){
			logger.info("order account transfer status is ["+atStatus+"] status info is "+AccountTransferStatusEnum.PAYBEFSUCC.getStatusDesc());
		}else{
			//订单状态错误不能完成付汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000506, context);
			logger.error("order status error ");
			throw new ServiceException("order Didn't finish the payment");
		}
	}

	/**
	 * 检查订单支付状态
	 * @param context
	 * @param order
	 * @throws ServiceException
	 */
	public static void checkOrderPayStatus(CommonContext context, BMerchantOrder order)
			throws ServiceException {
		String payStatus = order.getOrderstatus();
		if (payStatus.equals(BusinessConstantField.ORDER_PD)) {
			logger.info("order status is [" + payStatus+ "] status info is 支付完成");
		} else {
			// 订单状态错误不能完成购汇
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000507, context);
			logger.error("order Didn't finish the payment");
			throw new ServiceException("order Didn't finish the payment");
		}
	}
}
