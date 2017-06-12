package com.ztkx.cbpay.business.handledata;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractDbMapper;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BMerchantPermissionData extends AbstractDbMapper {

	Logger logger = Logger.getLogger(BMerchantPermissionData.class);
	private String tableName = " b_merchant_permission ";
	

	/**
	 * 根据商户id查询商户信息
	 * 
	 * @param merchantId
	 * @return 2016年3月15日 下午3:20:46
	 * @author zhangxiaoyun update by tianguangzhao 2016/3/16 增加valid 是否可用标识
	 * @throws HandlerException
	 */
	public List<Map<String, String>> getBMerchantPermission(String merchantId,String tranCode)
			throws HandlerException {

		String sqlStatement = "select * from "+tableName+" where MERCHANTID=? and trancode=? and VALID ='0' ";
		getConnection();
		logger.info("sql[" + sqlStatement + "]");
		logger.info("MERCHANTID ["+merchantId+"] tranCode["+tranCode+"]");
		List<Map<String, String>> resList = null;
		try {
			psmt = DataSourceUtil.getPreparedStatement(connection, sqlStatement);
			// 轮询将所有参数注入到psmt中
			psmt.setString(1, merchantId);
			psmt.setString(2, tranCode);
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
