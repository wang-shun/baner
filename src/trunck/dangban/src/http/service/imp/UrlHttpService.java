package http.service.imp;

import http.pojo.BusinessPojo;
import http.pojo.HttpPojo;
import http.service.HttpService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class UrlHttpService implements HttpService{
	private Logger logger =  Logger.getLogger(UrlHttpService.class);
	@Override
	public String service(HttpPojo pojo,BusinessPojo buspojo) {
		// TODO Auto-generated method stub
		String data = null; 
		logger.info("UrlHttpService is begin");
		if(pojo.getServer().length()>=(buspojo.getServer_begin()+buspojo.getServer_length())){
			data =pojo.getServer().substring(buspojo.getServer_begin(), buspojo.getServer_begin()+buspojo.getServer_length());
			logger.info("file is "+buspojo.getFile()+File.separator+data);
			File file = new File(buspojo.getFile()+File.separator+data);
			if(file.isFile()){
				try {
					InputStream fin = new FileInputStream(file);
					BufferedInputStream buf = new BufferedInputStream(fin);
					ByteArrayOutputStream dos = new ByteArrayOutputStream();
					byte[] tmp = new  byte[1024]; 
					int n=0;
					while((n=buf.read(tmp))!=-1){ 
						dos.write(tmp,0,n); 
					}
					if(logger.isDebugEnabled()){
						logger.debug("read return xml is "+dos.toString());
					}
					logger.info("UrlHttpService is succ...");
					return dos.toString("GBK");
				} catch (Exception e) {
					logger.error("server is error",e);
				}
			}
		}else{
			logger.error("policy set length is little");
		}
		return null;
	}

}
