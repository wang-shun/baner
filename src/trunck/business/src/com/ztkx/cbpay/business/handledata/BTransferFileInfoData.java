package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BTransferFileInfoData extends AbstractDbMapper{


	Logger logger = Logger.getLogger(BTransferFileInfoData.class);
	String sql = "insert into b_transfer_file_info (transfer_date, transfer_time, "
			+ "seqbatchno, transfer_type, filetype, count, filename, randompwd,hashvalue,"
			+ "signvalue, filestatus) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
	static String tableName = "b_transfer_file_info";
	
	/**
	 * @param TRANSFER_DATE
	 * @param FILENAME
	 * @param SEQBATCHNO
	 * @return
	 * 2016年3月28日 下午6:13:27
	 * @author zhangxiaoyun
	 * @throws HandlerException 
	 */
	public BTransferFileInfo getTransferFile(String TRANSFER_DATE,String FILENAME,String SEQBATCHNO) throws HandlerException{
		String sqlStatement = "select * from b_transfer_file_info t where t.transfer_date=? and t.seqbatchno=? and t.filename=?";
		
		logger.info("sql ["+sqlStatement+"]");
		if (logger.isDebugEnabled()) {
			logger.debug("start select table  "+tableName+" TRANSFER_DATE = ["+ TRANSFER_DATE + "] and FILENAME = [" + FILENAME + "] SEQBATCHNO = ["+SEQBATCHNO+"]");
		}
		BTransferFileInfo bTransferFileInfo = null;
		// 获取连接
		getConnection();
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sqlStatement);
			psmt.setString(1, TRANSFER_DATE);
			psmt.setString(2, SEQBATCHNO);
			psmt.setString(3, FILENAME);
			rs = psmt.executeQuery();
			
			if(rs.next()){
				bTransferFileInfo = new BTransferFileInfo();
				bTransferFileInfo.setCount(rs.getInt("COUNT"));
				bTransferFileInfo.setTransferDate(rs.getString("TRANSFER_DATE"));
				bTransferFileInfo.setTransferTime(rs.getString("TRANSFER_TIME"));
				bTransferFileInfo.setSeqbatchno(rs.getString("SEQBATCHNO"));
				bTransferFileInfo.setTransferType(rs.getString("TRANSFER_TYPE"));
				bTransferFileInfo.setFiletype(rs.getString("FILETYPE"));
				bTransferFileInfo.setFilename(rs.getString("FILENAME"));
				bTransferFileInfo.setRandompwd(rs.getString("RANDOMPWD"));
				bTransferFileInfo.setHashvalue(rs.getString("HASHVALUE"));
				bTransferFileInfo.setSignvalue(rs.getString("SIGNVALUE"));
				bTransferFileInfo.setFilestatus(rs.getString("FILESTATUS"));
			}
		} catch (SQLException e) {
			logger.error("select " + tableName + " data exception !", e);
			throw new HandlerException(e);
		} finally {
			relaceResource();

		}
		
		return bTransferFileInfo;
		
	}
	
	/**
	 * 批量的执行update操作
	 * @param sqlStatement		需要执行的sql语句
	 * @param map	sql中的问号参数值      如果map为空默认按传统的statement方式执行sql
	 * @return 修改的记录数
	 * 2016年3月14日 下午2:19:21
	 * @author zhangxiaoyun
	 * @throws HandlerException 
	 */
	public int executeUpdate(String sqlStatement,LinkedHashMap<String,String> map) throws HandlerException{
		
		logger.info("sql["+sqlStatement+"]");
		logger.info("params map is "+map);
		int res = 0;
		try{
			if(map != null && map.size()>0){
				//如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection, sqlStatement);
				//轮询将所有参数注入到psmt中
				DBUtil.preparePsmt(map,psmt);
				//执行sql语句
				res = psmt.executeUpdate();
			}else{
				//按照stmt的方式处理
				stmt = DataSourceUtil.getStatement(connection);
				res = stmt.executeUpdate(sqlStatement);	//执行sql语句
			}
		}catch(SQLException e){
			logger.error("execute sqlstatement exception",e);
			throw new HandlerException(e);
		}
		return res;
	}
	
	
	/**
	 * 插入BBuyExgCtrl对象
	 * @param bBuyExgCtrl
	 * @throws HandlerException
	 * 2016年3月15日 下午4:55:29
	 * @author zhangxiaoyun
	 */
	public void insertData(BTransferFileInfo bTransferFileInfo) throws HandlerException {
		logger.debug("insert into B_TRANSFER_FILE_INFO is begin ..");
		logger.info("sql[" + sql + "]");
		if(logger.isDebugEnabled()){
			logger.debug("B_TRANSFER_FILE_INFO data is ["+bTransferFileInfo+ "]");
		}
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			psmt.setString(1, bTransferFileInfo.getTransferDate());
			psmt.setString(2, bTransferFileInfo.getTransferTime());
			psmt.setString(3, bTransferFileInfo.getSeqbatchno());
			psmt.setString(4, bTransferFileInfo.getTransferType());
			psmt.setString(5, bTransferFileInfo.getFiletype());
			psmt.setInt(6, bTransferFileInfo.getCount());
			psmt.setString(7, bTransferFileInfo.getFilename());
			psmt.setString(8, bTransferFileInfo.getRandompwd());
			psmt.setString(9, bTransferFileInfo.getHashvalue());
			psmt.setString(10, bTransferFileInfo.getSignvalue());
			psmt.setString(11, bTransferFileInfo.getFilestatus());
			psmt.execute();
		} catch (Exception e) {
			logger.error("exec inser error", e);
			throw new HandlerException(e);
		}
	}

}
