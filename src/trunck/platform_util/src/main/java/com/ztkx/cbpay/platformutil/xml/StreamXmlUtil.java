package com.ztkx.cbpay.platformutil.xml;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;

/**
 * stream 方式解析xml 工具类
 * @author zhangxiaoyun
 *
 */
public class StreamXmlUtil {
	private static Logger logger = Logger.getLogger(StreamXmlUtil.class);
	
	public static XMLStreamReader getXmlStreamReader(byte[] content) throws XMLStreamException {
		XMLStreamReader reader = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		XMLInputFactory factory = XMLInputFactory.newFactory();
		reader = factory.createXMLStreamReader(bis);
		return reader;
	}
	
	/**
	 * 创建writer以系统默认的编码方式
	 * @param stream
	 * @return
	 * @throws XMLStreamException
	 */
	public static XMLStreamWriter getXmlStreamWriter(OutputStream stream) throws XMLStreamException {
		XMLStreamWriter writer = null;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		writer = factory.createXMLStreamWriter(stream);
		return writer;
	}
	
	/**
	 * 创建和enCoding编码一致的writer
	 * @param stream
	 * @param enCoding	创建writer的编码要和xml头中的编码一致
	 * @return
	 * @throws XMLStreamException
	 */
	public static XMLStreamWriter getXmlStreamWriter(OutputStream stream,String enCoding) throws XMLStreamException {
		XMLStreamWriter writer = null;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		writer = factory.createXMLStreamWriter(stream,enCoding);
		return writer;
	}

	/**
	 * 释放资源
	 * @param reader
	 */
	public static void closeReader(XMLStreamReader reader) {
		if(reader != null){
			try {
				reader.close();
			} catch (XMLStreamException e) {
				logger.error("close XMLStreamReader exception",e);
			}	
		}
	}
	
	/**
	 * 释放资源
	 * @param reader
	 */
	public static void closeWriter(XMLStreamWriter writer) {
		if(writer != null){
			try {
				writer.close();
			} catch (XMLStreamException e) {
				logger.error("close XMLStreamWriter exception",e);
			}	
		}
	}
	
}
