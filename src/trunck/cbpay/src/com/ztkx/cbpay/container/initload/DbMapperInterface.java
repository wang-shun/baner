package com.ztkx.cbpay.container.initload;

import java.sql.Connection;

import com.ztkx.cbpay.container.HandlerException;



/**
 * @ClassName: DbMapperInterface
 * @Description:平台启动加载表数据
 * @author zhangxiaoyun
 * @date 2016年6月30日 下午2:09:24
 */
public interface DbMapperInterface {
	
	/**
	 * 获取初始化数据库链接
	 * 2016年6月30日 下午4:17:02
	 * @author zhangxiaoyun
	 * @return
	 * @return Connection
	 */
	public Connection getConnection() throws HandlerException;
	
	/**
	 * 给当前mapper设置链接
	 * 2016年6月30日 下午4:18:17
	 * @author zhangxiaoyun
	 * @param para
	 * @return void
	 */
	public void setConnection(Connection para) throws HandlerException;
	
	/**
	 * 设置是否自动提交
	 * 2016年7月1日 下午1:27:12
	 * @author zhangxiaoyun
	 * @param autoCommit
	 * @throws HandlerException
	 * @return void
	 */
	public void setAutoCommint(boolean autoCommit) throws HandlerException;
	
	/**
	 * 设置是否需要释放当前链接
	 * 2016年6月30日 下午4:18:40
	 * @author zhangxiaoyun
	 * @param isRelaseConn
	 * @return void
	 */
	public void setRelaseConn(boolean isRelaseConn) throws HandlerException;
	
	/**
	 * 释放资源
	 * 2016年6月30日 下午4:18:58
	 * @author zhangxiaoyun
	 * @return void
	 */
	public void relaceResource() throws HandlerException;
}
