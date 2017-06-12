package com.ztkx.cbpay.container.protocol.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.platformutil.activemq.messagesend.MessageSender;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderList;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderManager;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.serialize.SerializeContextUtil;


public class JMSClientProcessImp implements ProtocolProcess{
	
	Logger logger = Logger.getLogger(JMSClientProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private boolean isStart = false;
	
	@Override
	public void start(ProtocolConfig conifg) {
		isStart = true;
		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		logger.info(conifg.getCommonConfig().getId()+" protocol start success");
	}
	
	@Override
	public void stop(){
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId()+" protocol already stop");
	}
	
	@Override
	public byte[] send(CommonContext context) throws Exception {
		
		if(!isStart){
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}
		// 获取MessageSender
		String serviceName = context.getFieldStr(ContainerConstantField.PROTOCOL_SERVICE_NAME);
		SenderList senderlist = null;
		MessageSender sender = null;
		try {
			senderlist = SenderManager.getSenderList(serviceName);
			sender = senderlist.pool();

			// 现将要传输的对象序列化
			byte[] bytes = SerializeContextUtil.serialize(context);

			Map<String, String> map = getParamMap();

			sender.Send(bytes, map);
		} catch (Exception e) {
			logger.error("send message error , service name is [ "+serviceName + " ]", e);
			// 将错误码放到context
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000006);
			throw e;
		} finally {
			// sender回收
			if(senderlist != null && sender != null){
				senderlist.add(sender);
			}
		}
		return null;
	}

	/**
	 * 将在in和out容器传递时所需要的所有参数信息封装入map中并返回。待定
	 * 
	 * @return 封装有在in和out容器传递时需要参数信息的map
	 */
	public Map<String, String> getParamMap() {
		Map<String, String> map = new HashMap<String, String>();
		// 放入当前容器名称
		map.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
		// 其他待定

		return map;
	}
	
	@Override
	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}
	@Override
	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	@Override
	public void setStatus(boolean status) {
		this.isStart = status;
	}

	@Override
	public boolean isStart() {
		return isStart;
	}

	@Override
	public byte[] send(CommonContext context, Map<String, String> pro) throws Exception{
		
		if(!isStart){
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}
		// 获取MessageSender
		String serviceName = pro.get(ContainerConstantField.PROTOCOL_SERVICE_NAME);
		SenderList senderlist = null;
		MessageSender sender = null;
		try {
			senderlist = SenderManager.getSenderList(serviceName);
			sender = senderlist.pool();

			// 现将要传输的对象序列化
			byte[] bytes = SerializeContextUtil.serialize(context);

			sender.Send(bytes, pro);
		} catch (Exception e) {
			logger.error("send message error , service name is [ "+serviceName + " ]", e);
			// 将错误码放到context
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000006);
			throw e;
		} finally {
			// sender回收
			if(senderlist != null && sender != null){
				senderlist.add(sender);
			}
		}
		return null;
	}
}
