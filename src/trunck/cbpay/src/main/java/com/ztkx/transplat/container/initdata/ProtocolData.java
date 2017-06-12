package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.Protocol;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

public class ProtocolData extends AbstractLoadInit{

	Logger logger = Logger.getLogger(ProtocolData.class);
	
	static String tableName = "PROTOCOL";
	private static ProtocolData instance = null;

	public static ProtocolData getInstance() {
		if (instance == null) {
			instance = new ProtocolData();
		}
		return instance;
	}

	private ProtocolData() {
		instance = this;
	}
	/**
	 * 获取协议的列表
	 * 2016年7月1日 上午10:34:25
	 * @author zhangxiaoyun
	 * @return
	 * @return List<Protocol>
	 */
	public List<Protocol> getProtocolList(){
		List<Protocol> list = new ArrayList<Protocol>(data.values());
		return list;
	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName+ " where owner='"+BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME)+"'";
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Protocol p = new Protocol();
				p.setOwner(rs.getString("OWNER"));
				p.setProtocolid(rs.getString("PROTOCOLID"));
				p.setProtocoltype(rs.getString("PROTOCOLTYPE"));
				p.setServerid(rs.getString("SERVERID"));
				p.setXmlconf(rs.getString("XMLCONF"));
				target.put(p.getProtocolid(), p);
			}
			res = true;
			logger.info(tableName+" init succ data Size:"+target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init PROTOCOL table data exception",e);
			throw new HandlerException("init PROTOCOL table data exception");
		}
		return res;
	}

}
