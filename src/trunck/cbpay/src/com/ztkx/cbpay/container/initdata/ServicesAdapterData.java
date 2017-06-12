package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.ProcessService;
import com.ztkx.cbpay.container.javabean.ServicesAdapter;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class ServicesAdapterData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(ServicesAdapterData.class);
	String tableName = "SERVICES_ADAPTER";
	private static ServicesAdapterData instance = null;

	public static ServicesAdapterData getInstance() {
		if (instance == null) {
			instance = new ServicesAdapterData();
		}
		return instance;
	}

	private ServicesAdapterData() {
		instance = this;
	}
	/**
	 * 根据系统id和系统交易嘛获取serviceAdapter
	 * @param systemid
	 * @param trancode
	 * @return
	 */
	public ServicesAdapter getServiceAdapter(String systemid,String trancode){
		return (ServicesAdapter) data.get(systemid+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+trancode);
	}

	@Override
	public boolean extracted(Map target) throws HandlerException {
		logger.info("start load "+tableName+" table data...");
		String sql =  "select * from "+tableName;
		boolean res = false;
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN)){
			sql = sql + " where owner = '"+ConstantConfigField.CONTAINER_NAME_IN+"'";
		}else{
			sql = sql + " where owner = '"+ConstantConfigField.CONTAINER_NAME_OUT+"'";
		}
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ServicesAdapter sa = new ServicesAdapter();
				sa.setOwner(rs.getString("OWNER"));
				sa.setProcesslist(rs.getString("PROCESSLIST"));
				sa.setSystemid(rs.getString("SYSTEMID"));
				sa.setTrancode(rs.getString("TRANCODE"));
				sa.setProcessservice(new ArrayList<ProcessService>());
				String[] processArr = sa.getProcesslist().split(ConstantConfigField.TABLE_VALUE_SEPARATOR_COMMA);
				for(String process:processArr){
					if(StringUtils.isNotBlank(process)){
						String[] serviceArr = process.split("\\"+ConstantConfigField.TABLE_VALUE_SEPARATOR_BAR);
						if(serviceArr.length!=3){
							logger.error("SYSTEMID:"+sa.getSystemid()+",TRANCODE:"+sa.getTrancode()+" is error,processlist'value is error");
							continue;
						}
						ProcessService ps = new ProcessService();
						ps.setServiceid(serviceArr[0]);
						ps.setServicetype(serviceArr[1]);
						ps.setMustrun(Boolean.parseBoolean(serviceArr[2]));
						sa.addProcessService(ps);
					}
				}
				target.put(rs.getString("SYSTEMID")+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+rs.getString("TRANCODE"),sa);
			}
			res = true;
			logger.info(tableName+" init succ data Size:"+target.size());
		} catch (SQLException e) {
			res = false;
			logger.error("init SERVICES_ADAPTER table data exception",e);
			throw new HandlerException("init SERVICES_ADAPTER table data exception");
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