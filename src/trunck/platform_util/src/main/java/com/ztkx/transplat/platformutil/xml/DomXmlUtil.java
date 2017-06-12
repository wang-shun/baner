package com.ztkx.transplat.platformutil.xml;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * 以dom的形式获取xml文件对象
 * @author zhangxiaoyun
 *
 */
public class DomXmlUtil {

	static Logger logger = Logger.getLogger(DomXmlUtil.class);
	
	/**
	 * 根据xml路径已Dom的方式获取Document
	 * @param xmlPath   xml路径
	 * @return
	 */
	private static Document getDocument(String xmlPath) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		FileInputStream file = null;
		try {
			builder = factory.newDocumentBuilder();
			file = new FileInputStream(xmlPath);
			document = builder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("parse xml exception"+xmlPath,e);
		}
		return document;
	}
	
	/**
	 * 获取dom xml的root标签
	 * @param xmlPath
	 * @return
	 */
	public static Element getRootElement(String xmlPath){
		Document document = getDocument(xmlPath);
		Element ele  = null;
		if(document != null){
			ele= document.getDocumentElement();
		}
		return ele;
		
	}
}
