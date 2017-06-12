package com.ztkx.cbpay.discenter.mqclient.send;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

public class DisSenderExceptionListener implements ExceptionListener {
	
	private DisMessageSender sender = null;
	private long interval_long = 5000l;
	Logger logger = Logger.getLogger(DisSenderExceptionListener.class);
	
	public DisSenderExceptionListener(DisMessageSender sender){
		this.sender = sender;
	}
	
	@Override
	public void onException(JMSException arg0) {
		logger.error("start reconnection ......");
		sender.setStart(false);
		int count =0;
		//重连前释放资源
		sender.relase();
		boolean reConnection = false;
		
		while(count<Integer.MAX_VALUE && !reConnection){
			count++;
			logger.error("start ["+count+"] reconnection...");
			reConnection = sender.init(sender.getInfo());
			String interval = BaseConfig.getConfig(ConstantConfigField.MQ_RECONNECTION_INTERVAL);
			if(StringUtils.isNotBlank(interval)){
				interval_long = Long.parseLong(interval);
			}
			
			try {
				Thread.sleep(interval_long);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
