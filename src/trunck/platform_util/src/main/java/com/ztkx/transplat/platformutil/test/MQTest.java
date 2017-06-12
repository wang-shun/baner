package com.ztkx.transplat.platformutil.test;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class MQTest {
	static Logger logger = Logger.getLogger(MQTest.class);
	public static void main(String[] args) throws NamingException, JMSException {
		
		String factory = "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
		String url = "tcp://172.30.12.33:61616";
		String queue = "dynamicQueues/example.jndi";
//		String queue = "example.jndi";
		Properties p = new Properties();
		p.setProperty(Context.INITIAL_CONTEXT_FACTORY, factory);
		p.setProperty(Context.PROVIDER_URL, url);
		InitialContext ctx = new InitialContext(p);
		ConnectionFactory connectionFactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		
		Destination destination = (Destination)ctx.lookup(queue);
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Session session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		MessageProducer producer = session.createProducer(destination);
		MessageProducer producer2 = session2.createProducer(destination);
         TextMessage message = session.createTextMessage();
         message.setText(".................");
         producer.send(message);
         producer2.send(message);
//		boolean flag = ActiveMQContext.getInstance().instance();
		
		//SenderManager.getInstance().sendInfo("send1", "测试发送是否成功");
	}
}
