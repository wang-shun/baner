package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="head")
@XmlType(propOrder = { "tranCode","merchantNo","orderId", "tranDate","tranTime", "bussFlowNo","respCode", "respMsg"})
public class CashierHead {
	private String tranCode="";
	private String merchantNo="";
	private String orderId="";
	private String tranDate="";
	private String tranTime="";
	private String bussFlowNo="";
	private String respCode="";
	private String respMsg="";
	
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getBussFlowNo() {
		return bussFlowNo;
	}
	public void setBussFlowNo(String bussFlowNo) {
		this.bussFlowNo = bussFlowNo;
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
