package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseUtil.BeanUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.time.TimeUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Post;
import org.inn.baner.bean.Topic;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.PostData;
import org.inn.baner.handler.data.TopicData;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 根据主题id获取主题下帖子列表 *
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban004_ObtainPostByTopic implements BusinessService {

	private Logger logger = Logger.getLogger(Ban004_ObtainPostByTopic.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		int topicId = Integer.parseInt(context.getFieldStr(Ban.topicid));
		String dateFormate = "yyyyMMdd HH:mm:ss";
		logger.info("topicid ["+topicId+"]");

		List<Post> postList = null;
		PostData postData = null;
		try {

			postData = new PostData();
			postList = postData.qryByTopicId(topicId);
			List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
			for (Post post : postList) {
				Map<String,Object> map = BeanUtil.objToMap(post);
				map.put(Ban.context, Base64.encodeBase64String(post.getContext()));
				Date createTime = post.getCreatetime();
				map.put(Ban.createtime,TimeUtil.dateFormate(dateFormate, createTime));
				map.put(Ban.updatetime, TimeUtil.dateFormate(dateFormate, post.getUpdatetime()));
				mapArrayList.add(map);
			}
			context.setObj(Ban.lists, mapArrayList, CommonContext.SCOPE_GLOBAL);

		} catch (Exception e) {
			ContextUtil.setErrorCode(BErrorCode.FAIL.code, context);
			logger.error("buss service exec exception ",e);
		}
		return context;
	}
}
