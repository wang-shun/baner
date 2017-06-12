package com.ztkx.transplat.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.initload.AbstractLoadInit;
import com.ztkx.transplat.container.javabean.PlatParams;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;
import com.ztkx.transplat.platformutil.db.c3p0.DataSourceUtil;

/**
 * 加载系统参数表 PLAT_PARAMS
 * @author zhangxiaoyun
 * 2016年3月11日 下午3:16:20
 * <p>Company:ztkx</p>
 * update by tianguangzhao 20160711 实现InvokerExecutor接口
 */
public class PlatParamsData extends AbstractLoadInit implements InvokerExecutor{
	Logger logger = Logger.getLogger(PlatParamsData.class);
	static String tableName = "PLAT_PARAMS";
	private static PlatParamsData instance = null;

	public static PlatParamsData getInstance() {
		if (instance == null) {
			instance = new PlatParamsData();
		}
		return instance;
	}
	/**update by tianguangzhao 20160711 
	 * 新增初始化变量data和tmpData
	 */
	private PlatParamsData() {
		data = new HashMap<String, PlatParams>();
		tmpData = new HashMap<String, PlatParams>();
		instance = this;
	}
	/**
	 * 获取系统参数值
	 * @param valueName
	 * @return
	 * 2016年3月11日 下午3:15:02
	 * @author zhangxiaoyun
	 */
	public String getParamValue(String valueName){
		return ((PlatParams)data.get(valueName)).getParamsValue();
	}

	/**
	 *根据参数名获取对应的一条记录 
	 * @param valueName
	 * @return
	 */
	public PlatParams getParam(String valueName){
		return (PlatParams)data.get(valueName);
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		boolean res = false;
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName +" where USEFLAG='1' ";
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				PlatParams pp = new PlatParams();
				pp.setParamsName(rs.getString("PARAMNAME"));
				pp.setParamsValue(rs.getString("PARAMVALUE"));
				pp.setUseFlag(rs.getString("USEFLAG"));
				pp.setParamDesc(rs.getString("PARAMDESC"));
				target.put(pp.getParamsName(), pp);
			}			
			logger.info(tableName+" init succ data Size:"+target.size());
			res = true;
		} catch (SQLException e) {
			res = false;
			logger.error("init "+tableName +" table data exception",e);
			throw new HandlerException("init "+tableName +" table data exception");
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