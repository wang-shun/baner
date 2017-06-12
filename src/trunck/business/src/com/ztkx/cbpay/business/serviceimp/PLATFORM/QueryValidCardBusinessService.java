package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BUserCardData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class QueryValidCardBusinessService  implements BusinessService {

	Logger logger = Logger.getLogger(QueryValidCardBusinessService.class);
	
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		logger.info("QueryValidCardBusinessService is begin ..");
		if("true".equals(context.getObj("checkInfoExistgFlag", CommonContext.SCOPE_GLOBAL))){
			String merchantno = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
			String purchaserId = context.getFieldStr("purchaserId", CommonContext.SCOPE_GLOBAL);
			BUserCardData userCardData = new BUserCardData();
			List<Map<String, String>> userCard = null;
			try {
				userCard = userCardData.getNormalCardInfo(merchantno, purchaserId);
			} catch (HandlerException e) {
				// TODO Auto-generated catch block
				context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
				logger.error("getCardInfo is error",e);
				throw new ServiceException(e);
			}
			if(userCard!=null&&userCard.size()>0){
				if(userCard.size()>1){
					logger.warn("card size[" + userCard.size()+"]");
				}
				logger.info("cardid["+userCard.get(0).get("CARDNO")+"]");
				context.setFieldStr("cardId", userCard.get(0).get("CARDNO"), CommonContext.SCOPE_GLOBAL);
			}
		}
		logger.info("QueryValidCardBusinessService is succ");
		return context;
	}

}
