package com.ztkx.cbpay.container.javabean;

public class MerchantCheckStatus {
	
	private String merchantId;
	private String check_date;
	private String check_time;
	private String bus_date;
	private String status;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCheck_date() {
		return check_date;
	}
	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getBus_date() {
		return bus_date;
	}
	public void setBus_date(String bus_date) {
		this.bus_date = bus_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
