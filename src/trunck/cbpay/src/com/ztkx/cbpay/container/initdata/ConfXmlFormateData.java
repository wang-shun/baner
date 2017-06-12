package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.ConfXmlFormate;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;


/**
 * 
 * tableName = CONF_XML_FORMATE
 *
 */
public class ConfXmlFormateData extends AbstractLoadInit implements InvokerExecutor{

	Logger logger = Logger.getLogger(ConfXmlFormateData.class);
	static String tableName = "CONF_XML_FORMATE";
	private static ConfXmlFormateData instance = null;

	public static ConfXmlFormateData getInstance() {
		if (instance == null) {
			instance = new ConfXmlFormateData();
		}
		return instance;
	}
	private ConfXmlFormateData() {
		data = new HashMap<String,List<ConfXmlFormate>>();
		tmpData = new HashMap<String,List<ConfXmlFormate>>();
		instance = this;
	}
	
	public List<ConfXmlFormate> getList() {
		return (List<ConfXmlFormate>) data.get(tableName);
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		
		List<ConfXmlFormate> list = new ArrayList<ConfXmlFormate>();
		logger.info("start load "+tableName+" table data...");
		String owner = null;
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN)){
			owner=ConstantConfigField.CONTAINER_NAME_IN;
		}else if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT)){
			owner=ConstantConfigField.CONTAINER_NAME_OUT;
		}
		
		String sql = "select * from "+tableName +" where owner='"+owner+"' ";
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ConfXmlFormate cxf = new ConfXmlFormate();
				cxf.setPath(rs.getString("PATH"));
				cxf.setSystemid(rs.getString("SYSTEMID"));
				cxf.setTrancode(rs.getString("TRANCODE"));
				cxf.setType(rs.getString("TYPE"));
				cxf.setOwner(rs.getString("OWNER"));
				list.add(cxf);
			}
			target.put(tableName, list);
			logger.info(tableName+" init succ data Size:"+list.size());
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init CONF_XML_FORMATE table data exception",e);
			throw new HandlerException("init CONF_XML_FORMATE table data exception");
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
