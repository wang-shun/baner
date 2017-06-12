package com.ztkx.cbpay.container.javabean;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 系统业务日期表
 * @author zhangxiaoyun
 * 2016年3月11日 下午2:36:24
 * <p>Company:ztkx</p>
 */
public class PlatDateParam {
	private String sysName;			//系统名称
	private volatile String acdt;			//会计日期
	private String lastAcdt;		//上一会计日期
	private String nextAcdt;		//下一会计日期
	private String sysStatus;		//1：启运 	0：停运
	ReentrantReadWriteLock rwl = null;
	
	public PlatDateParam() {
		rwl = new ReentrantReadWriteLock();
	}

	public String getSysName() {
		return sysName;
	}
	
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	/**
	 * 读取平台日期
	 * @return
	 * 2016年3月11日 下午3:09:25
	 * @author zhangxiaoyun
	 */
	public String getAcdt() {
		rwl.readLock().lock();
			String platDate = acdt;
		rwl.readLock().unlock();
		return platDate;
	}
	/**
	 * 写入平台日期
	 * @param acdt
	 * 2016年3月11日 下午3:09:34
	 * @author zhangxiaoyun
	 */
	public void setAcdt(String acdt) {
		rwl.writeLock().lock();
		this.acdt = acdt;
		rwl.writeLock().unlock();
	}
	public String getLastAcdt() {
		return lastAcdt;
	}
	public void setLastAcdt(String lastAcdt) {
		this.lastAcdt = lastAcdt;
	}
	public String getNextAcdt() {
		return nextAcdt;
	}
	public void setNextAcdt(String nextAcdt) {
		this.nextAcdt = nextAcdt;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	@Override
	public String toString() {
		return "[sysName=" + sysName + ", acdt=" + acdt
				+ ", lastAcdt=" + lastAcdt + ", nextAcdt=" + nextAcdt
				+ ", sysStatus=" + sysStatus + "]";
	}
	
	
}
