package com.ztkx.transplat.container.initdata;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMJDBC;
import com.ztkx.transplat.container.javabean.TransLog;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

/**
 * 用于记录交易日志信息
 * @author tianguanggzhao
 *
 */
public class TransLogData extends AbstractTMJDBC {
	Logger logger = Logger.getLogger(TransLogData.class);
	private static final String tableName = "TRANS_LOG";
	
	/**
	 * 将记录差入到数据库中
	 * @throws HandlerException 
	 */
	public void insertLog(TransLog transLog) throws HandlerException {
		try {
			//初始化链接
			getConnection();
			String insertSql = "insert into "+ tableName+ "(FLOWNO, TRANDATE, TRANTIME, TRANCODE, TRANFROM, SERVERCODE, SERVERID, SERVICERESPONSECODE, PLATRESPONSECODE,SERVICERESPONSEDESC) values(?,?,?,?,?,?,?,?,?,?)";
			
			if(logger.isDebugEnabled()){
				logger.debug("start insert table "+tableName + " sql = ["+insertSql+" ] , data = "+ (transLog ==null ? "null" : transLog.toString()));
			}
			psmt = DataSourceUtil.getPreparedStatement(connection, insertSql);
			psmt.setString(1, transLog.getFlowNo());
			psmt.setString(2, transLog.getTranDate());
			psmt.setString(3, transLog.getTranTime());
			psmt.setString(4, transLog.getTranCode());
			psmt.setString(5, transLog.getTranFrom());
			psmt.setString(6, transLog.getServerCode());
			psmt.setString(7, transLog.getServerId());
			psmt.setString(8, transLog.getServiceResponseCode());
			psmt.setString(9, transLog.getPlatResponseCode());
			psmt.setString(10, transLog.getServiceResponseDesc());
			psmt.executeUpdate();
		} catch (Exception e) {
		   logger.error("insert record error !",e);
		   throw new HandlerException(e);
		}finally{
			relaceResource();
		}
	}
}