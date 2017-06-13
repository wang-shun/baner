package org.inn.baner.handler.data;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.platformutil.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DataHandlerUtil {
	/**
	 * 释放资源
	 * @param abstractLoadInit
	 * @throws HandlerException
	 */
	public static void releaseSource(AbstractDbMapper abstractLoadInit) throws HandlerException{
		if(abstractLoadInit!=null){
			abstractLoadInit.setRelaseConn(true);
			abstractLoadInit.relaceResource();
			abstractLoadInit = null;
		}
	}

	/**
	 * 提交链接失败
	 * @param conn
	 * @throws ServiceException
	 */
	public static void setAutoCommit(Connection conn,boolean autoCommit) throws ServiceException{
		if(conn!=null){
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				throw new ServiceException(e);
			}
		}else{
			throw new ServiceException("Connection is null");
		}
	}

	/**
	 * 提交事务
	 * @param conn
	 * @throws ServiceException
	 */
	public static void commitConn(Connection conn) throws ServiceException{
		if(conn!=null){
			try {
				DBUtil.commit(conn);
			} catch (SQLException e) {
				throw new ServiceException(e);
			}
		}else{
			throw new ServiceException("Connection is null");
		}
	}

	/**
	 * 回滚事务
	 * @param conn
	 * @throws ServiceException
	 */
	public static void rollbakConn(Connection conn) throws ServiceException{
		if(conn!=null){
			try {
				conn.rollback();
			} catch (SQLException e) {
				throw new ServiceException(e);
			}
		}else{
			throw new ServiceException("Connection is null");
		}
	}
}
