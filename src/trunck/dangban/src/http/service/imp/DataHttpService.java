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

public class DataHttpService implements HttpService{
	private Logger logger =  Logger.getLogger(DataHttpService.class);
	@Override
	public String service(HttpPojo pojo,BusinessPojo buspojo) {
		// TODO Auto-generated method stub
		logger.info("DataHttpService is begin");
		String data = null;
		try{
			String data_bigin = pojo.getData().substring(pojo.getData().indexOf(buspojo.getPolicy()));
			String data_bg_to_end = data_bigin.substring(0, data_bigin.indexOf(buspojo.getPolicy()));
			data = data_bg_to_end.substring(data_bg_to_end.indexOf(">")+1, data_bg_to_end.indexOf("<"));
		}catch(Exception e){
			logger.error("message is not include tag ["+buspojo.getPolicy()+"]");
		}
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
				logger.info("DataHttpService is succ...");
				return dos.toString();
			} catch (Exception e) {
				logger.error("file "+ buspojo.getFile()+File.separator+data+" is not exist" );
			}
		}else{
			logger.error(buspojo.getName() + " is not exist!");
		}
		return null;
	}

}
