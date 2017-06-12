package com.ztkx.transplat.container.msg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.initload.InitFactory;
import com.ztkx.transplat.container.initload.InitialBean;
/**
 * 交易识别类的初始化工厂
 * @author zhangxiaoyun
 * 2016年2月2日 下午2:30:48
 * <p>Company:ztkx</p>
 */
public class MessageIdentifyFactory implements InitFactory {
	private static Map<String, MessageIdentifier> map = null;
	private Logger logger = Logger.getLogger(MessageIdentifyFactory.class);
	List<InitialBean> tranCodeHandler = null;
	@Override
	public void factory(List<InitialBean> list) {
		this.tranCodeHandler = list;
		map = new HashMap<String, MessageIdentifier>();
		for (int i = 0; i < tranCodeHandler.size(); i++) {
			InitialBean bean = tranCodeHandler.get(i);
			String id = bean.getId();
			String imp = bean.getImpl();
			MessageIdentifier identify = null;
			try {
				identify = (MessageIdentifier)Class.forName(imp).newInstance();
			} catch (Exception e) {
				logger.error("load MessageIdentifyFactory exception  id is["+id+"]",e);
			}
			if(identify!=null){
				map.put(id, identify);
			}
		}
	}
	
	/**
	 * 根据keymsg里面配置的交易识别特殊处理的id获取对象名称
	 * @param key
	 * @return
	 */
	public static MessageIdentifier getIdentify(String key){
		return map.get(key);
	}

}
