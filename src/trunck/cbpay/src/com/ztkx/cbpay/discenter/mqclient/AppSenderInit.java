package com.ztkx.cbpay.discenter.mqclient;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.initdata.AppAddressData;
import com.ztkx.cbpay.container.javabean.AppAddress;
import com.ztkx.cbpay.discenter.mqclient.receive.DisReceiverManager;
import com.ztkx.cbpay.discenter.mqclient.send.DisSenderManager;

/**
 * activeMQ启动工具类，负责启动监听和初始化sender的session并放入list中备用
 * 
 * @author 田光照 2015/12/23
 * update by zhangxiaoyun 2015-12-26 16:15:39
 *
 */
public class AppSenderInit {

	private static AppSenderInit activeMQControl;
	private List<AppAddress> inBoxList = null;
	private List<AppAddress> outBoxList = null;
	static Logger logger = Logger.getLogger(AppSenderInit.class);

	// 构造方法私有
	private AppSenderInit() {

	}

	// 单例模式获取对象
	public static AppSenderInit getInstance() {
		if (activeMQControl == null) {
			synchronized (AppSenderInit.class) {
				if (activeMQControl == null) {
					activeMQControl = new AppSenderInit();
				}
			}
		}
		return activeMQControl;
	}

	// 调用方法启动ActiveMQ的相关工具类
	public void instance() {
		
		//1.解析配置文件，获取配置列表
		outBoxList = AppAddressData.getInstance().getOutAddressList();
		inBoxList = AppAddressData.getInstance().getInAddressList();
		
		//2.初始化发送队列
		DisSenderManager.getInstance().initSender(outBoxList);
		
		//3.根据配置文件初始化监听队列
		DisReceiverManager.getInstance();
		DisReceiverManager.initReceiver(inBoxList);
	}

	/**
	 * 关闭释放资源
	 */
	public void close(){
		DisSenderManager.getInstance().close();
		DisReceiverManager.getInstance().close();
	}	
}
