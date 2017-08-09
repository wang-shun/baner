package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseUtil.BeanUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Comment;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.CommentData;
import org.inn.baner.handler.data.UserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 根据帖子ID查询该帖子下所有评论
 * @author rain.wang
 * 2017-08-09
 * <p>Company:ztkx</p>
 */
public class Ban012_ObtainCommentByPost implements BusinessService {

	private Logger logger = Logger.getLogger(Ban012_ObtainCommentByPost.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String postId = context.getFieldStr(Ban.postid,CommonContext.SCOPE_GLOBAL);
		String dateFormate = "yyyyMMdd HH:mm:ss";
		logger.info("postId ["+postId+"]");

		List<Comment> commentList = null;
		CommentData commentData = null;
		UserData userData = null;
		try {

            commentData = new CommentData();
			userData = new UserData();
			String[] postidArr = postId.split("@", -1);
			List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
			for (String postidArrItem : postidArr) {
				commentList = commentData.qryByPostId(postidArrItem);
				for (Comment comment : commentList) {
					Map<String,Object> map = BeanUtil.objToMap(comment);
					map.put(Ban.commentid, comment.getCommentid());
					map.put(Ban.postid, comment.getPostid());
                    map.put(Ban.parentcommentid, comment.getParentcommentid());
					map.put(Ban.mobileno, comment.getCreatormobileno());
					map.put(Ban.nickname, userData.qryByMobile(comment.getCreatormobileno()).getNickname());
					map.put(Ban.context, new String(comment.getContext()));
					Date createTime = comment.getCreatetime();
					map.put(Ban.createtime,TimeUtil.dateFormate(dateFormate, createTime));
					mapArrayList.add(map);
				}
			}

			context.setObj(Ban.lists, mapArrayList, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(Ban.size, String.valueOf(mapArrayList.size()), CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
