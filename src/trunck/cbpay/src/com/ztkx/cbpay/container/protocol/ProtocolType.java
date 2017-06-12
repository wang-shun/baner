package com.ztkx.cbpay.container.protocol;

public enum ProtocolType {
	HTTP("http"),	
	HTTPS("https"),
	TCP("tcp"),
	JMS("jms");
	
	private String code;
	
	private ProtocolType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
