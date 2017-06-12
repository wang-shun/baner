package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BPaymentFlow;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
/**
 * 交易流水表操作类，
 * @author tianguangzhao
 *update by tianguangzhao 2016/4/11 在每次update时都更新时间戳，记录最后操作时间！
 */
public class BPaymentFlowData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BPaymentFlowData.class);
	static String tableName = "B_PAYMENT_FLOW";

	// 对账时的临时表，暂时未用到，以后对账数据增大时，在数据库中直接对账，先将宝易互通上送文件中的数据，解析存入数据库中，然后对比临时表和交易流水表中的内容进行对账。
	// 2016/4/29 tiangunagzhao 对账时查出没有的数据和多出的数据，然后作登记
	static String tableNameTmp = "B_PAYMENT_FLOW_TMP";

	/**
	 * 查询交易流水方法
	 * 
	 * @param merchantId
	 *            商户号
	 * @param orderId
	 *            订单号
	 * @return
	 * @throws ServiceException
	 */
	public boolean selectCount(String payorderid) throws HandlerException {

		if (logger.isDebugEnabled()) {
			logger.debug(" select count from " + tableName + " where payorderid = [" + payorderid + "]");
		}
		getConnection();
		String sql = "select count(1) as num from " + tableName + " where payorderid = ? ";

		boolean flag = false;
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			psmt.setString(1, payorderid);
			rs = psmt.executeQuery();
			if (rs.next()) {
				String num = rs.getString("num");
				if (num != null && !num.equals("0")) {
					flag = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("exec sql error", e);
			throw new HandlerException(e);
		} finally {
			relaceResource();
		}
		return flag;
	}

	/**
	 * 向交易流水表中插入信息
	 * 
	 * @param bPaymentFlowBean
	 * @author tianguangzhao
	 * @throws HandlerException
	 * update by tianguangzhao 2016/04/11 新增 “支付完成时间字段”
	 */
	public void insertData(BPaymentFlow bPaymentFlowBean) throws HandlerException {
	
		String sqlStatement = "insert into "
				+ tableName
				+ " (PAYORDERID,TRANDATE,TRANTIME,TXNAMT,CURRENCY,MERCHANTID,ORDERID,ORDERTIME,CHANNEL,PAYTIME,PAYSTATUS,PURCHASERID,CHECKINGSTATUS,TMSMP，ORDERTDATE,PAYCARD,PAYDATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("PAYORDERID",bPaymentFlowBean.getPayorderid());
			map.put("TRANDATE",bPaymentFlowBean.getTrandate());
			map.put("TRANTIME",bPaymentFlowBean.getTrantime());
			map.put("TXNAMT",bPaymentFlowBean.getTxnamt());
			map.put("CURRENCY",bPaymentFlowBean.getCurrency());
			map.put("MERCHANTID",bPaymentFlowBean.getMerchantid());
			map.put("ORDERID",bPaymentFlowBean.getOrderid());
			map.put("ORDERTIME",bPaymentFlowBean.getOrdertime());
			map.put("CHANNEL",bPaymentFlowBean.getChannel());
			map.put("PAYTIME",bPaymentFlowBean.getPaytime());
			map.put("PAYSTATUS",bPaymentFlowBean.getPaystatus());
			map.put("PURCHASERID",bPaymentFlowBean.getPurchaserid());
			map.put("CHECKINGSTATUS",bPaymentFlowBean.getCheckingstatus());
			map.put("TMSMP",time);
			map.put("ORDERTDATE",bPaymentFlowBean.getOrderdate());
			map.put("PAYCARD",bPaymentFlowBean.getPaycard());
			map.put("PAYDATE",bPaymentFlowBean.getPaydate());
			list.add(map);
			batchExecuteUpdate(sqlStatement, list);			
		   //	connection.commit();
			if (logger.isDebugEnabled()) {
				logger.debug(" insert " + tableName + " table  sucess !");
			}
		} catch (HandlerException e) {
			logger.error("insert " + tableName + " table data error !", e);
			// update by tianguangzhao 2016/4/27 事务控制交由上层
			// try {
			// connection.rollback();
			// } catch (SQLException e1) {
			// logger.error("connection roll back error !");
			// new HandlerException(e1);
			// }
			throw e;
		}
	}

