package com.ztkx.cbpay.container.javabean;

import java.math.BigDecimal;

public class TranRouter extends TranRouterKey {
    private String max_amt;

	private String min_amt;

	private String currency_type;

	private String rate_policy;

	private String tran_opr;

	private String serverid;
	
	private String servercode;
	
	private BigDecimal MinAmt;
	
	private BigDecimal MaxAmt;
	
	private String rateChannelId;

	public String getMax_amt() {
		return max_amt;
	}

	public void setMax_amt(String max_amt) {
		this.max_amt = max_amt;
	}

	public String getMin_amt() {
		return min_amt;
	}

	public void setMin_amt(String min_amt) {
		this.min_amt = min_amt;
	}

	public String getCurrency_type() {
		return currency_type;
	}

	public void setCurrency_type(String currency_type) {
		this.currency_type = currency_type;
	}

	public String getRate_policy() {
		return rate_policy;
	}

	public void setRate_policy(String rate_policy) {
		this.rate_policy = rate_policy;
	}

	public String getTran_opr() {
		return tran_opr;
	}

	public void setTran_opr(String tran_opr) {
		this.tran_opr = tran_opr;
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

	public BigDecimal getMinAmt() {
		return MinAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		MinAmt = minAmt;
	}

	public BigDecimal getMaxAmt() {
		return MaxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		MaxAmt = maxAmt;
	}

	public String getRateChannelId() {
		return rateChannelId;
	}

	public void setRateChannelId(String rateChannelId) {
		this.rateChannelId = rateChannelId;
	}
	
	
	
}