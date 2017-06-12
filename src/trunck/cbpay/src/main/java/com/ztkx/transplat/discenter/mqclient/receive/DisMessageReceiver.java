package com.ztkx.transplat.discenter.mqclient.receive;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.transplat.discenter.mqclient.InitContextUtil;
import com.ztkx.transplat.platformutil.activemq.config.ServiceInfo;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;

/**
 * 消息发送的实体类
 * 
 * @author zhangxiaoyun
 *
 */
public class DisMessageReceiver {
	private InitialContext ctx = null;
	private ConnectionFactory connectionFactory = null;
	private Destination destination = null;
	private Session session = null;
	private Connection connection = null;
	private MessageConsumer consumer = null;
	private ServiceInfo info;
	private DisReceiverExceptionListener exceptionListener = null;
	private MessageListener messageListener = null;
	private String className = "com.ztkx.transplat.container.msglistener.TempletMsgListener";
	private boolean isInit = false;
	private boolean isStart = false;
	private Logger logger = Logger.getLogger(DisMessageReceiver.class);

	public DisMessageReceiver(ServiceInfo info) {
		this.info = info;
	}
	
	public boolean init(){
		return init(this.info);
	}
	
	/**
	 * 设置消息监听器
	 * @param messageListener
	 * 2016年3月13日 下午9:36:49
	 * @author zhangxiaoyun
	 */
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * 初始化messageSender
	 * 
	 * @param info
	 * @return
	 */
	public boolean init(ServiceInfo info) {
		try {
			ctx = InitContextUtil.getContext(info);
			connectionFactory = InitContextUtil.getConnectionFactory(ctx);
			connection = connectionFactory.createConnection();
			exceptionListener = new DisReceiverExceptionListener(this);
			connection.setExceptionListener(exceptionListener);// 注册异常监听器
			destination = (Destination) ctx.lookup(info.getQueueName());
			if(info.isAutoAcknowledge()){
				session = connection.createSession(info.isTransaction(),Session.AUTO_ACKNOWLEDGE);
			}else{
				session = connection.createSession(info.isTransaction(),Session.CLIENT_ACKNOWLEDGE);
			}
			
			consumer = session.createConsumer(destination);

			// 初始化消息监听器
			//如果消息监听器没有从注入，就自己根据配置初始化。
			if(messageListener == null){
				if(StringUtils.isNotEmpty(info.getMsgListener())){
					className = info.getMsgListener();
				}else if (StringUtils.isNotBlank(BaseConfig.getConfig(ConstantConfigField.MSGLISTERNER))) {
					className = BaseConfig.getConfig(ConstantConfigField.MSGLISTERNER);
				}
				logger.info("message Listener is ["+className+"]");
				messageListener = (MessageListener) Class.forName(className).newInstance();
			}

			consumer.setMessageListener(messageListener);
			connection.start();
			isStart = true;
		} catch (JMSException | NamingException e) {
			logger.error("init MessageSender is exception ServiceInfo is :\n"
					+ info, e);
			return false;
		} catch (Exception e) {
			logger.error("init MessageSender is exception ServiceInfo is :\n"
					+ info, e);
			return false;
		}
		return true;
	}

	public void setIsInit(boolean isinit) {
		this.isInit = isinit;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	/**
	 * 释放当前当前资源
	 */
	public void relase() {
		try {
			if (consumer != null) {
				consumer.close();
			}

			if (session != null) {
				session.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			logger.error("relase source exception ", e);
		}

	}

	public ServiceInfo getInfo() {
		return info;
	}
}
