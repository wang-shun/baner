package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.User;
import org.inn.baner.bean.Userloc;
import org.inn.baner.persist.mapper.UserMapper;
import org.inn.baner.persist.mapper.UserlocMapper;

public class UserLocData extends AbstractTMMybatis {

	Logger logger = Logger.getLogger(UserLocData.class);
	static String tableName = "userloc";

	/**
	 * 插入用户位置信息
	 * @param userloc
	 * @throws HandlerException
     */
	public int insertRecord(Userloc userloc) throws HandlerException {
		int res = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			UserlocMapper userlocMapper = sqlSession.getMapper(UserlocMapper.class);
			res = userlocMapper.insert(userloc);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + res + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
			relaceResource();
		}
		return res;
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
