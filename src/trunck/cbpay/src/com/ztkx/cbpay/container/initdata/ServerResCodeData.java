package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.ServerResCode;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;


public class ServerResCodeData extends AbstractLoadInit implements InvokerExecutor{

	Logger logger = Logger.getLogger(ServerResCodeData.class);
	
	String tableName = "serverrescode";
	private static ServerResCodeData instance = null;

	public static ServerResCodeData getInstance() {
		if (instance == null) {
			instance = new ServerResCodeData();
		}
		return instance;
	}

	/**update by tianguangzhao 20160711 
	 * 新增初始化变量data和tmpData
	 */
	private ServerResCodeData() {
		data = new HashMap<String, ServerResCode>();
		tmpData = new HashMap<String, ServerResCode>();
		instance = this;
	}
	/**
	 * 根据系统id和系统响应码获取平台响应码
	 * @param systemId
	 * @param responseCode
	 * @return
	 */
	public ServerResCode getPlatCode(String systemId,String responseCode){
		ServerResCode src = (ServerResCode) data.get(systemId+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+responseCode);
		return src;
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
				ServerResCode src = new ServerResCode();
				src.setServerid(rs.getString("SERVERID"));
				src.setServerrescode(rs.getString("SERVERRESCODE"));
				src.setServerresdes(rs.getString("SERVERRESDES"));
				src.setPlatcode(rs.getString("PLATCODE"));
				target.put(src.getServerid()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+src.getServerrescode(), src);
			}
			res = true;
			logger.info(tableName+" init succ list Size:"+target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init serverrescode table data exception",e);
			throw new HandlerException("init serverrescode table data exception");
		}
		return res;
	}
	
	
	/**
	 * 操作者接口实现
	 */
	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}
	@Override
	public boolean doCommand(Map<String,String> commandparam) throws HandlerException {
		return this.preload();
	}
	@Override
	public void cancleCommand() throws HandlerException {
		this.failHand();
	}
	@Override
	public void confirmOpr() throws HandlerException {
		this.reload();
	}
	
}
