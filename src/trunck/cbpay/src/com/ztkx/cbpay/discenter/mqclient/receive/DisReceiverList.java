package com.ztkx.cbpay.discenter.mqclient.receive;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;

/**
 * 负责向ActiveMQ的queue中发送信息的工具类；
 * 
 * @author 田光照 2015/12/22
 *
 */
public class DisReceiverList {

	// 保留初始化信息，用来新增sender
	private static Logger logger = Logger.getLogger(DisReceiverList.class);
	private ServiceInfo info;
	private ConcurrentLinkedQueue<DisMessageReceiver> receiverList = new ConcurrentLinkedQueue<DisMessageReceiver>();

	public DisReceiverList(ServiceInfo info) {
		this.info = info;
		for (int i = 0; i < info.getSessionCounts(); i++) {
			logger.info("start init ["+i+"] MessageReceiver " +info);
			DisMessageReceiver msr;
			msr = new DisMessageReceiver(info);
			msr.init();
			msr.setIsInit(true);
			receiverList.add(msr);
		}
	}

	public DisReceiverList() {
		
	}
	
	public void addMessageReceiver(DisMessageReceiver receiver){
		receiverList.add(receiver);
	}
	
	
}
