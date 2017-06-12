package com.ztkx.transplat.container.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.initload.InitFactory;
import com.ztkx.transplat.container.initload.InitialBean;

public class SystemHandlerFactory implements InitFactory{
	
	List<InitialBean> sysHandler = new ArrayList<InitialBean>();
	private static Map<String, SystemHandler> map = null;
	private Logger logger = Logger.getLogger(SystemHandlerFactory.class);
	@Override
	public void factory(List<InitialBean> list) {
		this.sysHandler = list;
		map = new HashMap<String, SystemHandler>();
		for (int i = 0; i < sysHandler.size(); i++) {
			InitialBean bean = sysHandler.get(i);
			String id = bean.getId();
			String imp = bean.getImpl();
			SystemHandler handler = null;
			try {
				handler = (SystemHandler)Class.forName(imp).newInstance();
			} catch (Exception e) {
				logger.error("load SystemHandlerFactory exception  id is["+id+"]",e);
			}
			if(handler!=null){
				map.put(id, handler);
			}
		}
	}
	
	/**
	 * 根据系统id获取当前系统的特殊处理类
	 * @param systemId
	 * @return
	 */
	public static SystemHandler getHandler(String systemId){
		return map.get(systemId);
	}

}
