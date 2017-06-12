package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.TransInfo;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

public class TransInfoData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(TransInfoData.class);
	String tableName = "TRANS_INFO";
	private static TransInfoData instance = null;

	public static TransInfoData getInstance() {
		if (instance == null) {
			instance = new TransInfoData();
		}
		return instance;
	}

	private TransInfoData() {
		instance = this;
	}
	/**
	 * 根据tranFrom和tranCode获取traninfo信息
	 * @param tranForm
	 * @param tranCode
	 * @return
	 */
	public TransInfo getTransInfo(String tranForm,String tranCode) {
		return (TransInfo) data.get(tranForm+ConstantConfigField.MQ_QUEUE_SEPARATOR+tranCode);
	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		
		logger.info("start load "+tableName+" table data...");
		boolean res = false;
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				TransInfo ti = new TransInfo();
				ti.setChannel_diy(rs.getString("CHANNEL_DIY"));
				ti.setOvertime(rs.getString("OVERTIME"));
				ti.setTran_opr(rs.getString("TRAN_OPR"));
				ti.setTranCode(rs.getString("TRANCODE"));
				ti.setTrandesc(rs.getString("TRANDESC"));
				ti.setTranFrom(rs.getString("TRANFROM"));	
				ti.setTranType(rs.getString("TRAN_TYPE"));
				target.put(ti.getTranFrom()+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+ti.getTranCode(), ti);
			}			
			logger.info(tableName+" init succ data Size:"+target.size());
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init TRANS_INFO table data exception",e);
			throw new HandlerException("init TRANS_INFO table data exception");
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