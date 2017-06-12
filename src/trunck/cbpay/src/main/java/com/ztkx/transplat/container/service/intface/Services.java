package com.ztkx.transplat.container.service.intface;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.platformutil.context.CommonContext;


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
