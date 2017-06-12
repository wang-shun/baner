package com.ztkx.cbpay.container.intercepter;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.intercepter.intface.CommonIntercepter;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class ErrorIntercepter implements CommonIntercepter {
	Logger logger = Logger.getLogger(ErrorIntercepter.class);
	@Override
	public CommonContext intercepter(CommonContext context) {
		if(context.getErrorCode()==null){
			return context;
		}
		logger.error("");
		
		return context;
	}

}
