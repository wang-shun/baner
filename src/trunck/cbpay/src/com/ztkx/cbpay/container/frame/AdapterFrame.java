package com.ztkx.cbpay.container.frame;

import com.ztkx.cbpay.platformutil.context.CommonContext;

public interface AdapterFrame extends Runnable{
	
	public void setContext(CommonContext context);
	/**
	 * 执行前拦截器
	 * @param context
	 */
	public void before(CommonContext context);
	/**
	 * 执行后拦截器
	 * @param context
	 */
	public void after(CommonContext context);
	/**
	 * 吊起交易的基础服务或者业务服务
	 * @param context
	 */
	public void invoke(CommonContext context);
}
