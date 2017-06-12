package com.cashier.test.bean;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"pageReturnUrl","purchaserId","createDate","backParam"})
public class MerchantTiedCardBody {
	private String pageReturnUrl="";
	private String purchaserId="";
	private String createDate="";
	private String backParam="";
	public String getPageReturnUrl() {
		return pageReturnUrl;
	}
	public void setPageReturnUrl(String pageReturnUrl) {
		this.pageReturnUrl = pageReturnUrl;
	}
	public String getPurchaserId() {
		return purchaserId;
	}
	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBackParam() {
		return backParam;
	}
	public void setBackParam(String backParam) {
		this.backParam = backParam;
	}
}
