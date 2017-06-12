package com.ztkx.cbpay.container.run.startup;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

public class MainRunnableStartup implements Runnable{
	static Map<String,String> containerMap = new HashMap<String, String>();
	FileLock infilelock = null;
	private ClassLoader classLoader = null;
	static{
		containerMap.put("IN", "com.ztkx.cbpay.container.run.startup.InContainerStartup");
		containerMap.put("OUT", "com.ztkx.cbpay.container.run.startup.OutContainerStartup");
		containerMap.put("PROCESS", "com.ztkx.cbpay.container.run.startup.ProcessContainerStartup");
		containerMap.put("DISCENTER", "com.ztkx.cbpay.container.run.startup.DiscenterContainerStartup");
		containerMap.put("TIMEDTASK", "com.ztkx.timedtask.run.TimedTaskStartup");
	}
	
	public MainRunnableStartup(ClassLoader classLoader){
		this.classLoader = classLoader;
		URLClassLoader CL = (URLClassLoader)classLoader;
		URL[] urls = CL.getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i]);
		}
		
	}
	
	/**
	 * 锁定文件
	 * @throws IOException 
	 */
	private void tryLockFile() throws IOException  {
		String container = System.getProperty(ConstantStartupField.CONTAINER);
		String filePath = container + "_container.lock";
		File inlockfile = new File(filePath);
		if (!inlockfile.isFile()) {
			inlockfile.createNewFile();
		} 
		infilelock = RunUtils.getFileLock(inlockfile);
		if (null == infilelock || !infilelock.isValid()) {
			System.out.println(container + "try file locak fail!");
			System.out.println("plase check system process");
			throw new IOException("try ["+filePath+"] file lock exception");
		}
	}
	
	/**
	 * 注册关闭钩子
	 */
	private void registerShutdownHook() {
		//注册关闭钩子
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				try {
					infilelock.release();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public static void sendCloseCommand(String port){
		try {
			Socket client = new Socket(ConstantStartupField.LOCALIP, Integer.parseInt(port));
			OutputStream out = client.getOutputStream();
			out.write(ConstantStartupField.COMMAND_STOP.getBytes());
			out.flush();
			client.shutdownOutput();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		String container = System.getProperty(ConstantStartupField.CONTAINER);
		String command = System.getProperty(ConstantStartupField.COMMAND);
		String closePort = System.getProperty(ConstantStartupField.CLOSE_PORT);
		Thread.currentThread().setContextClassLoader(classLoader);
		if(command.equals(ConstantStartupField.COMMAND_STOP)){
			//关闭jvm
			System.out.println("["+container+"] container closeing ...." );
			//关闭容器释放资源
			sendCloseCommand(closePort);
		}else if(command.equals(ConstantStartupField.COMMAND_STARTUP)){
			
			try {
				//创建文件锁
				tryLockFile();
				//注册关闭钩子
				registerShutdownHook();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}
			
			//启动容器的主类
			
			String startClass = containerMap.get(container);
			try {
				Class<?> clazz = classLoader.loadClass(startClass);
				Object obj = clazz.newInstance();
				Method method = clazz.getMethod("startup", null);
				method.invoke(obj, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
