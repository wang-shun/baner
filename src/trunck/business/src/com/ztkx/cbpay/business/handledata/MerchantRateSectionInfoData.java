package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantRateSectionInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 操作商户费率详细信息表的data类，此表中存放
 * 
 * @author tianguangzhao
 *
 */
public class MerchantRateSectionInfoData extends AbstractDbMapper {
	Logger logger = Logger.getLogger(MerchantRateSectionInfoData.class);
	static String tableName = "B_MERCHANT_RATE_SECTION_INFO";

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
	 * 将从计费模块获取到的最新费率入库
	 * 
	 * @param merchantRateInfo
	 * @throws HandlerException
	 */
	public void insertMerchantRateDetailInfo(List<BMerchantRateSectionInfo> merchantRateSectionInfos, String merchantNo) throws HandlerException {

		String sql = "insert into "
				+ tableName
				+ " (MERCHANTNO,RULEID,BEGININPUT,ENDINPUT,SECCHARGEMODE,SECCHARGEAMT,SECCHARGERATIO,TMSMP)  values (?,?,?,?,?,?,?,?) ";
		List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
		for (BMerchantRateSectionInfo merchantRateSectionInfo : merchantRateSectionInfos) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("MERCHANTNO", merchantNo);
			map.put("RULEID", merchantRateSectionInfo.getRuleId());
			map.put("BEGININPUT", merchantRateSectionInfo.getBeginInput());
			map.put("ENDINPUT", merchantRateSectionInfo.getEndInput());
			map.put("SECCHARGEMODE", merchantRateSectionInfo.getSecChargeMode());
			map.put("SECCHARGEAMT", merchantRateSectionInfo.getSecChargeAmt());
			map.put("SECCHARGERATIO",
					merchantRateSectionInfo.getSecChargeRatio());
			map.put("TMSMP", TimeUtil
					.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE));
			list.add(map);
		}

		// 调用批量插入方法，将数据插入表中
		batchExecuteUpdate(sql, list);
	}
}
