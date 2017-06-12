package com.ztkx.transplat.discenter.mqclient.receive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.transplat.container.javabean.AppAddress;
import com.ztkx.transplat.platformutil.activemq.config.ParseServiceInfoUtil;
import com.ztkx.transplat.platformutil.activemq.config.ServiceInfo;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.xml.Dom4jXmlUtil;

/**
 * 对外界提供向ActiveMQ发送信息的服务
 * 
 * @author zhangxiaoyun 2015年12月28日14:56:59
 *
 */
public class DisReceiverManager {

	private static DisReceiverManager senderManager;
	private static Map<String,DisReceiverList> receivers = new HashMap<String, DisReceiverList>();
	Logger logger = Logger.getLogger(DisReceiverManager.class); 
	// 构造方法私有化
	private DisReceiverManager() {

	}
	
	// 单例模式获取对象
	public static DisReceiverManager getInstance() {
		if (senderManager == null) {
			synchronized (DisReceiverManager.class) {
				if (senderManager == null) {
					senderManager = new DisReceiverManager();
				}
			}
		}
		return senderManager;
	}
	
	public static void initReceiver(List<AppAddress> inBoxList){
		for (int i = 0; i < inBoxList.size(); i++) {
			AppAddress address = inBoxList.get(i);
			String appId = address.getAppid();
			String status = address.getAppstatus();
			if(status.equals(ConstantConfigField.APP_ADDRESS_APPSTATUS_ON)){
				String url = address.getUrl();
				Element rootEle =  Dom4jXmlUtil.getRootElementByStr(url);
				ServiceInfo info = ParseServiceInfoUtil.parse(rootEle);
				DisReceiverList receiverList = new DisReceiverList(info);
				receivers.put(appId, receiverList);
			}
		}
	}

	public void close() {
		logger.info("start relase resource ....");
		logger.info("后续实现");
	}
}
