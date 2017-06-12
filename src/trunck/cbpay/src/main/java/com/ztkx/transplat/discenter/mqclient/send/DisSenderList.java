package com.ztkx.transplat.discenter.mqclient.send;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ztkx.transplat.platformutil.activemq.config.ServiceInfo;

/**
 * 负责向ActiveMQ的queue中发送信息的工具类；
 * 
 * @author 田光照 2015/12/22
 *
 */
public class DisSenderList {

	// 保留初始化信息，用来新增sender
	private static Logger logger = Logger.getLogger(DisSenderList.class);
	private ServiceInfo info;
	private ConcurrentLinkedQueue<DisMessageSender> senders = new ConcurrentLinkedQueue<DisMessageSender>();

	public DisSenderList(ServiceInfo info) {
		this.info = info;
		for (int i = 0; i < info.getSessionCounts(); i++) {
			logger.info("start init ["+i+"] MessageSender " +info);
			DisMessageSender msr;
			msr = new DisMessageSender(info);
			msr.setIsInit(true);
			senders.add(msr);
		}
	}

	/**
	 * 获取消息发送者
	 * 
	 * @return
	 */
	public DisMessageSender pool() {
		DisMessageSender sender = senders.poll();
		if (sender != null) {
			if(logger.isDebugEnabled()){
				logger.debug("current senders pool size is ["+senders.size()+"]");
			}
			return sender;
		} else {
			DisMessageSender msr = null;
			msr = new DisMessageSender(info);
			msr.setIsInit(false);

			return msr;
		}
	}

	/**
	 * 如果sender是容器启动创建的就还会队列
	 * 
	 * @param sender
	 */
	public void add(DisMessageSender sender) {
		if (sender!=null && sender.isInit()) {
			senders.add(sender);
			if(logger.isDebugEnabled()){
				logger.debug("current senders pool size is ["+senders.size()+"]");
			}
		} else {
			sender.relase();
		}
	}
}
