package com.ztkx.transplat.invoker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.transplat.invoker.bean.ExecutorBean;
import com.ztkx.transplat.invoker.bean.InvokerBean;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.xml.Dom4jXmlUtil;

public class InvokerManager{
	
	private static InvokerManager manager = null;
	String invokerFileName = ConstantConfigField.INVOKER_CONFIGFILE;
	String filePaht = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+ File.separator + "invokers" + File.separator;
	
	private Map<String,InvokerBean> invokerBeanMap = new HashMap<String,InvokerBean>();
	
	Logger logger = Logger.getLogger(InvokerManager.class);
	
	
	private InvokerManager(){
		//解析invokers.xml
		parseXml();
	}
	
	
	public static InvokerManager getInstance(){
		if(manager==null){
			synchronized (InvokerManager.class) {
				if(manager == null){
					manager = new InvokerManager();
				}
			}
		}
		return manager;
	}
	
	/**
	 * 根据id获取对应invoer的bean
	 * 2016年7月4日 上午11:46:38
	 * @author zhangxiaoyun
	 * @param id
	 * @return
	 * @return InvokerBean
	 */
	public InvokerBean getInvokerById(String id){
		return invokerBeanMap.get(id);
	}
	
	
	private void parseXml(){
		Element rootEle = Dom4jXmlUtil.getRootElement(filePaht+invokerFileName);
		List<Element> invokerList = rootEle.elements();
		for (Element ele : invokerList) {
			String invokerId = ele.attributeValue("id");
			String desc = ele.attributeValue("desc");
			String impl = ele.attributeValue("impl");
			InvokerBean invoker = new InvokerBean(invokerId, desc, impl);
			List<Element> executorList = ele.elements();
			List<ExecutorBean> executorBeans = new ArrayList<ExecutorBean>();
			
			for (Element executor : executorList) {
				String exeId = executor.attributeValue("id");
				String type = executor.attributeValue("type");
				int sequence = Integer.parseInt(executor.attributeValue("sequence"));
				ExecutorBean executorBean = new ExecutorBean(type, exeId, sequence);
				executorBeans.add(executorBean);
			}
			ExecutorBean[] tmp = new ExecutorBean[executorBeans.size()];
			tmp = executorBeans.toArray(tmp);
			Arrays.sort(tmp);
			invoker.setExecutorList(Arrays.asList(tmp));
			invokerBeanMap.put(invokerId, invoker);
		}
	}
	
	
//	public void factory(List<InitialBean> list) {
//		// TODO Auto-generated method stub
//		this.dataInit = list;
//		InitialBean bean = null;
//		for (int i = 0; i < list.size(); i++) {
//			try {
//				bean = list.get(i);
//				logger.info("start invoke [" + bean.getId() + "] Data loader");
//				String imp = bean.getImpl();
//				Class<?> clazz = Class.forName(imp);
//				CommdExecuter service = (CommdExecuter)clazz.newInstance();
//				map.put(bean.getId(), service);
//			} catch (Exception e) {
//				logger.error("load init data exception loader id is["+bean+"]",e);
//			}
//		}
//	}

}
