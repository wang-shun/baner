package http;

import http.constant.Constant;
import http.pojo.ResourcePojo;
import http.util.XmlParse;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class ResManage {
	private Logger logger =  Logger.getLogger(ResManage.class);
	public void manage(){
		PropertyConfigurator.configure(Constant.log4jProperties);
		List<ResourcePojo> resList = null;
		try {
			logger.info("xml parse is begin...");
			resList = XmlParse.parse(Constant.xmlpath);
			logger.info("xml parse is succ");
			if(null!=resList&&resList.size()>0){
				for(ResourcePojo respojo:resList){
					BusiManage busiManage = new BusiManage(respojo);
					new Thread(busiManage).start();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("resmanage start is error", e);
		}
	}
}
 