package org.inn.baner.serviceimp.PLATFORM;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantPermissionData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;
/**
 * 
 * @author lenovo
 *查询商户权限
 */
public class CheckMerchantPermissionBusinessService implements BusinessService {

	Logger logger = Logger.getLogger(CheckMerchantPermissionBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("CheckMerchantPermissionBusinessService is begin ..");
		String merchantId = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
		String tranCode = context.getFieldStr("merchantTranCode", CommonContext.SCOPE_GLOBAL)!=null?context.getFieldStr("merchantTranCode", CommonContext.SCOPE_GLOBAL):context.getFieldStr("tranCode", CommonContext.SCOPE_GLOBAL);
		logger.info("merchantId["+merchantId+"]");
		BMerchantPermissionData bMerchantPermissionData = new BMerchantPermissionData();
		List<Map<String, String>> list = null;
		try{
			list = bMerchantPermissionData.getBMerchantPermission(merchantId, tranCode);
		}catch(HandlerException e){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			logger.error("getBMerchantInfo is error");
			throw new ServiceException(e);
		}
		if(list==null||list.size()==0){
			logger.error("merchant is not support permission");
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000510);
		}
		logger.info("CheckMerchantPermissionBusinessService is succ");
		return context;
	}
}
