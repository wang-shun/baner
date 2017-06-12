package com.ztkx.transplat.container.protocol.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.protocol.ProtocolUtil;
import com.ztkx.transplat.container.protocol.config.CommonConfig;
import com.ztkx.transplat.container.protocol.config.ProtocolConfig;
import com.ztkx.transplat.container.protocol.config.RequestConfig;
import com.ztkx.transplat.container.protocol.config.ResponseConfig;
import com.ztkx.transplat.platformutil.context.CommonContext;

public class TCPClientProcessImp implements ProtocolProcess{
	
	Logger logger = Logger.getLogger(TCPClientProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	private boolean isStart = false;
	private Socket socket  = null;
	
	@Override
	public void start(ProtocolConfig conifg) {
		isStart = true;
		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		requestConfig = this.protocolConfig.getRequestConfig();
		responseConfig = this.protocolConfig.getResponseConfig();
		logger.info(conifg.getCommonConfig().getId()+" protocol start success");
	}
	
	@Override
	public void stop(){
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId()+" protocol already stop");
	}
	
	@Override
	public byte[] send(CommonContext context) throws Exception {
		if(!isStart){
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}
		
		//创建socket客户端
		logger.info("host ["+requestConfig.getHost()+"]");
		logger.info("port ["+requestConfig.getPort()+"]");
		logger.info("connection timeOut ["+requestConfig.getConnectTimeout()+"]");
		logger.info("read Timeout ["+responseConfig.getReadTimeout()+"]");
		socket = ProtocolUtil.createSocketClient(requestConfig.getHost(), requestConfig.getPort(), requestConfig.getConnectTimeout(), responseConfig.getReadTimeout());
		
		OutputStream out = null;
		InputStream in = null;
		byte[] resmsg = null;
		try{
			out = socket.getOutputStream();
			byte[] msg = context.getOrginalField().getBytes(commonConfig.getEncoding());
			ProtocolUtil.writeBySocket(protocolConfig, msg, out, socket);
			in = socket.getInputStream();
			resmsg = ProtocolUtil.read(protocolConfig, in);
		}catch(IOException e){
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000006);
			logger.error("protocol send msg exception ",e);
			throw new Exception(e);
		}finally{
			/**
			 * 释放资源
			 */
			freeSystemSource(in,out);
		}
		
		return resmsg;
	}
	
	/**
	 * 释放系统资源 
	 * @param in
	 * @param out
	 * 2016年3月6日 上午9:41:06
	 * @author zhangxiaoyun
	 */
	private void freeSystemSource(InputStream in, OutputStream out) {
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				logger.error("close inputstream exception",e);
			}
		}
		if(out!=null){
			try {
				out.close();
			} catch (IOException e) {
				logger.error("close outputStream exception",e);
			}
		}
		if(socket!=null && !socket.isClosed()){
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("close client socket exception",e);
			}
		}
	}

	@Override
	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}
	@Override
	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	@Override
	public void setStatus(boolean status) {
		this.isStart = status;
	}

	@Override
	public boolean isStart() {
		return isStart;
	}

	@Override
	public byte[] send(CommonContext context, Map<String, String> pro) throws Exception {
		// TODO 不需要实现
		return null;
	}
}
