package com.ztkx.cbpay.container.msglistener;

import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.util.MessageUtil;
import com.ztkx.cbpay.discenter.core.MessageHandler;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.threadpool.ThreadPoolManager;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * discenter mq消息监听器
 * @author zhangxiaoyun
 * 2016年1月28日 下午2:14:24
 * <p>Company:ztkx</p>
 */
public class DiscenterMsgListener implements MessageListener {
	static Logger logger = Logger.getLogger(DiscenterMsgListener.class);

	long timeoutMillisecond = 60 * 1000L;
	
	public DiscenterMsgListener() {
		try {
			// 从配置文件中获取超时时长
			timeoutMillisecond = Long.parseLong(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));

			if (logger.isDebugEnabled()) {
				logger.debug("get timeoutMillisecond success ! timeoutMillisecond = [ "+ timeoutMillisecond + " ]!");
			}

		} catch (Exception e) {
			logger.error("get timeoutMillisecond error param is [ "+ ConstantConfigField.OVER_TIME+ " ] !  now use defult value  [ " + timeoutMillisecond+ " ]", e);
		}
	}
	
	@Override
	public void onMessage(Message msg) {
		String msgId  = null;
		Map<String,String> map = null;
		try {
			map = (Map<String,String>) msg.getObjectProperty(ConstantConfigField.MSG_PROPERTIES);
			msgId = map.get(ContainerConstantField.JMS_MESSAGE_ID);
			if (logger.isDebugEnabled()) {
				logger.debug("get JMS_MESSAGE_ID  success ! JMS_MESSAGE_ID = [ "+ msgId + " ]!");
			}
			
			if(TimeUtil.isOverTime(msg.getJMSTimestamp(),timeoutMillisecond )){
				// 超时后打印超时信息，并将message强转String打印
				logger.error(" message over time ! message id is  [ " + msgId+ " ] ");
				return;
			}
		} catch (JMSException e) {
			logger.error("get message over time error !", e);
			return;
		}
		// 首先判断类型是否是BytesMessage，我们自己的消息都是BytesMessage,如果消息类型错误则结束。
		if (msg instanceof BytesMessage) {

			BytesMessage bm = (BytesMessage) msg;
			// 判断消息的发送方名称，如果为空，则丢弃不作处理。
			byte[] originalmsg = null;
			try {
				originalmsg = MessageUtil.resolveByteArrayMessageFromJMSMessage(bm);
			} catch (JMSException e) {
				logger.error("obtain response message error", e);
			}
			MessageHandler handler = new MessageHandler(originalmsg,msgId);
			
			ThreadPoolManager.getExecutorService().execute(handler);

		} else {
			logger.info("message type error !");
			return;
		}
	}
}