package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BCheckErrorList;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 操作和宝易互通对账的日志表
 * 
 * @author tianguangzhao
 *
 */
public class BCheckErrorListData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BCheckErrorList.class);
	// 此类操作的表名
	private static final String tableName = " B_CHECK_ERROR_LIST ";
	private static String sqlStatement = "insert into b_check_error_list (ACCOUNTDATE, chkchnl, orderid,"
			+ " chnljnlno, chkerrtyp, chkerrdealtyp, chkerrdealsts, errcancelmark, dealdate, stamp,"
			+ " trandate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * 批量的执行update操作
	 * 
	 * @param sqlStatement
	 *            需要执行的sql语句
	 * @param map
	 *            sql中的问号参数值 如果map为空默认按传统的statement方式执行sql
	 * @return 修改的记录数 2016年3月14日 下午2:19:21
	 * @author zhangxiaoyun
	 * @throws HandlerException
	 */
	public int batchExecuteUpdate(String sqlStatement,
			List<LinkedHashMap<String, String>> list) throws HandlerException {

		logger.info("sql[" + sqlStatement + "]");
		logger.info("params list is " + list);
		int res = 0;
		try {
			if (list != null && list.size() > 0) {
				// 如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,
						sqlStatement);

				LinkedHashMap<String, String> map = null;
				if (list.size() == 1) {
					map = list.get(0);
					// 轮询将所有参数注入到psmt中
					DBUtil.preparePsmt(map, psmt);
					// 执行sql语句
					res = psmt.executeUpdate();
				} else {
					// 批量更新
					for (int i = 0; i < list.size(); i++) {
						map = list.get(i);
						// 轮询将所有参数注入到psmt中
						DBUtil.preparePsmt(map, psmt);
						psmt.addBatch();
						if ((i + 1) % BusinessConstantField.BATCHNUM == 0) {
							res = res + psmt.executeBatch().length;
						}
					}
					// 最后在补个刀
					res = res + psmt.executeBatch().length;
				}
			} else {
				// 按照stmt的方式处理
				stmt = DataSourceUtil.getStatement(connection);
				res = stmt.executeUpdate(sqlStatement); // 执行sql语句
			}
		} catch (SQLException e) {
			logger.error("execute sqlstatement exception", e);
			throw new HandlerException(e);
		}
		return res;
	}

	/**
	 * 执行参数sql语句返回查询结果
	 * 
	 * @param sqlStatement
	 *            需要执行的sql语句
	 * @param map
	 *            sql中的问号参数值 如果map为空默认按传统的statement方式执行sql
	 * @return 2016年3月14日 下午2:19:21
	 * @author zhangxiaoyun
	 * @throws HandlerException
	 */
	public List<Map<String, String>> executeQuery(String sqlStatement,
			LinkedHashMap<String, String> map) throws HandlerException {

		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("params is " + map);
		List<Map<String, String>> resList = null;
		try {
			if (map != null) {
				// 如果map不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,
						sqlStatement);
				DBUtil.preparePsmt(map, psmt);
				// 执行sql语句
				rs = psmt.executeQuery();
			} else {
				// 按照stmt的方式处理
				stmt = DataSourceUtil.getStatement(connection);
				rs = stmt.executeQuery(sqlStatement);
			}
			// 将最终的查询结果转化为list
			resList = DBUtil.resConvertList(rs);
			if (resList.size() <= 0) {
				resList = null;
			}
		} catch (SQLException e) {
			logger.error("execute sqlstatement exception", e);
			throw new HandlerException(e);
		} finally {
			// 释放资源
			relaceResource();
		}
		return resList;

	}

	/**
	 * 对账完成之后，将结果插入数据库中
	 * 
	 * @param BCheckStatus
	 *            ，实体类，包含有记录信息
	 * @throws HandlerException
	 */
	public void insertRecord(BCheckErrorList bucl) throws HandlerException { 
		try {
			//新增更新时间戳，update by tianguangzhao 2016/4/11 
			psmt = DataSourceUtil.getPreparedStatement(connection, sqlStatement);
			prePreparedStatement(bucl);
			psmt.execute();
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success !");
			}
		} catch (Exception e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
	}

	private void prePreparedStatement(BCheckErrorList bucl) throws SQLException {
		psmt.setString(1, bucl.getAccountdate());
		psmt.setString(2, bucl.getChkchnl());
		psmt.setString(3, bucl.getOrderid());
		psmt.setString(4, bucl.getChnljnlno());
		psmt.setString(5, bucl.getChkerrtyp());
		psmt.setString(6, bucl.getChkerrdealtyp());
		psmt.setString(7, bucl.getChkerrdealsts());
		psmt.setString(8, bucl.getErrcancelmark());
		psmt.setString(9, bucl.getDealdate());
		Timestamp stamp = new Timestamp(TimeUtil.getCurrentTime());
		psmt.setTimestamp(10, stamp);
		psmt.setString(11, bucl.getTrandate());
	}

	/**
	 * 手动发起第二次对账之后将结果更新数据库中
	 * 
	 * @param BCheckStatus
	 *            ，实体类，包含有记录信息
	 * @throws HandlerException
	 */
	public int updateRecord(String jnlno, String status) throws HandlerException {
		int count = 0;
		try {

			String sqlStatement = "update  " + tableName + " set STATUS =? , TMSMP = ? where JNLNO = ? ";
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("STATUS", status);
			// 更新处理时间
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			map.put("TMSMP", time);
			map.put("JNLNO", jnlno);
			list.add(map);
			count = batchExecuteUpdate(sqlStatement, list);

			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success !");
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				logger.error("connection rollback error !", e1);
				throw new HandlerException(e1);
			}

			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
		return count;

	}
}
