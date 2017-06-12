package com.ztkx.cbpay.business.serviceimp.UMBpay;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BVerificationCode;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BVerificationCodeData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 验证短信服务，收到宝易互通返回的信息之后，进行判断和处理,暂时未用到
 * 
 * @author tianguangzhao
 *
 */
public class RegisterIssuedVerifySMSBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(RegisterIssuedVerifySMSBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("Issued sms register beginning...");
		}
		//此服务负责将收银台传入的报文进行登记，以便后续查询，是否使用待定   
		try {
			insertSMS(context);
		} catch (ServiceException e) {
			logger.error("insert sms request info error !", e);
			// 如果信息入库时抛出异常，注入错误码（插入数据异常）
			//update by tianguangzhao 20160525 修改错误码为“业务服务执行失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw e;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(" Issued sms register success ! ");
		}
		return context;
	}

	/**
	 * 将最新的短信信息保存到数据库中！
	 * 
	 * @param vcd
	 * @param context
	 * @throws Exception
	 */
	private void insertSMS(CommonContext context) throws ServiceException {
		BVerificationCodeData vcd = new BVerificationCodeData();
		BVerificationCode verificationCode = new BVerificationCode();
		// 用户标识号
		String mobileNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0032_MOBILE_NO,CommonContext.SCOPE_GLOBAL) == null ? default_value : context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0032_MOBILE_NO, CommonContext.SCOPE_GLOBAL);
		// 验证业务类型,1,短信；2，语音。如果不传参，默认为短信验证
		String verBizTyp = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS005_VER_TYP,CommonContext.SCOPE_GLOBAL) == null ? BVerificationCode.VALIDATE_SMS : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS005_VER_TYP, CommonContext.SCOPE_GLOBAL);
		// 短信验证渠道,获取不到默认为web
		String verChnl = context.getFieldStr(BusinessMessageFormateConstant.CASH_CHS005_CHANNEL_NO,CommonContext.SCOPE_GLOBAL) == null ? "WEB" : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_CHS005_CHANNEL_NO, CommonContext.SCOPE_GLOBAL);
		// 验证业务流水号(主键)
		String verJnlNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO, CommonContext.SCOPE_GLOBAL);
		// 验证码
		verificationCode.setMblNo(mobileNo);
		//第一次登记消息时，短信尚未生成所以不插入生成时间
//		verificationCode.setPrdDate(prdDate);
//		verificationCode.setPrdTime(prdTime);

		verificationCode.setVerBizTyp(verBizTyp);
		verificationCode.setVerChnl(verChnl);
		verificationCode.setVerJnlNo(verJnlNo);
		//将信息入库
		try {
			vcd.getConnection();
			vcd.insert(verificationCode);
		} catch (HandlerException e) {
			//插入数据库失败，注入错误码(插入数据异常)
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517,context);
			throw new ServiceException(e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(vcd);
		}

	}
}
