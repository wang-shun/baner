package org.inn.baner.serviceimp.PLATFORM;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class QueryOrderPackBusinessService implements BusinessService {

		Logger logger = Logger.getLogger(QueryOrderPackBusinessService.class);
		@Override
		public CommonContext service(CommonContext context) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("QueryOrderPackBusinessService is begin...");
		context.setFieldStr("msgType", BusinessConstantField.MSG_TYPE_REP, CommonContext.SCOPE_GLOBAL);
		Date nowTime=new Date(); 
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		context.setFieldStr("serverTime",time.format(nowTime), CommonContext.SCOPE_GLOBAL);
		context.setFieldStr("accdt",PlatDateParamData.getInstance().getPlatDate(), CommonContext.SCOPE_GLOBAL);
		logger.info("accdt["+PlatDateParamData.getInstance().getPlatDate()+"]");
		logger.info("QueryOrderPackBusinessService is succ");
		return context;
	}

}
