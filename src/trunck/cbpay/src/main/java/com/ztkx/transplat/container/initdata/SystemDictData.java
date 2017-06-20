package com.ztkx.transplat.container.initdata;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.SystemDict;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * 系统参数配置表（console模块使用）
 * @author tianguangzhao
 *
 */
public class SystemDictData extends AbstractLoadInit implements InvokerExecutor{

	Logger logger = Logger.getLogger(SystemDictData.class);
	String tableName = "SYSTEM_DICT";
	private static SystemDictData instance = null;

	public static SystemDictData getInstance() {
		if (instance == null) {
			instance = new SystemDictData();
		}
		return instance;
	}

	/**
	 * update by tianguangzhao 20160711 新增初始化变量data和tmpData
	 */
	private SystemDictData() {
		data = new HashMap<String, SystemDict>();
		tmpData = new HashMap<String, SystemDict>();
		instance = this;
	}
	/**
	 * 更具key中获取相应的system_dict表中的记录
	 * 
	 * @param key
	 * @return
	 */
	public SystemDict getSystemDict(String key){
		return (SystemDict) data.get(key);
	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		logger.info("start load " + tableName + " table data...");
		String sql = "select * from " + tableName;
		boolean res = false;
		if (logger.isDebugEnabled()) {
			logger.debug(tableName + " init sql[" + sql + "]");
		}
		
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				SystemDict systemDict = new SystemDict();
		        systemDict.setKey(rs.getString("keyvalue"));
		        systemDict.setDesc(rs.getString("description"));
		        systemDict.setName(rs.getString("namevalue"));
		        systemDict.setType(rs.getString("TYPE"));
		        //将记录封装到map中。
		        target.put(systemDict.getKey(), systemDict);
			}
			res = true;
			logger.info(tableName + " init succ data Size ["+target.size()+"]");
		} catch (SQLException e) {
			res = false;
			logger.error("init SERVER_INFO table data exception", e);
			new HandlerException("init SERVER_INFO table data exception");
		}
		return res;
	}
	/**
	 * 以下为操作者方法实现
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
