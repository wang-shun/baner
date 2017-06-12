package com.ztkx.cbpay.container.run.startup;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessContainerStartup{
	
	/**
	 * 启动容器
	 */
	public void startup() {
		//关闭端口
		String closePort = System.getProperty(ConstantStartupField.CLOSE_PORT);
		//容器初始化
		init();
		//监听关闭端口
		System.out.println("start listener close port......");
		listenerClosePort(closePort);
	}
	
	/**
	 * 关闭容器释放资源
	 * 需要添加具体容器释放资源的代码
	 */
	private void close(){
		
	}
	
	/**
	 * 容器初始化方法
	 */
	private void init(){
		System.out.println("in Container inti starting ......");
		long startTime = System.currentTimeMillis();
		
		
		long endTime= System.currentTimeMillis();
		System.out.println("process Container inti success in "+(endTime - startTime)+" millisecond");
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

