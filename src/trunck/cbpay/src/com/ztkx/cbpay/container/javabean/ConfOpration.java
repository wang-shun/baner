package com.ztkx.cbpay.container.javabean;

public class ConfOpration {
	private String tranCode;
    private String opr_code;

	private String serverid;

	private String servercode;

	private String server_diy;

	private String server_overtime;
	
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getServercode() {
		return servercode;
	}

	public void setServercode(String servercode) {
		this.servercode = servercode;
	}

	public String getOpr_code() {
		return opr_code;
	}

	public void setOpr_code(String opr_code) {
		this.opr_code = opr_code;
	}

	public String getServer_diy() {
		return server_diy;
	}

	public void setServer_diy(String server_diy) {
		this.server_diy = server_diy;
	}

	public String getServer_overtime() {
		return server_overtime;
	}

	public void setServer_overtime(String server_overtime) {
		this.server_overtime = server_overtime;
	}
}