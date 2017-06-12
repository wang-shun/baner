package cn.msec.cbpay.entity;

public class PayBatBean {
	private String orderCount;
	private String orderMount;
	private String merchantid;
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
	
}
