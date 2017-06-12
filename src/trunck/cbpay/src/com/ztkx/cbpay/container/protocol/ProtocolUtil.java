package com.ztkx.cbpay.container.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;

public class ProtocolUtil {

	/**
	 * 创建socket客户端
	 * 
	 * @param host
	 * @param port
	 * @return
	 * @throws IOException
	 *             2016年3月6日 上午9:59:57
	 * @author zhangxiaoyun
	 */
	public static Socket createSocketClient(String host, int port,
			int connectTimeOut, int readTimeOut) throws IOException {
		Socket client = new Socket();
		client.setKeepAlive(true);
		client.setReuseAddress(false);
		client.setReceiveBufferSize(8192);
		client.setSendBufferSize(8192);
		client.setSoLinger(false, -1);
		client.setTcpNoDelay(true);
		client.setSoTimeout(readTimeOut); // 读取超时时间
		InetSocketAddress iaddres = new InetSocketAddress(host, port);
		client.connect(iaddres, connectTimeOut); // 链接超时时间
		return client;
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param readTimeOut
	 * @return 2016年3月4日 下午4:09:33
	 * @author zhangxiaoyun
	 * @throws IOException
	 */
	public static ServerSocket createSocketServer(String host, int port)
			throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.setSoTimeout(0); // socket做服务端的时候不超时
		ss.setReuseAddress(false); // 关闭 SO_REUSEADDR 套接字选项
		ss.setReceiveBufferSize(8192);
		InetSocketAddress iaddres = new InetSocketAddress(port);
		ss.bind(iaddres);
		return ss;
	}
	
	/**
	 * httpserver 使用
	 * @param protocolConfig
	 * @param content
	 * @param out
	 * @throws IOException
	 * 2016年3月21日 下午2:49:52
	 * @author zhangxiaoyun
	 */
//	public static void writeByServletOut(ProtocolConfig protocolConfig, byte[] content,ServletOutputStream out) throws IOException {
//		
//		String policy = getWriterPolicy(protocolConfig);
//		String param = getWriterParams(protocolConfig);
//		
//		PolicyProxy.writeParam(out, param);
//		
//		switch (policy) {
//		case ProtocolConstantField.POLICY_STREAM_OVER_FLAG:
//			// 动态长度以流结束标记表示结束
//			PolicyProxy.writeByStreamOverFlag(out, content);
//			break;
//		case ProtocolConstantField.POLICY_DYNAMIC_LEN:
//			// 动态长度，有长度头
//			PolicyProxy.writeBydynamicLen(protocolConfig, out, content);
//			break;
//		case ProtocolConstantField.POLICY_FIX:
//			// 固定长度
//			break;
//		case ProtocolConstantField.POLICY_SPECIALC:
//			// 已特殊字符结束
//			break;
//		default:
//
//			break;
//		}
//	}
	
	public static void writebyHttp(ProtocolConfig protocolConfig, byte[] content,OutputStream out) throws IOException {

		String policy = getWriterPolicy(protocolConfig);

		switch (policy) {
		case ProtocolConstantField.POLICY_STREAM_OVER_FLAG:
			// 动态长度以流结束标记表示结束
			PolicyProxy.writeHttpByStreamOverFlag(out, content,protocolConfig);
			break;
		case ProtocolConstantField.POLICY_DYNAMIC_LEN:
			// 动态长度，有长度头
			PolicyProxy.writeBydynamicLen(protocolConfig, out, content);
			break;
		case ProtocolConstantField.POLICY_FIX:
			// 固定长度
			break;
		case ProtocolConstantField.POLICY_SPECIALC:
			// 已特殊字符结束
			break;
		case ProtocolConstantField.POLICY_DATAPARAM:{
			PolicyProxy.writeByDataParam(protocolConfig, out, content);
			break;
		}
		default:

			break;
		}
	}

	public static void writeBySocket(ProtocolConfig protocolConfig,
			byte[] content, OutputStream out, Socket socket) throws IOException {

		String policy = getWriterPolicy(protocolConfig);

		switch (policy) {
		case ProtocolConstantField.POLICY_STREAM_OVER_FLAG:
			// 动态长度以流结束标记表示结束
			PolicyProxy.writeSocketByStreamOverFlag(out, content, socket);
			break;
		case ProtocolConstantField.POLICY_DYNAMIC_LEN:
			// 动态长度，有长度头
			PolicyProxy.writeBydynamicLen(protocolConfig, out, content);
			break;
		case ProtocolConstantField.POLICY_FIX:
			// 固定长度
			break;
		case ProtocolConstantField.POLICY_SPECIALC:
			// 已特殊字符结束
			break;
		default:

			break;
		}
	}

