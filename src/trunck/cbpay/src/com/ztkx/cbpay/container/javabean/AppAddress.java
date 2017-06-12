package com.ztkx.cbpay.container.javabean;

public class AppAddress {

	private String appid;
	private String type;
	private String url;
	private String appstatus;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAppstatus() {
		return appstatus;
	}
	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}
	@Override
	public String toString() {
		return "appid-" + appid + "__type-" + type + "__url-" + url
				+ "__appstatus-" + appstatus;
	}
	
	
}
