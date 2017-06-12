package com.msds.cbpay.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.msds.cbpay.constant.ConstantField;
import com.msds.cbpay.entity.CashierHead;
import com.msds.cbpay.entity.CheckOrderRepBean;
import com.msds.cbpay.entity.CheckOrderReqBean;
import com.msds.cbpay.entity.MerchantOrderMsg;
import com.msds.cbpay.entity.OrderShowRepBean;
import com.msds.cbpay.entity.OrderShowReqBean;
import com.msds.cbpay.exception.CashierDealException;
import com.msds.cbpay.util.SendMsg;
import com.msds.cbpay.util.XmlReversalBean;

/**
 * 
 * @author sunyoushan
 *支付请求action
 *1.解密
 *2.查询展示内容，验证权限
 *3.计算手续费，入单等
 **/
@Controller
public class ControllerAction {
	Logger logger = Logger.getLogger(ControllerAction.class);	
	
	@RequestMapping("/msds.do")
	private ModelAndView acceptOrder(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "param", required = true) String param, ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("merchant msg is [" + param + "]");
		}
		//在参数为空的情况下，不知道商户的url
		if(StringUtils.isEmpty(param)){
			logger.error("merchant to cashier msg is null");
			throw new CashierDealException(null,null,null,null,null,null,ConstantField.MSG_ERROR_RESPDES_CBP014,ConstantField.MSG_ERROR_RESPDES_CBP014);
		}
		// 1.解密
		logger.info("------------------------------------------1.decode begin-----------------------------------------------------");
		logger.info("decode is begin");
		String msg = SendMsg.encodeOrg(param,ConstantField.CHS001);
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
		logger.info("decode is success");
		//商户传入的bean
		MerchantOrderMsg merchantOrderMsg = null;
		if (StringUtils.isNotEmpty(msg)) {
			merchantOrderMsg = XmlReversalBean.xmlToBean(msg, MerchantOrderMsg.class);
			modelAndView.getModel().put("merchantOrderMsg", merchantOrderMsg);
			if(request.getSession().getAttribute("tranFlow")==null){//判断是否是用户再刷新
				logger.info("this order is first request");
				request.getSession().setAttribute("tranFlow", merchantOrderMsg.getHead().getTranFlow());
			}else if(request.getSession().getAttribute("tranFlow").equals(merchantOrderMsg.getHead().getTranFlow())){
				//多次请求
				logger.info("order is not first,get value from session!");
				ModelAndView mv = (ModelAndView)request.getSession().getAttribute("modelAndViewlast");
				if(mv!=null&&mv.getViewName()!=null&&!"".equals(mv.getViewName())){
					return mv;
				}
			}
		} else {
			logger.error("plat return decode msg is null");
			throw new CashierDealException(null,null,null,null,null,null,ConstantField.DEFAULT_ERROR_RESPCODE,ConstantField.DEFAULT_ERROR_RESPDES);
		}
		logger.info("------------------------------------------1.decode succ-----------------------------------------------------");
		
		
		logger.info("------------------------------------------2.query begin-----------------------------------------------------");
		
		try{
			CheckOrderReqBean checkOrderReqBean  = new CheckOrderReqBean();
			CheckOrderRepBean checkOrderRepBean  = null;
			String repmsg = null;
			if (ConstantField.TRANCODE_PAY.equals(merchantOrderMsg.getHead().getTranCode())) {//判断商户是否将交易码提错
				merchantOrderMsg.getHead().setClientIP(request.getRemoteAddr());
				String reqmsg = null;
				packHead(checkOrderReqBean.getHead(),merchantOrderMsg,ConstantField.CHS002);//组装报文的bean
				checkOrderReqBean.getBody().setCurrency(merchantOrderMsg.getBody().getCurrency());
				checkOrderReqBean.getBody().setMerchantTranCode(merchantOrderMsg.getHead().getTranCode());
				checkOrderReqBean.getBody().setTradeCode(merchantOrderMsg.getBody().getTradeCode());
				checkOrderReqBean.getBody().setInvoiceNo(merchantOrderMsg.getBody().getInvoiceNo());
				reqmsg = XmlReversalBean.beanToXml(checkOrderReqBean,CheckOrderReqBean.class);
				logger.info("2.query request is begin");
				if (logger.isDebugEnabled()) {
					logger.debug("2.query req msg ["+reqmsg+"]");
				}
				repmsg = SendMsg.tcpsendPlat(reqmsg);
				if (logger.isDebugEnabled()) {
					logger.debug("2.query res msg ["+repmsg+"]");
				}
				logger.info("2.query response is succ");
				if (null != repmsg) {
					checkOrderRepBean = XmlReversalBean.xmlToBean(repmsg,CheckOrderRepBean.class);
					modelAndView.getModel().put("checkOrderRepBean", checkOrderRepBean.getBody());
				}
				logger.info("------------------------------------------2.query succ-----------------------------------------------------");
				if (ConstantField.SUCC.equals(checkOrderRepBean.getHead().getRespCode())) {//查询成功
					logger.info("------------------------------------------3.calc begin-----------------------------------------------------");
					OrderShowReqBean orderShowReqBean  = new OrderShowReqBean();
					orderShowReqBean.getBody().setPoundageFlag(checkOrderRepBean.getBody().getPoundageFlag());
					orderShowReqBean.getBody().setPoundageRate(checkOrderRepBean.getBody().getPoundageRate());
					orderShowReqBean.setHead(checkOrderRepBean.getHead());
					orderShowReqBean.getHead().setRespCode("");
					orderShowReqBean.getHead().setRespMsg("");
					OrderShowRepBean orderShowRepBean  = null;
					packMsg003(orderShowReqBean,merchantOrderMsg,ConstantField.CHS003);
					reqmsg = XmlReversalBean.beanToXml(orderShowReqBean,OrderShowReqBean.class);
					logger.info("3.calc request is begin");
					if (logger.isDebugEnabled()) {
						logger.debug(reqmsg);
					}
					repmsg = SendMsg.tcpsendPlat(reqmsg);
					if (logger.isDebugEnabled()) {
						logger.debug(repmsg);
					}
					logger.info("3.calc response is succ");
					if (null != repmsg) {
						orderShowRepBean = XmlReversalBean.xmlToBean(repmsg,OrderShowRepBean.class);
					} else {
						logger.error("get order show msg is null");
					}
					modelAndView.getModel().put("cashiermsg", orderShowRepBean);
					modelAndView.getModel().put("orderShowRepBean", orderShowRepBean.getBody());
					//判断chs003是否成功
					if(ConstantField.SUCC.equals(orderShowRepBean.getHead().getRespCode()))
						//判断是否绑过卡
						if(orderShowRepBean.getBody()!=null&&"true".equals(orderShowRepBean.getBody().getCheckInfoExistgFlag())){
							modelAndView.getModel().put("flag",true);
						}else{
							modelAndView.getModel().put("flag",false);
						}
					else{
						logger.error("3.calc is error !respMsg["+orderShowRepBean.getHead().getRespMsg()+"]");
						throw new CashierDealException(merchantOrderMsg.getBody().getOfflineNotifyUrl(),
								merchantOrderMsg.getBody().getPageReturnUrl(),
								merchantOrderMsg.getHead().getMerchantNo(),
								merchantOrderMsg.getHead().getClientTime(),
								merchantOrderMsg.getHead().getTranFlow(),
								merchantOrderMsg.getHead().getClientIP(),
								orderShowRepBean.getHead().getRespCode(),
								orderShowRepBean.getHead().getRespMsg());
					}
					logger.info("------------------------------------------3.calc end-----------------------------------------------------");
				} else {
					logger.error("2.query is error!respMsg["+checkOrderRepBean.getHead().getRespMsg()+"]");
					throw new CashierDealException(merchantOrderMsg.getBody().getOfflineNotifyUrl(),
							merchantOrderMsg.getBody().getPageReturnUrl(),
							merchantOrderMsg.getHead().getMerchantNo(),
							merchantOrderMsg.getHead().getClientTime(),
							merchantOrderMsg.getHead().getTranFlow(),
							merchantOrderMsg.getHead().getClientIP(),
							checkOrderRepBean.getHead().getRespCode(),
							checkOrderRepBean.getHead().getRespMsg());
				}
			}else{
				logger.error("merchant trancode["+merchantOrderMsg.getHead().getTranCode()+"] is not cbp001");
				throw new CashierDealException(merchantOrderMsg.getBody().getOfflineNotifyUrl(),
						merchantOrderMsg.getBody().getPageReturnUrl(),
						merchantOrderMsg.getHead().getMerchantNo(),
						merchantOrderMsg.getHead().getClientTime(),
						merchantOrderMsg.getHead().getTranFlow(),
						merchantOrderMsg.getHead().getClientIP(),
						ConstantField.MSG_ERROR_RESPDES_CBP014,ConstantField.MSG_ERROR_RESPDES_CBP014);
			}
		}catch(Exception e){
			logger.error("exception", e );
			if(e instanceof CashierDealException){
				throw e;
			}else{
				throw new CashierDealException(merchantOrderMsg.getBody().getOfflineNotifyUrl(),
						merchantOrderMsg.getBody().getPageReturnUrl(),
						merchantOrderMsg.getHead().getMerchantNo(),
						merchantOrderMsg.getHead().getClientTime(),
						merchantOrderMsg.getHead().getTranFlow(),
						merchantOrderMsg.getHead().getClientIP(),
						ConstantField.DEFAULT_ERROR_RESPCODE,ConstantField.DEFAULT_ERROR_RESPDES);
			}
		}
		if(modelAndView.getViewName()==null){
			modelAndView.setViewName("order");
		}
		request.getSession().setAttribute("modelAndViewlast", modelAndView);
		return modelAndView;
	}

	private void packHead(CashierHead cashierhead,MerchantOrderMsg merchantOrderMsg,String tranCode){
		cashierhead.setTranCode(tranCode);
		cashierhead.setMerchantNo(merchantOrderMsg.getHead().getMerchantNo());
		cashierhead.setOrderId(merchantOrderMsg.getBody().getOrderId());
		cashierhead.setTranDate(merchantOrderMsg.getBody().getOrderDate());
		cashierhead.setTranTime(merchantOrderMsg.getBody().getOrderTime());
	}
	
	private void packMsg003(OrderShowReqBean orderShowReqBean,MerchantOrderMsg merchantOrderMsg,String tranCode){
		orderShowReqBean.getHead().setTranCode(tranCode);
		orderShowReqBean.getBody().setTranFlow((merchantOrderMsg.getHead().getTranFlow()));
		orderShowReqBean.getBody().setPageReturnUrl(merchantOrderMsg.getBody().getPageReturnUrl());
		orderShowReqBean.getBody().setOfflineNotifyUrl(merchantOrderMsg.getBody().getOfflineNotifyUrl());
		orderShowReqBean.getBody().setClientIP(merchantOrderMsg.getHead().getClientIP());
		orderShowReqBean.getBody().setMerchantId(merchantOrderMsg.getHead().getMerchantNo());
		orderShowReqBean.getBody().setPurchaserId(merchantOrderMsg.getBody().getPurchaserId());
		orderShowReqBean.getBody().setMerchantName(merchantOrderMsg.getBody().getMerchantName());
		orderShowReqBean.getBody().setOrderDesc(merchantOrderMsg.getBody().getOrderDesc());
		orderShowReqBean.getBody().setTotalAmount(merchantOrderMsg.getBody().getTotalAmount());
		orderShowReqBean.getBody().setCurrency(merchantOrderMsg.getBody().getCurrency());
		orderShowReqBean.getBody().setValidUnit(merchantOrderMsg.getBody().getValidUnit());
		orderShowReqBean.getBody().setValidNum(merchantOrderMsg.getBody().getValidNum());
		orderShowReqBean.getBody().setBackParam(merchantOrderMsg.getBody().getBackParam());
		orderShowReqBean.getBody().setProductDesc(merchantOrderMsg.getBody().getProductDesc());
		orderShowReqBean.getBody().setProductId(merchantOrderMsg.getBody().getProductId());
		orderShowReqBean.getBody().setProductName(merchantOrderMsg.getBody().getProductName());
		orderShowReqBean.getBody().setShowUrl(merchantOrderMsg.getBody().getShowUrl());
		orderShowReqBean.getBody().setTradeType(merchantOrderMsg.getBody().getTradeType());
		orderShowReqBean.getBody().setTradeCode(merchantOrderMsg.getBody().getTradeCode());
		orderShowReqBean.getBody().setIsRef(merchantOrderMsg.getBody().getIsRef());
		orderShowReqBean.getBody().setInvoiceNo(merchantOrderMsg.getBody().getInvoiceNo());
		if(logger.isDebugEnabled()){
			logger.debug("chs003 req bean info "+orderShowReqBean.getBody()+"]");
		}
	}
}