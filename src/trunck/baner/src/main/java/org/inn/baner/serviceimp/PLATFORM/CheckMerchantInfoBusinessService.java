package org.inn.baner.serviceimp.PLATFORM;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;
/**
 * 
 * @author lenovo
 * 1.查询商户信息
 * 2.匹配商户币种
 *  *
 */
public class CheckMerchantInfoBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(CheckMerchantInfoBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("CheckMerchantInfoBusinessService is begin ..");
		String merchantId = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
		String currency = context.getFieldStr("currency", CommonContext.SCOPE_GLOBAL);
		logger.info("merchantId["+merchantId+"]  currency["+currency+"]");
		if(currency==null){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000532);
			return context;
		}
		boolean flag = false;
		String poundageFlag = null;
		String poundageRate = null;
		BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
		List<Map<String, String>> list = null;
		try{
			list = bMerchantInfoData.getBMerchantInfo(merchantId);
		}catch(HandlerException e){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			logger.error("getBMerchantInfo is error");
			throw new ServiceException(e);
		}
		
		if(list==null||list.size()==0){
			logger.error("merchant is not exist");
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000508);
			return context;
		}else{
			for(Map<String, String> map : list){
				if(currency.equals(map.get("CURRENCY_TYPE"))){
					flag = true;
					poundageFlag = map.get("POUNDAGEFLAG");
					poundageRate = map.get("POUNDAGERATE");
					break;
				}
			}
		}
		logger.info("flag["+flag+"]  poundageFlag["+poundageFlag+"]  poundageRate["+poundageRate+"]");
		if(flag){
			context.setFieldStr("poundageFlag", poundageFlag, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("poundageRate", poundageRate, CommonContext.SCOPE_GLOBAL);
		}else{
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000509);
			logger.error("merchant is not support currency");
		}
		logger.info("CheckMerchantInfoBusinessService is succ");
		return context;
	}
}
