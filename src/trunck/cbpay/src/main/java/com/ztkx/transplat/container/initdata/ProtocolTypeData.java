package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.ProtocolType;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

public class ProtocolTypeData extends AbstractLoadInit{

	Logger logger = Logger.getLogger(ProtocolTypeData.class);
	String tableName = "PROTOCOL_TYPE";
	private static ProtocolTypeData instance = null;

	public static ProtocolTypeData getInstance() {
		if (instance == null) {
			instance = new ProtocolTypeData();
		}
		return instance;
	}

	private ProtocolTypeData() {
		instance = this;
	}
	/**
	 * 根据协议id获取协议类型
	 * 2016年7月1日 上午10:38:50
	 * @author zhangxiaoyun
	 * @param typeId
	 * @return
	 * @return ProtocolType
	 */
	public ProtocolType getProtocolType(String typeId){
		return (ProtocolType) data.get(typeId);
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ProtocolType pt = new ProtocolType();
				pt.setProtocolconfig(rs.getString("PROTOCOLCONFIG"));
				pt.setProtocolparse(rs.getString("PROTOCOLPARSE"));
				pt.setProtocolprocess(rs.getString("PROTOCOLPROCESS"));
				pt.setProtocoltype(rs.getString("PROTOCOLTYPE"));
				target.put(pt.getProtocoltype(), pt);
			}
			logger.info(tableName+" init succ data Size:"+target.size());
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init PROTOCOL_TYPE table data exception",e);
			throw new HandlerException("init PROTOCOL_TYPE table data exception");
		}
		return res;
	}
	
}
