package com.ztkx.cbpay.platformutil.activemq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * ActiveMQ工具类的父类，包含sender和receiver的公共参数和公共方法
 * 
 * @author 田光照 2015/12/22
 */
public class ActiveMQPublic implements ExceptionListener {

	protected static ConnectionFactory connectionFactory;

	static Logger logger = getLogger();
	protected static Connection connection = null;

	// MQ用户名
	protected String mquser;
	// MQ密码
	protected String password;
	// MQurl
	protected String mqurl;
	// session的数量
	protected int sessionCounts;
	// 队列名称
	protected String queue;
	// 存放session的list,必须考虑线程安全问题
	public List<Session> list = Collections
			.synchronizedList(new ArrayList<Session>());

	// 无参数构造方法
	ActiveMQPublic() {

	}

	// 有参数的构造方法
	ActiveMQPublic(String mquser, String password, String mqurl,
			int sessionCounts, String queue) {
		// 为各个变量赋值
		this.mquser = mquser;
		this.password = password;
		this.mqurl = mqurl;
		this.queue = queue;
		this.sessionCounts = sessionCounts;
		// 调用初始化方法
		init();
	}

	// 初始化connectionFactory,connection等的初始方法
	protected void init() {
		// 打印出connectionfacotory相关信息
		if (logger.isDebugEnabled()) {
			logger.info("ActiveMq start param is  mquser = " + mquser
					+ " ; password = " + password + " ; mqurl = " + mqurl
					+ " ; sessionCounts = " + sessionCounts);
		}

		try {
			if (connectionFactory == null) {
				synchronized (ActiveMQPublic.class) {
					// 构造方法中初始化连接工厂
					connectionFactory = new ActiveMQConnectionFactory(mquser,
							password, mqurl);
					// 调用获取方法，建立connection
					startConnection(false);
				}
			}
		} catch (Exception e) {
			logger.error("create MQ connectionFactory error ! ", e);
		}

	}

	// 在ActiveMQ出现问题时，重新连接

	// 使用getLogger方法获取logger对象，方便子类重写
	private static Logger getLogger() {
		return Logger.getLogger(ActiveMQPublic.class);
	}

	// 获取Connection
	protected static Connection getConnection() {
		return connection;
	}

	// 创建Session
	protected Session createSession(Connection connection) {
		Session session = null;
		// createSession的两个参数,第一个参数表示是否支持事务，第二个参数表示消息确认模式,AUTO_ACKNOWLEDGE表示自动确认
		try {
			// 为了测试方便改为false，原定为true
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			logger.error("create activeMQ session error ! ", e);
		}
		return session;
	}

	// 获取Session
	protected Session getSession() {
		Session session = null;
		if (list != null && list.size() != 0) {
			// 从list中获取session，并将session移除
			for (int i = 0; i < list.size(); i++) {
				session = list.get(i);
				list.remove(i);
				break;
			}
		} else {
			session = createSession(connection);
		}
		return session;
	}

	// 关闭session
	protected void closeSession(Session session) {
		if (session != null && list.size() < sessionCounts) {
			list.add(session);
		} else {
			try {
				session.close();
			} catch (JMSException e) {
				logger.error("ActiveMQ unnecessary session close error !", e);
			}
		}
	}

	// 关闭connection
	protected void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			logger.error("ActiveMQ producer close error !", e);
		}
	}

	// 释放资源，connection ,session,producer
	protected void release(Connection connection, Session session) {
		closeSession(session);
		// 长连接不关闭connection,除非系统问题
		// closeConnection(connection);
	}

	// 加工message信息按照需要的格式返回
	protected Message processMessage() {
		Message message = null;
		return message;
	}

	// 获取连接connection方法
	protected void startConnection(boolean flag) {
		list.clear();
		try {
			connection = connectionFactory.createConnection();
			if (!flag) {
				connection.setExceptionListener(this);
			}
			// 开启连接
			connection.start();
			for (int i = 0; i < sessionCounts; i++) {
				Session session = createSession(connection);
				list.add(session);
			}
		} catch (JMSException e) {
			logger.error("ActiveMQ connection restart error !", e);
		}

	}

	// 当连接出现问题时,重新获取连接
	@Override
	public void onException(JMSException arg0) {
		startConnection(true);
	}

}
