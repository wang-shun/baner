package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.flowno.ZKFlowNoPoolManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Comment;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.CommentData;

import java.util.Date;

/**
 * 评论帖子
 * @author rain.wang
 * 2017-8-9 17:24:11
 * <p>Company:ztkx</p>
 */
public class Ban011_addComment implements BusinessService {

	private Logger logger = Logger.getLogger(Ban011_addComment.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);
		String postid = context.getFieldStr(Ban.postid,CommonContext.SCOPE_GLOBAL);
		String parentcommentid = context.getFieldStr(Ban.parentcommentid,CommonContext.SCOPE_GLOBAL);
		String commentContext = context.getFieldStr(Ban.context,CommonContext.SCOPE_GLOBAL);

		logger.info("mobileno ["+mobileno+"]");
		logger.info("postid ["+postid+"]");
		logger.info("parentcommentid ["+parentcommentid+"]");
		logger.info("commentContext ["+new String(Base64.decodeBase64(commentContext))+"]");

        Comment comment = new Comment();
        comment.setCommentid(ZKFlowNoPoolManager.getInstance().getSequence("6", Ban.commentidseq));
        comment.setPostid(postid);
        comment.setCreatormobileno(mobileno);
        comment.setParentcommentid(parentcommentid);
        comment.setContext(commentContext.getBytes());
        comment.setCreatetime(new Date());

		CommentData commentData  = null;
		try {
            commentData = new CommentData();
			commentData.setContext(context);

			int res = commentData.insertRecord(comment);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
