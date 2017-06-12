package com.ztkx.cbpay.container.initload;

public class InitialBean {
	private String id;
	private String impl;
	
	public InitialBean(String id, String impl) {
		this.id = id;
		this.impl = impl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	@Override
	public String toString() {
		return "LoaderBean [id=" + id + ", impl=" + impl+ "]";
	}
}
