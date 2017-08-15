package org.inn.baner.serviceimp;

import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseUtil.BeanUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Post;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.CommentData;
import org.inn.baner.handler.data.PostData;
import org.inn.baner.handler.data.TopicData;
import org.inn.baner.handler.data.UserData;
import org.inn.baner.util.ListCompareUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nightblue on 2017/8/10.
 */
public class Ban014_selectPost implements BusinessService {

    private Logger logger = Logger.getLogger(Ban014_selectPost.class);

    String dateFormate = "yyyyMMdd HH:mm:ss";

    @Override
    public CommonContext service(CommonContext context) throws ServiceException {

        String mobileno = context.getFieldStr(Ban.mobileno,CommonContext.SCOPE_GLOBAL);

        try {
        PostData postData = new PostData();
        TopicData topicData = new TopicData();
        UserData userData = new UserData();
        CommentData commentData = new CommentData();

        List<Post> postList = postData.qryByTopicMobileNo(mobileno);

        new ListCompareUtil().mySortDesc(postList,Post.class,new String [] {"createtime"});//0808修改根据创建时间排序

        List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
        for (Post post : postList) {
            Map<String,Object> map = BeanUtil.objToMap(post);
            map.put(Ban.postid, post.getPostid());
            map.put(Ban.topicname, topicData.qryById(post.getTopicid()).getTopicdesc());
            map.put(Ban.mobileno, post.getCreatormobileno());
            map.put(Ban.nickname, userData.qryByMobile(post.getCreatormobileno()).getNickname());
            map.put(Ban.postname, post.getPostname());
            map.put(Ban.postdesc, post.getPostdesc());
            map.put(Ban.context, new String(post.getContext()));
            map.put(Ban.zantimes, post.getZantimes());
            map.put(Ban.topicid, post.getTopicid());
            map.put(Ban.commentCount, commentData.countByPostId(post.getPostid()));
            Date createTime = post.getCreatetime();
            map.put(Ban.createtime, TimeUtil.dateFormate(dateFormate, createTime));
            map.put(Ban.updatetime, TimeUtil.dateFormate(dateFormate, post.getUpdatetime()));
            mapArrayList.add(map);
        }
        context.setObj(Ban.lists, mapArrayList, CommonContext.SCOPE_GLOBAL);
        context.setFieldStr(Ban.size, String.valueOf(mapArrayList.size()), CommonContext.SCOPE_GLOBAL);
        }catch (Exception e){
            ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
            logger.error("buss service exec exception ",e);
        }
        return context;
    }
}
