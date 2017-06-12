package com.ztkx.transplat.platformutil.context.imp;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
/**
 * 平台启动的时候通过这个类初始化context池
 * 使用和释放context都通过这个manager完成
 * @author zhangxiaoyun
 *
 */
public class ContextManager {
	
	private static ContextManager instance = null;
	private int poolSize = 300;
	public static Logger logger = Logger.getLogger(ContextManager.class);
	private ConcurrentLinkedQueue<CommonContext> list = null;
	
	private ContextManager(){
		String sizeStr = BaseConfig.getConfig(ConstantConfigField.CONTEXTPOOLSIZE);
		if(null != sizeStr && !sizeStr.equals("")){
			poolSize = Integer.parseInt(sizeStr);
			logger.info("context init poolsize is "+poolSize);
		}else{
			logger.warn("short of system params "+ConstantConfigField.CONTEXTPOOLSIZE+" use default config "+poolSize);
		}
		list = new ConcurrentLinkedQueue<CommonContext>();
		for(int i = 0; i<poolSize; i++){
			CommonContext context = new CbpayContext();
			context.init();
			context.setInit(true);
			
			list.add(context);
			logger.info("init context succ,the context sequence is ["+context.getSequence()+"] and pool current size is ["+list.size()+"]");
		}
	}
	
	/**
	 * 初始化类
	 * @return
	 */
	public static ContextManager getInstance(){
		if(instance == null){
			synchronized (ContextManager.class) {
				if(instance == null){
					instance = new ContextManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取上下文队列
	 * @return
	 */
	public CommonContext getContext() {
		CommonContext context = null;
		context = list.poll();
		if (null != context) {
			if (logger.isDebugEnabled()) {
				logger.debug("get context sequence is ["+context.getSequence()+"] the current CommonContext pool size is:"
						+ list.size());
			}
		} else {
			context = new CbpayContext();
			context.init(0);
			context.setInit(false);
			logger.error("the new context sequence is ["+context.getSequence()+"] context pool is overflow current Context pool size is:"+ list.size());
		}
		return context;
	}
	
	/**
	 * 归还交易上下文资源
	 * @param context
	 */
	public void relase(CommonContext context){
		if(context == null){
			logger.error("this conext is null:"+ context);
		}else{
			if(context.isInit()){
				context.clear();
				list.add(context);
				if (logger.isDebugEnabled()) {
					logger.debug("the current CommonContext pool size is:"+ list.size());
				}
			}else{
				if (logger.isDebugEnabled()) {
					logger.debug("the current context is not initialize");
				}
				//如果该上下文不是系统初始化的只释放资源，不归还队列
				context.clear();
				context = null;
			}
		}
	}
}
