package com.ztkx.cbpay.container.initload;

import java.sql.Connection;


/**
 * 
 * @ClassName: CommonLoadInitInterface
 * @Description:平台启动加载表数据
 * @author zhangxiaoyun
 * @date 2016年6月30日 下午2:09:24
 */
public interface CommonLoadInitInterface {
	
	public Connection loadBefore();
	
	public Connection getConnection();
	
	public boolean setConnection(Connection para);
	
	public void setRelaseConn(boolean isRelaseConn);
	
	public void loadAfter();
}
