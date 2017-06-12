package com.ztkx.transplat.discenter.core;

import org.apache.log4j.Logger;


public class MessageHandler implements Runnable{
	private byte[] msg = null;
	private String msgId = null;
	private Logger logger = Logger.getLogger(MessageHandler.class);
	
	public MessageHandler(byte[] msg,String msgId){
		this.msg = msg;
		this.msgId = msgId;
	}

	@Override
	public void run() {
		//根据msgid获取消息的同步对象
		Object obj = MessageBufferPool.getInstance().removeSynObj(msgId);
		if(obj == null){
			logger.error("msg is overTime "+new String());
			return;
		}
		//将消息注入消息缓冲队列
		MessageBufferPool.getInstance().addMsg(msgId, msg);
		logger.info("start notify synchronization response :"+msgId);
		synchronized (obj) {
			obj.notify();
		}
	}
}
