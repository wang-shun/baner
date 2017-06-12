package com.ztkx.transplat.platformutil.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

/**
 * reveiver调用的监听类，负责处理从activemq接收到的报文。
 * 
 * @author 田光照 2015/12/22
 */
public class Listener implements MessageListener {

	static Logger logger = Logger.getLogger(Listener.class);

	public synchronized void onMessage(Message arg0) {
		
	}
}
