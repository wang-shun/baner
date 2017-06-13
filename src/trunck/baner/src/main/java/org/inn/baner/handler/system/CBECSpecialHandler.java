package org.inn.baner.handler.system;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.system.SystemHandler;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import org.apache.log4j.Logger;

/**
 * 中信银行服务系统特殊处理类
 * @author zhangxiaoyun
 * 2016年2月2日 上午11:29:54
 * <p>Company:ztkx</p>
 */
public class CBECSpecialHandler implements SystemHandler {
	private Logger logger  = Logger.getLogger(CBECSpecialHandler.class);
	@Override
	public CommonContext beforeHandler(CommonContext context) {
		logger.info("start exec CBECSpecialHandler beforeHandler");
		String serverCode = context.getSDO().serverCode;
		String url_params = "/CBEC/"+serverCode+".do";
		if(logger.isDebugEnabled()){
			logger.debug("url_params ["+url_params+"]");
		}
		context.setFieldStr(ContainerConstantField.URL_PARAMS,url_params);
		logger.info("CBECSpecialHandler beforeHandler exec succ");
		return context;
	}

	@Override
	public CommonContext afterHandler(CommonContext context) {
		
		return context;
	}

}
