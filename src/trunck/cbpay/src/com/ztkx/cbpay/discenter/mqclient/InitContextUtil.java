package com.ztkx.cbpay.discenter.mqclient;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;

public class InitContextUtil {
	static Logger logger = Logger.getLogger(InitContextUtil.class);
	/**
	 * 初始化上下文工厂
	 * @param info
	 * @return
	 */
	public static InitialContext getContext(ServiceInfo info){
		Properties pro = new Properties();
		pro.setProperty(Context.INITIAL_CONTEXT_FACTORY, info.getFactory());
		pro.setProperty(Context.PROVIDER_URL, info.getUrl());
		//创建初始化上下文
		InitialContext ctx = null;
		try {
			ctx = new InitialContext(pro);
		} catch (NamingException e) {
			logger.error("init InitialContext exception ",e);
		}
		return ctx;
	}
	
	/**
	 * 初始化链接
	 * @param info
	 * @return
	 */
	public static ConnectionFactory getConnectionFactory(InitialContext ctx) {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
		} catch (NamingException e) {
			logger.error("lookup  ConnectionFactory fail",e);
		}
		return connectionFactory;
	}
}
