package com.ztkx.cbpay.business.bean;

/**
 * 支付流水表实体类
 * 
 * @author tianguangzhao
 *
 */
public class BPaymentFlow {
	//对账状态
	public static final String CHECKING_INIT = "00"; // 对账初始化
	public static final String CHECKING_PROCESSING = "01"; // 对账进中
	public static final String CHECKING_SUCCESS = "02"; // 对账成功
	public static final String CHECKING_FAILED = "03"; // 对账失败
	// 订单支付状态
	public static final String TRADING_INIT = "00"; // 交易支付初始化
	public static final String TRADING_PROCESSING = "01"; // 支付处理中
	public static final String TRADING_SUCCESS = "02"; // 支付成功
	public static final String TRADING_FAILED = "03"; // 支付失败
	
	private String payorderid; // 支付订单号
	private String trandate; // 订单建立日期
	private String trantime; // 订单建立时间
	private String txnamt; // 支付金额
	private String currency; // 支付币种
	private String merchantid; // 商户号
	private String orderid; // 商户订单号
	private String orderdate; // 商户订单日期
	private String ordertime; // 商户订单时间
	private String channel; // 订单支付渠道
	private String paytime; // 订单支付渠道日期
	private String paystatus; // 支付状态
	private String purchaserid; // 购买者标识
	private String checkingstatus; // 对账状态
	private String paycard; // 购买者卡号
	private String paydate; // 支付完成日期
	private String tmsmp; // 时间戳

	public String getPayorderid() {
		return payorderid;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public void setPayorderid(String payorderid) {
		this.payorderid = payorderid;
	}

	public String getTrandate() {
		return trandate;
	}

	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}

	public String getTrantime() {
		return trantime;
	}

	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}

	public String getTxnamt() {
		return txnamt;
	}

	public void setTxnamt(String txnamt) {
		this.txnamt = txnamt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String merchantordertime) {
		this.ordertime = merchantordertime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getPurchaserid() {
		return purchaserid;
	}

	public void setPurchaserid(String purchaserid) {
		this.purchaserid = purchaserid;
	}

	public String getCheckingstatus() {
		return checkingstatus;
	}

	public void setCheckingstatus(String checkingstatus) {
		this.checkingstatus = checkingstatus;
	}

	public String getTmsmp() {
		return tmsmp;
	}

	public void setTmsmp(String tmsmp) {
		this.tmsmp = tmsmp;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getPaycard() {
		return paycard;
	}

	public void setPaycard(String paycard) {
		this.paycard = paycard;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	@Override
	public String toString() {
		return "BPaymentFlow [payorderid=" + payorderid + ", trandate="
				+ trandate + ", trantime=" + trantime + ", txnamt=" + txnamt
				+ ", currency=" + currency + ", merchantid=" + merchantid
				+ ", orderid=" + orderid + ", orderdate=" + orderdate
				+ ", ordertime=" + ordertime + ", channel=" + channel
				+ ", paytime=" + paytime + ", paystatus=" + paystatus
				+ ", purchaserid=" + purchaserid + ", checkingstatus="
				+ checkingstatus + ", paycard=" + paycard + ", paydate="
				+ paydate + ", tmsmp=" + tmsmp + "]";
	}

}
