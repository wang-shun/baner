package com.ztkx.transplat.container.intercepter;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.intercepter.intface.CommonIntercepter;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.activemq.messagesend.MessageSender;
import com.ztkx.transplat.platformutil.activemq.messagesend.SenderList;
import com.ztkx.transplat.platformutil.activemq.messagesend.SenderManager;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.serialize.SerializeContextUtil;

public class AfterIntercepter implements CommonIntercepter {
	Logger logger = Logger.getLogger(AfterIntercepter.class);
	@Override
	public CommonContext intercepter(CommonContext context) {
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN))
		{
			//如果当前容器是in容器执行以下代码
			if(context.getSDO().tranType.equals(ConstantConfigField.TRANS_INFO_TRAN_TYPE_1)){
				//如果是同步交易需要响应
				SenderList serverList = SenderManager.getSenderList(ContainerConstantField.PROTOCOL_SERVICE_NAME_IN_DISCENTER);
				MessageSender sender = serverList.pool();
				try {
					Map<String,String> map= new HashMap<String,String>();
					map.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
					//消息messageId
					String jms_message_id = context.getFieldStr(ContainerConstantField.JMS_MESSAGE_ID, CommonContext.SCOPE_GLOBAL);
					map.put(ContainerConstantField.JMS_MESSAGE_ID, jms_message_id);
					String msg = context.getOrginalField();
					sender.Send(msg.getBytes(context.getSDO().enCodeing), map);
				} catch (Exception e) {
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000018, context);
					logger.error("AfterIntercepter send message exception",e);
				}
				finally{
					//释放资源
					if(sender!=null && serverList!=null){
						serverList.add(sender);
					}
				}
			}else{
				logger.info("tran type is not synchronization,do not need response");
			}
		}
		else if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT))
		{
			//如果当前容器是out容器执行以下代码
			/**
			 * 1.将context序列化后返回给接入容器
			 */
			byte[] obj = SerializeContextUtil.serialize(context);
			SenderList serverList = SenderManager.getSenderList(ContainerConstantField.PROTOCOL_SERVICE_NAME_OUT_IN);
			MessageSender sender = serverList.pool();
			try {
				Map<String,String> pro= new HashMap<String,String>();
				pro.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
				sender.Send(obj, pro);
			} catch (Exception e) {
				ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000018, context);
				logger.error("AfterIntercepter send message exception",e);
			}
			finally{
				//释放资源
				if(sender!=null ){
					serverList.add(sender);
				}
			}
		}
		return context;
	}

}
