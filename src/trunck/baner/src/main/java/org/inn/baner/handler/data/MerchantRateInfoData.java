package org.inn.baner.handler.data;

import com.ztkx.cbpay.business.bean.BMerchantRateInfo;
import com.ztkx.cbpay.business.bean.BMerchantRateSectionInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作商户费率表的data类
 *
 * @author tianguangzhao
 *
 */
public class MerchantRateInfoData extends AbstractDbMapper {
	Logger logger = Logger.getLogger(MerchantRateInfoData.class);
	static String tableName = "B_MERCHANT_RATE_INFO";

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
	public List<Map<String, String>> executeQuery(String sqlStatement,LinkedHashMap<String, String> map) throws HandlerException {

		relaceResource();
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
			getConnection();
		}
		return resList;

	}

	/**
	 * 将从计费模块获取到的最新费率入库,此处未修改，以后注意事务等问题
	 *
	 * @param merchantRateInfo
	 * @throws HandlerException
	 */
	public void insertMerchantRateInfo(List<BMerchantRateInfo> merchantRateInfos) throws HandlerException {
		// 为了确保在同一个事务中
		MerchantRateSectionInfoData merchantRateSectionData = new MerchantRateSectionInfoData();
		try {
			// 关闭自动提交功能
			connection.setAutoCommit(false);
			merchantRateSectionData.setConnection(connection);

			String sql = "insert into "
					+ tableName
					+ " (ID,MERCHANTNO,ROUNDINGMODE,CHARGEMODE,OVERSTRATEGY,CHARGECYCLE,BEGINAMT,ENDAMT,TRANTYPE,TRANCODE,PAYCHANNELCODE,BANKCARDCSTTYPE,BANKCARDTYPE,BANKCODE,CURTYPE,CHARGESTATESTR,STATE,WEIGHT,AVAILBEGINTIME,AVAILENDTIME,SECCHARGETYPE,TMSMP)  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			List<LinkedHashMap<String, String>> rateList = new ArrayList<LinkedHashMap<String, String>>();
			for (BMerchantRateInfo merchantRateInfo : merchantRateInfos) {
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("ID", merchantRateInfo.getId());
				map.put("MERCHANTNO", merchantRateInfo.getMerchantNo());
				map.put("ROUNDINGMODE", merchantRateInfo.getRoundingMode());
				map.put("CHARGEMODE", merchantRateInfo.getChargeMode());
				map.put("OVERSTRATEGY", merchantRateInfo.getOverStrategy());
				map.put("CHARGECYCLE", merchantRateInfo.getChargeCycle());
				map.put("BEGINAMT", merchantRateInfo.getBeginAmt());
				map.put("ENDAMT", merchantRateInfo.getEndAmt());
				map.put("TRANTYPE", merchantRateInfo.getTranType());
				map.put("TRANCODE", merchantRateInfo.getTranCode());
				map.put("PAYCHANNELCODE", merchantRateInfo.getPayChannelCode());
				map.put("BANKCARDCSTTYPE",merchantRateInfo.getBankCardCstType());
				map.put("BANKCARDTYPE", merchantRateInfo.getBankCardType());
				map.put("BANKCODE", merchantRateInfo.getBankCode());
				map.put("CURTYPE", merchantRateInfo.getCurType());
				map.put("CHARGESTATESTR", merchantRateInfo.getChargeStateStr());
				map.put("STATE", merchantRateInfo.getState());
				map.put("WEIGHT", merchantRateInfo.getWeight());
				map.put("AVAILBEGINTIME", merchantRateInfo.getAvailBeginTime());
				map.put("AVAILENDTIME", merchantRateInfo.getAvailEndTime());
				map.put("SECCHARGETYPE", merchantRateInfo.getSecChargeType());
				map.put("TMSMP",TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE));
				rateList.add(map);

				// 先将具体收费区间信息插入数据库中
				List<BMerchantRateSectionInfo> listSections = merchantRateInfo.getFeechargerulesections();
				merchantRateSectionData.insertMerchantRateDetailInfo(listSections,merchantRateInfo.getMerchantNo());
			}

			// 调用批量插入方法，将数据插入表中
			batchExecuteUpdate(sql, rateList);
			// 当所有数据正确入库之后，手动提交
			connection.commit();
		} catch (SQLException e) {
			logger.error(" merchant rate info insert into database error !", e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(" connection roll back  error !", e);
				throw new HandlerException(e);
			}
			// 将异常抛出
			throw new HandlerException(e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error(" connetion set AutoCommit error !", e);
				throw new HandlerException(e);
			}
			// 释放资源
			merchantRateSectionData.getConnection();
		}

	}
}
