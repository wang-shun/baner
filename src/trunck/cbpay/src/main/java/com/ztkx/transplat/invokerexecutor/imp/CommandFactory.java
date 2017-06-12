package com.ztkx.transplat.invokerexecutor.imp;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.initload.InitFactory;
import com.ztkx.transplat.container.initload.InitialBean;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;

/**
 * 平台所以交易配置表的加载工厂
 * @author zhangxiaoyun
 * 2016年2月2日 下午2:43:12
 * <p>Company:ztkx</p>
 */
public class CommandFactory implements InitFactory{
	public static CommandFactory instance = null;
	List<InitialBean> dataInit = null;
	private Logger logger = Logger.getLogger(CommandFactory.class);
	private Map<String,InvokerExecutor> commandObj = new HashMap<String,InvokerExecutor>();
	
	@Deprecated
	public CommandFactory(){
		instance = this;
	}
	
	@Override
	public void factory(List<InitialBean> list) {
		this.dataInit = list;
		InitialBean bean = null;
		try { 
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				logger.info("start invoke [" + bean.getId() + "] Data loader");
				String imp = bean.getImpl();
				Class<?> clazz = Class.forName(imp);
				Constructor<?> constructor =  clazz.getDeclaredConstructor(null);
				constructor.setAccessible(true);
				InvokerExecutor command = (InvokerExecutor)constructor.newInstance();
				commandObj.put(bean.getId(), command);
			}
		} catch (Exception e) {
			logger.error("load init data exception loader id is["+bean+"]",e);
		}
	}
	
	
	
	/**
	 * 根据init.xml文件中的dataInit 的id获取数据加载对象 
	 * 2016年6月30日 下午3:06:52
	 * @author zhangxiaoyun
	 * @param initId
	 * @return
	 * @return CommonLoadInitInterface
	 */
	public InvokerExecutor getObj(String initId){
		return commandObj.get(initId);
	}
	

}
