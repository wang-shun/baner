package com.ztkx.cbpay.business.serviceimp.UMBpay;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BUserCard;
import com.ztkx.cbpay.business.bean.BUserInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BUserCardData;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 用户实名认证交易，当进行用户实名认证时，则将该用户信息和卡信息入库保存，用户状态置为初始化，卡信息置为不可用。支付时发起则不进行更新
 * 
 * @author uisfte
 *
 */
public class UserAuthenticationRegisterBusinessService implements
		BusinessService {

	private Logger logger = Logger.getLogger(UserAuthenticationRegisterBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("UserAuthenticationRegisterBusinessService start ! ");
		}

		// 首先获取trancode，用户注册时的实名认证和用户支付时的实名认证，必须分开处理
		String tranCode = context.getSDO().TRANCODE == null ? "":context.getSDO().TRANCODE;
		if (logger.isDebugEnabled()) {
			logger.debug(" get tranCode success ! tranCode =  [" + tranCode + "] !");
		}
		if (tranCode.equalsIgnoreCase(BusinessConstantField.PAY_CERTIFIED_TRANCODE)) {
			// 如果是用户支付时的实名认证，则不需要登记，立刻返回即可
			return context;
		}
        
		String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
					CommonContext.SCOPE_GLOBAL) == null ? default_value
					: context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,CommonContext.SCOPE_GLOBAL);
		
		String purchaserid = context.getFieldStr(
					BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
					CommonContext.SCOPE_GLOBAL) == null ? default_value
					: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,CommonContext.SCOPE_GLOBAL);

	    String cardNo = context.getFieldStr(
					BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,
					CommonContext.SCOPE_GLOBAL) == null ? default_value
					: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,CommonContext.SCOPE_GLOBAL);

		// 检测用户信息是否存在
		boolean flagUser = true;
		// 检测用户卡信息是否存在
		String cardStatus = "";
		try {
			try {
				flagUser = checkUserInfoExist(merchantNo, purchaserid);
				cardStatus = checkUserCardStatus(merchantNo, purchaserid, cardNo);
			} catch (ServiceException e) {
				// 如果查询数据数据时异常，则注入错误码（查询数据异常），将异常抛给上游!
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("checkUserCard result is [" + (cardStatus == null ? "": cardStatus) + "] ， checkUserInfoExist result is [" + flagUser + "] ！");
			}

			try {
				updateDatabase(context, flagUser, cardStatus);
			} catch (ServiceException e) {
				//注入错误码“更新数据异常”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw e;
			}
		} catch (Exception e) {
			//注入错误码“业务服务执行失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("UserAuthenticationRegisterBusinessService error !", e);
			throw new ServiceException(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("UserAuthenticationRegisterBusinessService success ! ");
		}
		return context;
	}

	/**
	 * 将用户信息保存到数据库中 b_user_info 表
	 * 
	 * @param context
	 * @throws SQLException
	 * @throws ServiceException
	 */
	private void insertUserInfo(BUserInfoData uid, CommonContext context)
			throws ServiceException {
		BUserInfo ui = new BUserInfo();
		String merchantid = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,CommonContext.SCOPE_GLOBAL);
		String purchaserid = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,CommonContext.SCOPE_GLOBAL);
		String telnum = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_MOBILE_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_MOBILE_NO,CommonContext.SCOPE_GLOBAL);
		String nickName = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_NICK_NAME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_NICK_NAME,CommonContext.SCOPE_GLOBAL);
		String realName = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NAME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NAME,CommonContext.SCOPE_GLOBAL);
		String idTyp = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_CERT_TYPE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_TYPE,CommonContext.SCOPE_GLOBAL);
		String idNo = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_CERT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_NO,CommonContext.SCOPE_GLOBAL);
		//update by tianguangzhao 2016/4/29  登记时间和登记日期改为获取平台日期和时间！
