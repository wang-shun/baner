package com.ztkx.transplat.invokerexecutor;

import com.ztkx.transplat.container.initdata.DataInitFactory;
import com.ztkx.transplat.container.preload.PreLoadManager;
import com.ztkx.transplat.invokerexecutor.imp.CommandFactory;

/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月4日 下午5:21:54 
 */
public class ExecutorFactory {
	public static final String EXECUTOR_TYPE_DATAINIT="dataInit";								//平台基础数据
	public static final String EXECUTOR_TYPE_PRELOAD="preload";								//平台基础数据
	public static final String EXECUTOR_TYPE_COMMAND="command";								//自定义类
	/**
	 * 根据
	 * 2016年7月4日 下午5:23:09
	 * @author zhangxiaoyun
	 * @param type executor的类型
	 * @param id	executor的id
	 * @return
	 * @return InvokerExecutor
	 */
	public static InvokerExecutor getExecutory(String type,String id){
		if(type.equals(EXECUTOR_TYPE_DATAINIT)){
			return (InvokerExecutor) DataInitFactory.instance.getObj(id);
		}else if(type.equals(EXECUTOR_TYPE_PRELOAD)){
			return (InvokerExecutor) PreLoadManager.getInstance().getPreLoad(id);
		}else if(type.equals(EXECUTOR_TYPE_COMMAND)){
			return (InvokerExecutor) CommandFactory.instance.getObj(id);
		}
		return null;
	}
}
