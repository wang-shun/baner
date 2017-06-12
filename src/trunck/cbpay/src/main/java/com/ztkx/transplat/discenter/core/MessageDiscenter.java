package com.ztkx.transplat.discenter.core;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.discenter.mqclient.send.DisMessageSender;
import com.ztkx.transplat.discenter.mqclient.send.DisSenderList;
import com.ztkx.transplat.discenter.mqclient.send.DisSenderManager;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.time.TimeUtil;

/**
 * 消息分发中心，discenter的核心逻辑
 * @author zhangxiaoyun
 * 2016年1月28日 上午10:01:37
 * <p>Company:ztkx</p>
 */
public class MessageDiscenter {
	
	private static MessageDiscenter messageDiscenter = null;
	int channelOverTime = 3*60*1000;//3分钟
	Logger logger = Logger.getLogger(MessageDiscenter.class);
	private MessageDiscenter(){
		String sizeStr = BaseConfig.getConfig(ContainerConstantField.MESSAGE_OVER_TIME);

		if(null != sizeStr && !sizeStr.equals("")){
			channelOverTime = Integer.parseInt(sizeStr);
			logger.info("message_over_time is "+channelOverTime);
		}else{
			logger.warn("short of system params "+ContainerConstantField.MESSAGE_OVER_TIME+" use default config "+channelOverTime);
		}
	}
	
	public static MessageDiscenter getInstance(){
		if(messageDiscenter==null){
			synchronized (MessageDiscenter.class) {
				if(messageDiscenter==null){
					messageDiscenter = new MessageDiscenter();
				}
			}
		}
		return messageDiscenter;
	}
	
	/**
	 * 处理消息
	 * @param message
	 */
	public byte[]  processMessage(byte[] message,Map<String,String> map){
		
		//获取当前的sender
		DisSenderList senderList =  DisSenderManager.getInstance().getNextSenderList();
		DisMessageSender sender = senderList.pool();
		String msgId = getMsgId();
		boolean isReciveMsg = true;
		byte[] resmsg = null;
		Object obj = new Object();
		map.put(ContainerConstantField.JMS_MESSAGE_ID, msgId);
		logger.info("msgid send to app address is ["+sender.getInfo().getUrl()+"] msgId is ["+msgId+"]");
		try {
			//异步发送
			MessageBufferPool.getInstance().addSynObj(msgId, obj);
			sender.Send(message, map);
		} catch (Exception e) {
			logger.error("send message to app exception",e);
			MessageBufferPool.getInstance().removeSynObj(msgId);
			return null;	//TODO	这儿有问题
		}finally{
			//释放资源
			senderList.add(sender);
		}
		
		try {
			//同步等待
			long startTime = System.currentTimeMillis();
			synchronized (obj) {
				//用循环处理假唤醒
				long tmpOverTime = channelOverTime;
				while(isReciveMsg){
					obj.wait(tmpOverTime);
					if(MessageBufferPool.getInstance().getMsg(msgId)==null){
						//判断是否需要二次等待
						isReciveMsg = System.currentTimeMillis()-startTime <tmpOverTime;
						
						if(isReciveMsg){
							//如果需要进入二次等待计算二次等待时间
							tmpOverTime = channelOverTime-(System.currentTimeMillis()-startTime);//计算二次等待时间
							startTime = System.currentTimeMillis();
						}
					}else{
						break;
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("Sync waiting to be interrupted",e);
			return null;//TODO 异常处理有问题
		}
		
		if(isReciveMsg){
			resmsg = MessageBufferPool.getInstance().removeMsg(msgId);
		}else{
			//如果没有收到应用的响应报文，用请求报文做响应
			//删除同步等待锁对象
			MessageBufferPool.getInstance().removeSynObj(msgId);
			logger.error("message is overTime msgid is  ["+msgId+"]");
			resmsg = message;
		}
		return resmsg;
	}
	
	private String getMsgId(){
		return TimeUtil.getCurrentTime("HHmmssSSS")+"_"+UUID.randomUUID().toString();
	}
}
