package com.ztkx.cbpay.business.bean;

import java.sql.Timestamp;

/**
 * 对账差错表
 * @author zhangxiaoyun
 */
public class BCheckErrorList {
	
	// 对账渠道
	public static final String CHANNEL_UMB = "0"; // 和宝易互通对账
	
	//差错类型
	public static final String ERROR_STATUS_DIFFERENT ="0";//状态不符
	public static final String ERROR_PLA_EXTRA ="1";//平台多账
	public static final String ERROR_CHANNEL_EXTRA ="2";//合作渠道多账
	
	//处理状态
	public static final String DEAL_WAITING="0";//未处理
	public static final String DEAL_FINISH="1";//已处理
	public static final String DEAL_CANCEL="2";//差错取消
	
	private String accountdate;	//业务日期
	private String chkchnl;	//对账渠道
	private String orderid;			//订单号
	private String chnljnlno;		//渠道流水号
	private String chkerrtyp;		//错误类型
	private String chkerrdealtyp;	//处理方式 字段预留
	private String chkerrdealsts;	//错误处理标志
	private String errcancelmark;	//错误取消标志
	private String dealdate;
	private Timestamp tmsmp;
	private String trandate;
	
	public String getAccountdate() {
		return accountdate;
	}
	public void setAccountdate(String accountdate) {
		this.accountdate = accountdate;
	}
	public String getChkchnl() {
		return chkchnl;
	}
	public void setChkchnl(String chkchnl) {
		this.chkchnl = chkchnl;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getChnljnlno() {
		return chnljnlno;
	}
	public void setChnljnlno(String chnljnlno) {
		this.chnljnlno = chnljnlno;
	}
	public String getChkerrtyp() {
		return chkerrtyp;
	}
	public void setChkerrtyp(String chkerrtyp) {
		this.chkerrtyp = chkerrtyp;
	}
	public String getChkerrdealtyp() {
		return chkerrdealtyp;
	}
	public void setChkerrdealtyp(String chkerrdealtyp) {
		this.chkerrdealtyp = chkerrdealtyp;
	}
	public String getChkerrdealsts() {
		return chkerrdealsts;
	}
	public void setChkerrdealsts(String chkerrdealsts) {
		this.chkerrdealsts = chkerrdealsts;
	}
	public String getErrcancelmark() {
		return errcancelmark;
	}
	public void setErrcancelmark(String errcancelmark) {
		this.errcancelmark = errcancelmark;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public Timestamp getTmsmp() {
		return tmsmp;
	}
	public void setTmsmp(Timestamp tmsmp) {
		this.tmsmp = tmsmp;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
}
