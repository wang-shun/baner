package com.msds.cbpay.service;

import org.apache.log4j.Logger;

import com.msds.cbpay.util.HttpSend;


public class AsynSendMerchantService implements Runnable{
	Logger logger = Logger.getLogger(AsynSendMerchantService.class);
	private byte[] msg;
	private String url;
	public AsynSendMerchantService(String url,byte[] msg){
		this.msg = msg;
		this.url = url;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			HttpSend.doHttpPost(url, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("send msg is error",e);
		}
	}

}
