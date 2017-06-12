package com.ztkx.cbpay.platformutil.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;

public class ClassLoaderTest {
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		URL[] urls = new URL[1];
		File f = new File("E:\\tmpjar\\platform_util.jar");
		urls[0]=f.toURI().toURL();
		URLClassLoader url = new URLClassLoader(urls, null);
		Class clazz = url.loadClass("com.ztkx.cbpay.platformutil.baseconfig.BaseConfig");
		BaseConfig BaseConfig = (BaseConfig) clazz.newInstance();
		BaseConfig.getConfig("ww");
	}
}
