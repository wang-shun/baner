package com.msds.cbpay.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.msds.cbpay.entity.BaseParamBean;
import com.ztkx.cbpay.platformutil.activemq.ActiveMQInitManager;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

public class StartUpLoader implements ServletContextListener{
	public static Properties prop;
	public static Properties bankprop;
	public static BaseParamBean baseParamBean = new BaseParamBean();
	public static Map<String,String> bankmap = new HashMap<String,String>();
	public static JSONArray jsonArray = null;
	public static DecimalFormat format;
	Logger logger = Logger.getLogger(StartUpLoader.class);
	private void loader(){
		prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/baseparam.properties");
		try {
			prop.load(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("not found file baseparam.properties",e);
		}finally{
		}
	}
	private void parse(){
		 Field[] fieldArray = baseParamBean.getClass().getDeclaredFields();
		 for(Field field : fieldArray){
			String value = prop.getProperty(field.getName());
			field.setAccessible(true);
			logger.info("key["+field.getName()+"] : vlaue["+value+"] : type ["+field.getType()+"]");
			try{
				if(field.getType().getName().equals("int")){
					field.set(baseParamBean, Integer.parseInt(value));
				}else if(field.getType().getName().equals("long")){
					field.set(baseParamBean, Long.parseLong(value));
				}else{
					field.set(baseParamBean, value);
				}
			}catch(Exception e){
				logger.error("key["+field.getName()+"] parse error", e);
			}
		 }
	}
	private void initUtil(){
		TCPClientUtil.connectTimeOut = baseParamBean.getConnectTimeout();
		TCPClientUtil.readTimeOut = baseParamBean.getReadTimeout();
		TCPClientUtil.host = baseParamBean.getDiscenter_host();
		TCPClientUtil.port = baseParamBean.getDiscenter_port();
		TCPClientUtil.encode = baseParamBean.getEncode();
		XmlReversalBean.encode = baseParamBean.getEncode();
		String format = "";
		for(int i = 0;i<baseParamBean.getMsgheadlength();i++){
			format = format + "0";
		}
		TCPClientUtil.df = new DecimalFormat(format);
		TCPClientUtil.maxLength = baseParamBean.getMaxLength();
		TCPClientUtil.msgheadlength = baseParamBean.getMsgheadlength();
		Base64Util.charset = baseParamBean.getEncode();
		HttpSend.connectTimeout = baseParamBean.getConnectTimeout();
		HttpSend.readTimeout = baseParamBean.getReadTimeout();
	}
	public void loadbank(){
		bankprop = new Properties();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/bank.properties")));
			bankprop.load(br);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("not found file bank.properties",e);
		}finally{
		}
	}
	private void dealbank() {
		try {
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(Object pro : StartUpLoader.bankprop.keySet()){
				logger.debug("bank key["+pro.toString()+"]  value["+StartUpLoader.bankprop.getProperty(pro.toString())+"]");
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", pro.toString());
				map.put("text",StartUpLoader.bankprop.getProperty(pro.toString()));
				bankmap.put(pro.toString(),StartUpLoader.bankprop.getProperty(pro.toString()));
				list.add(map);
			}
			jsonArray = JSONArray.fromObject(list);
		} catch (Exception e) {
			logger.error("encode is error",e);
		}
	}
	private void createMq(){
		BaseConfig.getInstence("");
		String container_path = System.getProperty("container_path");
		logger.info("config_path ["+container_path+"]");
		BaseConfig.setConfig(ConstantConfigField.CONFIGPATH, container_path);
		ActiveMQInitManager.getInstance().instance();
	}
	private void msgHeadFormat(){
		String formatValue = "";
		for(int i=0;i<baseParamBean.getMsgheadlength();i++){
			formatValue = formatValue + "0";
		}
		logger.info("msg head formatValue["+formatValue+"]");
		format = new DecimalFormat(formatValue);
	}
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.debug("start exec loader ");
		loader();
		logger.debug("finish exec loader ");
		parse();
		logger.debug("finish exec parse ");
		initUtil();
		logger.debug("finish exec initUtil ");
		loadbank();
		logger.debug("finish exec loadbank ");
		dealbank();
		logger.debug("finish exec dealbank ");
		createMq();
		logger.debug("finish exec createMq ");
		msgHeadFormat();
		logger.debug("finish exec msgHeadFormat ");
	}
/*	public static void main(String[] args) {
		StartUpLoader loader= new StartUpLoader();
		loader.loadbank();
		loader.dealbank();
	}*/
}
