package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.PlatToChanelResCode;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class PlatToChanelResCodeData extends AbstractLoadInit implements InvokerExecutor{

	Logger logger = Logger.getLogger(PlatToChanelResCodeData.class);
	static String tableName = "plattochanelrescode";
	private static PlatToChanelResCodeData instance = null;

	public static PlatToChanelResCodeData getInstance() {
		if (instance == null) {
			instance = new PlatToChanelResCodeData();
		}
		return instance;
	}
	/**update by tianguangzhao 20160711 
	 * 新增初始化变量data和tmpData
	 */
	private PlatToChanelResCodeData() {
		data = new HashMap<String, PlatToChanelResCode>();
		tmpData = new HashMap<String, PlatToChanelResCode>();
		instance = this;
	}
	public  PlatToChanelResCode getBean(String fromId,String platCode){
		PlatToChanelResCode res = (PlatToChanelResCode) data.get(fromId+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+platCode);
		return res;
	}
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start load " + tableName + " table data...");
		String sql = "select * from " + tableName;
		if (logger.isDebugEnabled()) {
			logger.debug(tableName + " init sql[" + sql + "]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				PlatToChanelResCode ptcrc = new PlatToChanelResCode();
				ptcrc.setPlatcode(rs.getString("PLATCODE"));
				ptcrc.setTranForm(rs.getString("TRAN_FROM"));
				ptcrc.setChanelcode(rs.getString("CHANELCODE"));
				ptcrc.setChaneldes(rs.getString("CHANELDES"));
				target.put(ptcrc.getTranForm()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+ptcrc.getPlatcode(), ptcrc);
			}
			logger.info(tableName + " init succ list Size:" + target.size());
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init plattochanelrescode table data exception", e);
			throw new HandlerException("init plattochanelrescode table data exception");
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
