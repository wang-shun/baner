package com.ztkx.transplat.container.protocol.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.protocol.ProtocolUtil;
import com.ztkx.transplat.container.protocol.config.CommonConfig;
import com.ztkx.transplat.container.protocol.config.ProtocolConfig;
import com.ztkx.transplat.container.protocol.config.RequestConfig;
import com.ztkx.transplat.container.protocol.config.ResponseConfig;
import com.ztkx.transplat.discenter.core.MessageDiscenter;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;

/**
 * TCP协议服务端
 * @author zhangxiaoyun
 * 2016年3月4日 下午3:41:52
 * <p>Company:ztkx</p>
 */
public class TCPServerProcessImp implements ProtocolProcess {

	static Logger logger = Logger.getLogger(TCPServerProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	private boolean isStart = false;
	private ServerSocket server;
	private Socket socket ;
	// private ServletHandler servletHandler;
	@Override
	public void start(ProtocolConfig conifg) {

		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		requestConfig = this.protocolConfig.getRequestConfig();
		responseConfig = this.protocolConfig.getResponseConfig();
		
		/**
		 * 1.创建socketServer
		 * 2.绑定端口和ip
		 * 3.建立监听
		 * 4.接收到客户端连接后获取线程处理后续流程
		 * 5.关闭连接
		 */
		try {
			server = ProtocolUtil.createSocketServer(requestConfig.getHost(), requestConfig.getPort());
			isStart = true;
			
			receive();	//开始接收客户端的报文
			
		} catch (IOException e) {
			logger.error("create serverSocket exception",e);
		}
		logger.info(conifg.getCommonConfig().getId()+ " protocol start success");
	}

	/**
	 * 接收客户端的请求报文
	 * @throws IOException
	 * 2016年3月4日 下午5:23:49
	 * @author zhangxiaoyun
	 */
	private void receive() throws IOException {
		if (!isStart) {
			logger.error("protocol not startup ");
			throw new IOException("protocol not startup");
		}
		
		while((socket = server.accept())!=null){
			TCPMessageHandler handler = new TCPMessageHandler(socket);
			ThreadPoolManager.getExecutorService().execute(handler);
		}
	}

	@Override
	public void stop() {
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId() + " protocol already stop");
	}

	/**
	 * 没用
	 */
	@Override
	public byte[] send(CommonContext context) throws Exception {
		if (!isStart) {
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}

		return null;
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
	public byte[] send(CommonContext context, Map<String, String> pro)
			throws Exception {
		// TODO 不需要实现
		return null;
	}

	class TCPMessageHandler implements Runnable {
		private Socket socket = null;
		public TCPMessageHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			InputStream in = null;
			OutputStream out = null;
			Exception superExp = null;
			try {
				in = socket.getInputStream();

				//读取请求报文
				byte[] msg = ProtocolUtil.read(protocolConfig, in);
				logger.debug("  ["+socket.getRemoteSocketAddress()+"]");
				logger.info("client address ["+socket.getRemoteSocketAddress()+"] msg info [" + new String(msg,commonConfig.getEncoding()) + "]");
				Map<String, String> map = new HashMap<String, String>();
				map.put(ContainerConstantField.TRANFROM,
						commonConfig.getServerId());
				map.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig
						.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));

				// tcp协议没有url
				// map.put(ContainerConstantField.REQ_URL,
				// baseRequest.getRequestURI());

				/**
				 * 1.生成消息唯一序列号 2.将序列号注入到jsm message里 3.获取mqsender 4.将对象入缓冲队列
				 * 5.轮询将报文发送到mq上 6.当前线程等待
				 */
				byte[] resMsg = MessageDiscenter.getInstance().processMessage(msg, map);
				
				//开始响应
				out = socket.getOutputStream();
				ProtocolUtil.writeBySocket(protocolConfig, resMsg, out,socket);
				
			}
			catch(Exception e){
				logger.error("recive client msg exception, client is "+commonConfig.getId(),e);
			}
			finally{
				/**
				 * 释放资源
				 */
				freeSystemSource(in, out);
				
			}
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
	}
}
