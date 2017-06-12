package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.ServerInfo;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;
/**
 * 操作服务方信息表的类
 * update by tianguangzhao 20160711 
 * 实现InvokerExecutor接口
 *
 */
public class ServerInfoData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(ServerInfoData.class);
	String tableName = "SERVER_INFO";
	private static ServerInfoData instance = null;

	public static ServerInfoData getInstance() {
		if (instance == null) {
			instance = new ServerInfoData();
		}
		return instance;
	}

	/**
	 * update by tianguangzhao 20160711 新增初始化变量data和tmpData
	 */
	private ServerInfoData() {
		data = new HashMap<String, ServerInfo>();
		tmpData = new HashMap<String, ServerInfo>();
		instance = this;
	}
	/**
	 * 通过主键获取
	 * 
	 * @param key
	 *            ,主键serverId
	 * @return ServerInfo，封装信息的实体类
	 */
	public ServerInfo getServerInfo(String key) {
		return (ServerInfo) data.get(key);

	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		
		logger.info("start load " + tableName + " table data...");
		String sql = "select * from " + tableName;
		if (logger.isDebugEnabled()) {
			logger.debug(tableName + " init sql[" + sql + "]");
		}
		boolean res = false;
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ServerInfo si = new ServerInfo();
				si.setEncrypt_algorithm("ENCRYPT_ALGORITHM");
				si.setIp(rs.getString("IP"));
				si.setIsencrypt(rs.getString("ISENCRYPT"));
				si.setIssignature(rs.getString("ISSIGNATURE"));
				si.setProt(rs.getString("PROT"));
				si.setPublic_key_file(rs.getString("PUBLIC_KEY_FILE"));
				si.setRemittance_fee(rs.getString("REMITTANCE_FEE"));
				si.setServer_key(rs.getString("SERVER_KEY"));
				si.setServerdesc(rs.getString("SERVERDESC"));
				si.setServerid(rs.getString("SERVERID"));
				si.setSignature_algorithm(rs.getString("SIGNATURE_ALGORITHM"));
				si.setRes_code(rs.getString("RES_CODE"));
				si.setRes_msg(rs.getString("RES_MSG"));
				si.setKey_store_alias(rs.getString("KEY_STORE_ALIAS"));
				si.setKey_store_file(rs.getString("KEY_STORE_FILE"));
				si.setKey_store_password(rs.getString("KEY_STORE_PASSWORD"));
				target.put(si.getServerid(), si);
			}
			res = true;
			logger.info(tableName + " init succ data Size:" + target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init SERVER_INFO table data exception", e);
			throw new HandlerException("init SERVER_INFO table data exception");
		}
		return res;
	}
	//实现InvokerExecutor的方法
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