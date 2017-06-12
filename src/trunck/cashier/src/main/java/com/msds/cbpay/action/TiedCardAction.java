package com.msds.cbpay.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.msds.cbpay.constant.ConstantField;
import com.msds.cbpay.entity.MerchantTiedCardMsg;
import com.msds.cbpay.exception.CashierDealException;
import com.msds.cbpay.util.SendMsg;
import com.msds.cbpay.util.XmlReversalBean;

@Controller
public class TiedCardAction {
	Logger logger = Logger.getLogger(TiedCardAction.class);	
	@RequestMapping("/tiedbankcard.do")
	private ModelAndView tiedBankCard(HttpServletRequest request,@RequestParam(value = "param", required = true) String param,ModelAndView modelAndView) throws CashierDealException {
		if (logger.isDebugEnabled()) {
			logger.debug("merchant msg is [" + param + "]");
		}
		if(param ==null){
			logger.error("merchant msg is null");
			throw new CashierDealException(null,null,null,null,null,null,null,null);
		}
		try{
			// 1.进行解密操作
			String msg = SendMsg.encodeOrg(param,ConstantField.CHS001);
			// 将解密后报文转化成bean
			MerchantTiedCardMsg merchantTiedCardMsg = null;
			if (null != msg) {
				merchantTiedCardMsg = XmlReversalBean.xmlToBean(msg, MerchantTiedCardMsg.class);// 获取商户里的bean
				modelAndView.getModel().put("merchantTiedCardMsg", merchantTiedCardMsg);
			} else {
				logger.error("plat return decode msg is null");
			}
		}catch(Exception e){
			logger.error("tied card is error",e);
			throw new CashierDealException(null,null,null,null,null,null,null,null);
		}
		modelAndView.setViewName("tiedcard");
		return modelAndView;
	}
}
