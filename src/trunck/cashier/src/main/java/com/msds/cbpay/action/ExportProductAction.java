package com.msds.cbpay.action;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.msds.cbpay.entity.LuaguageBean;
import com.msds.cbpay.entity.MerchantOrderMsg;
import com.msds.cbpay.util.Base64Util;
import com.msds.cbpay.util.XmlReversalBean;



@Controller
public class ExportProductAction {
	Logger logger = Logger.getLogger(ExportProductAction.class);	
	@RequestMapping("/export")
	private ModelAndView export(HttpServletRequest request,@RequestParam(value = "param", required = true) String param, ModelAndView modelAndView) {
		logger.info("export is begin...");
		if (logger.isDebugEnabled()) {
			logger.debug("merchant msg is [" + param + "]");
		}
		MerchantOrderMsg merchantOrderMsg = null;
		try {
			if (null != param) {
				param = Base64Util.decode(param);
				merchantOrderMsg = XmlReversalBean.xmlToBean(param, MerchantOrderMsg.class);
			}
		}catch(Exception e){
				logger.error("exportproduct is error", e);
		}
		LuaguageBean luaguageBean = makeLuaguage(request.getLocale());
		modelAndView.getModel().put("merchantOrderMsg", merchantOrderMsg);
		modelAndView.getModel().put("luaguageBean", luaguageBean);
		modelAndView.setViewName("exportproduct");
		logger.info("export is succ");
		return modelAndView;
	}
	
	
	private LuaguageBean makeLuaguage(Locale locale){
		ResourceBundle myResourcesBundle = ResourceBundle.getBundle("i18n.language",locale);
		LuaguageBean luaguageBean = new LuaguageBean();
		luaguageBean.setCbpay(myResourcesBundle.getString("cbpay"));
		luaguageBean.setCash(myResourcesBundle.getString("cash"));
		luaguageBean.setOrderinfo(myResourcesBundle.getString("orderinfo"));
		luaguageBean.setMerchantname(myResourcesBundle.getString("merchantname"));
		luaguageBean.setProductname(myResourcesBundle.getString("productname"));
		luaguageBean.setOrdermoney(myResourcesBundle.getString("ordermoney"));
		luaguageBean.setOrdercurrency(myResourcesBundle.getString("ordercurrency"));
		luaguageBean.setPaymoney(myResourcesBundle.getString("paymoney"));
		luaguageBean.setPaycurrency(myResourcesBundle.getString("paycurrency"));
		luaguageBean.setOrderdate(myResourcesBundle.getString("orderdate"));
		luaguageBean.setOrderId(myResourcesBundle.getString("orderId"));
		luaguageBean.setCurrencyrate(myResourcesBundle.getString("currencyrate"));
		luaguageBean.setPayinfo(myResourcesBundle.getString("payinfo"));
		luaguageBean.setCardId(myResourcesBundle.getString("cardId"));
		luaguageBean.setPassword(myResourcesBundle.getString("password"));
		luaguageBean.setPay(myResourcesBundle.getString("pay"));
		return luaguageBean;
	}
}
