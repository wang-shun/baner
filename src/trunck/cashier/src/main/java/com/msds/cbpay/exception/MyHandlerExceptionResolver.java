package com.msds.cbpay.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.msds.cbpay.action.SendToMerchant;
import com.msds.cbpay.constant.ConstantField;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {   
  
	Logger logger = Logger.getLogger(MyHandlerExceptionResolver.class);
    @Override  
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {   
    	
    	//添加自己的异常处理逻辑，如日志记录等 
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("error");
    	String param = "";
    	logger.error("exception type is " + ex.toString());
    	try {
    		//自己抛出的异常
			if(ex instanceof CashierDealException){
				CashierDealException cashierDealException = (CashierDealException) ex;
				if(StringUtils.isNotEmpty(cashierDealException.offlineurl)){
					param = SendToMerchant.httpSend(cashierDealException.offlineurl, 
							cashierDealException.merchantNo,cashierDealException.clientTime,
							cashierDealException.tranFlow,cashierDealException.clientIP,
							cashierDealException.respCode, cashierDealException.respMsg);
				}
				if(StringUtils.isNotEmpty(cashierDealException.onlineurl)){
					mv.getModel().put("pageReturnUrl", cashierDealException.onlineurl);
					mv.getModel().put("params",param);//给前台通知
				}
				logger.info("cashierDealException is mgs["+cashierDealException.respMsg+"]");
				if(StringUtils.isNotBlank(cashierDealException.respMsg))
					request.setAttribute("respMsg", cashierDealException.respMsg);
				else
					request.setAttribute("respMsg", ConstantField.DEFAULT_ERROR_RESPDES);
			}else{
				request.setAttribute("respMsg",ConstantField.DEFAULT_ERROR_RESPDES);
			}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
    		logger.error("deal exception is error",e);
    		request.setAttribute("respMsg",ConstantField.DEFAULT_ERROR_RESPDES);
		}
    	
    	return mv;   
    }
}