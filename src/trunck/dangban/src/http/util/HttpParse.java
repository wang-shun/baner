package http.util;

import http.constant.Constant;
import http.pojo.HttpPojo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.log4j.Logger;

public class HttpParse {
	private static Logger logger =  Logger.getLogger(HttpParse.class);
	/**
	 * 
	 * @param in
	 * @param code
	 * @return
	 */
	public static HttpPojo parse(InputStream in,String code){
		HttpPojo pojo = new HttpPojo();
		try {
			BufferedInputStream buf = new BufferedInputStream(in);
			ByteArrayOutputStream msgbyte = new ByteArrayOutputStream();
			StringBuffer databuf = new StringBuffer();
			//Thread.sleep(10);
			byte[] tmp = new  byte[1024]; 
			int n=0;
			boolean flag = false;//判断是否读取完毕
			while((n=buf.read(tmp))>0){ 
				msgbyte.write(tmp,0,n);
				boolean numflag = false;//判断是否读到正文
				boolean lengthflag =false;//判断是否读到content-Length
				int length = 0;//http协议中content-length
				int count = 0;//正文的长度
				String[] valueArr = msgbyte.toString(code).split(System.getProperty("line.separator"));
				for(String value : valueArr){
					if(value.contains("Content-Length")){
						length = Integer.parseInt(value.split(":")[1].trim());
						if(length == 0){
							flag=true;
						}
						lengthflag = true;
					}
					if(lengthflag&&numflag){
						count += value.getBytes(code).length+System.getProperty("line.separator").length();
						databuf.append(value);
						if((count-System.getProperty("line.separator").length()) >=length){
							flag=true;
						}
					}
					//对同系统之间传递http协议
					if("".equals(value)&&!numflag){
						numflag = true;
					}
					//wins发给linux系统
					if("\r".equals(value)&&!numflag){
						numflag = true;
						count = value.length();
					}
					
				}
				if(flag){
					break;
				}
			}
			String msg = msgbyte.toString();
			if(logger.isDebugEnabled()){
				logger.debug("http request data is "+ msg);
			}
			//dos.close();
			String[] message = msg.split(System.getProperty("line.separator"));
			if(message.length>1){
				String[] typeArr = message[0].split(" ");
				logger.debug("http method is "+typeArr[0]);
		        pojo.setMethod(typeArr[0]);
		        String server = "";
		        if(typeArr[1].startsWith("/")){
		        	server = typeArr[1].substring(1);
		        }else{
		        	server = typeArr[1];
		        }
		        if(Constant.HTTP_GET.equalsIgnoreCase(pojo.getMethod())){
		        	get_parse(pojo,server,code);
		        }else if(Constant.HTTP_POST.equalsIgnoreCase(pojo.getMethod())){
		        	pojo.setServer(server);
		        	post_parse(pojo,databuf.toString());
		        }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("http parse is error "+ e);
		}
		if(logger.isDebugEnabled()){
			logger.debug(pojo.toString());
		}
		logger.info("http parse is succ...");
        return pojo;
	}
	private static void get_parse(HttpPojo pojo,String data,String code) throws UnsupportedEncodingException{
		String[] dataArr = data.split("\\?");
		if(dataArr.length>1){
			String content = URLDecoder.decode(dataArr[1], code);
			pojo.setServer(dataArr[0]);
			pojo.setData(content);
		}else if(dataArr.length == 1){
			pojo.setServer(dataArr[0]);
		}else{
			logger.error("http value is error");
		}
	}
	private static void post_parse(HttpPojo pojo,String data) throws IOException{
		pojo.setData(data);
	}
	public static String pack_http(String data,String code) throws UnsupportedEncodingException{
		String response = "";
        response += "HTTP/1.1 200 OK"+System.getProperty("line.separator");
        response += "Server: Sunpache 1.0"+System.getProperty("line.separator");
        response += "Content-Type: text/xml"+System.getProperty("line.separator");
        response += "Date: "+new Date()+System.getProperty("line.separator");
        response += System.getProperty("line.separator");
        response +=data+System.getProperty("line.separator");
        return response;
	}
}
