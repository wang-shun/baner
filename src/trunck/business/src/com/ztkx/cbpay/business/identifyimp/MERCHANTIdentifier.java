package com.ztkx.cbpay.business.identifyimp;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.msg.MessageIdentifier;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 中信银行的交易码在url里，需要获取url然后截取报文
 * @author zhangxiaoyun
 * 2016年2月2日 下午5:03:20
 * <p>Company:ztkx</p>
 */
public class MERCHANTIdentifier implements MessageIdentifier{
	private Logger logger = Logger.getLogger(MERCHANTIdentifier.class);
	@Override
	public String identify(CommonContext context) {
		//url = https://中信银行跨境电子商务服务地址/CBEC/CEBATEXC.do;
		String url = context.getFieldStr(ContainerConstantField.REQ_URL);
		
		if(url==null){
			return null;
		}
		String tranCode = url.substring(url.lastIndexOf("/") + 1,url.lastIndexOf(".do"));
		return tranCode;
	}
}
