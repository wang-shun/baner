package com.ztkx.cbpay.container.javabean;

public class MerchantCurrencyMapKey {
    private String currency_code;

	private String merchantid;

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid == null ? null : merchantid.trim();
	}

}