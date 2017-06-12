package http.util;

import http.constant.Constant;
import http.pojo.BusinessPojo;
import http.pojo.ResourcePojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParse {
	private static Logger logger =  Logger.getLogger(XmlParse.class);
	@SuppressWarnings("unchecked")
	public static List<ResourcePojo> parse(String file) throws DocumentException{
		logger.info("xml parse begin...");
		List<ResourcePojo> resourcepojolist = new ArrayList<ResourcePojo>();
		//1.读取XML文件,获得document对象
		SAXReader reader = new SAXReader();             
		Document document = reader.read(new File(file));
		Element root = document.getRootElement();
		List<Element> resouceList = root.elements();
		logger.debug("resource tag size is "+resouceList.size());
		for(Element resource : resouceList){
			ResourcePojo resPojo = new ResourcePojo();
			List<BusinessPojo> businesspojolist = new ArrayList<BusinessPojo>();
			resPojo.setName(resource.attribute("name").getText());
			resPojo.setPort(resource.attribute("port").getText());
			resPojo.setCode(resource.attribute("code").getText());
			resPojo.setFile(resource.attribute("file").getText());
			List<Element> busList = resource.elements();
			for(Element business:busList){
				BusinessPojo busPojo = new BusinessPojo();
				busPojo.setPolicy(business.attribute("policy").getText());
				busPojo.setName(business.attribute("name").getText());
				busPojo.setFile(resPojo.getFile());
				busPojo.setField(business.attribute("field").getText());
				if(busPojo.getField().equalsIgnoreCase(Constant.JUDGE_URL)){
					String[] num = busPojo.getPolicy().split(Constant.SEPARATE_COMMA);
					if(num.length!=2){
						logger.error(business.attribute("name").getText()+" attribute policy is error");
					}else{
						busPojo.setServer_begin(Integer.parseInt(num[0].trim()));
						busPojo.setServer_length(Integer.parseInt(num[1].trim()));
					}
				}
				businesspojolist.add(busPojo);
			}
			resPojo.setBusList(businesspojolist);
			resourcepojolist.add(resPojo);
		}
		logger.info("xml parse success!");
		return resourcepojolist;
	}
}
