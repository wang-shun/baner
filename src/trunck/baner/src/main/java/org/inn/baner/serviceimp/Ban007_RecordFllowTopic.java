package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import org.apache.log4j.Logger;
import org.inn.baner.bean.User;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.UserData;

/**
 * 登记用户关注的主题
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban007_RecordFllowTopic implements BusinessService {

	private Logger logger = Logger.getLogger(Ban007_RecordFllowTopic.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);
		String followtopic = context.getFieldStr(Ban.followtopic,CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno ["+mobileno+"]");
		logger.info("followtopic ["+followtopic+"]");

		User user = context.getBean(User.class);
		UserData userData  = null;
		try {
			userData = new UserData();
			int res = userData.update(user);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
