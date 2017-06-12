package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BSellExgDet;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BSellExgDetData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BSellExgDetData.class);
	String tableName = "B_SELL_EXG_DET";
	String inserSql = "insert into b_sell_exg_det (paybatno, paydate, pay_seqno, merchantid, payerid,"
			+ " payeraccount, payername, remit_ccy, remit_amt, orderid, payeename, "
			+ "payeecountrycode, pay_type, tran_code, tran_desc, is_ref, contract_no, "
			+ "invoice_no, applicant, applicant_tel, stamp) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
	 * 批量插入购汇明细
	 * @param bBuyExgDet
	 * @throws HandlerException
	 * 2016年3月15日 下午6:03:13
	 * @author zhangxiaoyun
	 */
	public void batchInsertData(List<BSellExgDet> list) throws HandlerException{
		logger.debug("insert into b_buy_exg_ctrl is begin ..");
		logger.info("sql[" + inserSql + "]");
		logger.info("params list is "+list);
		psmt = DataSourceUtil.getPreparedStatement(connection, inserSql);
		try {
			for (int i = 0; i < list.size(); i++) {
				BSellExgDet sellExgDet = list.get(i);
				if(logger.isDebugEnabled()){
					logger.debug( i + tableName+" data is["+sellExgDet+ "]");
				}
				preparePreparedStatement(sellExgDet);
				psmt.addBatch();
				if((i+1)%BusinessConstantField.BATCHNUM == 0){
					psmt.executeBatch();
				}
			}
			//最后补一下刀
			psmt.executeBatch();
		} catch (Exception e) {
			logger.error("batch exec inser error", e);
			throw new HandlerException(e);
		}
	}
	
	
	
	/**
	 * 插入BSellExgDet对象
	 * @param bSellExgDet
	 * @throws HandlerException
	 * 2016年3月15日 下午4:55:29
	 * @author zhangxiaoyun
	 */
	public void insertData(BSellExgDet bSellExgDet) throws HandlerException {
		logger.debug("insert into "+tableName+" is begin ..");
		logger.info("sql[" + inserSql + "]");
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" data is ["+bSellExgDet+ "]");
		}
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, inserSql);
			preparePreparedStatement(bSellExgDet);
			psmt.execute();
		} catch (Exception e) {
			logger.error("exec inser error", e);
			throw new HandlerException(e);
		}
	}

	private void preparePreparedStatement(BSellExgDet bSellExgDet)
			throws SQLException {
		psmt.setString(1, bSellExgDet.getPaybatno());
		psmt.setString(2, bSellExgDet.getPaydate());
		psmt.setString(3, bSellExgDet.getPaySeqno());
		psmt.setString(4, bSellExgDet.getMerchantid());
		psmt.setString(5, bSellExgDet.getPayerid());
		psmt.setString(6, bSellExgDet.getPayeraccount());
		psmt.setString(7, bSellExgDet.getPayername());
		psmt.setString(8, bSellExgDet.getRemitCcy());
		psmt.setBigDecimal(9, bSellExgDet.getRemitAmt());
		psmt.setString(10, bSellExgDet.getOrderid());
		psmt.setString(11, bSellExgDet.getPayeename());
		psmt.setString(12, bSellExgDet.getPayeecountrycode());
		psmt.setString(13, bSellExgDet.getPayType());
		psmt.setString(14, bSellExgDet.getTranCode());				//交易编码
		psmt.setString(15, bSellExgDet.getTranDesc());				//交易附言
		psmt.setString(16, bSellExgDet.getIsRef());					//是否保税区
		psmt.setString(17, bSellExgDet.getContractNo());			//市价金额
		psmt.setString(18, bSellExgDet.getInvoiceNo());				//优惠后金额
		psmt.setString(19, bSellExgDet.getApplicant());				//商户联系人
		psmt.setString(20, bSellExgDet.getApplicantTel());			//商户联系人电话
		Timestamp timestamp = new Timestamp((bSellExgDet.getStamp()==null?new Date():bSellExgDet.getStamp()).getTime());
		psmt.setTimestamp(21, timestamp);				//时间戳
	}
}
