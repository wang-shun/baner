package com.ztkx.cbpay.container.run.startup;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 所有容器的启动主类，这个类测试通过以后不需要改动
 * @author zhagnxiaoyun
 *
 */
public class MainRun {
	public String home_dir = "";
	public String lib_dir = "";
	/**
	 * 获取启动参数
	 * 初始化home_dir和lib_dir
	 */
	public  void getStartupParams(){
		home_dir = System.getProperty(ConstantStartupField.HOME_DIR);
		System.out.println("home_dir:"+home_dir);
		lib_dir = home_dir+ File.separator+"lib"+File.separator;
		System.out.println("home_dir ["+home_dir+"]");
		System.out.println("lib_dir ["+lib_dir+"]");
		System.getProperties().list(System.out);
	}
	
	/**
	 * 启动前准备
	 */
	public File[]  prestartup(String libPath){
		System.out.println("lib_dir["+libPath+"]");
		File f = new File(libPath);
		 File[] list = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		
		return list;
	}
	
	/**
	 * 将项目中所有的jar包加载到jvm中
	 * @param loader
	 * @param jarFile
	 * @return
	 * @throws MalformedURLException 
	 */
	private URLClassLoader loadJarFile(ClassLoader loader,File[] jarFile) throws  MalformedURLException {
		URL[] urls = new URL[jarFile.length];
		for (int i = 0; i < jarFile.length; i++) {
			File f = jarFile[i];
			urls[i]=f.toURI().toURL();
		}
		URLClassLoader clssLoader = new URLClassLoader(urls, loader);
		return clssLoader;
	}

	private ClassLoader getCurrentClassLoader() {
		return this.getClass().getClassLoader();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		if(args.length!=2){
			throw new Exception("params error");
		}
		
		MainRun mainRun = new MainRun();
		//加载系统参数
		mainRun.getStartupParams();
		
		//启动前准备，加载lib目录下所有jar包
		File[] fileList = mainRun.prestartup(mainRun.lib_dir);
		
		//获取当前classloader
		ClassLoader loader = mainRun.getCurrentClassLoader();
		
		//把lib目录下的所有jar包加载到classloader里
		URLClassLoader urlLoader = mainRun.loadJarFile(loader,fileList);
		
		//容器名称
		String param1 = args[0];
		System.setProperty(ConstantStartupField.CONTAINER, param1);
		//实际命令
		String param2 = args[1];
		System.setProperty(ConstantStartupField.COMMAND, param2);
		//吊起主启动类
		Class<?> clazz = urlLoader.loadClass("com.ztkx.cbpay.container.run.startup.MainRunnableStartup");
		Constructor  constructor = clazz.getDeclaredConstructor(ClassLoader.class);
		constructor.setAccessible(true);
		MainRunnableStartup mainRunnable  = (MainRunnableStartup)constructor.newInstance(urlLoader);
//		= (MainRunnableStartup)clazz.newInstance();
		Thread t = new Thread(mainRunnable);
//		t.setContextClassLoader(urlLoader);     //如果测的时候不行再加上
		t.start();
	}
}
