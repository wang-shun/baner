package com.ztkx.cbpay.container.initdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initload.AbstractLoadInit;
import com.ztkx.cbpay.container.javabean.ConfOpration;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.db.c3p0.DataSourceUtil;

public class ConfOprationData extends AbstractLoadInit implements InvokerExecutor{

	Logger logger = Logger.getLogger(ConfOprationData.class);
	private static ConfOprationData instance = null;
	
	private ConfOprationData(){
		data = new HashMap<String,List<ConfOpration>>();
		tmpData = new HashMap<String,List<ConfOpration>>();
		instance = this;
	}
	
	public static ConfOprationData getInstance() {
		if (instance == null) {
			instance = new ConfOprationData();
		}
		return instance;
	}
	
	/**
	 * 根据trancode和tranfrom获取CONF_OPRATION
	 * @param tranCode
	 * @param opr_code
	 * @return
	 */
	public ConfOpration getConfOpration(String tranCode,String opr_code){
		List<ConfOpration> list = (List<ConfOpration>) data.get(tranCode+ConstantConfigField.MQ_QUEUE_SEPARATOR+opr_code);
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据trancode、tranfrom、serverid 获取CONF_OPRATION
	 * @param tranCode
	 * @param opr_code
	 * @param serverid
	 * @return
	 * 2016年3月28日 下午3:25:49
	 * @author zhangxiaoyun
	 */
	public ConfOpration getConfOpration(String tranCode,String opr_code,String serverid){
		List<ConfOpration> list = (List<ConfOpration>) data.get(tranCode+ConstantConfigField.MQ_QUEUE_SEPARATOR+opr_code);
		if(list!=null && list.size()>1){
			for (ConfOpration conf : list) {
				if(conf.getServerid() == serverid){
					return conf;
				}
			}
		}else if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public boolean extracted(Map target) throws HandlerException {
		String tableName = "CONF_OPRATION";
		logger.info("start load "+tableName+" table data...");
		String sql = "select * from "+tableName;
		if(logger.isDebugEnabled()){
			logger.debug(tableName+" init sql["+sql+"]");
		}
		stmt = DataSourceUtil.getStatement(connection);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ConfOpration c = new ConfOpration();
				c.setTranCode(rs.getString("trancode"));
				c.setOpr_code(rs.getString("OPR_CODE"));
				c.setServer_diy(rs.getString("SERVER_DIY"));
				c.setServer_overtime(rs.getString("SERVER_OVERTIME"));
				c.setServercode(rs.getString("SERVERCODE"));
				c.setServerid(rs.getString("SERVERID"));
				String key = c.getTranCode()+ConstantConfigField.MQ_QUEUE_SEPARATOR+c.getOpr_code();
				if(target.containsKey(key)){
					((List<ConfOpration>) target.get(key)).add(c);
				}else{
					List<ConfOpration> tmplist = new ArrayList<ConfOpration>();
					tmplist.add(c);
					target.put(key, tmplist);
				}
			}
			logger.info(tableName+" init succ data Size:"+target.size());
		} catch (SQLException e) {
			logger.error("init CONF_OPRATION table data exception",e);
		}
		return true;
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
	
