package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.initdata.BTradeParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class CheckTradeCodeService implements BusinessService {

	Logger logger = Logger.getLogger(CheckTradeCodeService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("CheckTradeCodeService is begin ..");
		String tradeCode = context.getFieldStr("tradeCode", CommonContext.SCOPE_GLOBAL);
		String invoiceNo = context.getFieldStr("invoiceNo", CommonContext.SCOPE_GLOBAL);
		List<String> list = BTradeParamData.getInstance().getKeyList();
		logger.info("tradeCode["+tradeCode+"] ,invoiceNo["+invoiceNo+"], BTradeParamData size is "+list.size());
		if(!list.contains(tradeCode)){
			logger.error("merchant trade code["+tradeCode+"] is error");
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000535, context);
		}
		logger.info("CheckTradeCodeService is succ");
		return context;
	}
}
