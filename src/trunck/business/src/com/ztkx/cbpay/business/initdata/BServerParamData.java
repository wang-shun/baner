package com.ztkx.cbpay.business.initdata;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BServerParam;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class BServerParamData extends AbstractLoadInit {

	static Logger logger = Logger.getLogger(BServerParamData.class);
	String tableName = "B_SERVER_PARAM";
	private static BServerParamData instance = null;

	public static BServerParamData getInstance() {
		if (instance == null) {
			instance = new BServerParamData();
		}
		return instance;
	}

	private BServerParamData() {
		instance = this;
	}
	/**
	 * 根据服务id、参数名称获取参数值
	 * @param serverId
	 * @param currency_type
	 * @return
	 * 2016年3月29日 下午2:49:45
	 * @author zhangxiaoyun
	 */
	public BServerParam getParamsValue(String serverId,String paramName){
		return (BServerParam) data.get(serverId+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+paramName);
	}
	
	/**
	 * 根据服务系统和币种获取响应币种的pia账号
	 * @param serverId
	 * @param currency_type
	 * @return
	 * 2016年3月29日 下午2:49:45
	 * @author zhangxiaoyun
	 */
	public BServerParam getPIAAccount(String serverId,String currency_type) throws HandlerException{
		currency_type = currency_type+"_"+"PIA_ACCOUNT";
		return (BServerParam) data.get(serverId+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+currency_type);
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

	@Override
	public boolean extracted(Map target) throws HandlerException {
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName+" where USEFLG='1' ";
		boolean res = false;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				BServerParam bServerParam = new BServerParam();
				bServerParam.setServerid(rs.getString("SERVERID"));
				bServerParam.setParaname(rs.getString("PARANAME"));
				bServerParam.setParavalue(rs.getString("PARAVALUE"));
				bServerParam.setParamdesc(rs.getString("PARAMDESC"));
				target.put(bServerParam.getServerid()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+bServerParam.getParaname(), bServerParam);
			}
			logger.info("load init table ["+tableName+"] success size is ["+target.size()+"]");
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init CONVERT_RESCODE table data exception",e);
			throw new HandlerException("init CONVERT_RESCODE table data exception");
		}
		return res;
	}
	
	

}
