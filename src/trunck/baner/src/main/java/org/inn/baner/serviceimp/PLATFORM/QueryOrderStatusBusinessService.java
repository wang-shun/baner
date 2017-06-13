package org.inn.baner.serviceimp.PLATFORM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class QueryOrderStatusBusinessService  implements BusinessService {

	
	Logger logger = Logger.getLogger(QueryOrderStatusBusinessService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("QueryOrderStatusBusinessService is begin...");
		// 取普通字段
		String orderId = context.getFieldStr("orderId", CommonContext.SCOPE_GLOBAL);
		String trandate = context.getFieldStr("orderDate", CommonContext.SCOPE_GLOBAL);;
		String merchantId = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
		
		if(logger.isDebugEnabled()){
			logger.debug("orderId ["+ orderId +"]");
			logger.debug("trandate ["+ trandate +"]");
			logger.debug("merchantId ["+ merchantId +"]");
		}
		
		String sql = "select ORDERSTATUS from b_merchant_order where merchantid= ? and orderid= ? and orderdate= ?";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("merchantid", merchantId);
		map.put("orderid", orderId);
		map.put("orderdate", trandate);
		
		BMerchantOrderData data = new BMerchantOrderData();
		
		List<Map<String, String>> resList = null;
		try {
			resList = data.executeQuery(sql, map);
		} catch (HandlerException e) {
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			logger.error("query data exception");
			throw new ServiceException("query data exception",e);
		}
		
		if(resList == null){
			//订单不存在
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501);
			logger.error("order is not exist");
			throw new ServiceException("order is not exist");
		}
		else if(resList.size()>1){
			//订单重复
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000500);
			logger.error("order is repetition");
			throw new ServiceException("order is repetition");
		}else{
			Map<String,String> dataMap = resList.get(0);
			//订单支付状态
			String payStatus = dataMap.get("ORDERSTATUS");
			logger.info("order pay status is ["+payStatus+"]");
			context.setFieldStr("orderStatus", payStatus,CommonContext.SCOPE_GLOBAL);
		}
		logger.info("QueryOrderStatusBusinessService is succ");
		return context;
	}

}
