package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.PlatDateParam;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

/**
 * 系统业务日期参数表数据
 * @author zhangxiaoyun
 * 2016年3月11日 下午4:13:07
 * <p>Company:ztkx</p>
 */
public class PlatDateParamData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(PlatDateParamData.class);
	static String tableName = "PLAT_DATE_PARAM";
	private static PlatDateParamData instance = null;
	private ReadWriteLock lock = new ReentrantReadWriteLock(false);

	public static PlatDateParamData getInstance() {
		if (instance == null) {
			instance = new PlatDateParamData();
		}
		return instance;
	}

	private PlatDateParamData() {
		instance = this;
	}
	/**
	 * 获取平台日期
	 * @return
	 * 2016年3月11日 下午3:10:27
	 * @author zhangxiaoyun
	 */
	public String getPlatDate(){
		lock.readLock().lock();
		String date = ((PlatDateParam)data.get(tableName)).getAcdt();
		lock.readLock().unlock();
		return date;
	}
	/**
	 * 获取平台日期
	 * @return
	 * 2016年3月11日 下午3:10:27
	 * @author sunyoushan
	 */
	public String getLastPlatDate(){
		return ((PlatDateParam)data.get(tableName)).getLastAcdt();
	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		
		boolean res = false;
		PlatDateParam platDate = new PlatDateParam();
		
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				platDate.setSysName(rs.getString("SYSNAME"));
				platDate.setAcdt(rs.getString("ACDT"));
				platDate.setLastAcdt(rs.getString("LASTACDT"));
				platDate.setNextAcdt(rs.getString("NEXTACDT"));
				platDate.setSysStatus(rs.getString("SYSSTATUS"));
			}
			logger.info(tableName+" init succ data "+platDate);
			target.put(tableName, platDate);
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init ["+tableName+"] table data exception",e);
			throw new HandlerException("init ["+tableName+"] table data exception");
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
		lock.writeLock().lock();
		this.reload();
		lock.writeLock().unlock();
	}
	
}