package com.msds.cbpay.util;

import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.protocol.PolicyProxy;
import com.ztkx.cbpay.container.protocol.ProtocolUtil;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfigImp;
import com.ztkx.cbpay.container.protocol.config.RequestConfig;
import com.ztkx.cbpay.container.protocol.config.ResponseConfig;

public class TCPClientUtil {
	static Logger logger = Logger.getLogger(TCPClientUtil.class);
	public static String host;
	public static int port;
	public static int connectTimeOut;
	public static int readTimeOut;
	public static DecimalFormat df;
	public static String encode;
	public static int maxLength;
	public static int msgheadlength;
	public static byte[] send(byte[] msg) throws IOException{
		Socket socket = null;
		try{
		socket = ProtocolUtil.createSocketClient(host, port, connectTimeOut, readTimeOut);
		CommonConfig commonConfig = new CommonConfig();
		commonConfig.setFlag("client");
		commonConfig.setEncoding(encode);
		RequestConfig requestConfig = new RequestConfig();
		requestConfig.setDf(df);
		requestConfig.setMaxLen(maxLength);
		ResponseConfig responseConfig = new ResponseConfig();
		responseConfig.setDf(df);
		responseConfig.setMaxLen(maxLength);
		responseConfig.setMsgHeadLen(msgheadlength);
		ProtocolConfig protocolConfig = new ProtocolConfigImp();
		protocolConfig.setCommonConfig(commonConfig);
		protocolConfig.setRequestConfig(requestConfig);
		protocolConfig.setResponseConfig(responseConfig);
		PolicyProxy.writeBydynamicLen(protocolConfig, socket.getOutputStream(), msg);
		msg =  PolicyProxy.readBydynamicLen(protocolConfig, socket.getInputStream());
		}finally{
			freeSystemSource(socket);
		}
		return msg;
	}
	private static void freeSystemSource(Socket socket) {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("close client socket exception", e);
			}
		}
	}
}
