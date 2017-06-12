package com.ztkx.cbpay.business.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;



public class BTradeParamData extends AbstractLoadInit{

	Logger logger = Logger.getLogger(BTradeParamData.class);
	
	String tableName = "B_TRADE_PARAM";
	private static BTradeParamData instance = null;

	public static BTradeParamData getInstance() {
		if (instance == null) {
			instance = new BTradeParamData();
		}
		return instance;
	}

	private BTradeParamData() {
		instance = this;
	}
	
	public List<String> getKeyList(){
		List<String> list = new ArrayList<String>(data.values());
		return list ;
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start insert "+tableName+" table data...");
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String key = rs.getString("KEY");
				target.put(key, key);
			}
			res = true;
			logger.info(tableName+" init succ list Size:"+target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init B_TRADE_PARAM table data exception",e);
			throw new HandlerException("init B_TRADE_PARAM table data exception");
		}
		return res;
	}
}
