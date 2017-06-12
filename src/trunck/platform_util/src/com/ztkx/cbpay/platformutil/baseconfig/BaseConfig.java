package com.ztkx.cbpay.platformutil.baseconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 平台所以配置的对外接口
 * @author zhangxiaoyun
 *
 */
public class BaseConfig {
	private static BaseConfig baseConfig = null;
	private static Properties confPro;
	static Logger logger = Logger.getLogger(BaseConfig.class);

	//构造方法私有化 
	private BaseConfig(String filename) {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(filename));
			confPro= new Properties();
			confPro.load(inStream);
		} catch (IOException e) {
			confPro= new Properties();//用于cashier调用
			logger.error("FileNotFound filename=" + filename, e);
		} finally {
			try {
				inStream.close();
			} catch (Exception e2) {
				logger.error("close FileInputStream error", e2);
			}
		}
	}
	
	// 单例模式获取对象
	/**
	 * 配置文件路径
	 * @param filePath
	 * @return
	 */
	public static BaseConfig getInstence(String filePath) {
		if (baseConfig == null) {
			synchronized (BaseConfig.class) {
				if (baseConfig == null) {
					baseConfig = new BaseConfig(filePath);
				}
			}
			
		}
		return baseConfig;
	}

	//获取系统配置属性
	public static String getConfig(String key) {
		return confPro.getProperty(key);
	}
	
	public static void setConfig(String key,String value){
		confPro.setProperty(key, value);
	}
}
