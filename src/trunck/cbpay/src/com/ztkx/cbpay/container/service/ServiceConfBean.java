package com.ztkx.cbpay.container.service;

public class ServiceConfBean {
	private String id;
	private String type;	//1.baseservice 基础服务  2.businessservice 业务服务   3.protocolservice
	private String impl;

	public ServiceConfBean(String id, String type, String impl) {
		this.id = id;
		this.type = type;
		this.impl = impl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImpl() {
		return impl;
	}

	public void setImpl(String impl) {
		this.impl = impl;
	}

	@Override
	public String toString() {
		return "ServiceConfBean [id=" + id + ", type=" + type + ", impl="
				+ impl + "]";
	}
}
