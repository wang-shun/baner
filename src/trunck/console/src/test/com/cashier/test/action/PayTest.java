package com.cashier.test.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.util.XmlReversalBean;

import com.cashier.test.bean.MerchantOrderHead;
import com.cashier.test.bean.MerchantOrderMsg;
import com.cashier.test.util.EnAndDcryTest;


@Controller
@RequestMapping("/paytest")
public class PayTest {
	Logger logger = Logger.getLogger(PayTest.class);
	/**
	 * 生成xml
	 * @param body
	 * @param head
	 * @param req
	 * @return
	 */
	@RequestMapping("/makexml")
	@ResponseBody
	public String makeXml(MerchantOrderMsg.MerchantOrderBody body,MerchantOrderHead head,HttpServletRequest req){
		MerchantOrderMsg merchantOrderMsg = new MerchantOrderMsg();
		merchantOrderMsg.setBody(body);
		merchantOrderMsg.setHead(head);
		String xml = null;
		try{
			xml = XmlReversalBean.beanToXml(merchantOrderMsg, MerchantOrderMsg.class);
			logger.info("create xml["+xml+"]");
		}catch(Exception e){
			logger.error("system is error",e);
			return "systemerror";
		}
		return xml;
	}
	/**
	 * 加密
	 * @param body
	 * @param head
	 * @param req
	 * @return
	 */
	@RequestMapping("/mksecrit")
	@ResponseBody
	public String mksecrit(String reqxml,HttpServletRequest req){
		String msg = null;
		try{
			if(reqxml==null){
				return null;
			}
			EnAndDcryTest service = new EnAndDcryTest();
			String message = reqxml;
			msg = service.formatMessage(message);
			if(logger.isDebugEnabled()){
				logger.debug("加密后数据：\n"+msg+"\n");
			}
		}catch(Exception e){
			logger.error("system is error",e);
			return "systemerror";
		}
		return msg;
	}
}
