package com.ztkx.transplat.container.javabean;

public class ServicesAdapterKey {
    private String systemid;

	private String trancode;

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode == null ? null : trancode.trim();
	}
}