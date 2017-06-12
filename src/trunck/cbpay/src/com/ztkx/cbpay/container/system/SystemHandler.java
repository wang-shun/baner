package com.ztkx.cbpay.container.system;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 系统特殊处理接口
 * @author zhangxiaoyun
 * 2016年2月2日 上午11:27:34
 * <p>Company:ztkx</p>
 */
public interface SystemHandler {
	/**
	 * 系统前处理
	 * @param context
	 */
	public CommonContext beforeHandler(CommonContext context) throws HandlerException;
	/**
	 * 系统后处理
	 * @param context
	 */
	public CommonContext afterHandler(CommonContext context) throws HandlerException;
}
