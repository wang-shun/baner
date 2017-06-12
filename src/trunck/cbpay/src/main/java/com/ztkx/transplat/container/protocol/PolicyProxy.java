package com.ztkx.transplat.container.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.transplat.container.protocol.config.ProtocolConfig;

/**
 * 策略代理器
 * @author zhangxiaoyun
 *
 */
public class PolicyProxy {
	static Logger logger = Logger.getLogger(PolicyProxy.class);
	static String equalSign = "=";
	
	/**
	 * socket协议输入方法
	 * @param out
	 * @param content
	 * @param socket
	 * @throws IOException
	 * 2016年3月6日 上午9:26:46
	 * @author zhangxiaoyun
	 */
	public static void writeSocketByStreamOverFlag(OutputStream out,byte[] content,Socket socket) throws IOException{
		
		out.write(content);
		out.flush();
		socket.shutdownOutput();
	}
	/**
	 * http专用
	 * @param out
	 * @param content
	 * @param protocolConfig
	 * @throws IOException
	 */
	public static void writeHttpByStreamOverFlag(OutputStream out,byte[] content,ProtocolConfig protocolConfig) throws IOException{
//		String dataPamam = getDataParam(protocolConfig,"write");
//		out.write(dataPamam.getBytes(protocolConfig.getCommonConfig().getEncoding()));//写参数
//		out.write(equalSign.getBytes());//写等号
		/**
		 * 2016年5月16日16:48:56  update by zhangxiaoyun
		 */
		out.write(content);
		out.flush();
	}
	/**
	 * 动态长度写数据
	 * @param out
	 * @param content
	 * @throws Exception 
	 */
	public static void writeBydynamicLen(ProtocolConfig protocolConfig,OutputStream out,byte[] content) throws IOException{
		int countLen = content.length;
		long maxLen = getWriteMsgMaxLen(protocolConfig);
		String protocolType = protocolConfig.getCommonConfig().getType();
//		String dataParam = getDataParam(protocolConfig,"write");
		
//		if(ProtocolType.HTTP.getCode().equals(protocolType) || ProtocolType.HTTPS.getCode().equals(protocolType)){
//			countLen=countLen+dataParam.length()+1;
//		}
		
		if(countLen>maxLen){
			logger.error("msg length is not match countLen ["+countLen+"] maxLen ["+maxLen+"]");
			throw new IOException("msg length is overflow max length  countLen ["+countLen+"] maxLen ["+maxLen+"]");
		}
		
		//获取长度头格式化工具
		DecimalFormat df = getWriteDecimalFormat(protocolConfig);
		
		String counthead = df.format(countLen);
		String encode = protocolConfig.getCommonConfig().getEncoding();
		//先写长度头
		out.write(counthead.getBytes());
		out.flush();
		//如果是http协议需要写参数
//		if(ProtocolType.HTTP.getCode().equals(protocolType) || ProtocolType.HTTPS.getCode().equals(protocolType)){
//			out.write(dataParam.getBytes());
//			out.write(equalSign.getBytes());
//		}
		//在写报文体
		out.write(content);
		out.flush();
	}
	
	
	/**
	 * 根据参数名称写数据
	 * @param out
	 * @param content
	 * @throws Exception 
	 */
	public static void writeByDataParam(ProtocolConfig protocolConfig,OutputStream out,byte[] content) throws IOException{
		int countLen = content.length;
		long maxLen = getWriteMsgMaxLen(protocolConfig);
		String protocolType = protocolConfig.getCommonConfig().getType();
		String dataParam = getDataParam(protocolConfig,"write");
		//从新计算报文长度
		if(ProtocolType.HTTP.getCode().equals(protocolType) || ProtocolType.HTTPS.getCode().equals(protocolType)){
			countLen=countLen+dataParam.length()+1;
		}
		
//		如果是http协议需要写参数
		if(ProtocolType.HTTP.getCode().equals(protocolType) || ProtocolType.HTTPS.getCode().equals(protocolType)){
			out.write(dataParam.getBytes());
			out.write(equalSign.getBytes());
		}
		//在写报文体
		out.write(content);
		out.flush();
	}
	
