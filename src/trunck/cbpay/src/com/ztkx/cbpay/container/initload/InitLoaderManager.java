package com.ztkx.cbpay.container.initload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * 加载init.xml
 * 根据type的class属性，吊起响应的factory
 * @author zhangxiaoyun
 *
 */
public class InitLoaderManager {
	private static InitLoaderManager initLoaderManager = null;
	private static String TYPE="type";													//平台基础数据

	String configFileName = ConstantConfigField.INIT_DATACONFIGFILE;
	String configFilePaht = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+File.separator;
	private Logger logger = Logger.getLogger(InitLoaderManager.class);
	//typeid和factory的键值对
	private Map<String,InitFactory> typeMap = new ConcurrentHashMap<String,InitFactory>();
	
	private InitLoaderManager() {
		
		Element root = Dom4jXmlUtil.getRootElement(configFilePaht + configFileName);
		
		List<Element> typeEleList = root.elements(TYPE);	//获取所有的type标签
		for (int i = 0; i < typeEleList.size(); i++) {
			
			Element typeEle = typeEleList.get(i);
			String type_id = typeEle.attributeValue("id");
			String classes = typeEle.attributeValue("classes");
			InitFactory factory;
			try {
				logger.info("start class.forName classes ["+classes+"] id ["+type_id+"]");
				factory = (InitFactory)Class.forName(classes).newInstance();
				typeMap.put(type_id, factory);
				List<Element> initialEleList = typeEle.elements("initial");	//获取type标签下面的所有initial标签
				if(initialEleList!=null && initialEleList.size()>0){
					List<InitialBean> tmpList = new ArrayList<InitialBean>();
					logger.info("start exec ["+type_id+"] factory");
					for (int j = 0; j < initialEleList.size(); j++) {
						
						Element initialEle = initialEleList.get(j);
						String id = initialEle.attributeValue("id");
						String impl = initialEle.attributeValue("impl");
						InitialBean bean = new InitialBean(id,impl);
						tmpList.add(bean);
					}
					factory.factory(tmpList);
				}else{
					logger.warn(type_id+"initial element size is 0");
				}
				
			} catch (Exception e) {
				logger.error("exec ["+type_id+"] factory exception",e );
			}
		}
	}
	
	public static InitLoaderManager getInstance() {
		if (initLoaderManager == null) {
			synchronized(InitLoaderManager.class){
				if(initLoaderManager == null){
					initLoaderManager = new InitLoaderManager();
				}
			}
		}
		return initLoaderManager;
	}
	
	/**
	 * 根据factory id获取对应的factory 
	 * 	private static final String TYPE_DATAINIT="dataInit";								//平台基础数据
	 *	private static final String TYPE_SYSHANDLER="sysHandler";							//系统特殊处理
	 *	private static final String TYPE_TRANCODEHANDLER="identifyHandler";					//交易码特殊处理
	 *	private static final String TYPE_ENANDDECRYPTIONTOOLS="enAndDecryptionTools";		//加解密工具
	 * @param type_id
	 * @return
	 */
	public InitFactory getFactory(String type_id){
		return typeMap.get(type_id);
	}
}
