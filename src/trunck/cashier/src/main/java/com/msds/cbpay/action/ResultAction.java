package com.msds.cbpay.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msds.cbpay.entity.LuaguageBean;


@Controller
public class ResultAction {
	Logger logger = Logger.getLogger(ExportProductAction.class);	
	@RequestMapping("/success")
	private String exportSucc(HttpServletRequest request) {
		LuaguageBean luaguageBean = new LuaguageBean();
		ResourceBundle myResourcesBundle = ResourceBundle.getBundle("i18n.language",request.getLocale());
		luaguageBean.setSuccess(myResourcesBundle.getString("success"));
		luaguageBean.setSure(myResourcesBundle.getString("sure"));
		luaguageBean.setBackmerchant(myResourcesBundle.getString("backmerchant"));
		luaguageBean.setPayresult(myResourcesBundle.getString("payresult"));
		luaguageBean.setReturnpre(myResourcesBundle.getString("returnpre"));
		luaguageBean.setReturnsuf(myResourcesBundle.getString("returnsuf"));
		request.setAttribute("pageReturnUrl", request.getParameter("pageReturnUrl"));
		request.setAttribute("params", request.getParameter("params"));
		request.setAttribute("luaguageBean", luaguageBean);
		return "exportsuccess";
	}
	@RequestMapping("/error")
	private String exportErr(HttpServletRequest request) {
		request.setAttribute("pageReturnUrl", request.getParameter("pageReturnUrl"));
		request.setAttribute("params", request.getParameter("params"));
		String respMsg = "";
		try {
			respMsg = URLDecoder.decode(request.getParameter("respMsg"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(request.getParameter("respMsg") +"decode error",e);
		}
		request.setAttribute("respMsg", respMsg);
		return "error";
	}
}
