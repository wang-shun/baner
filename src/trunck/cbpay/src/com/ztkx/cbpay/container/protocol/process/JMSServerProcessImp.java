package com.ztkx.cbpay.container.protocol.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.container.protocol.config.RequestConfig;
import com.ztkx.cbpay.container.protocol.config.ResponseConfig;
import com.ztkx.cbpay.container.util.MessageUtil;
import com.ztkx.cbpay.discenter.mqclient.receive.DisMessageReceiver;
import com.ztkx.cbpay.discenter.mqclient.receive.DisReceiverList;
import com.ztkx.cbpay.discenter.mqclient.send.DisMessageSender;
import com.ztkx.cbpay.discenter.mqclient.send.DisSenderList;
import com.ztkx.cbpay.discenter.mqclient.send.DisSenderManager;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * TCP协议服务端
 * @author zhangxiaoyun
 * 2016年3月4日 下午3:41:52
 * <p>Company:ztkx</p>
 */
public class JMSServerProcessImp implements ProtocolProcess {

	static Logger logger = Logger.getLogger(JMSServerProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	private boolean isStart = false;
	long timeoutMillisecond = 60 * 1000L;
	DisReceiverList receiverList = null;
	// private ServletHandler servletHandler;
	@Override
	public void start(ProtocolConfig conifg) {

		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		requestConfig = this.protocolConfig.getRequestConfig();
		responseConfig = this.protocolConfig.getResponseConfig();
		
		
		try {
			// 从配置文件中获取超时时长
			timeoutMillisecond = Long.parseLong(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));
			if (logger.isDebugEnabled()) {
				logger.debug("get timeoutMillisecond success ! timeoutMillisecond = [ "+ timeoutMillisecond + " ]!");
			}
		} catch (Exception e) {
			logger.error("get timeoutMillisecond error param is [ "+ ConstantConfigField.OVER_TIME+ " ] !  now use defult value  [ " + timeoutMillisecond+ " ]", e);
		}
		
		
		/**
		 * 1.创建ServiceInfo
		 * 2.绑定端口和ip
		 * 3.建立监听
		 * 4.接收到客户端连接后获取线程处理后续流程
		 * 5.关闭连接
		 */
		
		ServiceInfo info = new ServiceInfo();
		info.setServiceName(commonConfig.getId());
		info.setFactory(commonConfig.getFactory());
		info.setQueueName(commonConfig.getQueueName());
		info.setUrl(commonConfig.getUrl());
		info.setSessionCounts(commonConfig.getSessionCount());
		info.setAutoAcknowledge(commonConfig.isAutoAcknowledge());
		info.setTransaction(commonConfig.isTransaction());
		info.setOverTime(commonConfig.getOverTime());
		info.setMsgListener(commonConfig.getMsgListener());
		
		receiverList = new DisReceiverList();
		JMSMessageHandler messagehandler = new JMSMessageHandler();
		for (int i = 0; i < info.getSessionCounts(); i++) {
			logger.info("start init  ["+ i +"]  MessageReceiver "+info);
			DisMessageReceiver msr;
			msr = new DisMessageReceiver(info);
			msr.setMessageListener(messagehandler);
			msr.init();
			msr.setIsInit(true);
			receiverList.addMessageReceiver(msr);
		}
		
		
		logger.info(conifg.getCommonConfig().getId()+ " protocol start success");
	}

	/**
	 * 接收客户端的请求报文
	 * @throws IOException
	 * 2016年3月4日 下午5:23:49
	 * @author zhangxiaoyun
	 */
	private void receive() throws IOException {
		if (!isStart) {
			logger.error("protocol not startup ");
			throw new IOException("protocol not startup");
		}
		
		
	}

	@Override
	public void stop() {
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId() + " protocol already stop");
	}

	/**
	 * 没用
	 */
	@Override
	public byte[] send(CommonContext context) throws Exception {
		if (!isStart) {
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}

		return null;
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
	public byte[] send(CommonContext context, Map<String, String> pro)
			throws Exception {
		// TODO 不需要实现
		return null;
	}

	class JMSMessageHandler implements MessageListener {
		
		/**
		 * 接收cashier发送过来的消息
		 * @param map
		 * @param msg
		 * 2016年3月13日 下午5:19:40
		 * @author zhangxiaoyun
		 */
		@Override
		public void onMessage(Message msg) {
			try{
				if(TimeUtil.isOverTime(msg.getJMSTimestamp(),timeoutMillisecond )){
					// 超时后打印超时信息，并将message强转String打印
					logger.error(" message over time ! message from cashier");
					return;
				}
			} catch (JMSException e) {
				logger.error("get message over time error !", e);
				return;
			}
			// 首先判断类型是否是BytesMessage，我们自己的消息都是BytesMessage,如果消息类型错误则结束。
			if (msg instanceof BytesMessage) {

				BytesMessage bm = (BytesMessage) msg;
				byte[] message = null;
				try {
					message = MessageUtil.resolveByteArrayMessageFromJMSMessage(bm);
				} catch (JMSException e) {
					logger.error("obtain response message error", e);
				}
				
				
				Map<String, String> mapPro = new HashMap<String, String>();
				mapPro.put(ContainerConstantField.TRANFROM,commonConfig.getServerId());
				mapPro.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
				/**
				 * jms协议的交易为异步交易，不需要等待in容器响应。所以直接发送到mq上,不需要本地缓存交易信息。
				 */
				DisSenderList senderList = null;
				DisMessageSender sender = null;
				try {
					senderList =  DisSenderManager.getInstance().getNextSenderList();
					sender = senderList.pool();
					sender.Send(message, mapPro);
				} catch (Exception e) {
					logger.error("send message exception",e);
				}finally{
					//释放资源
					if(senderList!=null)
						senderList.add(sender);
				}
			} else {
				logger.info("message type error !");
				return;
			}
			
		}
		
		/**
		 * 释放系统资源 
		 * @param in
		 * @param out
		 * 2016年3月6日 上午9:41:06
		 * @author zhangxiaoyun
		 */
		private void freeSystemSource(InputStream in, OutputStream out) {
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close inputstream exception",e);
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error("close outputStream exception",e);
				}
			}
		}
	}
}
