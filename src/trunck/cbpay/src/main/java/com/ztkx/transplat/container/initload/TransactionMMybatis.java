package com.ztkx.transplat.container.initload;

import com.ztkx.transplat.container.HandlerException;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;


/**
 * @ClassName: TransactionMJDBC
 * @Description:基于mybatis的事务管理工具
 * @author zhangxiaoyun
 * @date 2016年6月30日 下午2:09:24
 */
public interface TransactionMMybatis {

	/**
	 * 获取mybatis的sqlsession
	 * @return
	 * @throws HandlerException
     */
	public SqlSession getSqlSession() throws HandlerException;

	/**
	 * 获取sqlsession同时这是是否自动提交
	 * @param autoCommit
	 * @return
	 * @throws HandlerException
     */
	public SqlSession getSqlSession(Boolean autoCommit) throws HandlerException;
	/**
	 * 将外面的sqlSession传进来
	 * @param sqlSession
	 * @throws HandlerException
     */
	public void setSqlSession(SqlSession sqlSession) throws HandlerException;

	/**
	 * 设置是否需要释放当前session
	 * @param isRelase
	 * @throws HandlerException
     */
	public void isRelaseSession(boolean isRelase) throws HandlerException;
	
	/**
	 * 释放资源
	 * 2016年6月30日 下午4:18:58
	 * @author zhangxiaoyun
	 * @return void
	 */
	public void relaceResource() throws HandlerException;
}
