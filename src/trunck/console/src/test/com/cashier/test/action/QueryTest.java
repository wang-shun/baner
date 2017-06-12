package com.cashier.test.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.util.XmlReversalBean;

import com.cashier.test.bean.MerchantOrderHead;
import com.cashier.test.bean.MerchantQueryFrgMsg;
import com.cashier.test.bean.MerchantQueryOrderMsg;
import com.cashier.test.util.EnAndDcryTest;
import com.cashier.test.util.SendMsgOther;


@Controller
@RequestMapping("/querytest")
public class QueryTest {
	Logger logger = Logger.getLogger(QueryTest.class);
	/**
	 * 生成xml(查询交易)
	 * @param body
	 * @param head
	 * @param req
	 * @return
	 */
	@RequestMapping("/makequeryorderxml")
	@ResponseBody
	public String makequeryorderxml(MerchantQueryOrderMsg.MerchantQueryOrderBody body,MerchantOrderHead head,HttpServletRequest req){
		MerchantQueryOrderMsg merchantQueryOrderMsg = new MerchantQueryOrderMsg();
		merchantQueryOrderMsg.setBody(body);
		merchantQueryOrderMsg.setHead(head);
		String xml = null;
		try{
			xml = XmlReversalBean.beanToXml(merchantQueryOrderMsg, MerchantQueryOrderMsg.class);
			logger.info("create xml["+xml+"]");
		}catch(Exception e){
			logger.error("system is error",e);
			return "systemerror";
		}
		return xml;
	}
	/**
	 * 生成xml(查询汇率)
	 * @param body
	 * @param head
	 * @param req
	 * @return
	 */
	@RequestMapping("/makequeryfrgxml")
	@ResponseBody
	public String makequeryfrgxml(MerchantQueryFrgMsg.MerchantQueryFrgBody body,MerchantOrderHead head,HttpServletRequest req){
		MerchantQueryFrgMsg merchantQueryFrgMsg = new MerchantQueryFrgMsg();
		merchantQueryFrgMsg.setBody(body);
		merchantQueryFrgMsg.setHead(head);
		String xml = null;
		try{
			xml = XmlReversalBean.beanToXml(merchantQueryFrgMsg, MerchantQueryFrgMsg.class);
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
	@RequestMapping("/mkordersecrit")
	@ResponseBody
	public String mksecrit(String reqxml,String trancode,HttpServletRequest req){
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
			msg = SendMsgOther.send(msg,trancode);
			if(logger.isDebugEnabled()){
				logger.debug("响应数据：\n"+msg+"\n");
			}
			msg = service.reductionMessage(msg);
			if(logger.isDebugEnabled()){
				logger.debug("解密后数据：\n"+msg+"\n");
			}
		}catch(Exception e){
			logger.error("system is error",e);
			return "systemerror";
		}
		return msg;
	}
}
