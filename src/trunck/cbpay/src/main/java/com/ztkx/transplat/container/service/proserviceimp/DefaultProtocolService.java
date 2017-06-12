package com.ztkx.transplat.container.service.proserviceimp;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.protocol.ProtocolManager;
import com.ztkx.transplat.container.protocol.process.ProtocolProcess;
import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.ProtocolService;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;

public class DefaultProtocolService implements ProtocolService {

	private static Logger logger = Logger
			.getLogger(DefaultProtocolService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException{
		try {

			String serverId = context.getSDO().serverId;

			ProtocolProcess process = ProtocolManager.getInstance().getProcotolBySysId(serverId);

			logger.info("protocol id ["+process.getProtocolConfig().getCommonConfig().getId()+"]");
			byte[] message = process.send(context);

			if (message == null || message.length==0) {
				logger.error("get return message error ! message is null ! ");
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000006);
				throw new ServiceException("get return message error ! message is null !");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("server return message size is["
							+ message.length + "]");
				}
			}
			/**
			 * 2016年3月6日
			 * 因为和宝易互通没有加解密，所有以前在加解密部分处理的byte数组到String的转换挪出来放到in的前拦截器里和协议服务里
			 */
			//add start
			//将原始报文放回ORIGINAL_MSG字段
			String resMsgStr = new String(message,context.getSDO().enCodeing);
			logger.info("res msg from system "+context.getSDO().serverId+" is ["+resMsgStr+"]");
			 context.setOrginalField(resMsgStr);
			 context.setByteArray(ConstantConfigField.ORIGINAL_MSG_BYTE_ARRAY, message, CommonContext.SCOPE_GLOBAL);
			 //add end
			
//			context.setObj(ContainerConstantField.ORIGINAL_MSG, message,
//					CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			logger.error("protocol deal is error!", e);
			if(context.getErrorCode() == null){
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000016);
			}
		}
		return context;
	}
}
