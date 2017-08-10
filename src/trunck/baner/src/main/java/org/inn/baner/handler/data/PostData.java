package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Post;
import org.inn.baner.bean.PostExample;
import org.inn.baner.constant.Ban;
import org.inn.baner.persist.mapper.PostMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(PostData.class);
	static String tableName = "post";

	/**
	 * 插入帖子信息
	 * @param post
	 * @throws HandlerException
	 */
	public int insertRecord(Post post) throws HandlerException {
		int count = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
			count = postMapper.insert(post);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + count + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return count;
	}

	public List<Post> qryByTopicId(String topicid) throws HandlerException {
		List<Post> postList = null;
		try {
			//初始化sqlSession
			getSqlSession();

			Map<String, Object> para = new HashMap<>();
			para.put(Ban.topicid, topicid);

			postList = sqlSession.selectList("org.inn.baner.persist.mapper.PostMapperDIY.qryByTopicId",topicid);

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

	public int zan(int topicid,int postid) throws HandlerException {
		List<Post> postList = null;
		int res = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			Map<String, Object> para = new HashMap<>();
			para.put(Ban.topicid, topicid);
			para.put(Ban.postid, postid);

			res = sqlSession.update("org.inn.baner.persist.mapper.PostMapperDIY.zan",para);
			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success update row  size ["+res+"]");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return res;
	}

	public int cancleZan(int topicid,int postid) throws HandlerException {
		List<Post> postList = null;
		int res = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			Map<String, Object> para = new HashMap<>();
			para.put(Ban.topicid, topicid);
			para.put(Ban.postid, postid);

			res = sqlSession.update("org.inn.baner.persist.mapper.PostMapperDIY.cancleZan",para);

			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success update row  size ["+res+"]");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return res;
	}

	/**
	 * delete post by mobileNo and postId 20170810
	 * @param mobileNo
	 * @param postId
	 * @return
	 */
	public int deletePost(String mobileNo,String postId) throws HandlerException {
		PostMapper postMapper = getSqlSession(true).getMapper(PostMapper.class);//自动提交
		PostExample postExample = new PostExample();
		postExample.createCriteria().andCreatormobilenoEqualTo(mobileNo).andPostidEqualTo(postId);
		return  postMapper.deleteByExample(postExample);
	}

	/**
	 * select post by mobile no
	 * @param mobileNo
	 * @return
	 * @throws HandlerException
	 */
	public List<Post> qryByTopicMobileNo(String mobileNo) throws HandlerException {
		PostMapper postMapper = getSqlSession(false).getMapper(PostMapper.class);//自动提交
		PostExample postExample = new PostExample();
		postExample.createCriteria().andCreatormobilenoEqualTo(mobileNo);
		return postMapper.selectByExample(postExample);
	}
}
