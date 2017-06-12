package com.ztkx.transplat.container.javabean;

public class MerchantCurrencyMap extends MerchantCurrencyMapKey {
    private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	
}