/**
 * 根据支付流水号更新支付流水表中的数据
 * @param pa 支付流水号
 * @param tranState 交易状态
 * @param paydate  支付完成日期
 * @param paytime  支付完成时间
 * @return
 * @throws HandlerException
 */
	public int updatePayFlowStatus(String payorderid, String tranState,String paydate,String paytime) throws HandlerException {
		int count = 0;
		String sqlStatement = "update " + tableName + " set PAYSTATUS = ?, PAYDATE= ? ,PAYTIME = ? , TMSMP = ? where PAYORDERID = ?";
		try {
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("PAYSTATUS", tranState);
			map.put("PAYDATE", paydate);
			map.put("PAYTIME", paytime);
			//新增更新时间戳，update by tianguangzhao 2016/4/11 
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			map.put("TMSMP", time);
			map.put("PAYORDERID", payorderid);
			list.add(map);
			count = batchExecuteUpdate(sqlStatement, list);			
			if (logger.isDebugEnabled()) {
				logger.debug("update table success !");
			}
		} catch (HandlerException e) {
			logger.error("update table " + tableName + " error !", e);
			throw e;
		}

		return count;
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
	 * 根据支付流水号获取支付流水信息
	 * 
	 * @param payorderid
	 *            ,支付流水号
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getPaymentFlowByPayorderid(String payorderid) throws HandlerException {
		String sqlStatement = "select * from  " + tableName + " where PAYORDERID = ?  ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("PAYORDERID", payorderid);
		return executeQuery(sqlStatement, map);
	}

	/**
	 * 根据交易日期获取交易流水信息，用于对账时，根据账务日期进行对账
	 * 
	 * @param trandate
	 *            ,交易日期
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getPaymentFlowByTrandate(String trandate) throws HandlerException {
		String sqlStatement = "select * from  " + tableName + " where TRANDATE = ?  ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("TRANDATE", trandate);
		return executeQuery(sqlStatement, map);
	}

	/**
	 * 根据交易日期和商户号获取交易流水信息，用于对账时，根据账务日期进行对账
	 * 
	 * @param trandate
	 *            ,交易日期
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getInfoByTrandateAndMerchantNo(String trandate,String merchantNo) throws HandlerException {
		String sqlStatement = "select * from  " + tableName + " where TRANDATE = ? and  MERCHANTID = ? ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("TRANDATE", trandate);
		map.put("MERCHANTID", merchantNo);
		return executeQuery(sqlStatement, map);
	}
	
	/**
	 * 根据交易流水号获取待対帐的交易流水信息，用于对账时，根据账务日期进行对账
	 * 
	 * @param trandate
	 *            ,交易日期
	 * @return
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getMerchantNosByTrandateAndStatus(String trandate,String status) throws HandlerException {
		String sqlStatement = "select distinct MERCHANTID from  " + tableName + " where TRANDATE = ?  and CHECKINGSTATUS =? ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("TRANDATE", trandate);
		map.put("CHECKINGSTATUS", status);
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
	public int batchExecuteUpdate(String sqlStatement,
			List<LinkedHashMap<String, String>> list) throws HandlerException {

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
	 * 根据交易日期更新数据库中交易支付流水的状态
	 * 
	 * @param accountDate
	 * @param status
	 * @return
	 * @throws HandlerException
	 */
	public int updateStatusByTrandate(String trandate, String finalstatus) throws HandlerException {
		int count = 0;
		//此处根据trandate进行更新，以后注意修改
		try {
			String sqlStatement = "update  " + tableName + " set CHECKINGSTATUS = ? , TMSMP = ? where TRANDATE = ? ";
			LinkedHashMap map = new LinkedHashMap();
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			map.put("CHECKINGSTATUS", finalstatus);
			//新增更新时间戳，update by tianguangzhao 2016/4/11 
			String time = TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE);
			map.put("TMSMP", time);
			map.put("TRANDATE", trandate);
			list.add(map);
			count = batchExecuteUpdate(sqlStatement, list);
		} catch (HandlerException e) {
			logger.error("update error !", e);
			throw e;
		}
		return count;
	}


	/**
	 * 比较数据库中的数据，进行对账,以我方数据为准
	 * 暂时未用到
	 * @param accountDate
	 * @return
	 * @throws HandlerException
	 */
	public int checkingPayFlowByOurData(String accountdate) throws HandlerException {
		int i = -1;
		if (logger.isDebugEnabled()) {
			logger.debug(" checking pay flow start ! accountdate = [" + accountdate + "]");
		}

		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select count(1) as NUM from "
				+ tableName
				+ " a  where a.accountdate = ? and NOT EXISTS (select b.orderid from "
				+ tableNameTmp
				+ " b where  a.ordertdate= b.ordertdate and a.orderid = b.orderid and a.txnamt = b.txnamt and a.currency  = b.currency and a.paystatus = b.paystatus)";
		map.put("accountdate", accountdate);
		try {
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			if (list == null || list.size() == 0) {
				i = 0;
			} else {
				Map<String, String> sesultMap = list.get(0);
				String num = sesultMap.get("NUM");
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
	 * 暂时未用到
	 * @param accountDate
	 * @return
	 * @throws HandlerException
	 */
	public int checkingPayFlowByUMB(String accountdate) throws HandlerException {
		int i = -1;
		if (logger.isDebugEnabled()) {
			logger.debug(" checking pay flow start ! accountdate = ["
					+ accountdate + "]");
		}

		LinkedHashMap map = new LinkedHashMap();
		String sqlStatement = "select count(1) as NUM from "
				+ tableNameTmp
				+ " b  where  NOT EXISTS (select a.orderid from "
				+ tableName
				+ " a where a.accountdate = ? and b.ordertdate= a.ordertdate and a.orderid = b.orderid  and a.txnamt = b.txnamt and a.currency  = b.currency and a.paystatus = b.paystatus)";
		map.put("accountdate", accountdate);
		try {
			List<Map<String, String>> list = executeQuery(sqlStatement, map);
			Map<String, String> sesultMap = list.get(0);
			String num = sesultMap.get("NUM");
			i = Integer.parseInt(num);
		} catch (HandlerException e) {
			logger.error("checking pay flow error !", e);
			throw e;
		}

		return i;
	}

	/**
	 * 根据支付状态获取支付流水信息，用于定时任务，查询支付状态ctb002
	 * 
	 * @param payorderid
	 *            ,支付流水号
	 * @return
	 * @throws HandlerException
	 * update by tianguangzhao 2016/5/20 增加时间过滤，只查询 昨天和今天的交易!
	 */
	public List<Map<String, String>> getRecentlyInfoByPaystatus(String paystatus)
			throws HandlerException {
		String sqlStatement = "select * from  " + tableName + " where PAYSTATUS = ?  and TRANDATE >= (select LASTACDT from PLAT_DATE_PARAM t )  ";
		LinkedHashMap map = new LinkedHashMap();
		map.put("PAYSTATUS", paystatus);
		return executeQuery(sqlStatement, map);
	}
}