//		String regDate = context.getFieldStr(
//				BusinessMessageFormateConstant.CASH_TRAN_DATE,
//				CommonContext.SCOPE_GLOBAL) == null ? default_value : 
//				context.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE,CommonContext.SCOPE_GLOBAL);
//		String regTime = context.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_TIME,
//				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
//				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_TIME,CommonContext.SCOPE_GLOBAL);
		String regDate =TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE);;
		String regTime =TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE);;
		String regChnl = "web";
		String email = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_EMAIL,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_EMAIL,CommonContext.SCOPE_GLOBAL);
		ui.setNickName(nickName);
		ui.setMerchantid(merchantid);
		ui.setPurchaserid(purchaserid);
		ui.setRealName(realName);
		ui.setIdTyp(idTyp);
		ui.setIdNo(idNo);
		ui.setRegDate(regDate);
		ui.setRegTime(regTime);
		ui.setRegChnl(regChnl);
		ui.setEmail(email);
		ui.setTelnum(telnum);
		try {
			uid.insert(ui);
		} catch (HandlerException e) {
			//注入错误码“插入数据失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517, context);
			throw new ServiceException(e);
		}

	}

	/**
	 * 将用户的卡信息查询用户卡信息表
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void insertUserCardInfo(BUserCardData ucd, CommonContext context)
			throws ServiceException {
		BUserCard uc = new BUserCard();
		String merchantid = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,CommonContext.SCOPE_GLOBAL);
		String purchaserId = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,CommonContext.SCOPE_GLOBAL);
		String cardid = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,CommonContext.SCOPE_GLOBAL);
		String bankName = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_BANK_NAME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_BANK_NAME,CommonContext.SCOPE_GLOBAL);
		uc.setMerchantid(merchantid);
		uc.setPurchaserId(purchaserId);
		uc.setCardNo(cardid);
		uc.setBankName(bankName);
		try {
			ucd.insertUserCard(uc);
		} catch (HandlerException e) {
			//注入错误码“插入数据失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517, context);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询数据库中是否已经存在该客户的信息
	 * 
	 * @param merchantNo
	 *            商户号
	 * @param purchaserId
	 *            用户id
	 * @return
	 * @throws ServiceException
	 */
	private boolean checkUserInfoExist(String merchantNo, String purchaserId) throws ServiceException {
		BUserInfoData uid = new BUserInfoData();
		boolean flag = false;
		try {
			flag = uid.checkInfoExist(merchantNo, purchaserId);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		return flag;
	}

	/**
	 * 查询用户绑定的卡信息表中，是否存在该数据
	 * 
	 * @param merchantNo
	 * @param purchaserId
	 * @param acctNo
	 * @return
	 * @throws ServiceException
	 */
	private String checkUserCardStatus(String merchantNo, String purchaserId, String acctNo) throws ServiceException {
		BUserCardData ucd = new BUserCardData();
		String result = "";
		try {
			result = ucd.checkUserCardStatus(merchantNo, purchaserId, acctNo);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	/**
	 * 操作数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	private void updateDatabase(CommonContext context, boolean flagUser,String cardStatus) throws ServiceException {

		BUserInfoData uid = new BUserInfoData();
		BUserCardData ucd = new BUserCardData();
		//开启事务
		Connection connection = uid.getConnection();
    	ucd.setConnection(connection);

		// 关闭自动提交
		DataHandlerUtil.setAutoCommit(connection, false);

		try {
			// 如果用户信息不存在，则插入用户信息
			if (!flagUser) {
				insertUserInfo(uid, context);
			} else {
				// 如果用户存在，更新用户表中的数据,所以如果用户再次注册有可能是因为信息变更，此时应该允许其变更信息
				updateUserInfo(uid, context);
			}

			// 如果卡信息状态为null，则表示这张卡没有注册过，直接将数据插入数据库中
			if (cardStatus == null) {
				insertUserCardInfo(ucd, context);
			} else if (cardStatus.equals(BUserCard.VALID_DISABLE)) {
				// 此种情况下表示用户要重新绑定以前已经注销的卡
				logger.info("this card has already exits !, but status is disabled !");
			} else if (cardStatus.equals(BUserCard.VALID_NORMAL)) {
				// 如果用户注册已经注册过的卡有可能是因为用户需要更新某些信息。所以此时不能抛出异常
				String message = " this card is in use ! ";
				logger.warn(message);
				// throw new HandlerException(message);
			} else {
				// 正常情况下用户信息只应该时可用，不可用，没有数据，不会出现第四种情况
				String message = " user card status error ! status = [" + cardStatus + "] !";
				logger.error(message);
				throw new ServiceException(message);
			}
			//手动提交事务
			DataHandlerUtil.commitConn(connection);
		} catch (ServiceException e) {
			//发生异常首先回滚事务，然后注入错误码，再抛出异常给上游处理!
			DataHandlerUtil.rollbakConn(connection);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517,context);
			throw e;
		} finally {
			//调用工具类释放资源！
			DataHandlerUtil.releaseSource(ucd);
			DataHandlerUtil.releaseSource(uid);
		}

	}

	/**
	 * 将用户信息更新到到数据库中 b_user_info 表
	 * 
	 * @param context
	 * @throws SQLException
	 * @throws ServiceException
	 */
	private void updateUserInfo(BUserInfoData uid, CommonContext context) throws ServiceException {
		BUserInfo ui = new BUserInfo();
		String merchantid = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,
						CommonContext.SCOPE_GLOBAL);
		String purchaserid = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,CommonContext.SCOPE_GLOBAL);
		String telnum = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_MOBILE_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_MOBILE_NO,CommonContext.SCOPE_GLOBAL);
		String nickName = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_NICK_NAME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_NICK_NAME,CommonContext.SCOPE_GLOBAL);
		String realName = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NAME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NAME,CommonContext.SCOPE_GLOBAL);
		String idTyp = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_CERT_TYPE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_TYPE,CommonContext.SCOPE_GLOBAL);
		String idNo = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_CERT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_CERT_NO,CommonContext.SCOPE_GLOBAL);
		String regDate = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_TRAN_DATE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE,CommonContext.SCOPE_GLOBAL);
		String regTime = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_TRAN_TIME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_TIME,CommonContext.SCOPE_GLOBAL);
	    //验证渠道，目前只有web端
		String regChnl = "web";
		String email = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_EMAIL,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_EMAIL,CommonContext.SCOPE_GLOBAL);
		
		ui.setNickName(nickName);
		ui.setMerchantid(merchantid);
		ui.setPurchaserid(purchaserid);
		ui.setRealName(realName);
		ui.setIdTyp(idTyp);
		ui.setIdNo(idNo);
		ui.setRegDate(regDate);
		ui.setRegTime(regTime);
		ui.setRegChnl(regChnl);
		ui.setEmail(email);
		ui.setTelnum(telnum);

		int count = 0;
		try {
			count = uid.updateUserInfo(ui);
		} catch (HandlerException e) {
			//注入错误码“更新数据异常”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw new ServiceException(e);
		}
		//判断更新条数，
		if (count != 1) {
			String message = "updateUserInfo error ! update count =[" + count + "] !";
			logger.error(message);
			//注入错误码“更新数据异常”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw new ServiceException(message);
		}
	}

}
