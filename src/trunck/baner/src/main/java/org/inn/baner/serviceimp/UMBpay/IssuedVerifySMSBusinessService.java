package org.inn.baner.serviceimp.UMBpay;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BVerificationCodeData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 验证短信服务，收到宝易互通返回的信息之后，进行判断和处理,更新数据库中的状态和时间
 * 
 * @author tianguangzhao
 *
 */
public class IssuedVerifySMSBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(IssuedVerifySMSBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Issued sms serive beginning ...");
		}
		// 取关键字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String respCode = context.getResponseCode(); // 响应码
		
//		判断必要信息是否存在
//		update by tianguangzhao 20160524 是否为空的判断交给 拆包服务完成 
//		if (msgType == null || msgType.trim().equals("") || respCode == null || respCode.trim().equals("")) {
//			//缺少必要信息，注入错误码(BUSS_PLA000511-响应报文错误)
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
//			return context;
//		}
		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			logger.error(" msgType error msgType is [" + msgType + "] !");
			//响应码字段的值错误，注入错误码(BUSS_PLA000511-响应报文错误)
			//update by tianguangzhao 20160524 错误码改为“响应报文类型错误”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("get message success ! respCode = [" + respCode + "] , msgType = [" + msgType + "] !");
		}
		// PLA000000表示交易成功，其他码交易失败
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			// 如果响应吗为失败，则直接返回，不再进行后续操作
			logger.error(" trade failed ! responese code is ["+respCode+"] ! ");
			return context;
		} else {
			//获取验证流水号
			String flowNo  = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL);
			try {
				updateStatusByFlowNo(flowNo,context);
			} catch (ServiceException e) {
				// 如果更新数据库信息时抛出异常，注入错误码（BUSS_PLA000509-更新数据库错误）
				//update by tianguangzhao 20160525 错误码改为“业务服务执行异常”,并打印错误信息!
				logger.error("IssuedVerifySMSBusinessService error ! ",e);
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				throw e;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" Issued sms serive success ! ");
		}
		return context;
	}

	/**
	 *更新数据库中的交易信息状态
	 * 
	 * @param vcd
	 * @param mblNo
	 * @param verBizTyp
	 * @param context
	 * @throws Exception
	 */
	private void updateStatusByFlowNo(String flowNo,CommonContext context) throws ServiceException {
		int result = 0;
		BVerificationCodeData bVerificationCodeData = new BVerificationCodeData();
		try {
			bVerificationCodeData.getConnection();
			result = bVerificationCodeData.updateStatusByFlowNo(flowNo);
		} catch (HandlerException e) {
			//update by tianguanzhao 2010525 并插入错误码“更新数据库失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
			throw new ServiceException(e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bVerificationCodeData);
		}
		//判断更新条数，如果更新条数不为1，则表示数据出错，抛出异常！
		if (result != 1) {
			String message ="update sms info error ! update count =[" + result + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
	}
}
