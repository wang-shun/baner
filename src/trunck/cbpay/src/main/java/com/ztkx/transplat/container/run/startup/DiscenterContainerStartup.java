package com.ztkx.transplat.container.run.startup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ztkx.transplat.container.initload.InitLoaderManager;
import com.ztkx.transplat.container.protocol.ProtocolManager;
import com.ztkx.transplat.discenter.mqclient.AppSenderInit;
import com.ztkx.transplat.invoker.InvokerManager;
import com.ztkx.transplat.platformutil.activemq.ActiveMQInitManager;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;
public class DiscenterContainerStartup{
	
	static Logger logger = Logger.getLogger(DiscenterContainerStartup.class);
	
	//平台启动home目录
	String home_path = System.getProperty(ConstantConfigField.HOMEDIR);
	String config_path = home_path+File.separator+"config";
	String myconfig_path = config_path+File.separator;
	String configFile = "baseConf.properties";
	String log4jProperties = "log4j.properties";
	//关闭端口
	String closePort = System.getProperty(ConstantStartupField.CLOSE_PORT);
	
	
	/**
	 * 启动容器
	 */
	public void startup() {
		
		//容器初始化
		try{
			init();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//监听关闭端口
			System.out.println("start listener close port......");
			listenerClosePort(closePort);
		}
	}
	
	/**
	 * 关闭容器释放资源
	 * 需要添加具体容器释放资源的代码
	 */
	private void close(){
		
		//释放mq相关资源
		ActiveMQInitManager.getInstance().close();
	}
	
	/**
	 * 容器初始化方法
	 */
	private void init(){
		long startTime = System.currentTimeMillis();
		
		//初始化系统参数
		BaseConfig.getInstence(myconfig_path+configFile);
		BaseConfig.setConfig(ConstantConfigField.CONFIGPATH, myconfig_path);
		BaseConfig.setConfig(ConstantConfigField.HOMEDIR, home_path);
		
		
		PropertyConfigurator.configure(BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+log4jProperties);
		logger.info("load log4j params succ...\n");
		
		System.out.println("Discenter Container init starting ......");
		logger.info("Discenter Container init starting...\n");
		
		//初始化线程池资源
		logger.info("load system thread pool start...\n");
		ThreadPoolManager.getInstance();
		logger.info("load system thread pool succ...\n");
		
		//初始化数据库连接池
		logger.info("load system db pool start...\n");
		DataSourceUtil.getInstance();
		logger.info("load system db pool succ...\n");
		
		//启动加载基础数据
		logger.info("load system initloader start...\n");
		InitLoaderManager.getInstance();
		logger.info("load system initloader succ...\n");
		//启动加载invoker.xml文件
		logger.info("load system invokers start...\n");
		InvokerManager.getInstance();
		logger.info("load system invokers succ...\n");
		
		//启动加载protocol
		logger.info("begin start protocol \n");
		ProtocolManager.getInstance();
		logger.info("protocol start finish \n");
		
		//加载各路应用mqclient
		logger.info("load apps address \n");
		AppSenderInit.getInstance().instance();
		logger.info("load apps address succ \n");
		
		//MQ相关组件
		ActiveMQInitManager.getInstance().instance();
		
		long endTime= System.currentTimeMillis();
		System.out.println("Discenter Container init success in "+(endTime - startTime)+" millisecond");
		logger.info("Discenter Container init success in "+(endTime - startTime)+" millisecond");
	}
	
	
	private void listenerClosePort(String port){
		
		ServerSocket serverSocket = null;
		 try {    
	         serverSocket = new ServerSocket(Integer.parseInt(port));    
	        // 一旦有堵塞, 则表示服务器与客户端获得了连接
		        Socket client = serverSocket.accept();
		        InputStream in = client.getInputStream();
		        byte[] buff = new byte[100];
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int count = 0;
				while ((count = in.read(buff)) > 0) {
					bos.write(buff, 0, count);
				}
				String cmd = bos.toString();
				if(cmd.equals(ConstantStartupField.COMMAND_STOP)){
					//如果是stop命令调用colse方法释放资源
					close();
					System.exit(0);
				}
	        } catch (Exception e) {
	        	e.printStackTrace();
	     }
	}
}

