package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.TranFromInfo;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
/**
 * 
 * 交易来源信息表操作类
 * update by tianguangzhao 20160711 
 * 实现InvokerExecutor接口
 *
 */
public class TranFromInfoData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(TranFromInfoData.class);	
	String tableName = "TRAN_FROM_INFO";
	private static TranFromInfoData instance = null;

	public static TranFromInfoData getInstance() {
		if (instance == null) {
			instance = new TranFromInfoData();
		}
		return instance;
	}
	/**
	 * update by tianguangzhao 20160711 新增初始化变量data和tmpData
	 */
	private TranFromInfoData() {
		data = new HashMap<String, TranFromInfo>();
		tmpData = new HashMap<String, TranFromInfo>();
		instance = this;
	}
	/**
	 * 根据tranFrom的from_id 获取该条配置信息
	 * 
	 * @param name
	 * @return
	 */
	public TranFromInfo getTranFromInfoByFromId(String Fromid) {
		return (TranFromInfo) data.get(Fromid);
	}
	/**
	 * 判断当前渠道是否加密
	 * @param fromId
	 * @return
	 */
	public boolean  isEncryption(String fromId){
		boolean res = false;
		TranFromInfo tf = (TranFromInfo) data.get(fromId);
		if(tf == null){
			return res;
		}
		
		return tf.isEncrypt();
	}
	
	/**
	 * 判断当前渠道是否验证签名
	 * @param fromId
	 * @return
	 */
	public boolean  isSignature(String fromId){
		boolean res = false;
		TranFromInfo tf = (TranFromInfo) data.get(fromId);
		if(tf == null){
			return res;
		}
		
		return tf.isSignature();
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
				TranFromInfo tfi = new TranFromInfo();
				tfi.setEncrypt_algorithm(rs.getString("ENCRYPT_ALGORITHM"));
				tfi.setEncrypt_type(rs.getString("ENCRYPT_TYPE"));
				tfi.setFrom_desc(rs.getString("FROM_DESC"));
				tfi.setFrom_id(rs.getString("FROM_ID"));
				tfi.setFrom_type(rs.getString("FROM_TYPE"));
				String enCrypt = rs.getString("ISENCRYPT");
				if(enCrypt !=null && !"".equals(enCrypt) && enCrypt.trim().equals("1")){
					tfi.setEncrypt(true);
				}else {
					tfi.setEncrypt(false);
				}
				String signatrue = rs.getString("ISSIGNATURE");
				if(signatrue !=null && !"".equals(signatrue) && signatrue.trim().equals("1")){
					tfi.setSignature(true);
				}else {
					tfi.setSignature(false);
				}
				tfi.setPublic_key_file(rs.getString("PUBLIC_KEY_FILE"));
				tfi.setSignature_algorithm(rs.getString("SIGNATURE_ALGORITHM"));
				tfi.setSignature_type(rs.getString("SIGNATURE_TYPE"));
				tfi.setChanel_code(rs.getString("CHANEL_CODE"));
				tfi.setChanel_des(rs.getString("CHANEL_DES"));
				tfi.setKey_store_alias(rs.getString("KEY_STORE_ALIAS"));
				tfi.setKey_store_file(rs.getString("KEY_STORE_FILE"));
				tfi.setKey_store_password(rs.getString("KEY_STORE_PASSWORD"));
				target.put(tfi.getFrom_id(), tfi);
			}
			res = true;
			logger.info(tableName + " init succ data Size:" + target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init TRAN_FROM_INFO table data exception", e);
			throw new HandlerException("init TRAN_FROM_INFO table data exception");
		}
		return res;
	}
	
	/**
	 * 以下未操作者方法实现 
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
