package com.ztkx.cbpay.platformutil.activemq.config;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;

public class ParseServiceInfoUtil {
	static Logger logger = Logger.getLogger(ParseServiceInfoUtil.class);
	private static String LABEL_INITIAL_CONTEXT_FACTORY="INITIAL_CONTEXT_FACTORY";
	private static String LABEL_OVERTIME="overTime";
	private static String LABEL_AUTOACKNOWLEDGE="autoAcknowledge";
	private static String LABEL_ISTRANSACTION = "ISTRANSACTION";
	private static String LABEL_MESSAGELISTENER = "messageListener";
	private static String LABEL_PROVIDER_URL="PROVIDER_URL";
	private static String LABEL_QUEUENAME="queueName";
	private static String LABEL_SESSIONCOUNTS="sessionCounts";
	private static String LABEL_SELECTOR="selector";
	
	public static ServiceInfo parse(Element service){
		ServiceInfo serviceInfo = new ServiceInfo();
		if (service.getName().equalsIgnoreCase("service")) {
			serviceInfo.setServiceName(service
					.attributeValue("name"));
			// 对service标签下的url等标签进行遍历，并封装信息
			Iterator servicesInfos = service.elementIterator();
			while (servicesInfos.hasNext()) {
				Element servicesInfo = (Element) servicesInfos
						.next();
				if (servicesInfo.getName().equalsIgnoreCase(LABEL_PROVIDER_URL)) {
					serviceInfo.setUrl(servicesInfo.getTextTrim());
				} else if (servicesInfo.getName().equalsIgnoreCase(LABEL_QUEUENAME)) {
					serviceInfo.setQueueName(servicesInfo
							.getTextTrim());
				} else if (servicesInfo.getName().equalsIgnoreCase(LABEL_SESSIONCOUNTS)) {
					try {
						serviceInfo.setSessionCounts(Integer.parseInt(servicesInfo.getTextTrim()));
					} catch (NumberFormatException e) {
						logger.error("number format error ", e);
						serviceInfo.setSessionCounts(0);
					}
				} else if (servicesInfo.getName().equalsIgnoreCase(LABEL_INITIAL_CONTEXT_FACTORY)) {
					serviceInfo.setFactory(servicesInfo.getTextTrim());
				} else if (servicesInfo.getName().equalsIgnoreCase(LABEL_OVERTIME)){
					try {
						serviceInfo.setOverTime(Integer.parseInt(servicesInfo.getText()));
					} catch (NumberFormatException e) {
						logger.error("number format error ", e);
						serviceInfo.setOverTime(0);
					}
				}
				else if(servicesInfo.getName().equalsIgnoreCase(LABEL_AUTOACKNOWLEDGE)){
					serviceInfo.setAutoAcknowledge(Boolean.parseBoolean(servicesInfo.getText()));
				}
				else if(servicesInfo.getName().equals(LABEL_ISTRANSACTION)){
					serviceInfo.setTransaction(Boolean.parseBoolean(servicesInfo.getText()));
				}
				/**
				 * 2016-3-13 21:18:27
				 * 将messageListener的配置添加到每个队列的属性上，这样可以每个队列都使用不同的监听器
				 */
				else if(servicesInfo.getName().equals(LABEL_MESSAGELISTENER) && StringUtils.isNotEmpty(servicesInfo.getText())){
					serviceInfo.setMsgListener(servicesInfo.getText());
				}else if(servicesInfo.getName().equals(LABEL_SELECTOR) && StringUtils.isNotEmpty(servicesInfo.getText())){
					try {
						logger.info("selector org["+servicesInfo.getText()+"]");
						String texttmp = servicesInfo.getText();
						String key = "";
						String text = "";
						boolean flag = false;
						for(int i = 0 ;i < texttmp.length();i++){
							if(texttmp.charAt(i) == '}'){
								flag  = false;
								text = text + BaseConfig.getConfig(key);
								key="";
							}
							if(flag){
								key = key + texttmp.charAt(i);
							}else if(!(texttmp.charAt(i) == '}'||texttmp.charAt(i) == '{')){
								text = text + texttmp.charAt(i);
							}
							if(texttmp.charAt(i) == '{'){
								flag  = true;
							}
						}
						logger.info("selector["+text+"]");
						serviceInfo.setSelector(text);
					} catch (NumberFormatException e) {
						logger.error("selector format error ", e);
						serviceInfo.setOverTime(0);
					}
				}
			}
		} 
		return serviceInfo;
	}
}
