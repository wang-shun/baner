package com.ztkx.transplat.container.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class MessageUtil {
	
	
	/**
	 * 根据报文解析出 对应某个字段
	 * @param msg
	 * @param labelName
	 * @return
	 * @throws XMLStreamException 
	 */
	public static String getTranCode(byte[] msg,String labelName) throws XMLStreamException{
		XMLStreamReader reader = null;
		try {
			reader = getXmlStreamReader(msg);
			//跳过root标签以及root标签以前的空格
			reader.nextTag();
			int event = 0;
			while(reader.hasNext()){
				event = reader.next();
				if(event == XMLStreamReader.START_ELEMENT){
					String eleName = reader.getLocalName();
					if(eleName.equals(labelName)){
						return reader.getElementText();
					}
				}
			}
		} catch (XMLStreamException e) {
			throw e;
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (XMLStreamException e) {
					throw e;
				}
			}
		}
		
		return null;
		
	}
	
	private static XMLStreamReader getXmlStreamReader(byte[] content) throws XMLStreamException {
		XMLStreamReader reader = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		XMLInputFactory factory = XMLInputFactory.newFactory();
		try {
			reader = factory.createXMLStreamReader(bis);
		} catch (XMLStreamException e) {
			throw e;
		}
		return reader;
	}
	
	/**
	 * 此方法将BytesMessage,中写入的byte数组取出还原
	 * 
	 * @param BytesMessage
	 * 
	 * @return byte 数组
	 * @throws JMSException
	 */
	public static byte[] resolveByteArrayMessageFromJMSMessage(BytesMessage bm) throws JMSException {
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int count = 0;
		try {
			while ((count = bm.readBytes(buff)) != -1) {
				writer.write(buff, 0, count);
			}
			return writer.toByteArray();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
