package com.msds.cbpay.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class XmlReversalBean {
	public static String encode = "UTF-8";
	static Logger logger = Logger.getLogger(XmlReversalBean.class);
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String msg,Class<T>... t) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(t); 
        Unmarshaller unmarshaller = context.createUnmarshaller();  
        T bean = (T)unmarshaller.unmarshal(new StringReader(msg));
        return bean;
	}
	
	public static <T> String beanToXml(T t,Class... clazz) throws JAXBException, UnsupportedEncodingException{
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		 JAXBContext context = JAXBContext.newInstance(clazz);
         Marshaller marshaller = context.createMarshaller();  
         logger.debug("encode ["+encode+"]");
         marshaller.setProperty(Marshaller.JAXB_ENCODING,encode);
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
         marshaller.marshal(t, os); 
         return os.toString(encode);
	}
}
