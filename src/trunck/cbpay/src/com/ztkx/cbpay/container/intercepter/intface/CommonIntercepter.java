package com.ztkx.cbpay.container.intercepter.intface;

import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 平台拦截器用来识别交易或者释放资源
 * @author lenovo
 *
 */
public interface CommonIntercepter {
	public CommonContext intercepter(CommonContext context);
}
