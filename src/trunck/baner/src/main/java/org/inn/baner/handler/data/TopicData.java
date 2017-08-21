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


	public Topic qryById(String id) throws HandlerException {
		Topic topic = null;
		try {
			//初始化sqlSession
			getSqlSession();
			TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
			topic = topicMapper.selectByPrimaryKey(id);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return topic;
	}

    public List<Topic> qryChildById(String parentId) throws HandlerException {
        List<Topic> list = null;
        try {
            //初始化sqlSession
            getSqlSession();
            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
            TopicExample example = new TopicExample();
            example.createCriteria().andParenttopicidEqualTo(parentId);
            list = topicMapper.selectByExampleWithBLOBs(example);

            if (logger.isDebugEnabled()) {
                logger.debug("qry table " + tableName + " success");
            }
        } catch (HandlerException e) {
            logger.error("exec sql error", e);
            throw new HandlerException(e);
        }finally {
            relaceResource();
        }
        return list;
    }
}
