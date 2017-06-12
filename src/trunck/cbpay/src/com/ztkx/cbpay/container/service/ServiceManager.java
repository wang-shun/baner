package com.ztkx.cbpay.container.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.container.service.intface.Services;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

public class ServiceManager {
	private static ServiceManager manager = null;
	String configFileName = ConstantConfigField.INIT_SERVICECONFIGFILE;
	String configFilePath = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+File.separator+"services"+File.separator;
	static Map<String,Services> baseServiceMap = new HashMap<String,Services>();	//基础服务map
	static Map<String,Services> busiServiceMap = new HashMap<String,Services>();	//业务服务map
	static Map<String,Services> proServiceMap = new HashMap<String,Services>();//协议服务map
	private static String DEFAULTPROSERVICENAME = "defaultProService"; 
	private static Logger logger = Logger.getLogger(ServiceManager.class);

	
	private ServiceManager() {
		// 1.解析配置文件
		List<ServiceConfBean> list = parseConifg(configFilePath + configFileName);
		ServiceConfBean bean = null;
		
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				try {
					logger.info("start load service id [" + bean.getId() + "] imp ["+bean.getImpl()+"]");
					String imp = bean.getImpl();
					Class<?> clazz = Class.forName(imp);
					Services service = (Services)clazz.newInstance();
					if(bean.getType().equals(ConstantConfigField.SERVICE_TYPE_BASE)){
						baseServiceMap.put(bean.getId(), service);
					}else if(bean.getType().equals(ConstantConfigField.SERVICE_TYPE_BUS)){
						busiServiceMap.put(bean.getId(), service);
					}else if(bean.getType().equals(ConstantConfigField.SERVICE_TYPE_PRO)){
						proServiceMap.put(bean.getId(), service);
					}
				} catch (Exception e) {
					logger.error("service load exception loader id is["+bean+"]",e);
				}
			}
		
	}
	
	/**
	 * 获取基础服务
	 * @param id
	 * @return
	 */
	@Deprecated
	public static Services getBaseService(String id){
		return baseServiceMap.get(id);
	}
	/**
	 * 获取业务服务
	 * @param id
	 * @return
	 */
	@Deprecated
	public static Services getBusService(String id){
		return busiServiceMap.get(id);
	}
	
	/**
	 * 根据服务id和服务类型获取服务实体对象
	 * @param serviceId
	 * @param serviceType
	 * @return
	 */
	public static Services getService(String serviceId,String serviceType){
		Services service = null;
		switch (serviceType) {
		case ConstantConfigField.SERVICE_TYPE_BASE:
			service = baseServiceMap.get(serviceId);
			break;
		case ConstantConfigField.SERVICE_TYPE_BUS:
			service = busiServiceMap.get(serviceId);
			break;
		case ConstantConfigField.SERVICE_TYPE_PRO:
			service = proServiceMap.get(DEFAULTPROSERVICENAME);
			break;
		default:
			logger.error("service type is error. service id ["+serviceId+"] type ["+serviceType+"]");
			break;
		}
		return service;
	}
	
	public static ServiceManager getInstance() {
		if (manager == null) {
			synchronized(ServiceManager.class){
				if(manager == null){
					manager = new ServiceManager();
				}
			}
		}
		return manager;
	}
	
	private List<ServiceConfBean> parseConifg(String file){
		Map<String,String> tmp = new HashMap<String,String>();	//临时变量检查是否有重复数据
		Element root = Dom4jXmlUtil.getRootElement(file);
		List<Element> eleList = root.elements("service");
		List<ServiceConfBean> list = new ArrayList<ServiceConfBean>();
		for (int i = 0; i < eleList.size(); i++) {
			Element ele = eleList.get(i);
			String id = ele.attributeValue("id");
			if(!tmp.containsKey(id)){
				String type = ele.attributeValue("type");
				String impl = ele.attributeValue("impl");
				ServiceConfBean bean = new ServiceConfBean(id,type,impl);
				if(logger.isDebugEnabled()){
					logger.debug(bean);
				}
				list.add(bean);
				tmp.put(id, id);
			}
			else{
				logger.error("repetition id ["+id+"]");
			}
		}
		return list;
	}
}
