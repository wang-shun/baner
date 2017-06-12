package com.ztkx.cbpay.platformutil.activemq.messagesend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.activemq.ActiveMQContextManager;
import com.ztkx.cbpay.platformutil.activemq.config.ServiceInfo;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 负责向ActiveMQ的queue中发送信息的工具类；
 * 
 * @author 田光照 2015/12/22
 *
 */
public class SenderList {

	// 保留初始化信息，用来新增sender
	private static Logger logger = Logger.getLogger(SenderList.class);
	private ServiceInfo info;
	private ConcurrentLinkedQueue<MessageSender> senders = new ConcurrentLinkedQueue<MessageSender>();

	public SenderList(ServiceInfo info) {
		this.info = info;
		for (int i = 0; i < info.getSessionCounts(); i++) {
			logger.info("start init ["+i+"] MessageSender " +info);
			MessageSender msr;
			msr = new MessageSender(info);
			msr.setIsInit(true);
			senders.add(msr);
		}
	}

	/**
	 * 获取消息发送者
	 * 
	 * @return
	 */
	public MessageSender pool() {
		MessageSender sender = senders.poll();
		if (sender != null) {
			if(logger.isDebugEnabled()){
				logger.debug("current senders pool size is ["+senders.size()+"]");
			}
			return sender;
		} else {
			MessageSender msr = null;
			msr = new MessageSender(info);
			msr.setIsInit(false);

			return msr;
		}
	}

	/**
	 * 如果sender是容器启动创建的就还会队列
	 * 
	 * @param sender
	 */
	public void add(MessageSender sender) {
		if (sender.isInit()) {
			senders.add(sender);
			if(logger.isDebugEnabled()){
				logger.debug("current senders pool size is ["+senders.size()+"]");
			}
		} else {
			sender.relase();
		}
	}
}
