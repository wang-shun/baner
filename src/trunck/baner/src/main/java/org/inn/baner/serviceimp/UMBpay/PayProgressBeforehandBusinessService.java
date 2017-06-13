package org.inn.baner.serviceimp.UMBpay;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BMerchantInfoData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.BUserCardData;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 进行支付交易时，预处理服务，从数据库中获取订单信息，用户信息等宝易互通所需信息，为组包做准备。
 * 
 * @author tianguangzhao
 *
 */
public class PayProgressBeforehandBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(PayProgressBeforehandBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug(" PayProgressBeforehandBusinessService  beginning...");
		}

		// 从数据库中获取相关信息
		try {
			// 查询订单信息获取金额，币种，购买者标识等信心，并更新订单信息，插入支付流水号，时间戳等信息
			handleOrderInfo(context);
			// 查询商户信息表
			getMerchertInfo(context);
			// 查询用户信息
			getUserInfo(context);
			
		} catch (Exception e) {
			//打印错误信息，并注入错误码“业务服务执行异常”
            logger.error("PayProgressBeforehandBusinessService error !",e);     
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw new ServiceException(e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(" PayProgressBeforehandBusinessService  success...");
		}
		return context;
	}

	/**
	 * 查询商户信息表，获得商户平台收款账户别名和协议号
	 * 
	 * @param context
	 * @return
	 * @throws ServiceException
	 */
	private void getMerchertInfo(CommonContext context) throws ServiceException {
		BMerchantInfoData mid = new BMerchantInfoData();
		//获取商户号 
		String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
                 CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,CommonContext.SCOPE_GLOBAL);
		//根据商户号，查询商户信息
		List<Map<String, String>> list = null ;
		
		try {
			list=mid.getBMerchantInfo(merchantNo);
		} catch (HandlerException e) {
			//查询数据异常，注入错误码（查询数据异常）
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}

		if (list == null || list.size() == 0) {
			//查询不到商户信息，注入错误码（表中无有效数据）
			String message = "can not get Merchert info ; merchantNo =["+merchantNo+"] !";
			logger.error(message);
			//update by tianguangzhao  20160524 将错误码改为“订单不存在”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501,context);
			throw new ServiceException(message);
		}else if(list.size() != 1){
			//同一个商户号只能查到一笔数据，如果查询到多条数据，则说明数据库的信息错误,注入错误码（数据异常）
			String message = " get Merchert info error , get "+list.size()+" records when merchantNo =["+merchantNo+"] !";
			logger.error(message);
			//update by tianguangzhao 2060525 改为在不注入错误码有由游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}else{
			// 正常情况下只能查询到一笔数据!
			Map<String, String> map = list.get(0);
			String merPlatAcctAlias = map.get("MERPLATACCTALIAS");
			String protocolNo = map.get("PROTOCOLNO");
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_MER_PLAT_ACCT_ALIAS,merPlatAcctAlias, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_PROTOCOL_NO,protocolNo, CommonContext.SCOPE_GLOBAL);
		}
	}

	/**
	 * 查询活用信息表获取相应的信息,并注入到context
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void getUserInfo(CommonContext context) throws ServiceException {
		BUserInfoData uid = new BUserInfoData();
		String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO, CommonContext.SCOPE_GLOBAL);
		// 用户标识
		String purchaserid = context.getFieldStr(BusinessMessageFormateConstant.CBPAY_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CBPAY_PURCHASER_ID, CommonContext.SCOPE_GLOBAL);
		List<Map<String, String>> list = null;
		try {
			list = uid.getUserInfo(merchantNo,purchaserid);
		} catch (HandlerException e) {
			//查询用户信息异常，注入错误码(查询数据异常),并抛出异常
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if (list == null || list.size() == 0) {
			//查询不到用户信息，注入错误码（表中无有效数据）
			String messsge ="can not get user info ! merchantNo=["+merchantNo+"] , purchaserid =["+purchaserid+"]";
			logger.error(messsge);
			//update by tianguangzhao 20160524 错误码改为“用户不存在”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511,context);
			throw new ServiceException(messsge);
		}else if(list.size() != 1){
			//商户号和用户号唯一标识一个用户，如果查出两条则表示数据库存在问题,注入错误码（ 数据异常）
			String messsge ="get user info error ! merchantNo=["+merchantNo+"] ,purchaserid =["+purchaserid+"] , listsize =["+list.size()+"] !";
			logger.error(messsge);
			//update by tiangunangzhao 20160525 改为不注入错误码 由上游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(messsge);
		}else{
			//正常情况下，只能查询到一笔记录
			Map<String, String> map = list.get(0);
			String accountName = map.get("REALNAME");
			String certType = map.get("IDTYP");
			String certNo = map.get("IDNO");
			String mobileNo = map.get("TELNUM");
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_ACCOUNT_NAME,accountName, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_CERT_TYPE,certType, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_CERT_NO, certNo,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_MOBILE_NO,mobileNo, CommonContext.SCOPE_GLOBAL);
			// 宝易互通只扣除对私账户的金额，所以账户类型写死"00"
		}
		// 获取bankname
		String accountNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0001_ACCOUNT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_CP0001_ACCOUNT_NO, CommonContext.SCOPE_GLOBAL);

		try {
			list = getUserCardInfo(merchantNo, purchaserid, accountNo);
		} catch (ServiceException e) {
			//查询卡信息异常，注入错误码（查询数据异常）
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if (list == null || list.size() == 0) {
			//查询不到卡信息，注入错误码（表中无有效数据）
			String message = "get user card info error ! merchantNo =["+merchantNo+"] ,purchaserid =["+purchaserid+"] ,accountNo =["+accountNo+"] !";
			logger.error(message);
			//update by tianguangzhao 20160524 修改错误码“用户卡信息不存在”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000512,context);
			throw new ServiceException(message);
		}else if( list.size() != 1){
			//查询到的用户卡信息多余一条，则说明表中数据有误，注入错误码（数据异常）
			String message = "get user card info error ! merchantNo =["+merchantNo+"] ,purchaserid =["+purchaserid+"] ,accountNo =["+accountNo+"], list size = ["+list.size()+"]";
			logger.error(message);
			//update by tianguangzhao 20160525 改为不注入错误码有上游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}else{
			// 如果数据正确则应该从数据库中取出一条数据。
			Map<String, String> map = list.get(0);
			String bankName = map.get("BANKNAME");
			String openProvince = map.get("OPENPROVINCE");
			String openCity = map.get("OPENCITY");
			String openName = map.get("OPENNAME");

			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_BANK_NAME,bankName, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_OPEN_PROVINCE,openProvince, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_OPEN_CITY,openCity, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_OPEN_NAME,openName, CommonContext.SCOPE_GLOBAL);

		}
	}

	/**
	 * 获取用户卡信息
	 * 
	 * @param merchantNo
	 * @param purchaserid
	 * @param cardNum
	 * @return
	 * @throws HandlerException
	 */
	private List<Map<String, String>> getUserCardInfo(String merchantNo,String purchaserid, String cardNum) throws ServiceException {
		List<Map<String, String>> list = null;
		BUserCardData ucd = new BUserCardData();
		try {
			list = ucd.getUserCardInfo(merchantNo, purchaserid, cardNum);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		return list;

	}

	/**
	 * 查询订单表，获取订单信息
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void getOrderInfo(String orderId, String merchantNo,String tranDate, CommonContext context) throws ServiceException{

		BMerchantOrderData bmod = new BMerchantOrderData();	
		//获取订单信息，最后一个参数为支付状态，用于校验该笔订单是否处于待支付状态
       List<Map<String,String>> list = null;
		try {
			list = bmod.getOrderInfoInStatus(merchantNo, orderId, tranDate,BusinessConstantField.ORDER_WF);
		} catch (HandlerException e) {
			//查询数据库错误，注入错误码（查询数据异常）
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" get order info list success ! list size =[" + (list == null ? "0" : list.size())+"] !");

		}
		if (list == null || list.size() == 0) {
			//查询不到订单信息,注入错误码（表中无有效数据）
			String message = "can not get order info ! merchantNo=["+merchantNo+"],orderId=["+orderId+"],tranDate=["+tranDate+"],orderStatus=["+BusinessConstantField.ORDER_WF+"] !";
			logger.error(message);
			//update by tianguangzhao 20160524 将错误码修改为”订单不存在“
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501,context);
			throw new ServiceException(message);
		} else if (list.size() != 1) {
			String message = " get order info error ! get order info size = [" + list.size() + "] merchantNo=["+merchantNo+"],orderId=["+orderId+"],tranDate=["+tranDate+"],orderStatus=["+BusinessConstantField.ORDER_WF+"] ！";
			//查询到的数据条数异常，注入错误码（数据异常）
			logger.error(message);
			//update by tiangunagzhao 20160525 改为不注入错误码 由上游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}else{
			// 订单支付时，根据订单号，订单日期和订单状态查询出来的结果，应该只有一条
			Map<String, String> map1 = list.get(0);
			String tranAmt = map1.get("ACCEPTANCEMOUNT");
			String curType = map1.get("ACCEPTANCYCURRENCY");
			String purchaserid = map1.get("PURCHASERID");
			
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_TRANAMT, tranAmt,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0001_CURTYPE, curType,CommonContext.SCOPE_GLOBAL);
			// 此处的purchaserId只是供下一步使用
			context.setFieldStr(BusinessMessageFormateConstant.CBPAY_PURCHASER_ID,purchaserid, CommonContext.SCOPE_GLOBAL);
		}
	}

	/**
	 * 更新订单表中的订单信息，插入支付流水号，支付时间等信息
	 * 
	 * @param orderId
	 * @param merchantNo
	 * @param tranDate
	 * @throws HandlerException
	 */
	private void updateOrderInfo(String orderId, String merchantNo,String tranDate, String payorderid, String paycard) throws ServiceException {
		BMerchantOrderData bmod = new BMerchantOrderData();
		// 获取平台账务日期,update by tianguangzhao 2016/4/12 此时不用保存支付时间，等支付交易完成后，再将支付完成时间插入
		//String paytime = PlatDateParamData.getPlatDate();
		int count = 0;
		try {
			bmod.getConnection();
			count = bmod.updateOrderInfo(orderId, merchantNo, tranDate,payorderid, paycard, BusinessConstantField.ORDER_WF);
		} catch (HandlerException e) {
			 throw new ServiceException(e);
		}finally{
		   DataHandlerUtil.releaseSource(bmod);
		}
		//如果逻辑争取则只应该更新一条数据
		if (count != 1) {
			String message ="update order info error ! update count =[" + count + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
	}

	/**
	 * 操作订单表，首先查询订单信息，然后更新订单状态
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void handleOrderInfo(CommonContext context) throws ServiceException {
		// 获取订单号
		String orderId = context.getFieldStr(BusinessMessageFormateConstant.CASH_ORDER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_ORDER_ID, CommonContext.SCOPE_GLOBAL);
		// 获取商户号
		String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO, CommonContext.SCOPE_GLOBAL);

		// 获取交易日期
		String tranDate = context.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE, CommonContext.SCOPE_GLOBAL);
		// 获取订单信息，将需要的信息，封装到context中
		getOrderInfo(orderId, merchantNo, tranDate, context);
		// 更新订单表中的订单状态
		// 流水号这个流水号，是我们生成的，在渠道特殊处理类中注入context，所以应该取UMB_BUSS_FLOW_NO
		String payorderid = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO, CommonContext.SCOPE_GLOBAL);
		// 付款卡号
		String paycard = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS006_ACCOUNT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS006_ACCOUNT_NO, CommonContext.SCOPE_GLOBAL);

		updateOrderInfo(orderId, merchantNo, tranDate, payorderid, paycard);
	}

}
