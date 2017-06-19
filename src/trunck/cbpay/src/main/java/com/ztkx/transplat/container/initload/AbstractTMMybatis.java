package com.ztkx.transplat.container.initload;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.transplat.platformutil.db.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public abstract class AbstractTMMybatis implements TransactionMMybatis {

	public SqlSession sqlSession = null;
	public PreparedStatement psmt = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	private boolean isRelaseConn = true;	//是否释放链接  默认释放

	@Override
	public SqlSession getSqlSession() throws HandlerException{
		if(sqlSession==null){
			sqlSession = MybatisUtil.getSqlSession();
		}
		return sqlSession;
	}

	@Override
	public SqlSession getSqlSession(Boolean autoCommit) throws HandlerException{
		if(sqlSession==null){
			sqlSession = MybatisUtil.getSqlSession(autoCommit);
		}
		return sqlSession;
	}
	/**
	 * 将外面的connection设置进来，如果返回为ture说明设置成功，如果返回false说明设置失败
	 * @param connection
	 * @return
	 * 2016年3月15日 下午5:40:16
	 * @author zhangxiaoyun
	 */
	@Override
	public void setSqlSession(SqlSession sqlSession) throws HandlerException{

		if(this.sqlSession==null && sqlSession!=null){
			this.sqlSession = sqlSession;
		}else{
			throw new HandlerException("sqlSession ["+this.sqlSession+"] para ["+sqlSession+"]");
		}
	}


	public void isRelaseSession(boolean isRelase) throws HandlerException{

		this.isRelaseConn = isRelaseConn;
	}
	
	@Override
	public void relaceResource() throws HandlerException{

		if(isRelaseConn && sqlSession != null){
			MybatisUtil.closeSqlSession(sqlSession);
		}
	}
}
