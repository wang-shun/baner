package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import org.apache.log4j.Logger;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.PostData;

/**
 * 根据主题id获取主题下帖子列表 *
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban005_ZanOrCancle implements BusinessService {

	private Logger logger = Logger.getLogger(Ban005_ZanOrCancle.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		int topicId = Integer.parseInt(context.getFieldStr(Ban.topicid,CommonContext.SCOPE_GLOBAL));
		int postId = Integer.parseInt(context.getFieldStr(Ban.postid,CommonContext.SCOPE_GLOBAL));
		String iszan = context.getFieldStr(Ban.iszan,CommonContext.SCOPE_GLOBAL);

		logger.info("topicid ["+topicId+"]");
		logger.info("postId ["+postId+"]");
		logger.info("iszan ["+iszan+"]");

		PostData postData = null;
		try {
			postData = new PostData();
			postData.setContext(context);

			if (iszan.equals("0")) {
				postData.cancleZan(topicId, postId);
			} else if (iszan.equals("1")) {
				postData.zan(topicId, postId);
			}
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
