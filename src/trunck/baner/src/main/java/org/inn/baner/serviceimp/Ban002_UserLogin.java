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
 * 用户登录
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban002_UserLogin implements BusinessService {

	private Logger logger = Logger.getLogger(Ban002_UserLogin.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno, CommonContext.SCOPE_GLOBAL);
		String passwd = context.getFieldStr(Ban.passwd, CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno [" + mobileno + "]");
		logger.info("passwd [" + passwd + "]");

		UserData userData = null;
		try {

			userData = new UserData();
			User user = userData.qryByMobile(mobileno);
			if (!user.getPasswd().equals(passwd)) {
				logger.warn("user pwd error ["+BErrorCode.PWDERROR.desc+"]");
				ContextUtil.setErrorCode(BErrorCode.PWDERROR.code, context);
			}else{
				logger.info("user regist succ");
			}

			context.setFieldStr(Ban.nickname,user.getNickname(),CommonContext.SCOPE_GLOBAL);

		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
