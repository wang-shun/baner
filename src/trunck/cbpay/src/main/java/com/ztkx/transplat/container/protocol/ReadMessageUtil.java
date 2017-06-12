package com.ztkx.transplat.container.protocol;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

public class ReadMessageUtil {
	
	
	/**
	 * 读取参数
	 * @param contentLength
	 * @param in
	 * @param paraLen
	 * @return
	 * @throws IOException
	 */
	public static byte[] readParam(long contentLength, ServletInputStream in,int paraLen) throws IOException {
		
		if(contentLength!=-1){
			if(paraLen>=contentLength){
				throw new IOException("param length  error param length is ["+paraLen+"]");
			}
		}
		byte[] buffer = new byte[paraLen];
		int readSize = 0;
		int size = 0;
		//读http参数
		while((readSize = in.read(buffer,size,paraLen-size))>=0 && size<paraLen){
			size+=readSize;
			if(size == paraLen){
				break;
			}
		}
		//将等号读出来扔掉    
		if(size==paraLen){
			int len = 1;
			size = 0;
			byte[] equalBuffer = new byte[len];
			while((readSize = in.read(equalBuffer,size,len))>=0 && size<paraLen){
				if(readSize==len){
					break;
				}
			}
		}else{
			throw new IOException("read http param exception");
		}
		return buffer;
	}
	
	/**
	 * 读取参数
	 * @param contentLength
	 * @param in
	 * @param paraLen
	 * @return
	 * @throws IOException
	 */
	public static byte[] readParam(InputStream in,int paraLen) throws IOException {
		
		
		byte[] buffer = new byte[paraLen];
		int readSize = 0;
		int size = 0;
		//读http参数
		while((readSize = in.read(buffer,size,paraLen-size))>=0 && size<paraLen){
			size+=readSize;
			if(size == paraLen){
				break;
			}
		}
		//将等号读出来扔掉    
		if(size==paraLen){
			int len = 1;
			size = 0;
			byte[] equalBuffer = new byte[len];
			while((readSize = in.read(equalBuffer,size,len))>=0 && size<paraLen){
				if(readSize==len){
					break;
				}
			}
		}else{
			throw new IOException("read http param exception");
		}
		return buffer;
	}
	
	
	/**
	 * 读取报文头
	 * @param contentLength
	 * @param in
	 * @param msgHeadLen
	 * @return
	 * @throws IOException
	 */
	public static byte[] readMessageHeader(long contentLength, ServletInputStream in,int msgHeadLen) throws IOException {
		if(msgHeadLen>=contentLength){
			throw new IOException("msgHeadLen error msgHeadLen is ["+msgHeadLen+"]");
		}
		byte[] buffer = new byte[msgHeadLen];
		int readSize = 0;
		int size = 0;
		while((readSize = in.read(buffer,size,msgHeadLen-size))>=0 && size<msgHeadLen){
			size+=readSize;
			if(size == msgHeadLen){
				break;
			}
		}
		if(size!=msgHeadLen){
			throw new IOException("read message header length exception");
		}
		return buffer;
	}

	/**
	 * 读取报文体
	 * @param in
	 * @param msgLen
	 * @return
	 */
	public static byte[] readMessageBody(ServletInputStream in, int msgLen) throws IOException{
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
			throw new IOException("read message body exception size ["+size+"] msgLen ["+msgLen+"]");
		}
		return buffer;
	}
}