	/**
	 * 获取写策略
	 * 
	 * @param protocolConfig
	 * @return 2016年3月4日 下午6:19:17
	 * @author zhangxiaoyun
	 */
	private static String getWriterPolicy(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		String policy = null;
		// 根据协议标志获取写策略，
		// 如果是客户端协议 读策略应该从responseConfig中获取
		// 如果是服务端协议 读策略应该从requestConfig中获取
		if (flag.equals(ProtocolConstantField.FLAG_CLIENT)) {
			policy = protocolConfig.getRequestConfig().getPolicy();
		} else if (flag.equals(ProtocolConstantField.FLAG_SERVER)) {
			policy = protocolConfig.getResponseConfig().getPolicy();
		}
		return policy;
	}
	
	
	/**
	 * 获取会写参数 
	 * 
	 * @param protocolConfig
	 * @return 2016年3月4日 下午6:19:17
	 * @author zhangxiaoyun
	 */
	private static String getWriterParams(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		String param = null;
		// 根据协议标志获取写策略，
		// 如果是客户端协议 读策略应该从responseConfig中获取
		// 如果是服务端协议 读策略应该从requestConfig中获取
		if (flag.equals(ProtocolConstantField.FLAG_CLIENT)) {
			param = protocolConfig.getRequestConfig().getDataParams();
		} else if (flag.equals(ProtocolConstantField.FLAG_SERVER)) {
			param = protocolConfig.getResponseConfig().getDataParams();
		}
		return param;
	}

	/**
	 * 读取报文
	 * @param protocolConfig
	 * @param in
	 * @return
	 * @throws IOException
	 *             2016年3月4日 下午5:43:33
	 * @author zhangxiaoyun
	 */
	public static byte[] read(ProtocolConfig protocolConfig, InputStream in)
			throws IOException {
		byte[] resMsg = null;

		String policy = null;
		// 根据协议标志获取读取策略，
		policy = getReadPolicy(protocolConfig);

		switch (policy) {

		case ProtocolConstantField.POLICY_STREAM_OVER_FLAG:
			// 动态长度以流结束标记表示结束
			resMsg = PolicyProxy.readByStreamOverFlag(protocolConfig,in);
			break;
		case ProtocolConstantField.POLICY_DYNAMIC_LEN:
			resMsg = PolicyProxy.readBydynamicLen(protocolConfig, in);
			// 动态长度，有长度头
			break;
		case ProtocolConstantField.POLICY_FIX:
			// 固定长度
			break;
		case ProtocolConstantField.POLICY_SPECIALC:
			// 已特殊字符结束
			break;
		case ProtocolConstantField.POLICY_DATAPARAM:
		{
			resMsg = PolicyProxy.readByDataParam(protocolConfig, in);
			break;
		}
		
		default:

			break;
		}
		return resMsg;
	}

	/**
	 * 获取读策略
	 * 
	 * @param protocolConfig
	 * @return 2016年3月4日 下午6:17:26
	 * @author zhangxiaoyun
	 */
	private static String getReadPolicy(ProtocolConfig protocolConfig) {
		// 如果是客户端协议 读策略应该从responseConfig中获取
		// 如果是服务端协议 读策略应该从requestConfig中获取
		String policy = null;
		String flag = protocolConfig.getCommonConfig().getFlag();

		if (flag.equals(ProtocolConstantField.FLAG_CLIENT)) {
			policy = protocolConfig.getResponseConfig().getPolicy();
		} else if (flag.equals(ProtocolConstantField.FLAG_SERVER)) {
			policy = protocolConfig.getRequestConfig().getPolicy();
		}
		return policy;
	}

	/**
	 * 通过返回流得到返回的字符串
	 * 
	 * @param stream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static byte[] getErrorMsg(InputStream stream) throws IOException {
		try {
			ByteArrayOutputStream writer = new ByteArrayOutputStream();

			byte[] buff = new byte[8192];
			int count = 0;
			while ((count = stream.read(buff)) > 0) {
				writer.write(buff, 0, count);
			}
			return writer.toByteArray();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 获取http或者https链接
	 * 
	 * @param ctype
	 */
	public static HttpURLConnection getHttpConnection(ProtocolConfig config,String ctype,String file) throws IOException {
		String type = config.getCommonConfig().getType();
		URL url = new URL(type, config.getRequestConfig().getHost(), config.getRequestConfig().getPort(), file);
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(config.getRequestConfig().getMethod());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "stargate");
//		conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		conn.setRequestProperty("Content-Type", ctype);
		conn.setConnectTimeout(config.getRequestConfig().getConnectTimeout());
		conn.setReadTimeout(config.getResponseConfig().getReadTimeout());
		return conn;
	}

	/**
	 * 获取 https 链接
	 * 
	 * @param config
	 * @param ctype
	 * @return
	 * @throws Exception
	 */
	public static HttpsURLConnection getHttpsConnection(ProtocolConfig config,String ctype,String file) throws Exception {
		// 获取一个SSLContext实例
		SSLContext ctx = config.getRequestConfig().getSslcontext();
		String type = config.getCommonConfig().getType();
		URL url = new URL(type, config.getRequestConfig().getHost(), config
				.getRequestConfig().getPort(), file);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(ctx.getSocketFactory());
		conn.setRequestMethod(config.getRequestConfig().getMethod());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "stargate");
		conn.setRequestProperty("Content-Type", ctype);
		conn.setConnectTimeout(config.getRequestConfig().getConnectTimeout());
		conn.setReadTimeout(config.getResponseConfig().getReadTimeout());
		return conn;
	}
}
