package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.container.javabean.TransMgsLogStamp;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class TransMgsLogStampData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(TransMgsLogStampData.class);
	static Map<String, TransMgsLogStamp> map = new HashMap<String, TransMgsLogStamp>();
	static List<TransMgsLogStamp> list = new ArrayList<TransMgsLogStamp>();
	static String tableName = "TRANS_MSG_LOG_STAMP";


	public void insert(TransMgsLogStamp transmgslogstamp) throws Exception {
		// 获取链接
		getConnection();
		
		String insertsql = "insert into " + tableName + " values (?,?,?,?,?,?)";
		if (logger.isDebugEnabled()) {
			logger.debug("start insert " + tableName + " table data...");
			logger.debug(tableName + " insert insertsql[" + insertsql + "]");
			logger.debug("Flowno = [" + transmgslogstamp.getFlowno() + "]");
			logger.debug("trandate = [" + transmgslogstamp.getTrandate() + "]");

		}
		psmt = DataSourceUtil.getPreparedStatement(connection, insertsql);
		try {
			psmt.setString(1, transmgslogstamp.getFlowno());
			psmt.setString(2, transmgslogstamp.getTrandate());
			// timestamp
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			psmt.setTimestamp(3, ts);
			psmt.setTimestamp(4, ts);
			psmt.setTimestamp(5, ts);
			psmt.setTimestamp(6, ts);
			psmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("insert TRANS_MGS_LOG_STAMP table data is success...");
			}
		} catch (SQLException e) {
			logger.error("insert TRANS_MGS_LOG_STAMP table data exception", e);
			throw e;
		} finally {
			relaceResource();
		}

	}

	/**
	 * 当交易首次被登记时，把基础信息插入trans_mgs_log_stamp(交易日志)表中。
	 * 
	 * @param flowNo
	 * @param trandate
	 * @throws Exception
	 */
	public void insert(String flowNo, String trandate) throws Exception {
		// 获取链接
		getConnection();
	
		String insertsql = "insert into " + tableName + " (flowno,trandate,Timestamp1) values ('" + flowNo + "','"+trandate+"',CURRENT_TIMESTAMP)";
		if (logger.isDebugEnabled()) {
			logger.debug("start insert " + tableName + " table data...");
			logger.debug("insert insertsql[" + insertsql + "]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			stmt.executeUpdate(insertsql);
			if (logger.isDebugEnabled()) {
				logger.debug(" insert " + tableName + " table  sucess !");
			}
		} catch (SQLException e) {
			logger.error("insert " + tableName + " table data error !", e);
			throw e;
		} finally {
			relaceResource();
		}

	}

	/**
	 * 更新交易处理状态
	 * 
	 * @param flowNo
	 * @param trandate
	 * @param msgOrder
	 * @throws Exception
	 */
	public void update(String flowNo, String trandate, String msgOrder)
			throws Exception {
		// 获取链接
		if (logger.isDebugEnabled()) {
			logger.debug("start update " + tableName + " table data...");
		}
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update  " + tableName + " set ");
		if (msgOrder.equalsIgnoreCase(ContainerConstantField.MSG_ORDER_SECEND)) {
			updateSql.append(" Timestamp2 ");
		} else if (msgOrder
				.equalsIgnoreCase(ContainerConstantField.MSG_ORDER_THREE)) {
			updateSql.append(" Timestamp3 ");
		} else if (msgOrder
				.equalsIgnoreCase(ContainerConstantField.MSG_ORDER_FOUR)) {
			updateSql.append(" Timestamp4 ");
		} else {
			String errorMsg = " msg order error ! msg order is " + msgOrder;
			logger.info(errorMsg);
			throw new Exception(errorMsg);
		}
		updateSql.append(" = CURRENT_TIMESTAMP where flowno = '" + flowNo + "' and trandate = '"+trandate+"'");
		if (logger.isDebugEnabled()) {
			logger.info("update updateSql[" + updateSql + "]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			stmt.executeUpdate(updateSql.toString());
		} catch (SQLException e) {
			logger.error("update " + tableName + " table data error !", e);
			throw e;
		} finally {
			relaceResource();
		}

	}
}
