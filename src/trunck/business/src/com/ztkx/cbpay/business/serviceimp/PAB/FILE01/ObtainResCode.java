package com.ztkx.cbpay.business.serviceimp.PAB.FILE01;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 从定长报文头中截取通讯响应码
 * @author zhangxiaoyun
 * 2016年3月24日 下午2:29:25
 * <p>Company:ztkx</p>
 */
public class ObtainResCode implements BusinessService{

	Logger logger = Logger.getLogger(ObtainResCode.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		//文件日期
		String resCode = context.getFieldStr(BusinessConstantField.PAB_RES_CODE, CommonContext.SCOPE_GLOBAL);
		String resMsg = context.getFieldStr(BusinessConstantField.PAB_RES_MSG, CommonContext.SCOPE_GLOBAL);
		context.setFieldStr("PROCESS_STATUS", resCode, CommonContext.SCOPE_GLOBAL);
		logger.info("resCode ["+resCode+"] resMsg ["+resMsg+"]");
		return context;
	}

}
