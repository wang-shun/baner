package com.ztkx.transplat.container.frame;

/**
 * frame 初始化工厂
 * @author zhangxiaoyun
 *
 */
public class FramFactory {
	
	/**
	 * 初始化同步运行时框架
	 * @return
	 */
	public static RunnableSynAdapterFrame getSynAdapterFram(){
		return new RunnableSynAdapterFrame();
	}
	
	/**
	 * 初始化异步运行时框架
	 * @return
	 */
	public static RunnableAsynAdapterFrame getAsynAdapterFram(){
		return new RunnableAsynAdapterFrame();
	}
}
