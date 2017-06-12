package http;

import http.constant.Constant;
import http.pojo.BusinessPojo;
import http.pojo.HttpPojo;
import http.pojo.ResourcePojo;
import http.service.imp.DataHttpService;
import http.service.imp.UrlHttpService;
import http.util.HttpParse;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class BusiManage implements Runnable{
	private Logger logger =  Logger.getLogger(BusiManage.class);
	ResourcePojo respojo;
	public BusiManage(ResourcePojo respojo){
		this.respojo = respojo;
	}
	private void invokeServer() throws NumberFormatException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
			logger.info(respojo.getName()+"'s port is "+respojo.getPort());
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(respojo.getPort().trim()));
			while (true) {
	            Socket socket = serverSocket.accept();
	            logger.debug(respojo.getName() + " request is begin.");
	            InputStream in = socket.getInputStream();
	            //解析http传入的数据，封装成对象
	            HttpPojo httppojo = HttpParse.parse(in,respojo.getCode());
	            
	            if(null!=respojo.getBusList()&&StringUtils.isNotBlank(httppojo.getServer())){
	            	for(BusinessPojo buspojo : respojo.getBusList()){
	            		if(Constant.JUDGE_URL.equals(buspojo.getField())){
	            			logger.info("BusinessPojo is find " + buspojo.getName());
	            			UrlHttpService service = new UrlHttpService();
	            			String data = service.service(httppojo,buspojo);
	            			String response = HttpParse.pack_http(data,respojo.getCode());
	            			socket.getOutputStream().write(response.getBytes(respojo.getCode()));
	            			if(logger.isDebugEnabled()){
	            				logger.debug("http response data is "+response);
	            			}
	            			socket.getOutputStream().flush();
	            			socket.close();
	            			logger.debug("http response succ... ");
	            		}else if(Constant.JUDGE_DATA.equals(buspojo.getField())){
	            			logger.info("BusinessPojo is find " + buspojo.getName());
	            			DataHttpService service = new DataHttpService();
	            			String responsedata = service.service(httppojo,buspojo);
	            			String response = HttpParse.pack_http(responsedata,respojo.getCode());
	            			socket.getOutputStream().write(response.getBytes(respojo.getCode()));
	            			if(logger.isDebugEnabled()){
	            				logger.debug("http response data is "+response);
	            			}
	            			socket.getOutputStream().flush();
	            			socket.close();
	            			logger.debug("http response succ... ");
	            		}
	            	}
	            }
	        }
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			logger.info("busimanage is begin...");
			invokeServer();
			logger.info("busimanage is succ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("server start is error "+e);
		} 
	}
	
}
