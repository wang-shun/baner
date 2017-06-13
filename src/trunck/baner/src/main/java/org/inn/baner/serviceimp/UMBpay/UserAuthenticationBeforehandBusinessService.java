package org.inn.baner.serviceimp.UMBpay;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BUserCardData;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 用户实名认证预处理类，当进行实名认证时，判断是用户注册时的实名认证还是支付交易时的实名认证，如果是支付交易时的实名认证则，从数据库中获取用户信息
 * 
 * @author tianguangzhao
 *
 */
public class UserAuthenticationBeforehandBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(UserAuthenticationBeforehandBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("PaymentAuthenticationBeforehandBusinessService start ! ");
		}

		//获取当前交易码，chs004和chs008都会走该程序,如果是chs008则为支付时的实名认证，此时的用户信息需要从数据库中获取
		String tranCode = context.getSDO().TRANCODE == null ? "" : context.getSDO().TRANCODE;

		if (logger.isDebugEnabled()) {
			logger.debug("get trancode success ! tranCode =  [" + tranCode + "] !");
		}

		if (tranCode.equalsIgnoreCase(BusinessConstantField.PAY_CERTIFIED_TRANCODE)) {
			// 如果是chs008则表示，该交易是支付时进行的实名认证，所以部分信息需要从数据库中获取
			try {
				getUserInfo(context);
			} catch (ServiceException e) {
				// update by tianguangzhao ,错误码改为“业务服务执行失败”
				logger.error("get user info error !", e);
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
				throw e;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("PaymentAuthenticationBeforehandBusinessService success ! ");
		}
		return context;
	}

	/**
	 * 查询活用信息表获取相应的信息,并注入到context
	 * 
	 * @param context
	 * @throws HandlerException
	 */
	private void getUserInfo(CommonContext context) throws ServiceException {
		BUserInfoData uid = new BUserInfoData();
		String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO, CommonContext.SCOPE_GLOBAL);
		// 用户标识号
		String purchaserid = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID, CommonContext.SCOPE_GLOBAL);
		
		List<Map<String, String>> list = null;
		try {
			list = uid.getUserInfo(merchantNo, purchaserid);
		} catch (HandlerException e) {
			//查询用户信息异常，则注入错误码(查询数据异常)，然后抛出异常
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if(list == null || list.size()==0){
			//如果获取到的用户信息为空，则表示数据库中数据有误，注入错误码（表中无有效数据），并返回！
			String message = "error ! cant get user info ! merchantNo =["+merchantNo+"] , purchaserid=["+purchaserid+"] !";
			logger.error(message);
			//update by tianguangzhao 20160524 错误码修改为“用户信息不存在”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511,context);
			throw new ServiceException(message);
		}else if(list.size() > 1){
			//如果获取到的用户信息条数大于1，则表示数据库中数据有误，注入错误码（数据异常），并返回！
			String message = "user info error ! merchantNo =["+merchantNo+"] , purchaserid=["+purchaserid+"] has more than one record !";
			logger.error(message);
			//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}else{
			//正常情况下查询结果只有一条！
			Map<String, String> map = list.get(0);
			String accountName = map.get("REALNAME");
			String certType = map.get("IDTYP");
			String certNo = map.get("IDNO");
			String mobileNo = map.get("TELNUM");
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NAME,accountName, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_TYPE,certType, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_NO, certNo,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_MOBILE_NO,mobileNo, CommonContext.SCOPE_GLOBAL);
		}
		// 获取bankname
		String accountNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO, CommonContext.SCOPE_GLOBAL);
		try {
			list = getUserCardInfo(merchantNo, purchaserid, accountNo);
		} catch (ServiceException e) {
			//如果查询抛出异常则，注入错误码（），然后向上游抛出异常！
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}

		if (list == null || list.size() == 0) {
			//如果查询到的数据为空，则表示数据库中信息异常，注入错误码（表中无有效数据），并返回
			String message = "error ! cant get user card info !";
			logger.error(message);
			//update by tianguangzhao 20160524 错误码 修改为 “用户卡信息不存在”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000512,context);
			throw new ServiceException(message);
		} else if(list.size() > 1){
			//如果获取到的用户卡信息条数大于1，则表示数据库中数据有误，注入错误码（数据异常），并返回！
			String message = "user info error ! merchantNo =["+merchantNo+"] , purchaserid=["+purchaserid+"] ，accountNo =["+accountNo+"] has more than one record !";
			logger.error(message);
			//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}else{
			//正常情况下，只会得到一笔记录
			Map<String, String> map = list.get(0);
			String bankName = map.get("BANKNAME");
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_BANK_NAME,bankName, CommonContext.SCOPE_GLOBAL);
		}
	}

	/**
	 * 获取用户卡信息
	 * 
	 * @param merchantNo
	 * @param purchaserid
	 * @param cardNum
	 * @return
	 * @throws ServiceException
	 */
	private List<Map<String, String>> getUserCardInfo(String merchantNo,String purchaserid, String cardNum) throws ServiceException {
		List<Map<String, String>>  list = null;
		BUserCardData ucd = new BUserCardData();
		try {
			list = ucd.getUserCardInfo(merchantNo, purchaserid, cardNum);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		return list;

	}
}
