package com.msds.cbpay.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class SendMsg {
	static Logger logger = Logger.getLogger(StartUpLoader.class);
	/**
	 * tcp����
	 * @param msg
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String tcpsendPlat(String msg) throws UnsupportedEncodingException, IOException {
		byte[] msgByteArray = TCPClientUtil.send(msg.getBytes(StartUpLoader.baseParamBean.getEncode()));
		msg = new String(msgByteArray,StartUpLoader.baseParamBean.getEncode());
		return msg;
	}
	/**
	 * jms����
	 * @param msg
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	
	public static void jmssendPlat(String msg) throws UnsupportedEncodingException, Exception {
		JmsSend.send(msg.getBytes(StartUpLoader.baseParamBean.getEncode()));
	}
	/**
	 * ����
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	public static String encodeOrg(String msg,String trancode) throws Exception {
		byte[] msgByteArray = null;
		logger.info("decode is begin");
		if (logger.isDebugEnabled()) {
			logger.debug("request msg["+msg+"]");
		}
		msg = Base64Util.encode(msg);
		msg = MakeXML.makeSecretTrancodeXml(msg,trancode);
		msgByteArray = TCPClientUtil.send(msg.getBytes(StartUpLoader.baseParamBean.getEncode()));
		msg = new String(msgByteArray,StartUpLoader.baseParamBean.getEncode());
		if (logger.isDebugEnabled()) {
			logger.debug("response msg["+msg+"]");
		}
		logger.info("decode is succ");
		return msg;
	}
}
