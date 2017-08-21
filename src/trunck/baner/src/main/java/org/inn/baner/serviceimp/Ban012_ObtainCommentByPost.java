package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseUtil.BeanUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Comment;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.CommentData;
import org.inn.baner.handler.data.UserData;

import java.util.*;

/**
 * 根据帖子ID查询该帖子下所有评论
 * @author rain.wang
 * 2017-08-09
 * <p>Company:ztkx</p>
 */
public class Ban012_ObtainCommentByPost implements BusinessService {

	private Logger logger = Logger.getLogger(Ban012_ObtainCommentByPost.class);
    private String dateFormate = "yyyyMMdd HH:mm:ss";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		String postId = context.getFieldStr(Ban.postid,CommonContext.SCOPE_GLOBAL);
		logger.info("postId ["+postId+"]");

		List<Comment> commentList = null;
		CommentData commentData = null;
		UserData userData = null;
		try {

            commentData = new CommentData();
			userData = new UserData();
			String[] postidArr = postId.split("@", -1);
			List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();

			Map<String,List<Comment>> parentCommentMap  = new HashMap<>();
            List<Comment> parentCommentList = new ArrayList<Comment>();
            //从数据库查询评论，并按照父子记录到Map中
			for (String postidArrItem : postidArr) {
                commentList = commentData.qryByPostId(postidArrItem);
                for (Comment comment : commentList) {
                    if (StringUtils.isNotBlank(comment.getParentcommentid())) {
                        if (parentCommentMap.get(comment.getParentcommentid()) != null) {
                            parentCommentMap.get(comment.getParentcommentid()).add(comment);
                        } else {
                            List<Comment> tmp = new ArrayList<Comment>();
                            tmp.add(comment);
                            parentCommentMap.put(comment.getParentcommentid(), tmp);
                        }
                    } else {
                        parentCommentList.add(comment);
                    }
                }
            }
            int size = parentCommentList.size();
            //将结果进行遍历，拼装返回报文
            for(Comment comment : parentCommentList){
                Map<String,Object> map = BeanUtil.objToMap(comment);
                map.put(Ban.commentid, comment.getCommentid());
                map.put(Ban.postid, comment.getPostid());
                map.put(Ban.parentcommentid, comment.getParentcommentid());
                map.put(Ban.mobileno, comment.getCreatormobileno());
                map.put(Ban.nickname, userData.qryByMobile(comment.getCreatormobileno()).getNickname());
                map.put(Ban.context, new String(comment.getContext()));
                map.put(Ban.createtime,TimeUtil.dateFormate(dateFormate, comment.getCreatetime()));
                List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
                fillChildList(childList,comment,parentCommentMap,userData);
                map.put(Ban.childlist,childList);
                map.put(Ban.childsize,childList.size());
                mapArrayList.add(map);
                size += childList.size();
            }

			context.setObj(Ban.lists, mapArrayList, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(Ban.size, String.valueOf(size), CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}

	private void fillChildList(List<Map<String, Object>> childList,Comment parentComment,Map<String,List<Comment>> parentCommentMap,UserData userData) throws HandlerException {
        if(parentCommentMap.get(parentComment.getCommentid()) != null){
            //该评论有子评论
            for(Comment childComment : parentCommentMap.get(parentComment.getCommentid())){
                Map<String,Object> childMap = BeanUtil.objToMap(childComment);
                childMap.put(Ban.commentid, childComment.getCommentid());
                childMap.put(Ban.postid, childComment.getPostid());
                childMap.put(Ban.parentcommentid, childComment.getParentcommentid());
                childMap.put(Ban.mobileno, childComment.getCreatormobileno());
                childMap.put(Ban.nickname, userData.qryByMobile(childComment.getCreatormobileno()).getNickname());
                childMap.put(Ban.context, new String(childComment.getContext()));
                childMap.put(Ban.createtime,TimeUtil.dateFormate(dateFormate, childComment.getCreatetime()));
                fillChildList(childList,childComment,parentCommentMap,userData);
                childList.add(childMap);
            }
        }
    }
}
