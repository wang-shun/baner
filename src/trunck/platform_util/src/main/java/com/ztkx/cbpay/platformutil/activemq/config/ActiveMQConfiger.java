package com.ztkx.cbpay.platformutil.activemq.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * 负责读取ActiveMQconfig.xml配置文件中的配置信息，并进行封装
 * 
 * @author 田光照 2015/12/22
 * update by zhangxiaoyun 2015/12/26
 */
public class ActiveMQConfiger {

	private static ActiveMQConfiger xmlFileReader = null;
	private static List<ServiceInfo> inBoxList;
	private static List<ServiceInfo> outBoxList;
	private String configFileName = ConstantConfigField.ACTIVEMQ_CONFIGFILE;
	private String configFilePath = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+File.separator+"protocol"+File.separator;
	

	static Logger logger = Logger.getLogger(ActiveMQConfiger.class);

	// 构造方法私有
	private ActiveMQConfiger() {
		// 调用readXmlParam方法将配置信息读入内存中
		readXmlParam();
	}

	// 单例获取对象,双重检查
	public static ActiveMQConfiger getInstance() {
		if (xmlFileReader == null) {
			synchronized (ActiveMQConfiger.class) {
				if (xmlFileReader == null) {
					xmlFileReader = new ActiveMQConfiger();
				}
			}
		}
		return xmlFileReader;
	}

	// 生成inBoxList和outBoxList的set和get方法
	public List<ServiceInfo> getInBoxList() {
		return inBoxList;
	}

	public void setInBoxList(List<ServiceInfo> inBoxList) {
		ActiveMQConfiger.inBoxList = inBoxList;
	}

	public List<ServiceInfo> getOutBoxList() {
		return outBoxList;
	}

	public void setOutBoxList(List<ServiceInfo> outBoxList) {
		ActiveMQConfiger.outBoxList = outBoxList;
	}

	// 解析xml配置文件，并将配置信息进行封装,并加载到内存里
	private void readXmlParam() {
		// 首先读取文件到内存中获取document对象
		Element root = Dom4jXmlUtil.getRootElement(configFilePath+configFileName);
		// root下子节点的list
		Iterator it = root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			if (element.getName().equalsIgnoreCase("inboxs")) {
				// inboxs标签中的配置信息进行封装
				inBoxList = resolve(element);
			} else if (element.getName().equalsIgnoreCase("outboxs")) {
				// 对outbox标签中的信息进行封装
				outBoxList = resolve(element);
			} else {
				logger.info("xml file build error file name is " + configFilePath);
			}

		}

	}

	// 读取xml文件获取document对象
	private Document ReadFile() {
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		// 读取文件 转换成Document
		Document document;
		try {
			document = reader.read(new File(configFilePath+configFileName));
		} catch (DocumentException e) {
			logger.error("read xml file error file name is " + configFilePath, e);
			return null;
		}

		return document;
	}

	// 逐步解析element，将配置信息存入相应的list中
	private List<ServiceInfo> resolve(Element element) {
		List<ServiceInfo> list = new ArrayList<ServiceInfo>();
		// 获取inboxs或outbox下面的配置标签并核对标签个数
		List<Element> boxs = element.elements();
		if (boxs == null || boxs.size() == 0) {
			logger.info("xml file build error file name is " + configFilePath);
			return null;
		} else {
			for (Element box : boxs) {
				ServiceInfo serviceInfo = new ServiceInfo();
				// 对inbox或者outbox标签的下一级标签service进行遍历
				Iterator services = box.elementIterator();
				while (services.hasNext()) {
					Element service = (Element) services.next();
					ServiceInfo info = ParseServiceInfoUtil.parse(service);
					list.add(info);
				}
			}
		}
		return list;
	}
	
	
	public String getConfigFilePath() {
		return configFilePath;
	}
	
	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}
	
	
}
