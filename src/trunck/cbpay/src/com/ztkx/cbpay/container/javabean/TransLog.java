package com.ztkx.cbpay.container.javabean;

public class TransLog {

	private String flowNo;      //平台流水号
	private String tranDate;    //平台日期
	private String tranTime;    //平台时间
	private String tranCode;    //交易发起方交易码
	private String tranFrom;    //交易发起方编号
	private String serverCode;  //服务方交易码
	private String serverId;    //服务方编号号
	private String serviceResponseCode; //服务方响应码
	private String serviceResponseDesc; //服务方响应码描述
	private String platResponseCode;   //平台相应码
	
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTranFrom() {
		return tranFrom;
	}
	public void setTranFrom(String tranFrom) {
		this.tranFrom = tranFrom;
	}
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getServiceResponseCode() {
		return serviceResponseCode;
	}
	public void setServiceResponseCode(String serviceResponseCode) {
		this.serviceResponseCode = serviceResponseCode;
	}
	public String getPlatResponseCode() {
		return platResponseCode;
	}
	public void setPlatResponseCode(String platResponseCode) {
		this.platResponseCode = platResponseCode;
	}
	public String getServiceResponseDesc() {
		return serviceResponseDesc;
	}
	public void setServiceResponseDesc(String serviceResponseDesc) {
		this.serviceResponseDesc = serviceResponseDesc;
	}
	@Override
	public String toString() {
		return "TransLog [flowNo=" + flowNo + ", tranDate=" + tranDate
				+ ", tranTime=" + tranTime + ", tranCode=" + tranCode
				+ ", tranFrom=" + tranFrom + ", serverCode=" + serverCode
				+ ", serverId=" + serverId + ", serviceResponseCode="
				+ serviceResponseCode + ", serviceResponseDesc="
				+ serviceResponseDesc + ", platResponseCode="
				+ platResponseCode + "]";
	}

}
