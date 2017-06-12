package com.ztkx.cbpay.container.service.intface;

import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.platformutil.context.CommonContext;


/**
 * 所有服务类的基础接口
 * @author zhangxiaoyun
 *
 */
public interface Services {
	/**
	 * 所有基础服务的启动接口
	 * @param context
	 * @return
	 */
	public CommonContext service(CommonContext context) throws ServiceException;
}
