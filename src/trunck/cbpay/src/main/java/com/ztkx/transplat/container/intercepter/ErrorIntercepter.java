package com.ztkx.transplat.container.intercepter;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.intercepter.intface.CommonIntercepter;
import com.ztkx.transplat.platformutil.context.CommonContext;

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
