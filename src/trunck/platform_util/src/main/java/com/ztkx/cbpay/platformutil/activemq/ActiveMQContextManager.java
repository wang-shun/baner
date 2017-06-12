package com.ztkx.cbpay.platformutil.activemq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * activemq 上下文资源管理类
 * 
 * @author zhangxiaoyun 2015-12-26 16:42:14
 *
 */
public class ActiveMQContextManager {

	private static ActiveMQContextManager activeMQControl;
	private static Map<String, InitialContext> ctxMap;		//上下文map
	private static Map<String, ConnectionFactory> ctfMap;	//链接工厂map
	private List<ServiceInfo> allList = null;
	static Logger logger = Logger.getLogger(ActiveMQContextManager.class);

	// 构造方法私有
	private ActiveMQContextManager() {
		
	}
	
	public static ConnectionFactory getConnectionFactory(String key){
		return ctfMap.get(key);
	}
	
	public static InitialContext getInitalContext(String key){
		return ctxMap.get(key);
	}
	
	
	private ActiveMQContextManager(List<ServiceInfo> allList) throws NamingException {
		ctxMap = new HashMap<String, InitialContext>();
		ctfMap = new HashMap<String, ConnectionFactory>();
		//创建初始化工厂
		for (int i = 0; i < allList.size(); i++) {
			ServiceInfo info = allList.get(i);
			String key = info.getFactory()+ConstantConfigField.MQ_QUEUE_SEPARATOR+info.getUrl();
			if(!ctxMap.containsKey(key)){
				Properties pro = new Properties();
				pro.setProperty(Context.INITIAL_CONTEXT_FACTORY, info.getFactory());
				pro.setProperty(Context.PROVIDER_URL, info.getUrl());
				//创建初始化上下文
				InitialContext ctx = new InitialContext(pro);
				ConnectionFactory connectionFactory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
				ctxMap.put(key, ctx);
				ctfMap.put(key, connectionFactory);
			}
			
		}
		
	}

	// 单例模式获取对象
	public static ActiveMQContextManager getInstance(List<ServiceInfo> allList) {
		if (activeMQControl == null) {
			synchronized (ActiveMQContextManager.class) {
				if (activeMQControl == null) {
					try {
						activeMQControl = new ActiveMQContextManager(allList);
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return activeMQControl;
	}

	
}
