package com.msds.cbpay.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.msds.cbpay.constant.ConstantField;
import com.msds.cbpay.entity.BuyBatBean;
import com.msds.cbpay.entity.CashierHead;
import com.msds.cbpay.entity.PayRepBean;
import com.msds.cbpay.entity.PayReqBean;
import com.msds.cbpay.entity.SmsCheckRepBean;
import com.msds.cbpay.entity.SmsCheckReqBean;
import com.msds.cbpay.entity.SmsSendRepBean;
import com.msds.cbpay.entity.SmsSendReqBean;
import com.msds.cbpay.entity.TiedCardRepBean;
import com.msds.cbpay.entity.TiedCardReqBean;
import com.msds.cbpay.exception.CashierDealException;
import com.msds.cbpay.util.SendMsg;
import com.msds.cbpay.util.StartUpLoader;
import com.msds.cbpay.util.XmlReversalBean;

/**
 * 
 * @author sunyoushan
 *
 */
@Controller
public class AjaxRequestAction {
	Logger logger = Logger.getLogger(AjaxRequestAction.class);	
	@RequestMapping("/bank")
	private void acceptOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			//response.setCharacterEncoding(StartUpLoader.baseParamBean.getEncode());
			response.getWriter().print(StartUpLoader.jsonArray.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("get bank error",e);
			throw new Exception();
		}
	}
	/**
	 * 绑卡
	 * @param request
	 * @param response
	 * @param modelAndView
	 * @return
	 * @throws CashierDealException 
	 */
	@RequestMapping("/tiedcard")
	private String tiedcard(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView){
		logger.info("tiedcard is begin...");
		logger.debug("request["+request.getParameter("sms")+"]");
		try {
			if(request.getSession().getAttribute("phonetoken")==null){
				logger.error("cashier is error ");
				response.getWriter().write("systemfail");
				return null;
			}
			TiedCardReqBean tiedCardReqBean = new TiedCardReqBean();
			TiedCardRepBean tiedCardRepBean = null;
			request.setCharacterEncoding(StartUpLoader.baseParamBean.getEncode());
			CashierHead cashierHead = tiedCardReqBean.getHead();
			cashierHead.setTranCode(ConstantField.CHS004);
			cashierHead.setMerchantNo(request.getParameter("merchantId"));
			cashierHead.setTranDate(request.getParameter("orderDate"));
			cashierHead.setTranTime(request.getParameter("orderTime"));
			tiedCardReqBean.getBody().setBankName(StartUpLoader.bankmap.get(request.getParameter("bankname")));
			tiedCardReqBean.getBody().setAccountNo(request.getParameter("bankCard"));
			tiedCardReqBean.getBody().setCertType(request.getParameter("identitycard"));
			tiedCardReqBean.getBody().setCertNo(request.getParameter("identitycardnum"));
			tiedCardReqBean.getBody().setMobileNo(request.getParameter("mobile"));
			tiedCardReqBean.getBody().setPhoneToken((String)request.getSession().getAttribute("phonetoken"));
			tiedCardReqBean.getBody().setPhoneVerCode(request.getParameter("sms"));
			tiedCardReqBean.getBody().setPurchaserId(request.getParameter("purchaserId"));
			tiedCardReqBean.getBody().setAccountName(request.getParameter("personname"));
			String reqmsg = null;
			String repmsg = null;
			reqmsg = XmlReversalBean.beanToXml(tiedCardReqBean,TiedCardReqBean.class);
			logger.info("send to discenter begin...");
			if(logger.isDebugEnabled()){
				logger.debug("reqmsg["+reqmsg+"]");
			}
			repmsg = SendMsg.tcpsendPlat(reqmsg);
			if(logger.isDebugEnabled()){
				logger.debug("repmsg["+repmsg+"]");
			}
			logger.info("send to discenter end");
			if (null != repmsg) {
				tiedCardRepBean = XmlReversalBean.xmlToBean(repmsg,TiedCardRepBean.class);
			} else {
				logger.error("get order show msg is null");
			}
			if(ConstantField.SUCC.equals(tiedCardRepBean.getHead().getRespCode())){
				if(ConstantField.RESULT_SU.equals(tiedCardRepBean.getBody().getTranState())){
					response.getWriter().write("true");
					request.getSession().removeAttribute("phonetoken");
				}else{
					response.getWriter().write("smsfail");
				}
			}else{
				response.getWriter().write(tiedCardRepBean.getHead().getRespCode()+";"+tiedCardRepBean.getHead().getRespMsg());
			}
		}catch(Exception e) {
			logger.error("cashier is error ",e);
			try {
				response.getWriter().write("systemfail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("cashier is  error ",e1);
			}
		}
		logger.info("tiedcard is succ");
		return null;
	}
	/**
	 * 发送短信
	 * @param request
	 * @param response
	 * @param modelAndView
	 * @return
	 * @throws CashierDealException 
	 */
	@RequestMapping("/smssend")
	private String smssend(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws CashierDealException {
		logger.info("smssend is begin...");
		
		try {
			SmsSendReqBean smsSendReqBean = new SmsSendReqBean();
			SmsSendRepBean smsSendRepBean = null;
			request.setCharacterEncoding(StartUpLoader.baseParamBean.getEncode());
			CashierHead cashierHead = smsSendReqBean.getHead();
			cashierHead.setTranCode(ConstantField.CHS005);
			cashierHead.setMerchantNo(request.getParameter("merchantId"));
			cashierHead.setTranDate(request.getParameter("orderDate"));
			cashierHead.setTranTime(request.getParameter("orderTime"));
			smsSendReqBean.getBody().setMobileNo(request.getParameter("mobile"));
			smsSendReqBean.getBody().setAccountName(request.getParameter("personname"));
			String reqmsg = null;
			String repmsg = null;
			reqmsg = XmlReversalBean.beanToXml(smsSendReqBean,SmsSendReqBean.class);
			
			logger.info("send to discenter begin...");
			if(logger.isDebugEnabled()){
				logger.debug("reqmsg["+reqmsg+"]");
			}
			
			repmsg = SendMsg.tcpsendPlat(reqmsg);
			
			if(logger.isDebugEnabled()){
				logger.debug("repmsg["+repmsg+"]");
			}
			logger.info("send to discenter end");
			
			if (null != repmsg) {
				smsSendRepBean = XmlReversalBean.xmlToBean(repmsg,SmsSendRepBean.class);
			} else {
				logger.error("get order show msg is null");
			}
			logger.debug("sms["+ smsSendRepBean.getBody().getPhoneToken()+"]");
			if(ConstantField.SUCC.equals(smsSendRepBean.getHead().getRespCode())){
				request.getSession().setAttribute("phonetoken", smsSendRepBean.getBody().getPhoneToken());
				response.getWriter().write("true");
			}else{
				logger.error("plat return phonetoken error");
				response.getWriter().write("false");
			}
		}catch(Exception e) {
			logger.error("cashier is error",e);
			try {
				response.getWriter().write("systemfail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("cashier is error ",e1);
			}
		}
		logger.info("smssend is succ");
		return null;
	}
	/**
	 * 短信验证
	 * @param request
	 * @param response
	 * @param modelAndView
	 * @return
	 * @throws CashierDealException 
	 */
	@RequestMapping("/smscheck")
	private String smscheck(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws CashierDealException {
		logger.info("smscheck is begin...");
		logger.debug("request["+request.getParameter("p2ptext")+"]");
		if(request.getSession().getAttribute("phonetoken")==null){
			logger.error("cashier is error ");
			throw new CashierDealException(null,null,null,null,null,null,null,null);
		}
		try {
			SmsCheckReqBean smsCheckReqBean = new SmsCheckReqBean();
			SmsCheckRepBean smsCheckRepBean = null;
			request.setCharacterEncoding(StartUpLoader.baseParamBean.getEncode());
			CashierHead cashierHead = smsCheckReqBean.getHead();
			cashierHead.setTranCode(ConstantField.CHS008);
			cashierHead.setMerchantNo(request.getParameter("merchantId"));
			cashierHead.setOrderId(request.getParameter("orderId"));
			cashierHead.setTranDate(request.getParameter("orderDate"));
			cashierHead.setTranTime(request.getParameter("orderTime"));
			smsCheckReqBean.getBody().setAccountNo(request.getParameter("cardid"));
			smsCheckReqBean.getBody().setPhoneVerCode(request.getParameter("p2ptext"));
			smsCheckReqBean.getBody().setPhoneToken((String)request.getSession().getAttribute("phonetoken"));
			smsCheckReqBean.getBody().setPurchaserId(request.getParameter("purchaserId"));
			String reqmsg = null;
			String repmsg = null;
			
			reqmsg = XmlReversalBean.beanToXml(smsCheckReqBean,SmsCheckReqBean.class);
			logger.info("send to discenter begin...");
			if(logger.isDebugEnabled()){
				logger.debug("reqmsg["+reqmsg+"]");
			}
			repmsg = SendMsg.tcpsendPlat(reqmsg);
			if(logger.isDebugEnabled()){
				logger.debug("repmsg["+repmsg+"]");
			}
			logger.info("send to discenter end");
			if (null != repmsg) {
				smsCheckRepBean = XmlReversalBean.xmlToBean(repmsg,SmsCheckRepBean.class);
			} else {
				logger.error("get order show msg is null");
			}
			if(ConstantField.SUCC.equals(smsCheckRepBean.getHead().getRespCode())){
				if(ConstantField.RESULT_SU.equals(smsCheckRepBean.getBody().getTranState())){
					request.getSession().removeAttribute("sms");
					request.getSession().removeAttribute("order");
					request.getSession().removeAttribute("modelAndViewlast");
					logger.info("checksms  is succ");
					this.pay(request, response, modelAndView);
				}else{
					logger.error("plat is error");
					response.getWriter().write("smsfail");
				}
			}else{
				response.getWriter().write(smsCheckRepBean.getHead().getRespCode()+";"+smsCheckRepBean.getHead().getRespMsg());
			}
		}catch(Exception e) {
			logger.error("send sms error");
			try {
				response.getWriter().write("systemfail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("cashier is  error ",e);
			}
		}
		return null;
	}
	/**
	 * 支付
	 * @param request
	 * @param response
	 * @param modelAndView
	 * @return
	 * @throws CashierDealException 
	 */
	private String pay(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws CashierDealException {
		logger.info("pay is begin..");
		PayReqBean payReqBean = new PayReqBean();
		PayRepBean payRepBean = null;
		CashierHead cashierHead = payReqBean.getHead();
		cashierHead.setTranCode(ConstantField.CHS006);
		cashierHead.setMerchantNo(request.getParameter("merchantId"));
		cashierHead.setOrderId(request.getParameter("orderId"));
		cashierHead.setTranDate(request.getParameter("orderDate"));
		cashierHead.setTranTime(request.getParameter("orderTime"));
		payReqBean.getBody().setAccountNo(request.getParameter("cardid"));
		payReqBean.getBody().setPhoneToken(request.getParameter("p2ptext"));
		String reqmsg = null;
		String repmsg = null;
		try {
			reqmsg = XmlReversalBean.beanToXml(payReqBean,PayReqBean.class);
			logger.info("send to discenter begin...");
			if(logger.isDebugEnabled()){
				logger.debug("reqmsg["+reqmsg+"]");
			}
			repmsg = SendMsg.tcpsendPlat(reqmsg);
			if(logger.isDebugEnabled()){
				logger.debug("repmsg["+repmsg+"]");
			}
			logger.info("send to discenter end");
			if (null != repmsg) {
				payRepBean = XmlReversalBean.xmlToBean(repmsg,PayRepBean.class);
			} else {
				logger.error("get order show msg is null");
			}
			if(ConstantField.SUCC.equals(payRepBean.getHead().getRespCode())&&ConstantField.RESULT_SU.equals(payRepBean.getBody().getTranState())){
				response.getWriter().write("true;");
				repmsg = SendToMerchant.httpSend(request.getParameter("offlineNotifyUrl"), request.getParameter("merchantId"),
						request.getParameter("clientTime"), request.getParameter("tranFlow"), 
						request.getParameter("clientIP"), payRepBean.getHead().getRespCode(), 
						payRepBean.getHead().getRespMsg());
				response.getWriter().write(repmsg);//给前台通知
				if(StartUpLoader.baseParamBean.getNowbuybat().equals(ConstantField.BUYBAT_YES))
					buybat(cashierHead);
			}else{
				response.getWriter().write("payfail");
				logger.info("pay is fail");
				return null;
			}
		}catch(Exception e) {
			logger.error("pay error",e);
			try {
				response.getWriter().write("systemfail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("cashier is error ",e);
			}
			logger.info("pay is fail");
			return null;
		}
		logger.info("pay is succ");
		return null;
	}
	/**
	 *购汇
	 * @param cashierHead
	 */
	private void buybat(CashierHead cashierHead){
		logger.info("buybat is begin");
		BuyBatBean buyBatBean = new BuyBatBean();
		cashierHead.setTranCode(ConstantField.CHS007);
		buyBatBean.setHead(cashierHead);
		String reqmsg = null;
		try {
			reqmsg = XmlReversalBean.beanToXml(buyBatBean,BuyBatBean.class);
			logger.info("send to discenter is begin");
			if(logger.isDebugEnabled()){
				logger.debug("reqmsg["+reqmsg+"]");
			}
			SendMsg.jmssendPlat(reqmsg);
			logger.info("send to discenter end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("buybat error");
		}
		logger.info("buybat is succ");
	}
}