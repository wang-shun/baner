package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMMybatis;
import org.apache.log4j.Logger;
import org.inn.baner.bean.Userloc;
import org.inn.baner.bean.UserlocExample;
import org.inn.baner.persist.mapper.UserlocMapper;

import java.util.List;

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
		}
		return res;
	}

	public List<Userloc> qryByPlatDate(String platdate) throws HandlerException {
		List<Userloc> list = null;
		try {
			//初始化sqlSession
			getSqlSession();
			UserlocMapper userlocMapper = sqlSession.getMapper(UserlocMapper.class);
			UserlocExample example = new UserlocExample();
			example.createCriteria().andPlatdateEqualTo(platdate);
			list = userlocMapper.selectByExample(example);

			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
		}
		return list;
	}

	public Userloc qry(String platdate,String mobileno) throws HandlerException {
		Userloc userloc = null;
		try {
			//初始化sqlSession
			getSqlSession();
			UserlocMapper userlocMapper = sqlSession.getMapper(UserlocMapper.class);
			UserlocExample example = new UserlocExample();
			example.createCriteria()
					.andPlatdateEqualTo(platdate).andMobilenoEqualTo(mobileno);

			List<Userloc> list = userlocMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				userloc = list.get(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("qry table " + tableName + " success");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
		}
		return userloc;
	}

	public int update(Userloc userloc) throws HandlerException {
		int res = -1;
		try {
			//初始化sqlSession
			getSqlSession();
			UserlocMapper userlocMapper = sqlSession.getMapper(UserlocMapper.class);
			res = userlocMapper.updateByPrimaryKeySelective(userloc);

			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success  count =[" + res + "] ");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}finally {
		}
		return res;
	}

    /**
     * 获取该用户最近的位置信息
     * @param mobileno
     * @return
     */
    public Userloc getLastLoc(String mobileno) throws HandlerException {
        Userloc userloc = null;
        try {
            //初始化sqlSession
            getSqlSession();
            UserlocMapper userlocMapper = sqlSession.getMapper(UserlocMapper.class);
            UserlocExample example = new UserlocExample();
            example.createCriteria().andMobilenoEqualTo(mobileno);
            example.setOrderByClause(" platdate desc ");
            List<Userloc> list = userlocMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                userloc = list.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("qry table " + tableName + " success");
            }
        } catch (HandlerException e) {
            logger.error("exec sql error", e);
            throw new HandlerException(e);
        }finally {
        }
        return userloc;
    }
}
