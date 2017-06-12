package com.msds.cbpay.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ztkx.cbpay.platformutil.log.FlowNoContainer;

public class MakeFlowNo implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String Flowno = (String)((HttpServletRequest)request).getSession().getAttribute("Flowno");
		if(StringUtils.isNotEmpty(Flowno))
			FlowNoContainer.setFlowNo(Flowno);
		else if(StringUtils.isNotBlank(request.getParameter("Flowno"))){
			FlowNoContainer.setFlowNo(Flowno);
		}else{
			Flowno = ((HttpServletRequest)request).getSession().getId();
			FlowNoContainer.setFlowNo(Flowno);
			((HttpServletRequest)request).getSession().setAttribute("Flowno", Flowno);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
