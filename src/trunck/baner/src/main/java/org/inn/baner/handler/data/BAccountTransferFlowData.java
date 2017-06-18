package org.inn.baner.handler.data;


import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractTMJDBC;
import com.ztkx.transplat.platformutil.db.DBUtil;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作账户划转信息表数据的类
 *
 * @author tianguangzhao
 *
 */
public class BAccountTransferFlowData extends AbstractTMJDBC {

	Logger logger = Logger.getLogger(BAccountTransferFlowData.class);
	static String tableName = "B_ACCOUNT_TRANSFER_FLOW";
	// 对账时用到的临时表
	static String tableNameTmp = "B_ACCOUNT_TRANSFER_FLOW_TMP";

	/**
	 * 调用此方法更新账户划转的结果,用于账户划转交易
	 *
	 * @param transferFlowNo，账户划转批次号
	 * @param satus
	 * @param payTxnDate
	 * @param paytxnTime
	 * update by tianguangzhao 2016/4/27 去掉支付渠道流水号 即宝易互通返回的流水号(由于宝易互通不返回)
	 * @return
	 * @throws HandlerException
	 */
	public int updateTransInfo(String transferBatchNo, String satus, String payTxnDate,String paytxnTime) throws HandlerException {
		int count = 0;
		String sql = "update " + tableName + " set ACTTRAFSTATUS= ? ,PAYTXNDATE =? , PAYTXNTIME = ? ,TMSMP = ? where  TRANSFERBATCHNO = ?";
		try {
			LinkedHashMap map = new LinkedHashMap();
			map.put("ACTTRAFSTATUS", satus);
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			map.put("PAYTXNDATE", payTxnDate);
			map.put("PAYTXNTIME", paytxnTime);
			map.put("TMSMP", time);
			map.put("TRANSFERBATCHNO", transferBatchNo);
			count = executeUpdate(sql, map);
			if (logger.isDebugEnabled()) {
				logger.debug(" update table success !");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw e;
		}
		return count;
	}

	/**
	 * 插入支付流水信息
	 *
	 * @param acountTransferFlowList
	 * @throws HandlerException
	 */
	public void insertRecord(List<BAccountTransferFlow> acountTransferFlowList)throws HandlerException {
		String sqlStatement = "insert into "+ tableName
				+ " (TRANSFERBATCHNO,TXNDATE,TXNTIME,CURRENCY,TXNAMT,PAYACCOUNTNO,TRANSFERFLG,ACTTRAFSTATUS,PAYTXNDATE,PAYTXNTIME,BUYBATNO,PAYBATNO,TMSMP,CHECKINGSTATUS,ORDERID,BAK,EXRATE,RECACCOUNTNO,TRANSTYPE,TRANSFERFLOWNO,PAYCUSTOMERNO,RECCUSTOMERNO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			// 循环获得数据
			for (BAccountTransferFlow acountTransferFlow : acountTransferFlowList) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("TRANSFERBATCHNO", acountTransferFlow.getTransferBatchNo());
				map.put("TXNDATE", acountTransferFlow.getTxnDate());
				map.put("TXNTIME", acountTransferFlow.getTxnTime());
				map.put("CURRENCY", acountTransferFlow.getCurrency());
				map.put("TXNAMT", acountTransferFlow.getTxnAmt());
				map.put("PAYACCOUNTNO", acountTransferFlow.getPayAccountNo());
				map.put("TRANSFERFLG", acountTransferFlow.getTransferFlg());
				map.put("ACTTRAFSTATUS", acountTransferFlow.getActTrafStatus());
				map.put("PAYTXNDATE", acountTransferFlow.getPayTxnDate());
				map.put("PAYTXNTIME", acountTransferFlow.getPaytxnTime());
				map.put("BUYBATNO", acountTransferFlow.getBuyBatNo());
				map.put("PAYBATNO", acountTransferFlow.getPayBatNo());
				// 获取时间戳
				String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
				map.put("TMSMP", time);
				map.put("CHECKINGSTATUS",acountTransferFlow.getCheckingStatus());
				map.put("ORDERID", acountTransferFlow.getOrderid());
				map.put("BAK", acountTransferFlow.getBak());
				map.put("EXRATE", acountTransferFlow.getExrate());
				map.put("RECACCOUNTNO", acountTransferFlow.getRecAccountNo());
				map.put("TRANSTYPE", acountTransferFlow.getTransType());
				map.put("TRANSFERFLOWNO", acountTransferFlow.getTransferFlowNo());
				//PAYCUSTOMERNO,RECCUSTOMERNO, update by tianguangzhao 2016/5/9 新增收付款客户号
				map.put("PAYCUSTOMERNO", acountTransferFlow.getPayCustomerNo());
				map.put("RECCUSTOMERNO", acountTransferFlow.getRecCustomerNo());
				list.add(map);
			}
			int count = batchExecuteUpdate(sqlStatement, list);
			if (logger.isDebugEnabled()) {
				logger.debug("insert table " + tableName + " success ! insert count =[" + count + "] !");
			}
		} catch (HandlerException e) {
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		}
	}

	/**
	 * 查询表中的数据
	 *
	 * @param sqlStatement
	 *            需要查询的sql语句
	 * @param map
	 *            参数
	 * @return 符合要求的list集合
	 * @throws HandlerException
	 */
	public List<Map<String, String>> executeQuery(String sqlStatement,LinkedHashMap<String, String> map) throws HandlerException {

		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("params is " + map);
		List<Map<String, String>> resList = null;
		try {
			if (map != null) {
				// 如果map不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,sqlStatement);
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
	 * 根据交易流水号获取账户划转信息
	 *
	 * @param transferFlowNo,划转批次号
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getAcountTransferFlowInfoByTransferBatchNo(String transferBatchNo) throws HandlerException {
		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select distinct ACTTRAFSTATUS  from " + tableName + " where TRANSFERBATCHNO = ? ";
		map.put("TRANSFERFLOWNO", transferBatchNo);
		return executeQuery(sqlStatement, map);
	}

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
	public int executeUpdate(String sqlStatement,LinkedHashMap<String, String> map) throws HandlerException {

		logger.info("sql[" + sqlStatement + "]");
		logger.info("params map is " + map);
		int res = 0;
		try {
			if (map != null && map.size() > 0) {
				// 如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,sqlStatement);
				// 轮询将所有参数注入到psmt中
				DBUtil.preparePsmt(map, psmt);
				// 执行sql语句
				res = psmt.executeUpdate();
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
	 * 宝易互通返回账户划转结果后，更新表中的信息
	 *
	 * @param transferBatchNo
	 *            划转流水号
	 * @param tranDate
	 *            划转日期
	 * @param tranTime
	 *            划转时间
	 * @param status
	 *            账户划转状态
	 * @throws HandlerException
	 */
	public int updateAcountTransferResult(String transferBatchNo, String tranDate,String tranTime, String status)
			throws HandlerException {
		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "update " + tableName + " set ACTTRAFSTATUS= ? , PAYTXNDATE = ? , PAYTXNTIME = ?  , TMSMP = ? where  TRANSFERBATCHNO = ?";
		map.put("ACTTRAFSTATUS", status);
		map.put("PAYTXNDATE", tranDate);
		map.put("PAYTXNTIME", tranTime);
		String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
		map.put("TMSMP", time);
		map.put("TRANSFERBATCHNO", transferBatchNo);
		return executeUpdate(sqlStatement, map);
	}

	/**
	 * 根据交易日期，获取当天处理的所有交易
	 *
	 * @param txndate
	 *            ,交易日期
	 * @return
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getTransFlowByTrandate(String txndate) throws HandlerException {
		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select * from " + tableName + " where TXNDATE = ? ";
		map.put("TXNDATE", txndate);
		return executeQuery(sqlStatement, map);

	}

	/**
	 * 根据账务日期将交易流水表中的交易信息置为开始对账,用于对账开始时
	 *
	 * @param accountDate
	 * @throws HandlerException
	 */
	public int updateStatusBytxnDate(String txndate, String status) throws HandlerException {
		String sqlStatement = "update  " + tableName + " set CHECKINGSTATUS = ? ，TMSMP = ? where TXNDATE = ?  ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("CHECKINGSTATUS", status);
		String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
		map.put("TMSMP", time);
		map.put("TXNDATE", txndate);
		return executeUpdate(sqlStatement, map);
	}

	/**
	 * 比较数据库中的数据，进行对账,以我方数据为准
	 *
	 * @param accountDate
	 * @return
	 * @throws HandlerException
	 */
	public int checkingAccountTransFlowByOurData (String txndate) throws HandlerException {
		int i = -1;
		if (logger.isDebugEnabled()) {
			logger.debug(" checking account trans flow start ! trandate = [" + txndate + "] !");
		}

		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select count(1) as num from "
				+ tableName
				+ " a  where a.txndate = ? and NOT EXISTS (select b.TRANSFERFLOWNO from "
				+ tableNameTmp
				+ " b where a.txndate = b.txndate , a.accountno = b.accountno  , a.TRANSFERFLOWNO = b.TRANSFERFLOWNO  , a.transferflg = b.transferflg  , a.currency = b.currency  , a.paytxnretcode = b.paytxnretcode  , a.accountname = b.accountname  )";
		map.put("txndate", txndate);
		try {
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			if (list == null || list.size() == 0) {
				i = 0;
			} else {
				Map<String, String> sesultMap = list.get(0);
				String num = sesultMap.get("num");
				i = Integer.parseInt(num);
			}
		} catch (HandlerException e) {
			logger.error("checking pay flow error !", e);
			throw e;
		}

		return i;
	}

	/**
	 * 比较数据库中的数据，进行对账，以收到的数据为准
	 *
	 * @param accountDate
	 * @return
	 * @throws HandlerException
	 */
	public int checkingAccountTransFlowByUMB(String trandate) throws HandlerException {
		int i = -1;
		if (logger.isDebugEnabled()) {
			logger.debug(" checking account trans flow start ! date = ["
					+ trandate + "]");
		}

		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select count(1) as num from "
				+ tableNameTmp
				+ " b  where  NOT EXISTS (select a.TRANSFERFLOWNO from "
				+ tableName
				+ " a where a.txndate = ? and a.txndate = b.txndate  , a.accountno = b.accountno  , a.TRANSFERFLOWNO = b.TRANSFERFLOWNO  , a.transferflg = b.transferflg  , a.currency = b.currency  , a.paytxnretcode = b.paytxnretcode  , a.accountname = b.accountname)";
		map.put("txndate", trandate);
		try {
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			Map<String, String> sesultMap = list.get(0);
			String num = sesultMap.get("num");
			i = Integer.parseInt(num);
		} catch (HandlerException e) {
			logger.error("checking pay flow error !", e);
			throw e;
		}

		return i;
	}

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
	public int batchExecuteUpdate(String sqlStatement,List<LinkedHashMap<String, String>> list) throws HandlerException {

		logger.info("sql[" + sqlStatement + "]");
		logger.info("params list is " + list);
		int res = 0;
		try {
			if (list != null && list.size() > 0) {
				// 如果list不是null按照psmt的方式处理
				psmt = DataSourceUtil.getPreparedStatement(connection,sqlStatement);
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
	 * 根据交易日期，获取付款方账号和客户号
	 *
	 * @param txndate
	 *            ,交易日期
	 * @return
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getPayerAccoutNoByTrandateAndStatus(String txndate,String status) throws HandlerException {
		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select distinct PAYACCOUNTNO , PAYCUSTOMERNO from " + tableName + " where TXNDATE = ? and CHECKINGSTATUS = ? ";
		map.put("TXNDATE", txndate);
		map.put("CHECKINGSTATUS", status);
		return executeQuery(sqlStatement, map);

	}
/**
 * 根据交易日期和对账状态获取所需信息
 * @param txndate
 * @param status
 * @return
 * @throws HandlerException
 */
	public List<Map<String, String>> getCheckInfoByTrandateAndAccountNo(String txndate,String status,String acctNo) throws HandlerException {
		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select * from " + tableName + " where TXNDATE = ? and CHECKINGSTATUS = ? and PAYACCOUNTNO = ?";
		map.put("TXNDATE", txndate);
		map.put("CHECKINGSTATUS", status);
		map.put("PAYACCOUNTNO", acctNo);
		return executeQuery(sqlStatement, map);

	}

	/**
	 * 查询近两日处于某特定划转状态中的交易
	 * @param status
	 * @return
	 * @throws HandlerException
	 */
		public List<Map<String, String>> getRecentlyInfoBystatus(String status) throws HandlerException {
			LinkedHashMap map = new LinkedHashMap();
			String sqlStatement = "select * from " + tableName + " where TXNDATE >= (select LASTACDT from PLAT_DATE_PARAM t ) and  ACTTRAFSTATUS = ?";
			map.put("ACTTRAFSTATUS", status);
			return executeQuery(sqlStatement, map);

		}
}
