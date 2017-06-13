package org.inn.baner.serviceimp.PLATFORM;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class CheckOrderBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(CheckOrderBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("CheckOrderBusinessService is begin ..");
		String merchantId = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
		String orderId = context.getFieldStr("orderId", CommonContext.SCOPE_GLOBAL);
		String trandate = context.getFieldStr("tranDate", CommonContext.SCOPE_GLOBAL);
		BMerchantOrderData bpfd = new BMerchantOrderData();
		bpfd.getConnection();
		int count = 0;
		try{
			count = bpfd.selectCount(merchantId,orderId,trandate);
		}catch(HandlerException e){
			logger.error("query merchant order is error",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515, context);
		}finally{
			DataHandlerUtil.releaseSource(bpfd);
		}
		logger.info("orderid["+orderId+"] count is "+count);
		if(count>0){
			context.setResponseCode(BusinessErrorCodeConstant.BUSS_PLA000500);
			logger.error("order["+orderId+"] is not first");
		}
		logger.info("CheckOrderBusinessService is succ");
		return context;
	}
}
