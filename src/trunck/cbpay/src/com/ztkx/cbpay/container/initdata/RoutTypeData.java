package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.RoutType;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class RoutTypeData extends AbstractLoadInit{
	Logger logger = Logger.getLogger(RoutTypeData.class);
	String tableName = "ROUT_TYPE";
	
	public RoutTypeData(){
		data = new HashMap<String, List<RoutType>>();
		tmpData = new HashMap<String, List<RoutType>>();
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName ;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		List<RoutType> list = new ArrayList<RoutType>();
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				RoutType rt = new RoutType();
				rt.setRouteres(rs.getString("ROUTERES"));
				rt.setRoutetype(rs.getString("ROUTETYPE"));
				rt.setRouteField(rs.getString("ROUTEFIELD"));
				rt.setTran_opr(rs.getString("TRAN_OPR"));
				list.add(rt);
			}
			logger.info(tableName+" init succ data Size:"+list.size());
			target.put(tableName, list);
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init ROUT_TYPE table data exception",e);
			throw new HandlerException("init ROUT_TYPE table data exception");
		}		
		return res;
	}	
}