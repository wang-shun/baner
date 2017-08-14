package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.flowno.ZKFlowNoPoolManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Post;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.PostData;

import java.util.Date;

/**
 * 上传帖子
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban008_UploadPost implements BusinessService {

	private Logger logger = Logger.getLogger(Ban008_UploadPost.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);
		String topicid = context.getFieldStr(Ban.topicid,CommonContext.SCOPE_GLOBAL);
		String postname = context.getFieldStr(Ban.postname,CommonContext.SCOPE_GLOBAL);
        Integer isAnon = Integer.valueOf(context.getFieldStr(Ban.isAnon,CommonContext.SCOPE_GLOBAL));
		String postContext = context.getFieldStr(Ban.context,CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno ["+mobileno+"]");
		logger.info("topicid ["+topicid+"]");
		logger.info("postname ["+postname+"]");
        logger.info("isAnon ["+isAnon+"]");
		logger.info("postContext ["+new String(Base64.decodeBase64(postContext))+"]");

		Post post = new Post();
		post.setCreatormobileno(mobileno);
		post.setTopicid(topicid);
		post.setPostname(postname);
		post.setPostid(ZKFlowNoPoolManager.getInstance().getSequence("6", Ban.postidseq));
		post.setContext(postContext.getBytes());
        post.setIsanon(isAnon);
		post.setZantimes(0);
		post.setCreatetime(new Date());
		post.setUpdatetime(new Date());

		PostData postData  = null;
		try {
			postData = new PostData();

			int res = postData.insertRecord(post);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
