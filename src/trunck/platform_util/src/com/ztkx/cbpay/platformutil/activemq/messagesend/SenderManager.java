package com.ztkx.cbpay.platformutil.activemq.messagesend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;

/**
 * 对外界提供向ActiveMQ发送信息的服务
 * 
 * @author 田光照 2015/12/23
 *
 */
public class SenderManager {

	private static SenderManager senderManager;
	private static Map<String,SenderList> senders = new HashMap<String, SenderList>();
	Logger logger = Logger.getLogger(SenderManager.class); 
	// 构造方法私有化
	private SenderManager() {

	}
	
	// 单例模式获取对象
	public static SenderManager getInstance() {
		if (senderManager == null) {
			synchronized (SenderManager.class) {
				if (senderManager == null) {
					senderManager = new SenderManager();
				}
			}
		}
		return senderManager;
	}
	
	public static void initSender(List<ServiceInfo> outBoxList){
		for (int i = 0; i < outBoxList.size(); i++) {
			ServiceInfo info = outBoxList.get(i);
			SenderList senderList = new SenderList(info);
			senders.put(info.getServiceName(), senderList);
		}
	}
	
	/**
	 * 根据队列名称获取队列的SenderList
	 * @param serviceName
	 * @return
	 */
	public static SenderList getSenderList(String serviceName){
		return senders.get(serviceName);
	}

	public void close() {
		logger.info("start relase resource ....");
		logger.info("后续实现");
	}
}
