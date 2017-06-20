package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Topic;
import org.inn.baner.bean.TopicExample;
import org.inn.baner.persist.mapper.TopicMapper;

import java.util.List;

public class TopicData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(TopicData.class);
	static String tableName = "topic";

	public List<Topic> qryAll() throws HandlerException {
		List<Topic> topicList = null;
		try {
			//初始化sqlSession
			getSqlSession();

			TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
			topicList = topicMapper.selectByExample(new TopicExample());

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return topicList;
	}
}
