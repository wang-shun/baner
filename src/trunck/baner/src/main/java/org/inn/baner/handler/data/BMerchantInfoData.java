package org.inn.baner.handler.data;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BMerchantInfoData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BMerchantInfoData.class);

	/**
	 * 根据商户id查询商户信息
	 *
	 * @param merchantId
	 * @return 2016年3月15日 下午3:20:46
	 * @author zhangxiaoyun update by tianguangzhao 2016/3/16 增加valid 是否可用标识
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getBMerchantInfo(String merchantId) throws HandlerException {

		String sqlStatement = "select * from B_MERCHANT_INFO where MERCHANTID=? and VALID ='0' ";
		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("MERCHANTID [" + merchantId + "]");
		List<Map<String, String>> resList = null;
		try {
			psmt = DataSourceUtil
					.getPreparedStatement(connection, sqlStatement);
			// 轮询将所有参数注入到psmt中
			psmt.setString(1, merchantId);
			// 执行sql语句
			rs = psmt.executeQuery();
			resList = DBUtil.resConvertList(rs);
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
	 * 获取所有正常商户的信息，用于调用计费模块商户费率查询功能
	 *
	 * @return 封装有商户信息的list
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getAllMerchantInfo()
			throws HandlerException {
		String sqlStatement = "select * from B_MERCHANT_INFO where VALID ='0' ";
		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		List<Map<String, String>> resList = null;
		try {
			psmt = DataSourceUtil
					.getPreparedStatement(connection, sqlStatement);
			// 执行sql语句
			rs = psmt.executeQuery();
			resList = DBUtil.resConvertList(rs);
		} catch (SQLException e) {
			logger.error("execute sqlstatement exception", e);
			throw new HandlerException(e);
		} finally {
			// 释放资源
			relaceResource();
		}
		return resList;

	}
}
