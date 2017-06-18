package com.ztkx.transplat.container.initload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;



public abstract class AbstractTMJDBC implements TransactionMJDBC {

	public Connection connection = null;
	public PreparedStatement psmt = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	private boolean isRelaseConn = true;	//是否释放链接  默认释放
	
	/**
	 * 获取数据库链接
	 * @return
	 * 2016年3月15日 下午5:33:53
	 * @author zhangxiaoyun
	 */
	@Override
	public Connection getConnection() throws HandlerException{
		if(connection==null){
			connection = DataSourceUtil.getConnection();
		}
		return connection;
	}
	
	/**
	 * 将外面的connection设置进来，如果返回为ture说明设置成功，如果返回false说明设置失败
	 * @param connection
	 * @return
	 * 2016年3月15日 下午5:40:16
	 * @author zhangxiaoyun
	 */
	@Override
	public void setConnection(Connection para) throws HandlerException{
		if(connection==null && para!=null){
			connection = para;
		}else{
			throw new HandlerException("connection ["+connection+"] para ["+para+"]");
		}
	}
	
	@Override
	public void setAutoCommint(boolean autoCommit) throws HandlerException {
		try{
			if(connection != null){
				connection.setAutoCommit(autoCommit);
			}else{
				throw new HandlerException("connection is null");
			}
		}catch(Exception e){
			throw new HandlerException("connection is null");
		}
	}
	
	
	public void setRelaseConn(boolean isRelaseConn) {
		this.isRelaseConn = isRelaseConn;
	}
	
	@Override
	public void relaceResource() throws HandlerException{
		DataSourceUtil.closeResultSet(rs);
		DataSourceUtil.closePreparedStatement(psmt);
		DataSourceUtil.closeStatement(stmt);
		if(isRelaseConn && connection != null){
			setAutoCommint(true);
			DataSourceUtil.closeConnect(connection);
			connection = null;
		}
	}
}
