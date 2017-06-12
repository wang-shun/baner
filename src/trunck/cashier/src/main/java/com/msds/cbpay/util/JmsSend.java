package com.msds.cbpay.util;

import com.ztkx.cbpay.platformutil.activemq.messagesend.MessageSender;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderList;
import com.ztkx.cbpay.platformutil.activemq.messagesend.SenderManager;

public class JmsSend {
	public static void send(byte[] bytes) throws Exception{
		SenderList senderlist = SenderManager.getSenderList("cashier_discenter");
		MessageSender sender = senderlist.pool();
		sender.Send(bytes, null);
		if(senderlist != null && sender != null){
			senderlist.add(sender);
		}
	}
}
