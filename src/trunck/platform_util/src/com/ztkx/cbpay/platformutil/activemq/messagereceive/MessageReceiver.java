package com.ztkx.cbpay.platformutil.activemq.messagereceive;

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

import com.ztkx.cbpay.platformutil.activemq.ActiveMQContextManager;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 消息发送的实体类
 * 
 * @author zhangxiaoyun
 *
 */
public class MessageReceiver {
	private InitialContext ctx = null;
	private ConnectionFactory connectionFactory = null;
	private Destination destination = null;
	private Session session = null;
	private Connection connection = null;
	private MessageConsumer consumer = null;
	private ServiceInfo info;
	private ReceiverExceptionListener exceptionListener = null;
	private MessageListener messageListener = null;
	private String className = "com.ztkx.cbpay.container.msglistener.TempletMsgListener";
	private boolean isInit = false;
	private boolean isStart = false;
	private Logger logger = Logger.getLogger(MessageReceiver.class);

	public MessageReceiver(ServiceInfo info) {
		this.info = info;
		init(info);
	}

	/**
	 * 初始化messageSender
	 * 
	 * @param info
	 * @return
	 */
	public boolean init(ServiceInfo info) {
		try {
			String key = info.getFactory()
					+ ConstantConfigField.MQ_QUEUE_SEPARATOR + info.getUrl();
			ctx = ActiveMQContextManager.getInitalContext(key);
			connectionFactory = ActiveMQContextManager
					.getConnectionFactory(key);
			connection = connectionFactory.createConnection();
			exceptionListener = new ReceiverExceptionListener(this);
			connection.setExceptionListener(exceptionListener);// 注册异常监听器
			destination = (Destination) ctx.lookup(info.getQueueName());
			if(info.isAutoAcknowledge()){
				session = connection.createSession(info.isTransaction(),Session.AUTO_ACKNOWLEDGE);
			}else{
				session = connection.createSession(info.isTransaction(),Session.CLIENT_ACKNOWLEDGE);
			}
			if(StringUtils.isBlank(info.getSelector()))
				consumer = session.createConsumer(destination);
			else
				consumer = session.createConsumer(destination,info.getSelector());

			// 初始化消息监听器
			if (StringUtils.isNotBlank(BaseConfig
					.getConfig(ConstantConfigField.MSGLISTERNER))) {
				className = BaseConfig
						.getConfig(ConstantConfigField.MSGLISTERNER);
			}
			
			// 配置命令监听器
			if (StringUtils.isNotBlank(info.getMsgListener())) {
				className = info.getMsgListener();
			}

			messageListener = (MessageListener) Class.forName(className)
					.newInstance();

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
