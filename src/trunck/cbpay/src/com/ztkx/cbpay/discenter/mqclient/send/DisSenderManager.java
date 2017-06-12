package com.ztkx.cbpay.discenter.mqclient.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.container.javabean.AppAddress;
import com.ztkx.cbpay.container.util.LoopLinkedList;
import com.ztkx.cbpay.platformutil.activemq.config.ParseServiceInfoUtil;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * app的发送地址管理
 * @author zhangxiaoyun
 * 2016年1月27日 上午10:35:24
 * <p>Company:ztkx</p>
 */
public class DisSenderManager {

	private static DisSenderManager senderManager;
	//在这个队列上做轮询机制
	private static Map<String,DisSenderList> senders = new HashMap<String, DisSenderList>();
	private static Map<String,Boolean> senderStatus = new HashMap<String, Boolean>();
	private static LoopLinkedList<String> loopList = null;
	
	Logger logger = Logger.getLogger(DisSenderManager.class); 
	
	// 构造方法私有化
	private DisSenderManager() {
		loopList = new LoopLinkedList<String>();
	}
	
	// 单例模式获取对象
	public static DisSenderManager getInstance() {
		if (senderManager == null) {
			synchronized (DisSenderManager.class) {
				if (senderManager == null) {
					senderManager = new DisSenderManager();
				}
			}
		}
		return senderManager;
	}
	
	public void initSender(List<AppAddress> outBoxList){
		for (int i = 0; i < outBoxList.size(); i++) {
			AppAddress address = outBoxList.get(i);
			String appId = address.getAppid();
			String status = address.getAppstatus();
			if(status.equals(ConstantConfigField.APP_ADDRESS_APPSTATUS_ON)){
				String url = address.getUrl();
				Element rootEle =  Dom4jXmlUtil.getRootElementByStr(url);
				ServiceInfo info = ParseServiceInfoUtil.parse(rootEle);
				DisSenderList senderList = new DisSenderList(info);
				senders.put(appId, senderList);
				loopList.add(appId);
				senderStatus.put(appId, true);
			}
		}
	}
	
	/**
	 * 根据队列名称获取队列的SenderList
	 * @param serviceName
	 * @return
	 */
	public DisSenderList getNextSenderList(){
		String appid = loopList.getNext();
		if(appid == null){
			logger.error("obtain appid is null");
			return null;
		}
		return senders.get(appid);
	}

	public void close() {
		logger.info("start relase resource ....");
		logger.info("后续实现");
	}
	
	public static DisSenderList getSender(String key){
		return senders.get(key);
	}
	
	public static void putSender(String key,DisSenderList senderList){
		senders.put(key, senderList);
	}
	
	public static void addloopList(String key){
		if(loopList == null){
			loopList = new LoopLinkedList<String>();
		}
		loopList.add(key);
	}
	
	public static void removeloopList(String key){
		if(loopList == null){
			loopList = new LoopLinkedList<String>();
		}
		loopList.remove(key);
	}
	
	public static void main(String[] args) {
		String url = "<service name=\"discenter_in\"><INITIAL_CONTEXT_FACTORY>org.apache.activemq.jndi.ActiveMQInitialContextFactory</INITIAL_CONTEXT_FACTORY><PROVIDER_URL>tcp://127.0.0.1:61616</PROVIDER_URL><queueName>dynamicQueues/discenter_in.queue</queueName><overTime>0</overTime><isTransaction>false</isTransaction><autoAcknowledge>true</autoAcknowledge><sessionCounts>5</sessionCounts></service>";
		Element rootEle =  Dom4jXmlUtil.getRootElementByStr(url);
		System.out.println(rootEle);
	}
}
