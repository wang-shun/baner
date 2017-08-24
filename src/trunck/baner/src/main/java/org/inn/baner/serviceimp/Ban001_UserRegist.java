package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.flowno.ZKFlowNoPoolManager;
import org.apache.log4j.Logger;
import org.inn.baner.bean.User;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.UserData;

/**
 * 用户注册
 * @author zhangxiaoyun
 * 2017-06-19
 * <p>Company:ztkx</p>
 */
public class Ban001_UserRegist implements BusinessService {

	private Logger logger = Logger.getLogger(Ban001_UserRegist.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno, CommonContext.SCOPE_GLOBAL);
		String nickName = context.getFieldStr(Ban.nickname, CommonContext.SCOPE_GLOBAL);
		String passwd = context.getFieldStr(Ban.passwd, CommonContext.SCOPE_GLOBAL);
        String job = context.getFieldStr(Ban.job, CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno [" + mobileno + "]");
		logger.info("nickName [" + nickName + "]");
		logger.info("passwd [" + passwd + "]");
        logger.info("job [" + job + "]");
		UserData userData = null;
		try {
			userData = new UserData();
			userData.setContext(context);

			User user = userData.qryByMobile(mobileno);
			if (user == null) {
				user = new User();
				user.setMobileno(mobileno);
				user.setNickname(nickName);
				user.setPasswd(passwd);
                user.setJob(job);
				String banid = ZKFlowNoPoolManager.getInstance().getSequence("5",Ban.banerid);
				logger.info("banerId ["+banid+"]");
				user.setBanerid(banid);
				userData.insertRecord(user);
				logger.info("user regist succ");
			}else{
				user.setNickname(nickName);
				user.setPasswd(passwd);
				user.setFollowtopic(passwd);
                user.setJob(job);
				if ("".equals(user.getBanerid()) || user.getBanerid() == null) {
					//访问zk获取唯一流水号
					String banid = ZKFlowNoPoolManager.getInstance().getSequence("5",Ban.banerid);
					logger.info("banerId ["+banid+"]");
					user.setBanerid(banid);
				}
				userData.update(user);
				logger.info("user [" + user.getMobileno() + "] update succ");
			}
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ["+BErrorCode.FAIL.desc+"]",e);
		}
		return context;
	}
}
