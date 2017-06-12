package com.ztkx.cbpay.business.bean;

import java.sql.Timestamp;

/**
 * 宝易互通对账记录表实体类
 * 
 * @author tianguaqngzhao
 *
 */
public class BCheckStatus {
	
	//设置划转交易处理状态
	public static final String STATUS_INIT="0";
	public static final String STATUS_SUCCESS="1";
	public static final String STATUS_ERROR="2";
	//是指对账类型
	public static final String CHECK_PAY_FLOW="0"; //交易流水对账
	public static final String CHECK_TRANS_FLOW="1";//账户划转对账
	public static final String CHECK_GH="2";	//购汇对账
	public static final String CHECK_JH="3";	//结汇对账
	//对账渠道
	public static final String CHANNEL_UMB="0";	//和宝易互通对账
	public static final String CHANNEL_PAB="1";	//和宝易互通对账
	//是否处理（失败的对账）
	public static final String HANDLED_FALSE="0";	//未处理
	public static final String HANDLED_TRUE="1";	//已处理
	
	
	private String jnlno;
	private String trandate;
	private String accountdate;
	private String checktype;
	private String status;
	//update by tianguangzhao 2016/5/4 按照目前对账方式已经不需要文件名称，暂时保留，为了后续优化对账
	private String filename;
	private String trantime;
	private Timestamp tmsmp;
	private String checknl;
	//update by tianguangzhao 20160613 是否已处理标识
	private String isHandled; 

	public String getJnlno() {
		return jnlno;
	}

	public void setJnlno(String jnlno) {
		this.jnlno = jnlno;
	}

	public String getTrandate() {
		return trandate;
	}

	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}

	public String getAccountdate() {
		return accountdate;
	}

	public void setAccountdate(String accountdate) {
		this.accountdate = accountdate;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrantime() {
		return trantime;
	}

	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}
	public Timestamp getTmsmp() {
		return tmsmp;
	}

	public void setTmsmp(Timestamp tmsmp) {
		this.tmsmp = tmsmp;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	

	public String getChecknl() {
		return checknl;
	}

	public void setChecknl(String checknl) {
		this.checknl = checknl;
	}

	public String getIsHandled() {
		return isHandled;
	}

	public void setIsHandled(String isHandled) {
		this.isHandled = isHandled;
	}

	@Override
	public String toString() {
		return "BCheckStatus [jnlno=" + jnlno + ", trandate=" + trandate
				+ ", accountdate=" + accountdate + ", checktype=" + checktype
				+ ", status=" + status + ", filename=" + filename
				+ ", trantime=" + trantime + ", tmsmp=" + tmsmp + ", checknl="
				+ checknl + ", isHandled=" + isHandled + "]";
	}

}
