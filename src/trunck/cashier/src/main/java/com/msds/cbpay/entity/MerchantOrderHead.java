package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder = {"msgType","chanId","merchantNo","clientTime","serverTime",
		"tranFlow","tranCode","clientIP","accdt","respCode","respMsg"})
public class MerchantOrderHead {
	private String msgType="";
	private String chanId="";
	private String merchantNo="";
	private String clientTime="";
	private String serverTime="";
	private String tranFlow="";
	private String tranCode="";
	private String clientIP="";
	private String accdt="";
	private String respCode="";
	private String respMsg="";
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getChanId() {
		return chanId;
	}
	public void setChanId(String chanId) {
		this.chanId = chanId;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getClientTime() {
		return clientTime;
	}
	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public String getTranFlow() {
		return tranFlow;
	}
	public void setTranFlow(String tranFlow) {
		this.tranFlow = tranFlow;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	public String getAccdt() {
		return accdt;
	}
	public void setAccdt(String accdt) {
		this.accdt = accdt;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
