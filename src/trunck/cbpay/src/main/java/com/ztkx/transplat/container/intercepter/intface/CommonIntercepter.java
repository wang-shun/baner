package com.ztkx.transplat.container.intercepter.intface;

import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * 平台拦截器用来识别交易或者释放资源
 * @author lenovo
 *
 */
public interface CommonIntercepter {
	public CommonContext intercepter(CommonContext context);
}
