package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.AppAddress;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class AppAddressData extends AbstractLoadInit{

	private Logger logger = Logger.getLogger(AppAddressData.class);
	private static AppAddressData instance = null;
	private AppAddressData() {
		data = new HashMap<String, List<AppAddress>>();
		tmpData = new HashMap<String , List<AppAddress>>();
		instance = this;
	}
	
	public static AppAddressData getInstance(){
		if(instance==null){
			instance = new AppAddressData();
		}
		return instance;
	}
	
	/**
	 * 获取接入地址
	 * @return
	 */
	public List<AppAddress> getInAddressList(){
		return (List<AppAddress>) data.get(ConstantConfigField.APP_ADDRESS_TYPE_IN);
	}
	/**
	 * 获取接出地址
	 * @return
	 */
	public List<AppAddress> getOutAddressList(){
		return (List<AppAddress>) data.get(ConstantConfigField.APP_ADDRESS_TYPE_OUT);
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		List<AppAddress> inList = new ArrayList<AppAddress>();
		List<AppAddress> outList = new ArrayList<AppAddress>();
		boolean res = false;
		
		String tableName = "APP_ADDRESS";
		logger.info("start insert "+tableName+" table data...");
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				AppAddress aa = new AppAddress();
				aa.setAppid(rs.getString("APPID"));
				aa.setAppstatus(rs.getString("APPSTATUS"));
				aa.setType(rs.getString("TYPE"));
				aa.setUrl(rs.getString("URL"));
				if(aa.getType().equals(ConstantConfigField.APP_ADDRESS_TYPE_IN)){
					inList.add(aa);
				}else if(aa.getType().equals(ConstantConfigField.APP_ADDRESS_TYPE_OUT)){
					outList.add(aa);
				}else{
					logger.error("appAddress type is error"+aa.getAppid()+" "+aa.getType());
				}
			}
			logger.info(tableName+" init succ inList Size:"+inList.size());
			logger.info(tableName+" init succ outList Size:"+outList.size());
			
			target.put(ConstantConfigField.APP_ADDRESS_TYPE_IN, inList);
			target.put(ConstantConfigField.APP_ADDRESS_TYPE_OUT, outList);
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init "+tableName+" table data exception",e);
			throw new HandlerException("init "+tableName+" table data exception");
		}
		return res;
	}
}
