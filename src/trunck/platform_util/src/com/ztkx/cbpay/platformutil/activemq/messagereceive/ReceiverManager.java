package com.ztkx.cbpay.platformutil.activemq.messagereceive;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 对外界提供向ActiveMQ发送信息的服务
 * 
 * @author zhangxiaoyun 2015年12月28日14:56:59
 *
 */
public class ReceiverManager {

	private static ReceiverManager senderManager;
	private static Map<String,ReceiverList> receivers = new HashMap<String, ReceiverList>();
	Logger logger = Logger.getLogger(ReceiverManager.class); 
	// 构造方法私有化
	private ReceiverManager() {

	}
	
	// 单例模式获取对象
	public static ReceiverManager getInstance() {
		if (senderManager == null) {
			synchronized (ReceiverManager.class) {
				if (senderManager == null) {
					senderManager = new ReceiverManager();
				}
			}
		}
		return senderManager;
	}
	
	public static void initReceiver(List<ServiceInfo> inBoxList){
		for (int i = 0; i < inBoxList.size(); i++) {
			ServiceInfo info = inBoxList.get(i);
			ReceiverList receiverList = new ReceiverList(info);
			receivers.put(info.getServiceName(), receiverList);
		}
	}

	public void close() {
		logger.info("start relase resource ....");
		logger.info("后续实现");
	}
}
