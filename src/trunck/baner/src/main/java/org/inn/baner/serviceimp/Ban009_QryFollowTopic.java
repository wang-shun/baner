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
 * 查询关注
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban009_QryFollowTopic implements BusinessService {

	private Logger logger = Logger.getLogger(Ban009_QryFollowTopic.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno ["+mobileno+"]");

		UserData userData  = null;
		try {
			userData = new UserData();

			User user = userData.qryByMobile(mobileno);
			context.setFieldStr(Ban.followtopic, user.getFollowtopic(), CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(Ban.mobileno, user.getMobileno(), CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(Ban.nickname, user.getNickname(), CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
