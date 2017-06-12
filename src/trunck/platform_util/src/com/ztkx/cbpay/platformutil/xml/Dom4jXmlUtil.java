package com.ztkx.cbpay.platformutil.xml;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 以dom4j的方式获取xml对象
 * 
 * @author zhangxiaoyun
 *
 */
public class Dom4jXmlUtil {

	static Logger logger = Logger.getLogger(Dom4jXmlUtil.class);
	
	/**
	 * 根据xml路径以dom4j的方式获取Document
	 * @param xmlPath   xml路径
	 * @return
	 */
	private static Document getDocument(String xmlPath) {
		
		SAXReader reader = new SAXReader();                
	    Document document = null;
		try {
			document = reader.read(new File(xmlPath));
		} catch (DocumentException e) {
			logger.error("parse xml exception"+xmlPath,e);
		}  
		return document;
	}
	
	
	/**
	 * 根据xml路径以dom4j的方式获取Document
	 * @param xmlPath   xml路径
	 * @return
	 */
	private static Document getDocument(File file) {
		
		SAXReader reader = new SAXReader();                
	    Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			logger.error("parse xml exception"+file.getAbsolutePath(),e);
		}  
		return document;
	}
	
	/**
	 * 获取 xml的root标签
	 * @param xmlPath
	 * @return
	 */
	public static Element getRootElement(String xmlPath){
		Document document = getDocument(xmlPath);
		Element ele = null;
		if(document != null){
			ele = document.getRootElement();
		}
		
		return ele;
		
	}
	
	/**
	 * 获取 xml的内容获取xml的root标签
	 * @param xmlPath
	 * @return
	 */
	public static Element getRootElementByStr(String xmlContexts){
		Document document = null;
		try {
			document = DocumentHelper.parseText(xmlContexts);
		} catch (DocumentException e) {
			logger.error("parse xml by strConstext exception ["+xmlContexts+"]",e);
		}
		Element ele = null;
		if(document != null){
			ele = document.getRootElement();
		}
		return ele;
		
	}
	
	/**
	 * 根据file获取xml root标签
	 * @param file
	 * @return
	 */
	public static Element getRootElement(File file){
		Document document = getDocument(file);
		Element ele = null;
		if(document != null){
			ele = document.getRootElement();
		}
		return ele;
	}
	
	
}
