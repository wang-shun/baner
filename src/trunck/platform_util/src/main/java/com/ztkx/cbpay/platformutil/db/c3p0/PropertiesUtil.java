package com.ztkx.cbpay.platformutil.db.c3p0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 读取配置文件的工具类
 * 
 * @author tianguangzhao
 *
 */

public class PropertiesUtil {

	private static PropertiesUtil propertiesUtil = null;
	static Logger logger = Logger.getLogger(PropertiesUtil.class);

	// 构造方法私有化
	private PropertiesUtil() {

	}

	// 单例模式获取对象
	public static PropertiesUtil getInstence() {
		if (propertiesUtil == null) {
			synchronized (PropertiesUtil.class) {
				if (propertiesUtil == null) {
					propertiesUtil = new PropertiesUtil();
				}
			}
		}

		return propertiesUtil;
	}

	// 根据传入信息读取配置文件参数，filename是 文件路径+文件名，key是参数名，
	public String getProperties(String filename, String key) {
		String value = "";
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(filename));
			Properties prop = new Properties();
			prop.load(inStream);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e) {
			logger.error("FileNotFound filename=" + filename, e);
		} catch (IOException e) {
			logger.error("IOException filename=" + filename + "|key=" + key, e);
		} finally {
			try {
				inStream.close();
			} catch (Exception e2) {
				logger.error("close FileInputStream error", e2);
			}
		}
		return value;
	}

}
