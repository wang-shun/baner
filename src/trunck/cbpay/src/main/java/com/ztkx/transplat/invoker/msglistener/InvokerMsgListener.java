package com.ztkx.transplat.invoker.msglistener;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.invoker.InvokerHandler;
import com.ztkx.transplat.invoker.bean.InvokerParams;
import com.ztkx.transplat.platformutil.activemq.messagesend.SenderUtil;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.log.FlowNoContainer;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;
/**
 * 
 * @ClassName: InvokerMsgListener
 * @Description: 命令服务端消息监听器
 * @author zhangxiaoyun
 * @date 2016年7月5日 下午4:36:59
 */
public class InvokerMsgListener  implements MessageListener {

	Logger logger = Logger.getLogger(InvokerMsgListener.class);
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) message;
			try {
				InvokerParams params =  (InvokerParams) om.getObject();
				FlowNoContainer.setFlowNo(params.getSerialId());
				logger.info("recive msg is [ "+params+"]");
				MessageHandler handler = new MessageHandler(params);
				ThreadPoolManager.getExecutorService().execute(handler);
			} catch (JMSException e) {
				logger.error("recive message exception",e);
			}
		}
	}
	
	class MessageHandler implements Runnable{
		private InvokerParams params = null;
		
		public MessageHandler(InvokerParams params){
			this.params = params;
		}
		@Override
		public void run() {
			FlowNoContainer.setFlowNo(params.getSerialId());
			boolean res = false;
			if(params.getMode().equals(InvokerParams.MODE_SYN)){
				//同步命令
				res = InvokerHandler.handlerSyn(params);
				
			}else if(params.getMode().equals(InvokerParams.MODE_ASYN)){
				res = InvokerHandler.handlerAsyn(params);
			}
			params.setSucc(res);
			Map<String, String> titileMap = new HashMap<String, String>();
			titileMap.put(ConstantConfigField.CONTAINID, params.getSourceNode());
			try {
				SenderUtil.sendObj(params, ContainerConstantField.PROTOCOL_SERVICE_NAME_CONSOLE_OTHER, titileMap);
			} catch (Exception e) {
				logger.error("send message exception",e);
			}
		}
	}
}
