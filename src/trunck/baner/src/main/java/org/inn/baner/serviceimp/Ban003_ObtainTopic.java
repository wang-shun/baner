package org.inn.baner.serviceimp;


import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BusinessService;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseUtil.BeanUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Topic;
import org.inn.baner.constant.Ban;
import org.inn.baner.constant.enums.BErrorCode;
import org.inn.baner.handler.data.TopicData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取所有主题先行
 * @author zhangxiaoyun
 * 2016-3-15 16:24:11
 * <p>Company:ztkx</p>
 */
public class Ban003_ObtainTopic implements BusinessService {

	private Logger logger = Logger.getLogger(Ban003_ObtainTopic.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {

		List<Topic> topicList = null;
		TopicData topicData = null;
		try {

			topicData = new TopicData();
			topicData.setContext(context);
			topicList = topicData.qryAll();
			List<Map<String, Object>> mapArrayList = new ArrayList<Map<String, Object>>();
			for (Topic topic : topicList) {
				Map<String,Object> map = BeanUtil.objToMap(topic);
                map.put(Ban.topicid, topic.getTopicid());
                map.put(Ban.topicdesc, topic.getTopicdesc());
                map.put(Ban.mobileno, topic.getCreatormobileno());
                map.put(Ban.parenttopicid, topic.getParenttopicid());
                map.put(Ban.topiclogo, topic.getTopiclogo()==null?"":new String(topic.getTopiclogo()));
				mapArrayList.add(map);
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
