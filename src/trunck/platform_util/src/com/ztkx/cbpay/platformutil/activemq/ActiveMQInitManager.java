package com.ztkx.cbpay.platformutil.activemq;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ActiveMQConfiger;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.activemq.messagereceive.ReceiverManager;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderManager;

/**
 * activeMQ启动工具类，负责启动监听和初始化sender的session并放入list中备用
 * 
 * @author 田光照 2015/12/23
 * update by zhangxiaoyun 2015-12-26 16:15:39
 *
 */
public class ActiveMQInitManager {

	private static ActiveMQInitManager activeMQControl;
	private List<ServiceInfo> inBoxList = null;
	private List<ServiceInfo> outBoxList = null;
	static Logger logger = Logger.getLogger(ActiveMQInitManager.class);

	// 构造方法私有
	private ActiveMQInitManager() {

	}

	// 单例模式获取对象
	public static ActiveMQInitManager getInstance() {
		if (activeMQControl == null) {
			synchronized (ActiveMQInitManager.class) {
				if (activeMQControl == null) {
					activeMQControl = new ActiveMQInitManager();
				}
			}
		}
		return activeMQControl;
	}

	// 调用方法启动ActiveMQ的相关工具类
	public void instance() {
		
		//1.解析配置文件，获取配置列表
		outBoxList = ActiveMQConfiger.getInstance().getOutBoxList();
		inBoxList = ActiveMQConfiger.getInstance().getInBoxList();
		
		//2.初始化上下文工厂
		List<ServiceInfo> allList = null;
		if(outBoxList!=null){
			allList= new ArrayList<ServiceInfo>(outBoxList);
		}
		
		if(allList!=null && inBoxList!=null){
			allList.addAll(inBoxList);
		}else if(allList==null && inBoxList!=null){
			allList= new ArrayList<ServiceInfo>(inBoxList);
		}
		
		if(allList!=null){
			ActiveMQContextManager.getInstance(allList);
		}
		
		//2.初始化发送队列
		if(outBoxList!=null){
			SenderManager.getInstance();
			SenderManager.initSender(outBoxList);			
		}

		
		//3.根据配置文件初始化监听队列
		if(inBoxList!=null ){
			ReceiverManager.getInstance();
			ReceiverManager.initReceiver(inBoxList);
		}

	}

	/**
	 * 关闭释放资源
	 */
	public void close(){
		SenderManager.getInstance().close();
		ReceiverManager.getInstance().close();
	}
	
	public List<ServiceInfo> getInBoxList() {
		return inBoxList;
	}

	public List<ServiceInfo> getOutBoxList() {
		return outBoxList;
	}
	
	
}
