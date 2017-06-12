package com.ztkx.transplat.platformutil.activemq.messagesend;

import java.util.Map;

/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月5日 上午9:59:39 
 */
public class SenderUtil {
	
	/**
	 * 发送对象类型的报文
	 * 2016年7月5日 上午11:17:27
	 * @author zhangxiaoyun
	 * @param obj
	 * @param serviceName		队列名称
	 * @param titileMap			消息头
	 * @throws Exception
	 * @return void
	 */
	public static void sendObj(MessageObj obj,String serviceName,Map<String,String> titileMap) throws Exception{
		SenderList senderlist = null;
		MessageSender  sender = null;
		try {
			//发送消息
			senderlist = SenderManager.getSenderList(serviceName);
			sender = senderlist.pool();
			sender.SendObj(obj, titileMap);
		} catch (Exception e) {
			throw e;
		}finally{
			if(senderlist != null && sender != null){
				senderlist.add(sender);
			}
		}
	}
}
