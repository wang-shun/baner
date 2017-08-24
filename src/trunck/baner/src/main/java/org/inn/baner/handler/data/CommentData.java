package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Comment;
import org.inn.baner.bean.CommentExample;
import org.inn.baner.persist.mapper.CommentMapper;

import java.util.List;

public class CommentData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(CommentData.class);
	static String tableName = "comment";

	/**
	 * 插入帖子信息
	 * @param comment
	 * @throws HandlerException
	 */
	public int insertRecord(Comment comment) throws HandlerException {
		int count = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			count = commentMapper.insert(comment);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + count + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
		}
		return count;
	}

	public List<Comment> qryByPostId(String postId) throws HandlerException {
		List<Comment> commentList = null;
		try {
			//初始化sqlSession
			getSqlSession();
            CommentExample example = new CommentExample();
            example.createCriteria().andPostidEqualTo(postId);
            example.setOrderByClause(" createtime desc ");

            CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
            commentList = commentMapper.selectByExampleWithBLOBs(example);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success list size ["+commentList.size()+"]");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
		}
		return commentList;
	}

    public long countByPostId(String postId) throws HandlerException {
        long num=0L;
        try {
            //初始化sqlSession
            getSqlSession();
            CommentExample example = new CommentExample();
            example.createCriteria().andPostidEqualTo(postId);

            CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
            num = commentMapper.countByExample(example);

            if (logger.isDebugEnabled()) {
                logger.debug("qry table " + tableName + " success count size ["+num+"]");
            }
        } catch (HandlerException e) {
            logger.error("exec sql error", e);
            throw new HandlerException(e);
        }finally {
        }
        return num;
    }
}
