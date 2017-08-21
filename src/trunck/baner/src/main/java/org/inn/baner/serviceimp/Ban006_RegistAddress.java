package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Userloc;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.UserLocData;

import java.util.Date;

/**
 * 登记用户的位置信息
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban006_RegistAddress implements BusinessService {

	private Logger logger = Logger.getLogger(Ban006_RegistAddress.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String latitude = context.getFieldStr(Ban.latitude,CommonContext.SCOPE_GLOBAL);
		String  longitude = context.getFieldStr(Ban.longitude,CommonContext.SCOPE_GLOBAL);
		String  mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);

		logger.info("latitude ["+latitude+"]");
		logger.info("longitude ["+longitude+"]");
		logger.info("mobileno ["+mobileno+"]");
		Userloc userLoc = new Userloc();
		String platdate = TimeUtil.getCurrentTime(null);
		userLoc.setPlatdate(platdate);
		userLoc.setMobileno(mobileno);
		userLoc.setCreatetime(new Date());
		userLoc.setLatitude(latitude);
		userLoc.setLongitude(longitude);

		UserLocData userLocData = null;
		try {
			userLocData = new UserLocData();
			Userloc userloc = userLocData.qry(platdate, mobileno);
			int res = -1;
			if (userloc == null) {
				res = userLocData.insertRecord(userLoc);
			}else{
				res = userLocData.update(userLoc);
			}
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
