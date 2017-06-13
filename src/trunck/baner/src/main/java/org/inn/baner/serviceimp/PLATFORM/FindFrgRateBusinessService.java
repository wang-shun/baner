package org.inn.baner.serviceimp.PLATFORM;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BForeignCurrencyRateData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

public class FindFrgRateBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(FindFrgRateBusinessService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("FindFrgRateBusinessService is begin ...");
		String currency = context.getFieldStr("currency", CommonContext.SCOPE_GLOBAL);
		String exQuoteDate = context.getFieldStr("exQuoteDate", CommonContext.SCOPE_GLOBAL);
		logger.info("currency["+currency+"]  exQuoteDate["+exQuoteDate+"]");
		BForeignCurrencyRateData bForeignCurrencyRateData = new BForeignCurrencyRateData();
		bForeignCurrencyRateData.getConnection();
		Map<String,String> map = null;
		try{
			map = bForeignCurrencyRateData.selectMaxExsellPrice(exQuoteDate,currency);
			if(map==null){
				logger.warn("this exsellprice is null");
				map = bForeignCurrencyRateData.selectMaxExsellPrice(TimeUtil.getCurrentTime("YYYYMMdd"),currency);
				if(map==null){
					logger.warn("this exsellprice is null");
					map = bForeignCurrencyRateData.selectMaxExsellPrice(TimeUtil.getLastDate(),currency);
					if(map==null){
						logger.warn("this exsellprice is null");
						context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000526);
						return context;
					}
				}
			}
		}catch(Exception e){
			logger.error("query table b_foreign_currency_rate is error");
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bForeignCurrencyRateData);
		}
		context.setFieldStr("exBuyPrice", map.get("PRICE"),CommonContext.SCOPE_GLOBAL);
		context.setFieldStr("exQuoteTime", map.get("EXQUOTETIME"),CommonContext.SCOPE_GLOBAL);
		logger.info("exQuoteTime["+map.get("EXQUOTETIME")+"]  exSellPrice["+map.get("EXSELLPRICE")+"]");
		logger.info("FindFrgRateBusinessService is succ");
		return context;
	}

}