	/**
	 * 以流的结束标记读数据
	 * @param in
	 * @return
	 * @throws IOException
	 * 
	 */
	public static byte[] readByStreamOverFlag(ProtocolConfig protocolConfig,InputStream in) throws IOException {
		
		ByteArrayOutputStream writer = null;
		byte[] resMsg = null;
		try{
			writer = new ByteArrayOutputStream();
			byte[] buff = new byte[8192];
			int count = 0;
			while ((count = in.read(buff)) != -1) {
				writer.write(buff, 0, count);
			}
			resMsg= writer.toByteArray();
		}finally{
			if(writer!=null)
				writer.close();
		}
		return resMsg;
	}

	private static int readHttpDataParam(ProtocolConfig protocolConfig,InputStream in) throws IOException, UnsupportedEncodingException {
		if(ProtocolType.HTTP.getCode().equals(protocolConfig.getCommonConfig().getType()) || ProtocolType.HTTPS.getCode().equals(protocolConfig.getCommonConfig().getType())){
			String dataParam = getDataParam(protocolConfig,"read");
			if(StringUtils.isNotBlank(dataParam)){
				byte[] paramNamebytes = ReadMessageUtil.readParam(in, dataParam.length());
				String paramName = new String(paramNamebytes,protocolConfig.getCommonConfig().getEncoding());
				if (!paramName.equals(dataParam)) {
					logger.error("param name error current paramName is ["+ paramName + "] + config paramName is ["+ dataParam + "]");
					throw new IOException("param name error current paramName is ["+ paramName + "] + config paramName is ["+ dataParam + "]");
				}else{
					return dataParam.length();
				}
			}
		}
		return -1;
	}
	/**
	 * 动态长度读数据
	 * @param protocolConfig
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBydynamicLen(ProtocolConfig protocolConfig,InputStream in) throws IOException {
		
		int msgHeadLen = getReadMsgHeadLen(protocolConfig);	//读取报文长度
		int headLen = getReadHeadLen(protocolConfig);
		byte[] resMsg = null;
		if(headLen==-1){
			//如果报文长度在最前面走这段代码
			//读取报文头
			byte[] msgHead = readMessageHeader(in, msgHeadLen);
			int msgLen = Integer.parseInt(new String(msgHead,protocolConfig.getCommonConfig().getEncoding()));
			//读取报文体
			resMsg = readMessageBody(in,msgLen);
		}else{
			//如果报文长度在前222为报文中走这段代码
			//读取报文头
			byte[] msgHead = readMessageHeader(in, headLen);
			int start = getStartIndex(protocolConfig);
			int end = getEndIndex(protocolConfig);
			byte[] msgLenArray = Arrays.copyOfRange(msgHead, start, end+1);
			int msgLen = Integer.parseInt(new String(msgLenArray,protocolConfig.getCommonConfig().getEncoding()));
			//读取报文体
			byte[] msg = readMessageBody(in,msgLen);
			//将报文头和报文体拼接
			resMsg = arrayJoin(msgHead, msg);
		}
		return resMsg;
	}
	
	
	public static byte[] readByDataParam(ProtocolConfig protocolConfig,InputStream in) throws IOException {
		//读取http数据参数
		int paramsLen = readHttpDataParam(protocolConfig, in);
		byte[] resMsg = readByStreamOverFlag(protocolConfig,in);
		return resMsg;
	}

	private static byte[] arrayJoin(byte[] array1, byte[] array2) {
		byte[] resMsg = new byte[array1.length+array2.length];
		
		for (int i = 0; i < array1.length; i++) {
			resMsg[i]=array1[i];
		}
		
		for (int i = 0; i < array2.length; i++) {
			resMsg[array1.length+i]=array2[i];
		}
		return resMsg;
	}
	
	/**
	 * 获取读消息头的长度
	 * @param protocolConfig
	 * @return
	 * 2016年3月4日 下午6:24:53
	 * @author zhangxiaoyun
	 */
	private static int getReadMsgHeadLen(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		int msgHeadLen = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			msgHeadLen = protocolConfig.getRequestConfig().getMsgHeadLen();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			msgHeadLen = protocolConfig.getResponseConfig().getMsgHeadLen();
		}
		return msgHeadLen;
	}
	
	/**
	 * 获取读消息头的长度
	 * @param protocolConfig
	 * @type read   write
	 * @return
	 * 2016年3月4日 下午6:24:53
	 * @author zhangxiaoyun
	 */
	private static String getDataParam(ProtocolConfig protocolConfig,String type) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		String dataParam = null;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			if(type.equals("write")){
				dataParam = protocolConfig.getResponseConfig().getDataParams();
			}else if(type.equals("read")){
				dataParam = protocolConfig.getRequestConfig().getDataParams();
			}
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			if(type.equals("write")){
				dataParam = protocolConfig.getRequestConfig().getDataParams();
			}else if(type.equals("read")){
				dataParam = protocolConfig.getResponseConfig().getDataParams();
			}
		}
		return dataParam;
	}
	
	private static int getStartIndex(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		int startIndex = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			startIndex = protocolConfig.getRequestConfig().getStart();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			startIndex = protocolConfig.getResponseConfig().getStart();
		}
		return startIndex;
	}
	private static int getEndIndex(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		int endIndex = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			endIndex = protocolConfig.getRequestConfig().getEnd();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			endIndex = protocolConfig.getResponseConfig().getEnd();
		}
		return endIndex;
	}
	
	/**
	 * 获取定长报文头长度的长度
	 * @param protocolConfig
	 * @return
	 */
	private static int getReadHeadLen(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		int headLen = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			headLen = protocolConfig.getRequestConfig().getHeadLen();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			headLen = protocolConfig.getResponseConfig().getHeadLen();
		}
		return headLen;
	}
	
	
	
	/**
	 * 获取写消息头的长度
	 * @param protocolConfig
	 * @return
	 * 2016年3月4日 下午6:24:53
	 * @author zhangxiaoyun
	 */
	private static int getWriteMsgHeadLen(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		int msgHeadLen = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			msgHeadLen = protocolConfig.getResponseConfig().getMsgHeadLen();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			msgHeadLen = protocolConfig.getRequestConfig().getMsgHeadLen();
		}
		return msgHeadLen;
	}
	
	/**
	 * 获取协议的decimalFormat
	 * @param protocolConfig
	 * @return
	 * 2016年3月6日 上午9:14:40
	 * @author zhangxiaoyun
	 */
	private static DecimalFormat getWriteDecimalFormat(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		DecimalFormat decimalFormat = null;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			decimalFormat = protocolConfig.getResponseConfig().getDf();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			decimalFormat = protocolConfig.getRequestConfig().getDf();
		}
		return decimalFormat;
	}
	
	/**
	 * 获取写消息的最大长度
	 * @param protocolConfig
	 * @return
	 * 2016年3月4日 下午6:24:53
	 * @author zhangxiaoyun
	 */
	private static long getWriteMsgMaxLen(ProtocolConfig protocolConfig) {
		String flag = protocolConfig.getCommonConfig().getFlag();
		long msgHeadLen = -1;
		if(flag.equals(ProtocolConstantField.FLAG_SERVER)){
			msgHeadLen = protocolConfig.getResponseConfig().getMaxLen();
		}else if(flag.equals(ProtocolConstantField.FLAG_CLIENT)){
			msgHeadLen = protocolConfig.getRequestConfig().getMaxLen();
		}
		return msgHeadLen;
	}
	
	
	/**
	 * 读取报文头
	 * @param contentLength
	 * @param in
	 * @param msgHeadLen
	 * @return
	 * @throws IOException
	 */
	private static byte[] readMessageHeader(InputStream in,int msgHeadLen) throws IOException {
		
		byte[] buffer = new byte[msgHeadLen];
		int size = 0;
		int readSize = 0;
		while((readSize = in.read(buffer,size,msgHeadLen-size))>=0 && size<msgHeadLen){
			size+=readSize;
			if(size == msgHeadLen){
				break;
			}
		}
		if(size!=msgHeadLen){
			throw new IOException("read message header length exception real size is ["+size+"] msgHeadLen is ["+msgHeadLen+"]");
		}
		return buffer;
	}
	/**
	 * 读取报文体
	 * @param in
	 * @param msgLen
	 * @return
	 */
	private static byte[] readMessageBody(InputStream in, int msgLen) throws IOException{
		byte[] buffer = null;
		int readSize = 0;
		buffer = new byte[msgLen];
		int size = 0;
		while((readSize = in.read(buffer,size,msgLen-size))>=0 && size<msgLen){
			size+=readSize;
			if(size == msgLen){
				break;
			}
		}
		if(size!=msgLen){
			throw new IOException("read message body exception real size is ["+size+"] msgLen is ["+msgLen+"]");
		}
		return buffer;
	}
}
