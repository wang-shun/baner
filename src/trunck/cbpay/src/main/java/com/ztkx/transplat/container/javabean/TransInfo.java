package com.ztkx.transplat.container.javabean;

public class TransInfo{
    private String trandesc;
	private String tran_opr;
	private String channel_diy;
	private String overtime;
	private String tranFrom;
	private String tranCode;
	private String tranType;		//交易类型  1.同步交易    2.异步交易  
	
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getTranFrom() {
		return tranFrom;
	}
	public void setTranFrom(String tranFrom) {
		this.tranFrom = tranFrom;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTrandesc() {
		return trandesc;
	}
	public void setTrandesc(String trandesc) {
		this.trandesc = trandesc;
	}
	public String getTran_opr() {
		return tran_opr;
	}
	public void setTran_opr(String tran_opr) {
		this.tran_opr = tran_opr;
	}

	public String getChannel_diy() {
		return channel_diy;
	}

	public void setChannel_diy(String channel_diy) {
		this.channel_diy = channel_diy;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
}