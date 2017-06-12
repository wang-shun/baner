package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BForeignCurrencyRateData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

public class QueryFrgCurrRateBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(QueryFrgCurrRateBusinessService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("QueryFrgCurrRateBusinessService is begin ..");
		try{
			String currency = context.getFieldStr("currency", CommonContext.SCOPE_GLOBAL);
			BigDecimal orderamount = new BigDecimal(context.getFieldStr("totalAmount", CommonContext.SCOPE_GLOBAL));
			String rate = null;
			BForeignCurrencyRateData bForeignCurrencyRateData = new BForeignCurrencyRateData();
			bForeignCurrencyRateData.getConnection();
			try{
				rate = bForeignCurrencyRateData.selectExsellPrice(TimeUtil.getCurrentTime("YYYYMMdd"),currency);
				if(StringUtils.isEmpty(rate)){
					logger.warn("this CLIENT_EXCHANGE_RATE is null");
					rate = bForeignCurrencyRateData.selectExsellPrice(TimeUtil.getLastDate(),currency);
					if(StringUtils.isEmpty(rate)){
						logger.error("this CLIENT_EXCHANGE_RATE is null");
						context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000526);
						return context;
					}
				}
			}catch(Exception e){
				logger.error("query table b_foreign_currency_rate is error");
				context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
				throw new ServiceException(e);
			}finally{
				DataHandlerUtil.releaseSource(bForeignCurrencyRateData);
			}
			
			String poundageFlag = context.getFieldStr("poundageFlag", CommonContext.SCOPE_GLOBAL);
			String poundageRate = context.getFieldStr("poundageRate", CommonContext.SCOPE_GLOBAL);
			BigDecimal acceptancerate = new BigDecimal(rate);//表中汇率
			BigDecimal poundageRateDecimal = new BigDecimal(poundageRate);//手续费费率
			logger.info("CLIENT_EXCHANGE_RATE ["+rate+"]");
			if("1".equals(poundageFlag)){//用户
				BigDecimal acceptanceordermount = orderamount.multiply(acceptancerate);//订单转本币之后金额
				BigDecimal acceptancepoundagemount = acceptanceordermount.multiply(poundageRateDecimal);//手续费
				context.setFieldStr("acceptanceMount",acceptanceordermount.add(acceptancepoundagemount).toString(), CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("acceptanceRate", acceptancerate.toString(), CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("merchantPoundage",acceptancepoundagemount.toString(), CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("acceptancyCurrency", BusinessConstantField.China_currency, CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("purchaseMount",context.getFieldStr("totalAmount", CommonContext.SCOPE_GLOBAL), CommonContext.SCOPE_GLOBAL);
			}else{//商户
				BigDecimal acceptancepoundagemount = orderamount.multiply(poundageRateDecimal);//手续费
				BigDecimal acceptanceordermount = orderamount.multiply(acceptancerate);//收单金额
				context.setFieldStr("acceptanceMount",acceptanceordermount.toString(), CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("acceptanceRate", acceptancerate.toString(), CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("merchantPoundage",acceptancepoundagemount.toString(), CommonContext.SCOPE_GLOBAL);//手续费
				context.setFieldStr("acceptancyCurrency", BusinessConstantField.China_currency, CommonContext.SCOPE_GLOBAL);
				context.setFieldStr("purchaseMount",orderamount.subtract(acceptancepoundagemount).toString(), CommonContext.SCOPE_GLOBAL);//购汇金额
			}
		}catch(Exception e){
			logger.error("calc acceptanceMount is error",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
		}
		logger.info("QueryFrgCurrRateBusinessService is end");
		return context;
	}

}
