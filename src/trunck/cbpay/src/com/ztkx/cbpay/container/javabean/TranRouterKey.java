package com.ztkx.cbpay.container.javabean;

public class TranRouterKey {
    private String routekey;

	private String type;

	

	public String getRoutekey() {
		return routekey;
	}

	public void setRoutekey(String routekey) {
		this.routekey = routekey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

}