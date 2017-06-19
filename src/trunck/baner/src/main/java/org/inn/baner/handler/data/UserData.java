package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.User;
import org.inn.baner.persist.mapper.UserMapper;

public class UserData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(UserData.class);
	static String tableName = "user";

	/**
	 * 插入用户信息
	 * @param user
	 * @throws HandlerException
     */
	public void insertRecord(User user) throws HandlerException {
		try {
			//初始化sqlSession
			getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			int count = userMapper.insert(user);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + count + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
	}

	public User qryByMobile(String mobileno) throws HandlerException {
		User user = null;
		try {
			//初始化sqlSession
			getSqlSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.selectByPrimaryKey(mobileno);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return user;
	}
}
