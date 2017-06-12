package com.ztkx.cbpay.container.initload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public abstract class AbstractLoadInit implements LoadInitDataInterface {

	public Connection connection = null;
	public PreparedStatement psmt = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	private boolean isRelaseConn = true;	//是否释放链接  默认释放
	private Logger logger = Logger.getLogger(AbstractLoadInit.class);
	protected volatile  Map data = new HashMap();
	protected  Map tmpData = new HashMap();
	
	/**
	 * 获取数据库链接
	 * @return
	 * 2016年3月15日 下午5:33:53
	 * @author zhangxiaoyun
	 */
	@Override
	public Connection getConnection(){
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
	public void setRelaseConn(boolean isRelaseConn) {
		this.isRelaseConn = isRelaseConn;
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

	@Override
	public void load() throws HandlerException{
		
		try{
			getConnection();
			extracted(data);
		}finally{
			relaceResource();
		}
	}
	@Override
	public boolean preload() throws HandlerException{
		boolean isSucc = false;
		try{
			logger.info("start preload data size is ["+data.size()+"]");
			logger.info("start preload tmpData size is ["+tmpData.size()+"]");
			getConnection();
			isSucc =extracted(tmpData);
			logger.info("preload finish tmpData size is ["+tmpData.size()+"]");
		}finally{
			relaceResource();
		}
		return isSucc;
	}
	
	@Override
	public void reload() throws HandlerException {
		if(tmpData == null || tmpData.size()==0){
			throw new HandlerException("tmpData is null");
		}
		logger.info("start reload data size is ["+data.size()+"]");
		logger.info("start reload tmpData size is ["+tmpData.size()+"]");
		data.clear();
		data.putAll(tmpData);
		tmpData.clear();
		logger.info("reload finish data size is ["+data.size()+"]");
		logger.info("reload finish tmpData size is ["+tmpData.size()+"]");
	}
	
	@Override
	public void failHand() throws HandlerException {
		tmpData.clear();
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
	
	/**
	 * 执行查询语句将数据库中的数据加载到内存
	 * 2016年6月30日 下午5:15:35
	 * @author zhangxiaoyun
	 * @param target
	 * @return
	 * @throws HandlerException
	 * @return boolean
	 */
	public abstract boolean extracted(Map target) throws HandlerException;
}
