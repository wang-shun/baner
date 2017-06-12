package com.ztkx.cbpay.container.preload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * 初始化数据的加载类
 * @author zhangxiaoyun
 *
 */
public class PreLoadManager {
	private static PreLoadManager loadManager = null;
	String configFileName = ConstantConfigField.PRE_LOADCONFIGFILE;
	String configFilePaht = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+File.separator+"preload"+File.separator;
	private Logger logger = Logger.getLogger(PreLoadManager.class);
	private Map<String,CommonPreloadInterface> map = new HashMap<String, CommonPreloadInterface>();

	private PreLoadManager() {
		// 1.解析配置文件
		List<PreLoadConfBean> list = parseConifg(configFilePaht + configFileName);
		PreLoadConfBean[] arraysObj = list.toArray(new PreLoadConfBean[0] );
		Arrays.sort(arraysObj);
		PreLoadConfBean bean = null;
		try {
			for (int i = 0; i < arraysObj.length; i++) {
				bean = arraysObj[i];
				logger.info("start invoke [" + bean.getId() + "] preloader");
				String imp = bean.getImpl();
				Class<?> clazz = Class.forName(imp);
				CommonPreloadInterface loadInit = (CommonPreloadInterface)clazz.newInstance();
				loadInit.load();
				map.put(bean.getId(), loadInit);
			}
		} catch (Exception e) {
			logger.error("exception loader id is["+bean+"]",e);
		}
	}
	
	public static PreLoadManager getInstance() {
		if (loadManager == null) {
			synchronized(PreLoadManager.class){
				if(loadManager == null){
					loadManager = new PreLoadManager();
				}
			}
		}
		return loadManager;
	}
	
	public CommonPreloadInterface getPreLoad(String key){
		return map.get(key);
	}
	
	private List<PreLoadConfBean> parseConifg(String file){
		Map<String,String> tmp = new HashMap<String,String>();	//临时变量检查是否有重复数据
		Element root = Dom4jXmlUtil.getRootElement(file);
		List<Element> eleList = root.elements("preload");
		List<PreLoadConfBean> list = new ArrayList<PreLoadConfBean>();
		for (int i = 0; i < eleList.size(); i++) {
			Element ele = eleList.get(i);
			String id = ele.attributeValue("id");
			if(!tmp.containsKey(id)){
				String type = ele.attributeValue("type");
				String impl = ele.attributeValue("impl");
				String sequence = ele.attributeValue("sequence");
				PreLoadConfBean bean = new PreLoadConfBean(id,type,impl,Integer.parseInt(sequence));
				if(logger.isDebugEnabled()){
					logger.debug(bean);				
				}
				tmp.put(id, id);
				list.add(bean);
			}else{
				logger.error("repetition id ["+id+"]");
			}
			
		}
		return list;
	}
}
