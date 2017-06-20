package com.ztkx.transplat.platformutil.db.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;

/**
 * 数据源工具类
 * @author tianguangzhao
 *
 */
 
public class DataSourceUtil {

	private static DataSourceUtil dataSource = null;
	private static ComboPooledDataSource ds = null;
	// c3p0配置文件目录
	private String fileName = ConstantConfigField.DB_POOLCONFIGFILE;
	private String filePath = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+File.separator+"dbpool"+File.separator;
	static Logger logger = Logger.getLogger(DataSourceUtil.class);

	// 构造方法中初始化数据库连接池
	private DataSourceUtil() {
		if (ds == null) {

			try {
				logger.info("dbpool configuration file is " + (filePath+fileName));
				// 更改配置文件位置，默认情况下，配置文件只能方法类的本级目录下
				System.setProperty("com.mchange.v2.c3p0.cfg.xml", filePath+fileName);
				// 建立数据库连接池
				ds = new ComboPooledDataSource("myApp");
				// 获取数据源信息，数据库名称，URL，最大线程数等；
				logger.info("user name = " + ds.getUser());
				logger.info("password = " + ds.getPassword());
				logger.info("DriverClass name = " + ds.getDriverClass());
				logger.info("jdbcurl  = " + ds.getJdbcUrl());
				logger.info("InitialPoolSize = " + ds.getInitialPoolSize());
				logger.info("MaxIdleTime = " + ds.getMaxIdleTime());
				logger.info("MaxPoolSize = " + ds.getMaxPoolSize());
				logger.info("MinPoolSize = " + ds.getMinPoolSize());
				logger.info("maxIdleTimeExcessConnections = " + ds.getMaxIdleTimeExcessConnections());
				logger.info("acquireIncrement = " + ds.getAcquireIncrement());
				logger.info("idleConnectionTestPeriod = " + ds.getIdleConnectionTestPeriod());
				logger.info("acquireRetryAttempts = " + ds.getAcquireRetryAttempts());
				logger.info("acquireRetryDelay = " + ds.getAcquireRetryDelay());
				logger.info("testConnectionOnCheckout = " + ds.isTestConnectionOnCheckout());
				logger.info("preferredTestQuery = " + ds.getPreferredTestQuery());
			} catch (Exception e) {
				logger.error("create datasource error !", e);
			}
		}
	}

	// 获取对象采用单例模式
	/*public static synchronized DataSourceUtil getInstance() {
		if (dataSource == null) {
			dataSource = new DataSourceUtil();
		}
		return dataSource;
	}*/
	// 获取对象采用单例模式
	/**
	 * update by zhangxiaoyun
	 * @return
	 */
	public static DataSourceUtil getInstance() {
		if (dataSource == null) {
			synchronized(DataSourceUtil.class){
				if(dataSource == null){
					dataSource = new DataSourceUtil();
				}
			}
		}
		return dataSource;
	}

	// 获取数据库里连接
	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error("getConnection error !", e);
		} finally {
			if (logger.isDebugEnabled()) {
				// 获取数据源最大连接数，现有连接数和处于忙碌状态的连接数信息；
				printLog();
			}
		}
		return null;
	}

	static void printLog() {
		try {
			logger.debug("current connections number is "+ ds.getNumConnections());
			logger.debug(" Busy Connections num is "+ ds.getNumBusyConnections());
		} catch (SQLException e) {
			logger.error("get current Connection info error !", e);
		}
	}

	// 释放数据库连接
	public static void closeConnect(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				if (!con.getAutoCommit()) {
					con.commit();
				}
				// c3p0的connection类的close方法不是将连接关闭而是将连接回收到资源池中，close方法被c3p0重写了。
				con.close();
			}
		} catch (Throwable e) {
			logger.error("closeConnect error !", e);
		}finally{
			if (logger.isDebugEnabled()) {
				// 获取数据源最大连接数，现有连接数和处于忙碌状态的连接数信息；
				printLog();
			}
		}
	}

	// 获取Statement
	public static  Statement getStatement(Connection conn) {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt;
		} catch (SQLException e) {
			logger.error("getStatement error !", e);
		}
		return null;
	}
	
	// 关闭Statement
	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null )
				stmt.close();
		} catch (Throwable e) {
			logger.error("closeStatement error !", e);
		}
	}

	// 获取PreparedStatement
	public static PreparedStatement getPreparedStatement(Connection conn,
			String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			return ps;
		} catch (SQLException e) {
			logger.error("getPreparedStatement error !", e);
		}
		return null;
	}

	// 关闭PreparedStatement
	public static void closePreparedStatement(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (Throwable e) {
			logger.error("closePreparedStatement error !", e);
		}
	}

	// 执行sql查询数据库中的数据获取结果集ResultSet
	public static ResultSet doQuery(Statement stmt, String sql) {
		try {
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			logger.error("doQuery error !", e);
		}
		return null;
	}

	// 关闭查询结果集ResultSet
	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null )
				rs.close();
		} catch (Throwable e) {
			logger.error("closeResultSet error !", e);
		}
	}

	// 查询完数据库完毕后关闭connecton,Statement,ResultSet
	public static void releaseSelectConnect(Connection con, Statement stmt,
			ResultSet rs) {
		closeResultSet(rs);
		closeStatement(stmt);
		closeConnect(con);
	}

	// 更新完数据库完毕后关闭coonecton和Statement
	public static void releaseUpdateConnect(Connection con, Statement stmt) {
		closeStatement(stmt);
		closeConnect(con);
	}

	//
	// 应用程序关闭时直接销毁数据源
	protected void finalize() throws Throwable {
		DataSources.destroy(ds);
		super.finalize();
	}

}
