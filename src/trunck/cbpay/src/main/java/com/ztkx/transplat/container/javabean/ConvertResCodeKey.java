package com.ztkx.transplat.container.javabean;

public class ConvertResCodeKey {
    private String responsecode;

	private String serverid;

	public String getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode == null ? null : responsecode.trim();
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid == null ? null : serverid.trim();
	}

}