package com.cashier.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class TestInit  implements ServletContextListener{

	Logger logger = Logger.getLogger(TestInit.class);
	private void initTest(){
		try{
			logger.info("test.properties["+System.getProperty("container_path")+"/"+"test.properties"+"]");
			File testproperties = new File((System.getProperty("container_path")+"/"+"test.properties"));
			if(testproperties.isFile()){
				Properties prop = new Properties();
				logger.info("load test.properties begin");
				prop.load(new FileInputStream(testproperties));
				CashierTestConstant.keyStoreFile = (String) prop.get("keyStoreFile");
				logger.info("keyStoreFile["+CashierTestConstant.keyStoreFile+"]");
				CashierTestConstant.keyStoreAlias = (String) prop.get("keyStoreAlias");
				logger.info("keyStoreAlias["+CashierTestConstant.keyStoreAlias+"]");
				CashierTestConstant.keyStorePassword = (String) prop.get("keyStorePassword");
				logger.info("keyStorePassword["+CashierTestConstant.keyStorePassword+"]");
				CashierTestConstant.publicCertificateFile = (String) prop.get("publicCertificateFile");
				logger.info("publicCertificateFile["+CashierTestConstant.publicCertificateFile+"]");
				CashierTestConstant.url = (String) prop.get("url");
				logger.info("url["+CashierTestConstant.url+"]");
				CashierTestConstant.connectTimeout = Integer.parseInt((String)prop.get("connectTimeout")) ;
				logger.info("connectTimeout["+CashierTestConstant.connectTimeout+"]");
				CashierTestConstant.readTimeout = Integer.parseInt((String)prop.get("readTimeout"));
				logger.info("readTimeout["+CashierTestConstant.readTimeout+"]");
				logger.info("load test.properties end");
			}
		}catch(Exception e){
			logger.warn("test plat init error",e);
		}finally{
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("init test begin");
		this.initTest();
		logger.info("init test end");
	}

}
