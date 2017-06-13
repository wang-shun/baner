package org.inn.baner.handler.data;

import com.ztkx.cbpay.business.bean.BCheckStatus;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作和宝易互通对账的日志表
 *
 * @author tianguangzhao
 *
 */
public class BCheckStatusData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BCheckStatusData.class);
	// 此类操作的表名
	private static final String tableName = " B_CHECK_STATUS ";
	private static String sqlStatement = "insert into " + tableName + " (TRANDATE,ACCOUNTDATE,CHECKTYPE,"
			+ "STATUS,TRANTIME,FILENAME,TMSMP,JNLNO,CHECKNL,ISHANDLED) values (?,?,?,?,?,?,?,?,?,?)";


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
	public void insertRecord(BCheckStatus bucl) throws HandlerException {
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


	/**
	 *
	 * @param accountdate	业务日期
	 * @param checktype		对账类型
	 * @param checknl		对账渠道
	 * @return
	 * @throws HandlerException
	 */
	public BCheckStatus getRecord(String accountdate,String  checktype,String checknl) throws HandlerException {
		try {
			//新增更新时间戳，update by tianguangzhao 2016/4/11
			String sqlstatement = "select * from b_check_status t where t.accountdate=? and t.checktype=? and t.checknl=?";
			psmt = DataSourceUtil.getPreparedStatement(connection, sqlstatement);
			psmt.setString(1, accountdate);
			psmt.setString(2, checktype);
			psmt.setString(3, checknl);
			psmt.executeQuery();
			rs = psmt.executeQuery();
			List<BCheckStatus> list = rsConvertToBean(rs);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success !");
			}
			if(list.size()>0)
				return list.get(0);
			else{
				return null;
			}
		} catch (Exception e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
	}

	private void prePreparedStatement(BCheckStatus bucl) throws SQLException {
		psmt.setString(1, bucl.getTrandate());
		psmt.setString(2, bucl.getAccountdate());
		psmt.setString(3, bucl.getChecktype());
		psmt.setString(4, bucl.getStatus());
		psmt.setString(5, bucl.getTrantime());
		psmt.setString(6, bucl.getFilename());
		Timestamp stamp = new Timestamp(TimeUtil.getCurrentTime());
		psmt.setTimestamp(7, stamp);
		psmt.setString(8, bucl.getJnlno());
		psmt.setString(9, bucl.getChecknl());
		psmt.setString(10, bucl.getIsHandled());
	}

	private List<BCheckStatus> rsConvertToBean(ResultSet rs) throws SQLException {
		List<BCheckStatus> list = new ArrayList<BCheckStatus>();
		while(rs.next()){
			BCheckStatus bean = new BCheckStatus();
			bean.setTrandate(rs.getString(1));
			bean.setAccountdate(rs.getString(2));
			bean.setChecktype(rs.getString(3));
			bean.setStatus(rs.getString(4));
			bean.setTrantime(rs.getString(5));
			bean.setFilename(rs.getString(6));
			bean.setTmsmp(rs.getTimestamp(7));
			bean.setJnlno(rs.getString(8));
			bean.setChecknl(rs.getString(9));
			bean.setIsHandled(rs.getString(10));
			list.add(bean);
		}
		return list;
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
		} catch (HandlerException e) {
			// update by tiangunagzhao 2016/4/27 事务交由上层处理
			// try {
			// connection.rollback();
			// } catch (Exception e1) {
			// logger.error("connection rollback error !", e1);
			// throw new HandlerException(e1);
			// }

			logger.error("exec sql error", e);
			throw e;
		}
		return count;

	}

	/**
	 * 根据对账的业务日期和对账渠道更新对账状态
	 * @param accountdate
	 * @param checknl	对账状态
	 * @param status
	 * @param checktype 对账类型
	 * @return
	 * @throws HandlerException
	 */
	public int updateRecord(String accountdate, String checknl,String status,String checktype) throws HandlerException {
		int count = 0;
		try {

			String sqlStatement = "update  " + tableName + " set STATUS =? , TMSMP = ? where ACCOUNTDATE = ? and CHECKNL=? and checktype=?";
			psmt = DataSourceUtil.getPreparedStatement(connection,sqlStatement);
			psmt.setString(1, status);
			psmt.setTimestamp(2, TimeUtil.getTimeStamp());
			psmt.setString(3, accountdate);
			psmt.setString(4, checknl);
			psmt.setString(5, checktype);
			count = psmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success !");
			}
		} catch (Exception e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
		return count;
	}

	/**
	 * 根据主键查询表中数据,用于console发起的手工对账时，根据主键查询
	 *
	 * @param jnlno
	 *            主键，对账流水号
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getPaymentFlowByJnlno(String jnlno) throws HandlerException {
		String sqlStatement = "select * from  " + tableName+ " where JNLNO = ? ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("JNLNO", jnlno);
		return executeQuery(sqlStatement, map);
	}

	/**
	 * 手工对账完成后，更新以前的日志信息出力标识为“已处理”
	 * @param accountdate
	 * @return
	 * @throws HandlerException
	 */
	public int updateBeforeRecordIsHandled(String accountdate)throws HandlerException {
		int count = 0;
		try {

			String sqlStatement = "update  " + tableName + " set ISHANDLED = ? , TMSMP = ? where ACCOUNTDATE = ? and STATUS = ?";
			psmt = DataSourceUtil.getPreparedStatement(connection,sqlStatement);
			psmt.setString(1, BCheckStatus.HANDLED_TRUE);
			psmt.setTimestamp(2, TimeUtil.getTimeStamp());
			psmt.setString(3, accountdate);
			psmt.setString(4, BCheckStatus.STATUS_ERROR);
			count = psmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug("update table " + tableName + " success !");
			}
		} catch (Exception e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
		return count ;
	}
}
