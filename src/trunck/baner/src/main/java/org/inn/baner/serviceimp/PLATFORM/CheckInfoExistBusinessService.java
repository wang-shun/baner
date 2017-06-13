package org.inn.baner.serviceimp.PLATFORM;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 
 * @author sunyoushan
 *查询用户信息，判断是否存在
 */
public class CheckInfoExistBusinessService implements BusinessService {
	
	Logger logger = Logger.getLogger(CheckInfoExistBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		logger.info("CheckInfoExistBusinessService is begin...");
		// TODO Auto-generated method stub
		String merchantno = context.getFieldStr("merchantNo", CommonContext.SCOPE_GLOBAL);
		String purchaserId = context.getFieldStr("purchaserId", CommonContext.SCOPE_GLOBAL);
		BUserInfoData userinfodata = new BUserInfoData();
		List<Map<String, String>> userinfo = null;
		try {
			userinfo = userinfodata.getUserInfo(merchantno, purchaserId);
		} catch (HandlerException e) {
			// TODO Auto-generated catch block
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			logger.error("checkInfoExist is error");
			throw new ServiceException(e);
		}
		if(userinfo==null||userinfo.size()==0){
			context.setObj("checkInfoExistgFlag","false", CommonContext.SCOPE_GLOBAL);
			logger.debug("not valid userinfo in system");
		}else{
			context.setObj("checkInfoExistgFlag","true", CommonContext.SCOPE_GLOBAL);
			context.setObj("realName", userinfo.get(0).get("REALNAME"), CommonContext.SCOPE_GLOBAL);
			context.setObj("telnum", userinfo.get(0).get("TELNUM"), CommonContext.SCOPE_GLOBAL);
			logger.debug("telnum["+userinfo.get(0).get("TELNUM")+"] , realname ["+userinfo.get(0).get("REALNAME")+"]");
		}
		logger.info("CheckInfoExistBusinessService is succ");
		return context;
	}
}
