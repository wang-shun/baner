package org.inn.baner.serviceimp.UMBpay;

import java.sql.Connection;

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
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 用户实名认证交易，根据宝易互通返回的认证结果，更新状态
 * 
 * @tianguangzhao
 *
 */
public class UserAuthenticationUpdateBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(UserAuthenticationUpdateBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("update user info start ! ");
		}
		//update by tianguangzhao 20160612 
		//首先判断是否存在系统错误如果存在，则将数据库中的信息改为验证失败
		if(context.getErrorCode() != null){
			//更新用户信息和卡信息
			updateUserInfo(context, "N");
		}

		// 获取重要字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String tranState = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0030_TRAN_STATE,CommonContext.SCOPE_GLOBAL); // 返回信息
		String respCode = context.getResponseCode(); // 响应吗
		// 我们的状态码，
		String myTransState = "";
		//记录最终状态，“Y”表示成功，“N”表示失败，只用于区分验证结果！
		String resultStatus ="";

		try {
			// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则注入错误码并直接返回!
			if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
				logger.error(" msgType error msgType is [" + msgType + "] ！");
				//update by tianguangzhao 20160524 错误码修改为 “响应报文类型错误”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
				return context;
			}
			// PLA000000表示交易成功，其他码交易失败
			if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
				// 如果响应码为不为BASE_PLA000000，则表示该笔交易处理失败，直接返回，不再进行后续操作
				logger.error(" trade failed ! responese code is ["+respCode+"] !");
				return context;
			}
			//update by tianguangzhao 2016/4/28 除了交易成功之外，其他状态都为转为失败
			if (tranState.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
				//交易成功
				resultStatus ="Y";
				myTransState = BusinessConstantField.TRADING_SUCCESS;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
				//交易失败
				resultStatus ="N";
				myTransState = BusinessConstantField.TRADING_FAILED;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_INIT)) {
				//交易初始化
				//myTransState = BusinessConstantField.TRADING_INIT;
				resultStatus ="N";
				myTransState = BusinessConstantField.TRADING_FAILED;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
				//交易进行中
				//myTransState = BusinessConstantField.TRADING_PROCESSING;
				resultStatus ="N";
				myTransState = BusinessConstantField.TRADING_FAILED;
			} else {
				//如果宝易互通返回的交易状态码，和预先给出的不一致，则表示宝易互通的响应报文异常，注入错误码（响应报文错误），并返回
				//update by tianguangzhao 20160524 错误码修改为 “业务服务执行失败”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				logger.error(" tranState error ! tranState = [" + tranState + "] !");
				return context;
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("final status = [" + myTransState + "] ,resultStatus = ["+resultStatus+"] !");
			}
			
			// 将自己的状态码注入context中
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS004_TRAN_STATE, myTransState,CommonContext.SCOPE_GLOBAL);
			
			// 如果是用户支付时发起的实名认证，而且认证成功，由于数据库中用户信息和卡信息状态都是正常，所以不用再次更新
			if (myTransState.equals(BusinessConstantField.TRADING_SUCCESS)&& context.getSDO().TRANCODE.equals(BusinessConstantField.PAY_CERTIFIED_TRANCODE)) {
				if(logger.isDebugEnabled()){
					logger.debug(" trancode = chs008 , validate success ! ");
				}
				return context;
			}
			
			//更新用户信息和卡信息
			updateUserInfo(context, resultStatus);

		} catch (Exception e) {
			// 抓到异常，却没有得到错误信息，表示发生了预料外的异常！注入错误码（业务服务执行异常），
			logger.error("update user info and user card info error ! ", e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw new ServiceException(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("update userinfo success ! ");
		}
		
		return context;
	}

	/**
	 * 根据商户号和用户标识，更新用户信息表
	 * 
	 * @param merchantNo
	 * @param purchaserid
	 * @throws ServiceException
	 */
	private int updateUserStatus(BUserInfoData bUserInfoData,String merchantNo, String purchaserid,String usrSts, String realnmflg) throws ServiceException {
	
		int result = 0 ;
		try {
			result = bUserInfoData.updateUserStatus(merchantNo, purchaserid,usrSts, realnmflg);
		} catch (HandlerException e) {
           throw new ServiceException(e);
		}
		// 如果逻辑争取则只应该更新一条数据
		if (result != 1) {
			String message = "update user status error ! update count =[" + result + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
		return result;
	}

	/**
	 * 更新数据库中的用户卡状态，置为可用
	 * 
	 * @param merchantNo
	 * @param purchaserid
	 * @param accountNo
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	private int updateUserCardStatus(BUserCardData bUserCardData,String merchantNo, String purchaserid,String accountNo, String status) throws ServiceException {
		
		int result = 0 ;
		try {
			result = bUserCardData.updateUserCardStatus(merchantNo, purchaserid,accountNo, status);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		//如果逻辑争取则只应该更新一条数据
		if (result != 1) {
			String message = "update user card status error ! update count =[" + result + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
		return result;
	}

	/**
	 * 更新用户状态和卡状态
	 * 
	 * @param context
	 * @param tranStatus
	 *            状态标识
	 * @throws ServiceException
	 */
	private void updateUserInfo(CommonContext context, String tranStatus)throws ServiceException {

		// 获取商户id
		String merchantNo = context.getFieldStr(
				BusinessMessageFormateConstant.UMB_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context.getFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO,
						CommonContext.SCOPE_GLOBAL);
		// 获取用户id
		String purchaserid = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value
				: context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_PURCHASER_ID,CommonContext.SCOPE_GLOBAL);
		// 获取用户账号
		String accountNo = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS004_ACCOUNT_NO,
						CommonContext.SCOPE_GLOBAL);
        //用户状态
		String usrSts = "";
		//用户实名认证标示
		String realnmflg = "";
		//卡状态
		String cardStatus = "";

		if (tranStatus.equals("Y")) {
			usrSts = BUserInfo.USER_STATUS_NORMAL;
			realnmflg = BUserInfo.VERIFY_PASS;
			cardStatus = BUserCard.VALID_NORMAL;
		} else {
			usrSts = BUserInfo.USER_STATUS_DISABLE;
			realnmflg = BUserInfo.VERIFY_FAILED;
			cardStatus = BUserCard.VALID_DISABLE;
		}
		
		BUserInfoData bUserInfoData = new BUserInfoData();
		BUserCardData bUserCardData = new BUserCardData();
		//开启事务
		Connection conn = bUserInfoData.getConnection();
		bUserCardData.setConnection(conn);
		//设置自动提交为false
		DataHandlerUtil.setAutoCommit(conn, false);
			
		try {
			if (!context.getSDO().TRANCODE.equals(BusinessConstantField.PAY_CERTIFIED_TRANCODE)) {
				// 用户注册时发起的实名认证，在宝易互通返回结果后，需要更新用户表中数据的状态
				updateUserStatus(bUserInfoData,merchantNo, purchaserid, usrSts, realnmflg);
			}
			updateUserCardStatus(bUserCardData,merchantNo, purchaserid, accountNo,cardStatus);
			//手动提交事务！
			DataHandlerUtil.commitConn(conn);
			
		} catch (ServiceException e) {
		  //捕获异常后，首先回滚事务，然后注入错误码（），并抛出异常给上游处理
			DataHandlerUtil.rollbakConn(conn);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw e;
		}finally{
			//调用工具类释放资源！
			DataHandlerUtil.releaseSource(bUserCardData);
			DataHandlerUtil.releaseSource(bUserInfoData);
		}
	}
}
