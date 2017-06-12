package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.Platrescodedesc;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;
/**
 * 此类用于操作平台错误码表
 * @author tianguangzhao
 *
 */
public class PlatrescodedescData extends AbstractLoadInit implements InvokerExecutor {
	private static Logger logger = Logger.getLogger(PlatrescodedescData.class);
	private static PlatrescodedescData instance = null;

	public static PlatrescodedescData getInstance() {
		if (instance == null) {
			instance = new PlatrescodedescData();
		}
		return instance;
	}
	/**
	 * 初始化相关
	 */
	private PlatrescodedescData(){
		data = new HashMap<String,Platrescodedesc>();
		tmpData = new HashMap<String,Platrescodedesc>();
		instance = this;
	}
	/**
	 * 根据错误码获取错误信息
	 * @param errorcode 错误码
	 * @return
	 */
	public  Platrescodedesc getPaltrescodedesc(String errorcode){
		return  (Platrescodedesc) data.get(errorcode);
	}
	
	/**
	 * 执行查询语句将数据库中的数据加载到内存
	 * 2016年6月30日 下午5:04:21
	 * @author zhangxiaoyun
	 * @param target
	 * @return
	 * @throws HandlerException
	 * @return boolean
	 */
	@Override
	public boolean extracted(Map target) throws HandlerException {
		String tableName = "platrescodedesc";
		boolean res = false;
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName ;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Platrescodedesc platrescodedesc = new Platrescodedesc();
				platrescodedesc.setErrorcode(rs.getString("ERRORCODE"));
				platrescodedesc.setErrordesc(rs.getString("ERRORDESC"));
				//将信息封装到map中
				target.put(platrescodedesc.getErrorcode(), platrescodedesc);
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
