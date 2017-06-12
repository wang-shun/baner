package com.ztkx.cbpay.platformutil.activemq.messagereceive;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;

/**
 * 负责向ActiveMQ的queue中发送信息的工具类；
 * 
 * @author 田光照 2015/12/22
 *
 */
public class ReceiverList {

	// 保留初始化信息，用来新增sender
	private static Logger logger = Logger.getLogger(ReceiverList.class);
	private ServiceInfo info;
	private ConcurrentLinkedQueue<MessageReceiver> receiver = new ConcurrentLinkedQueue<MessageReceiver>();

	public ReceiverList(ServiceInfo info) {
		this.info = info;
		for (int i = 0; i < info.getSessionCounts(); i++) {
			logger.info("start init ["+i+"] MessageReceiver " +info);
			MessageReceiver msr;
			msr = new MessageReceiver(info);
			msr.setIsInit(true);
			receiver.add(msr);
		}
	}
}
