package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import org.apache.log4j.Logger;
import org.inn.baner.bean.User;
import org.inn.baner.constant.BErrorCode;
import org.inn.baner.constant.Ban001;
import org.inn.baner.handler.data.UserData;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 发起购汇
 * 1.登记购汇总控表
 * 2.登记购汇明细表
 * 3.更新商户订单表     汇率渠道=平安银行    购汇批次号      购汇状态为初始状态
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban001_UserRegist implements BusinessService {

	private Logger logger = Logger.getLogger(Ban001_UserRegist.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban001.mobileno, CommonContext.SCOPE_GLOBAL);
		String nickName = context.getFieldStr(Ban001.nickname, CommonContext.SCOPE_GLOBAL);
		String passwd = context.getFieldStr(Ban001.passwd, CommonContext.SCOPE_GLOBAL);
		logger.info("mobileno [" + mobileno + "]");
		logger.info("nickName [" + nickName + "]");
		logger.info("passwd [" + passwd + "]");
		UserData userData = null;
		try {
			User user = new User();
			user.setMobileno(mobileno);
			user.setNickname(nickName);
			user.setPasswd(passwd);

			userData = new UserData();
			userData.insertRecord(user);
			logger.info("user regist succ");

		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
