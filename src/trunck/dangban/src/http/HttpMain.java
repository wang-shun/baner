package http;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class HttpMain {
	//private static final String xmlpath = "bin/http/util/httpResource.xml";
	public String home_dir = "/ztkx/cbpay/dangban/http";
	public String lib_dir = "lib";
	public static void main(String[] args) {
		HttpMain httpmain = new HttpMain();
		System.out.println("HttpMain loadjar is begin");
		//加载系统参数
		httpmain.getStartupParams();
		//启动前准备，加载lib目录下所有jar包
		File[] fileList = httpmain.prestartup(httpmain.lib_dir);
				
		//获取当前classloader
		ClassLoader loader = httpmain.getCurrentClassLoader();
		//把lib目录下的所有jar包加载到classloader里
		try {
			URLClassLoader urlLoader = httpmain.loadJarFile(loader,fileList);
			System.out.println("jar load end");
			Class<?> clazz = urlLoader.loadClass("http.ResManage");
			Method md = clazz.getMethod("manage", null);
			md.invoke(clazz.newInstance(), null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * 获取启动参数
	 * 初始化home_dir和lib_dir
	 */
	public  void getStartupParams(){
		lib_dir = home_dir+File.separator+lib_dir;
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
	
	private ClassLoader getCurrentClassLoader() {
		return this.getClass().getClassLoader();
	}
	
	private URLClassLoader loadJarFile(ClassLoader loader,File[] jarFile) throws  MalformedURLException {
		URL[] urls = new URL[jarFile.length];
		for (int i = 0; i < jarFile.length; i++) {
			File f = jarFile[i];
			System.out.println(f.getAbsolutePath());
			urls[i]=f.toURI().toURL();
		}
		URLClassLoader clssLoader = new URLClassLoader(urls, loader);
		return clssLoader;
	}
}
