package com.ztkx.cbpay.container.protocol.process;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.protocol.ProtocolConstantField;
import com.ztkx.cbpay.container.protocol.ProtocolUtil;
import com.ztkx.cbpay.container.protocol.ReadMessageUtil;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.container.protocol.config.RequestConfig;
import com.ztkx.cbpay.container.protocol.config.ResponseConfig;
import com.ztkx.cbpay.discenter.core.MessageDiscenter;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * http 服务端协议
 * 
 * @author zhangxiaoyun 2016年1月27日 下午4:12:05
 *         <p>
 *         Company:ztkx
 *         </p>
 */
public class HTTPServerProcessImp implements ProtocolProcess {

	static Logger logger = Logger.getLogger(HTTPServerProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	private boolean isStart = false;
	private Server httpServer;

	// private ServletHandler servletHandler;
	@Override
	public void start(ProtocolConfig conifg) {

		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		requestConfig = this.protocolConfig.getRequestConfig();
		responseConfig = this.protocolConfig.getResponseConfig();
		/**
		 * 启动http服务 1.初始化jetty server 2.初始化servletHandler 3.在handler上注入serlvet
		 * 4.启动server
		 */
		httpServer = new Server();

		// HTTP Configuration
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("http");
		http_config.setOutputBufferSize(32768);
		http_config.setRequestHeaderSize(8192);
		http_config.setResponseHeaderSize(8192);

		ServerConnector http = new ServerConnector(httpServer,
				new HttpConnectionFactory(http_config));

		http.setPort(requestConfig.getPort());
		http.setIdleTimeout(30000);

		httpServer.setConnectors(new Connector[] { http });

		// servletHandler = new ServletHandler();
		// servletHandler.addServletWithMapping(HTTPMessageHandler.class,
		// requestConfig.getFile());
		HTTPMessageHandler handler = new HTTPMessageHandler();
		httpServer.setHandler(handler);
		try {
			httpServer.start();
		} catch (Exception e) {
			logger.error("start jetty server fail" + httpServer, e);
		}
		isStart = true;
		logger.info(conifg.getCommonConfig().getId()
				+ " protocol start success");
	}

	@Override
	public void stop() {
		isStart = false;
		if (httpServer != null) {
			try {
				httpServer.stop();
			} catch (Exception e) {
				logger.error("stop jetty server fail" + httpServer, e);
			}
		}
		this.protocolConfig = null;
		logger.info(commonConfig.getId() + " protocol already stop");
	}

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

	class HTTPMessageHandler extends AbstractHandler {

		@Override
		public void handle(String target, Request baseRequest,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
//			PrintWriter out = response.getWriter();
			logger.info("discenter receive msg");
			ServletOutputStream outputStrem = response.getOutputStream();
			ServletInputStream in = request.getInputStream();
			String remoteHost = request.getRemoteAddr();		//远端地址
			// 如果为动态长度 获取长度头
			String policy = requestConfig.getPolicy();
			int msgHeadLen = requestConfig.getMsgHeadLen();

			// 读取报文体
			byte[] messageBuffer = null;

			// 获取Content-Length
			String contentLengthStr = request.getHeader("Content-Length");
			long contentLength = Long.parseLong(contentLengthStr);
			
			switch (policy) {
			case ProtocolConstantField.POLICY_DYNAMIC_LEN: {
				//检测Content-Length
				if (contentLength > requestConfig.getMaxLen()) {
					logger.error("message length exceed protocol define max length current length is ["+ contentLength+ "+ max length is ["+ requestConfig.getMaxLen() + "]");
					String resMsg = "message length exceed protocol define max length current length is ["+ contentLength+ "+ max length is ["+ requestConfig.getMaxLen() + "]";
					ProtocolUtil.writebyHttp(protocolConfig, resMsg.getBytes(),outputStrem);
					outputStrem.close();
					return;
				}
				
				//读取动态长度
				byte[] msgLenBytes = ReadMessageUtil.readMessageHeader(contentLength, in, msgHeadLen);
				int msgLen = Integer.parseInt(new String(msgLenBytes, commonConfig.getEncoding()));
				messageBuffer = ReadMessageUtil.readMessageBody(in, msgLen);
				break;
			}
			case ProtocolConstantField.POLICY_FIX: {
				long msgLen = requestConfig.getMaxLen();
				if (msgLen != contentLength) {
					logger.error("msg head check fail msgLenHead is [" + msgLen+ "] actural msg len is [" + contentLength + "]");
					String message = "msg head check fail msgLenHead is " + msgLen+ " actural msg len is " + contentLength;
					ProtocolUtil.writebyHttp(protocolConfig, message.getBytes(), outputStrem);
					outputStrem.close();
					return;
				}
				//读取根据长度读取报文内容
				messageBuffer = ReadMessageUtil.readMessageBody(in, new Long(contentLength).intValue());
				break;
			}
			case ProtocolConstantField.POLICY_DATAPARAM:{
				// 检测参数名称
				String dataParam = requestConfig.getDataParams();
				if(StringUtils.isEmpty(dataParam)){
					ServletException e =  new ServletException("data param config error!!");
					logger.error("data param config error!!",e);
					throw e;
				}
				
				byte[] paramNamebytes = ReadMessageUtil.readParam(contentLength, in, dataParam.length());
				contentLength = contentLength - (dataParam.length() + 1);
				String paramName = new String(paramNamebytes,commonConfig.getEncoding());
				if (!paramName.equals(dataParam)) {
					logger.error("param name error current paramName is ["+ paramName + "] + config paramName is ["+ dataParam + "]");
					String resMsg = "param name error current paramName is ["+ paramName + "] + config paramName is ["+ dataParam + "]";
					ProtocolUtil.writebyHttp(protocolConfig, resMsg.getBytes(),outputStrem);
					outputStrem.close();
					return;
				}
				messageBuffer = ReadMessageUtil.readMessageBody(in, new Long(contentLength).intValue());
				break;
			}
			default:
				break;
			}
			
			String message = new String(messageBuffer, commonConfig.getEncoding());
			message = URLDecoder.decode(message, commonConfig.getEncoding());
			logger.info("recive msg from remoteHost[" + remoteHost + "] msg info [" + message + "]");
			
			byte[] bytes = message.getBytes(commonConfig.getEncoding());
			Map<String, String> map = new HashMap<String, String>();
			map.put(ContainerConstantField.TRANFROM, commonConfig.getServerId());
			map.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig
					.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
			map.put(ContainerConstantField.REQ_URL, baseRequest.getRequestURI());

			/**
			 * 1.生成消息唯一序列号 2.将序列号注入到jsm message里 3.获取mqsender 4.将对象入缓冲队列
			 * 5.轮询将报文发送到mq上 6.当前线程等待
			 */
			byte[] resMsg = MessageDiscenter.getInstance().processMessage(bytes, map);
			
			ProtocolUtil.writebyHttp(protocolConfig, resMsg, outputStrem);
			outputStrem.close();
		}
	}
}
