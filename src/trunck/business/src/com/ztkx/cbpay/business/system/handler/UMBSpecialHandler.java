package com.ztkx.cbpay.business.system.handler;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initdata.ServerInfoData;
import com.ztkx.cbpay.container.javabean.ServerInfo;
import com.ztkx.cbpay.container.system.SystemHandler;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.container.util.MessageUtil;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 宝易互通服务系统特殊处理类
 * 
 * @author zhangxiaoyun 2016年2月2日 上午11:29:54
 *         <p>
 *         Company:ztkx
 *         </p>
 */
public class UMBSpecialHandler implements SystemHandler {
	private Logger logger = Logger.getLogger(UMBSpecialHandler.class);

	@Override
	public CommonContext beforeHandler(CommonContext context) throws HandlerException{
		logger.info("start exec UMBSpecialHandler beforeHandler");

		// 注入请求地址,宝易互通不需要，联调时注意取消。update by tianguangzhao  此方法用于测试时
//		String serverCode = context.getSDO().serverCode;
//		String url_params = serverCode + ".do";
//		context.setFieldStr(ContainerConstantField.URL_PARAMS, url_params);

		try {
			// 注入公共报文头流水号
			String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.CASH_MERCHANT_NO,CommonContext.SCOPE_GLOBAL) == null ? "" : context.getFieldStr(
					BusinessMessageFormateConstant.CASH_MERCHANT_NO, CommonContext.SCOPE_GLOBAL);
			//收银台传的商户号和宝易互通接口中的字段并不相同，此处需要转换
			context.setFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO,merchantNo, CommonContext.SCOPE_GLOBAL);
			//注入交易流水号
			String bunessFlowNo = merchantNo + TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
			context.setFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,bunessFlowNo, CommonContext.SCOPE_GLOBAL);
			//注入交易日期
			String trandate = TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_TRAN_DATE, trandate,CommonContext.SCOPE_GLOBAL);
			//注入交易时间
			String trantime = TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_TRAN_TIME, trantime,CommonContext.SCOPE_GLOBAL);
			//注入报文类型
			context.setFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP, "0001",CommonContext.SCOPE_GLOBAL);
			// 打印报文
			if (logger.isDebugEnabled()) {
			//	logger.debug("url_params = [" + url_params + "]");
				logger.debug("bunessFlowNo = [" + bunessFlowNo + "]");
				logger.debug("trandate = [" + trandate + "]");
				logger.debug("trantime = [" + trantime + "]");
			}
		} catch (Exception e) {
			//update by tianguangzhao 20160526 注入错误码，并抛出异常
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000520, context);
			logger.error("UMBSpecialHandler error !", e);
			throw new HandlerException(e);
		}
		logger.info("UMBSpecialHandler beforeHandler exec succ");
		return context;
	}

	@Override
	public CommonContext afterHandler(CommonContext context) {
		//根据服务响应码判断是否需要使用错误拆包格式
		try{
			String serverId = context.getSDO().serverId;
			ServerInfo server = ServerInfoData.getInstance().getServerInfo(serverId);
			String fieldName =  server.getRes_code();
			
			//开始预拆包，获取响应码字段
			byte[] msg = context.getByteArray(ConstantConfigField.ORIGINAL_MSG_BYTE_ARRAY, CommonContext.SCOPE_GLOBAL);
			String respCode = MessageUtil.getTranCode(msg,fieldName);
			logger.info("repcode is ["+respCode+"]");
			if(!respCode.equals("C000000000")){
				//如果响应码不是成功响应码，设置交易的拆包为错误拆包
				context.getSDO().svrErrUnpack = serverId;
				logger.info("error unpack file is ["+serverId+"]");
			}
			
		}catch(Exception e){
			logger.error("UMBSpecial  after Handler error !", e);
		}
		return context;
	}

}
