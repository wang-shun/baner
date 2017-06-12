package com.ztkx.transplat.invoker.msglistener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.ztkx.transplat.invoker.bean.InvokerParams;
import com.ztkx.transplat.invoker.client.InvokerParamQueue;
import com.ztkx.transplat.platformutil.log.FlowNoContainer;

/**
 * 命令客户端消息监听器
 * @ClassName: InvokerClientMsgListener
 * @author zhangxiaoyun
 * @date 2016年7月5日 下午4:37:22
 */
public class InvokerClientMsgListener  implements MessageListener {

	Logger logger = Logger.getLogger(InvokerClientMsgListener.class);
	
	@Override
	public void onMessage(Message message) {
		
		logger.info(" message listener start...");
		
		if (message instanceof ObjectMessage) {
			try {
				ObjectMessage om = (ObjectMessage) message;
				InvokerParams params =  (InvokerParams) om.getObject();
				FlowNoContainer.setFlowNo(params.getSerialId());
				logger.info("recive params is "+params);
				InvokerParamQueue.instance.add(params);
				logger.info(" message listener succ ! ");
			} catch (Exception e) {
				logger.error("listener is error", e);
			}
		}
		
	}
}
