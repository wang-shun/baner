package com.ztkx.cbpay.platformutil.activemq.messagesend;

import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.ActiveMQContextManager;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 消息发送的实体类
 * 
 * @author zhangxiaoyun
 *
 */
public class MessageSender {
	private InitialContext ctx = null;
	private ConnectionFactory connectionFactory = null;
	private Destination destination = null;
	private Session session = null;
	private Connection connection = null;
	private MessageProducer producer = null;
	private ServiceInfo info;
	private SenderExceptionListener exceptionListener = null;
	private boolean isInit = false;
	private boolean isStart = false;
	private Logger logger = Logger.getLogger(MessageSender.class);

	public MessageSender(ServiceInfo info) {
		this.info = info;
		init(info);
	}

	/**
	 * 异步发送消息
	 * 
	 * @param message
	 */
	public void Send(byte[] message, Map<String, String> map) throws Exception {
		// 判断协议是否为启动
		if (!isStart) {
			throw new Exception("sender is not start");
		}
		try {
			BytesMessage msg = session.createBytesMessage();
			// 将map中的信息取出，写入massage
			if(map != null && map.size()>0){
				if(logger.isDebugEnabled()){
					logger.debug("param map is [ "+ map.toString() + "]");
				}
				msg.setObjectProperty(ConstantConfigField.MSG_PROPERTIES, map);
			}
			
			msg.writeBytes(message);
			producer.send(msg);
			// 打印出需要传输的message和目的地queue方便调试！
			logger.info("ServiceInfo is " + info.getServiceName()+" Transfer message is succ ");
		} catch (JMSException e) {
			logger.error("send message exception, start init this sender ", e);
			// 需要新开一个线程,先不做根据后期测试情况在看
			// TODO
			// exceptionListener.onException(e);
		}
	}
	
	
	/**
	 * 异步发送消息
	 * comman
	 * @param message
	 */
	public void SendObj(MessageObj obj,Map<String, String> titlemap) throws Exception {
		// 判断协议是否为启动
		if (!isStart) {
			throw new Exception("sender is not start");
		}
		try {
			ObjectMessage msg = session.createObjectMessage(obj);
			// 打印出需要传输的message和目的地queue方便调试！
			if(titlemap!=null){
				msg.setStringProperty(ConstantConfigField.CONTAINID, titlemap.get(ConstantConfigField.CONTAINID));
			}
			producer.send(msg);
			logger.info(" ServiceInfo is " + info.getServiceName()+" sender message succ");
		} catch (JMSException e) {
			logger.error("send message exception, start init this sender ", e);
			// 需要新开一个线程,先不做根据后期测试情况在看
			// TODO
			// exceptionListener.onException(e);
		}
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
			exceptionListener = new SenderExceptionListener(this);
			connection.setExceptionListener(exceptionListener);// 注册异常监听器
			destination = (Destination) ctx.lookup(info.getQueueName());
			if(info.isAutoAcknowledge()){
				session = connection.createSession(info.isTransaction(),Session.AUTO_ACKNOWLEDGE);
			}else{
				session = connection.createSession(info.isTransaction(),Session.CLIENT_ACKNOWLEDGE);
			}
			producer = session.createProducer(destination);
			producer.setTimeToLive(info.getOverTime());
			connection.start();
			isStart = true;
		} catch (JMSException | NamingException e) {
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
			if (producer != null) {
				producer.close();
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
