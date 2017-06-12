package com.ztkx.cbpay.container.enanddecryption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.initload.InitFactory;
import com.ztkx.cbpay.container.initload.InitialBean;

public class EnAndDecryptionFactory implements InitFactory {
	List<InitialBean> enAndDecryption = null;
	private static Map<String, EnAndDecryptHandler> mapHandler = null;
	private Logger logger = Logger.getLogger(EnAndDecryptionFactory.class);

	@Override
	public void factory(List<InitialBean> list) {
		mapHandler = new HashMap<String, EnAndDecryptHandler>();
		this.enAndDecryption = list;
		InitialBean bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			logger.info("start invoke [" + bean.getId() + "] Data loader");
			String imp = bean.getImpl();
			String id = bean.getId();
			EnAndDecryptHandler handler = null;
			try {
				Class<?> clazz = Class.forName(imp);
				handler = (EnAndDecryptHandler) clazz.newInstance();
			} catch (Exception e) {
				logger.error("load init EnAndDecryptionFactory exception  is["
						+ id + "]", e);
			}
			if (handler != null) {
				mapHandler.put(id, handler);

			}
		}

	}

	/**
	 * 根据tranFrom 获取系统加解密处理类
	 * 
	 * @param key
	 * @return
	 */
	public static EnAndDecryptHandler getHandler(String key) {
		return mapHandler.get(key);
	}

}
