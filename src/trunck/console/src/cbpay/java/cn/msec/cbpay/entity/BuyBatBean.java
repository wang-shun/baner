package cn.msec.cbpay.entity;

public class BuyBatBean {
	private String orderCount;
	private String orderMount;
	private String merchantid;
	private String currency;
	private String cause;
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public String getOrderMount() {
		return orderMount;
	}
	public void setOrderMount(String orderMount) {
		this.orderMount = orderMount;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
