package com.ztkx.transplat.platformutil.test;

import org.apache.log4j.Logger;

import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.CbpayContext;

public class CbpayContextTest {
	static Logger logger = Logger.getLogger(CbpayContextTest.class);
	public static void main(String[] args) {
		CommonContext context = new CbpayContext();
		context.init();
		
//		context.setFieldStr("1", "1");
//		context.setFieldStr("2", "2",CommonContext.SCOPE_GLOBAL);
//		context.setFieldStr("5", "5",CommonContext.SCOPE_LOCAL);
//		context.setObj("4", new Thread(),CommonContext.SCOPE_GLOBAL);
//		context.setObj("6", "6",CommonContext.SCOPE_LOCAL);
//		context.setObj("3", new Date());
//		context.setFieldStr("3", "fjdklajfkdlsajfkld");
		context.setObj("1", new BeanTEst(),CommonContext.SCOPE_GLOBAL);
		context.setObj("1", new BeanTEst(),CommonContext.SCOPE_LOCAL);
		BeanTEst t1 = (BeanTEst)context.getObj("1", CommonContext.SCOPE_GLOBAL);
		BeanTEst t2 = (BeanTEst)context.getObj("1", CommonContext.SCOPE_LOCAL);
		logger.debug(context);
		
		t2.age="8";
		t1.age="10";
		CommonContext context1 = context.clone();
		logger.debug(context1);
		
	}
}
