package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Post;
import org.inn.baner.bean.PostExample;
import org.inn.baner.bean.Topic;
import org.inn.baner.bean.TopicExample;
import org.inn.baner.persist.mapper.PostMapper;
import org.inn.baner.persist.mapper.TopicMapper;

import java.util.List;

public class PostData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(PostData.class);
	static String tableName = "post";

	public List<Post> qryByTopicId(int topicid) throws HandlerException {
		List<Post> postList = null;
		try {
			//≥ı ºªØsqlSession
			getSqlSession();

			PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
			PostExample postExample = new PostExample();
			postExample.createCriteria().andTopicidEqualTo(topicid);

			postList = postMapper.selectByExample(postExample);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success list size ["+postList.size()+"]");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return postList;
	}
}
