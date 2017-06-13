package org.inn.baner.handler.data;

import com.ztkx.cbpay.business.bean.BSellExgCtrl;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class BSellExgCtrlData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BSellExgCtrlData.class);
	private static String tableName = "B_SELL_EXG_CTRL";
	private static String sql = "insert into b_sell_exg_ctrl (paybatno, paydate, paytime, quotechnl, remit_ccy, "
			+ "remit_amt, cost_type, payee_acct_no, payee_client_name, payee_address, payer_acct_no, "
			+ "payer_client_name, payer_address, payee_acct_open_branch_id, remark, clear_bank_bic, "
			+ "process_msg, process_status, fail_reason, chkstatus, stamp,filename,"
			+ "BUSS_STATUS,TOT_NUM) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

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


	public List<BSellExgCtrl> executeQueryResBean(String sqlStatement,
			LinkedHashMap<String, String> map) throws HandlerException {

		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("params is " + map);
		List<BSellExgCtrl> resList = null;
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
			resList = resConvertList(rs);
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
	 * 插入BSellExgCtrl对象
	 * @param bSellExgCtrl
	 * @throws HandlerException
	 * 2016年3月15日 下午4:55:29
	 * @author zhangxiaoyun
	 */
	public void insertData(BSellExgCtrl bSellExgCtrl) throws HandlerException {
		logger.debug("insert into b_buy_exg_ctrl is begin ..");
		logger.info("sql[" + sql + "]");
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" data is ["+bSellExgCtrl+ "]");
		}
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sql);
			preparePreparedStatement(bSellExgCtrl);				//买入金额
			psmt.execute();
		} catch (Exception e) {
			logger.error("exec inser error", e);
			throw new HandlerException(e);
		}
	}

	/**
	 * 根据rs返回对象的list
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<BSellExgCtrl> resConvertList(ResultSet rs) throws SQLException {
		List<BSellExgCtrl> list = new ArrayList<BSellExgCtrl>();

		while(rs.next()){
			BSellExgCtrl bBuyExgCtrl = new BSellExgCtrl();
			bBuyExgCtrl.setPaybatno(rs.getString(1));
			bBuyExgCtrl.setPaydate(rs.getString(2));
			bBuyExgCtrl.setPaytime(rs.getString(3));
			bBuyExgCtrl.setQuotechnl(rs.getString(4));
			bBuyExgCtrl.setRemitCcy(rs.getString(5));
			bBuyExgCtrl.setRemitAmt(rs.getBigDecimal(6));
			bBuyExgCtrl.setCostType(rs.getString(7));
			bBuyExgCtrl.setPayeeAcctNo(rs.getString(8));
			bBuyExgCtrl.setPayeeClientName(rs.getString(9));
			bBuyExgCtrl.setPayeeAddress(rs.getString(10));
			bBuyExgCtrl.setPayerAcctNo(rs.getString(11));
			bBuyExgCtrl.setPayerClientName(rs.getString(12));
			bBuyExgCtrl.setPayerAddress(rs.getString(13));
			bBuyExgCtrl.setPayeeAcctOpenBranchId(rs.getString(14));
			bBuyExgCtrl.setRemark(rs.getString(15));
			bBuyExgCtrl.setClearBankBic(rs.getString(16));
			bBuyExgCtrl.setProcessMsg(rs.getString(17))	;//市价金额
			bBuyExgCtrl.setProcessStatus(rs.getString(18));//优惠后金额
			bBuyExgCtrl.setFailReason(rs.getString(19));//卖出账号
			bBuyExgCtrl.setChkstatus(rs.getString(20));
			bBuyExgCtrl.setStamp(rs.getTimestamp(21));//卖出金额
			bBuyExgCtrl.setFileName(rs.getString(22));//买入金额
			bBuyExgCtrl.setBuss_status(rs.getString(23));
			bBuyExgCtrl.setTot_num(rs.getInt(24));
			list.add(bBuyExgCtrl);
		}
		return list;
	}
	
	private void preparePreparedStatement(BSellExgCtrl bSellExgCtrl)
			throws SQLException {
		psmt.setString(1, bSellExgCtrl.getPaybatno());
		psmt.setString(2, bSellExgCtrl.getPaydate());
		psmt.setString(3, bSellExgCtrl.getPaytime());
		psmt.setString(4, bSellExgCtrl.getQuotechnl());
		psmt.setString(5, bSellExgCtrl.getRemitCcy());
		psmt.setBigDecimal(6, bSellExgCtrl.getRemitAmt());
		psmt.setString(7, bSellExgCtrl.getCostType());
		psmt.setString(8, bSellExgCtrl.getPayeeAcctNo());
		psmt.setString(9, bSellExgCtrl.getPayeeClientName());
		psmt.setString(10, bSellExgCtrl.getPayeeAddress());
		psmt.setString(11, bSellExgCtrl.getPayerAcctNo());
		psmt.setString(12, bSellExgCtrl.getPayerClientName());
		psmt.setString(13, bSellExgCtrl.getPayerAddress());	
		psmt.setString(14, bSellExgCtrl.getPayeeAcctOpenBranchId());
		psmt.setString(15, bSellExgCtrl.getRemark());
		psmt.setString(16, bSellExgCtrl.getClearBankBic());					
		psmt.setString(17, bSellExgCtrl.getProcessMsg());					
		psmt.setString(18, bSellExgCtrl.getProcessStatus());				
		psmt.setString(19, bSellExgCtrl.getFailReason());
		psmt.setString(20, bSellExgCtrl.getChkstatus());
		psmt.setTimestamp(21, new Timestamp((bSellExgCtrl.getStamp()==null?new Date():bSellExgCtrl.getStamp()).getTime()));
		psmt.setString(22, bSellExgCtrl.getFileName());
		psmt.setString(23, bSellExgCtrl.getBuss_status());
		psmt.setInt(24, bSellExgCtrl.getTot_num());
	}
}
