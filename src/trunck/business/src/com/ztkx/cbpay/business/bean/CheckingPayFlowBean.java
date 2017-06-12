package com.ztkx.cbpay.business.bean;

import java.math.BigDecimal;

/**
 * 该bean用于对账，根据hashcode判断，数据是否完全相同
 * 
 * @author uisfte
 *
 */
public class CheckingPayFlowBean {
	// 交易日期
	private String tranDate;
	// 订单号
	private String orderid;
	// 宝易互通流水号
	private String payjnlno;
	// 交易金额
	private BigDecimal txnAmt;
	// 币种
	private String currency;
	// 交易状态
	private String status;
	// 账号
	private String accountNo;
	
	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPayjnlno() {
		return payjnlno;
	}

	public void setPayjnlno(String payjnlno) {
		this.payjnlno = payjnlno;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingPayFlowBean other = (CheckingPayFlowBean) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (payjnlno == null) {
			if (other.payjnlno != null)
				return false;
		} else if (!payjnlno.equals(other.payjnlno))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (tranDate == null) {
			if (other.tranDate != null)
				return false;
		} else if (!tranDate.equals(other.tranDate))
			return false;
		if (txnAmt == null) {
			if (other.txnAmt != null)
				return false;
		} else if (txnAmt.compareTo(other.txnAmt) != 0)
			return false;
		return true;
	}

	public boolean equalsWithOutStatus(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingPayFlowBean other = (CheckingPayFlowBean) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (payjnlno == null) {
			if (other.payjnlno != null)
				return false;
		} else if (!payjnlno.equals(other.payjnlno))
			return false;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (tranDate == null) {
			if (other.tranDate != null)
				return false;
		} else if (!tranDate.equals(other.tranDate))
			return false;
		if (txnAmt == null) {
			if (other.txnAmt != null)
				return false;
		} else if (txnAmt.compareTo(other.txnAmt) != 0)
			return false;
		return true;
	}
	
	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Override
	public String toString() {
		return "CheckingPayFlowBean [tranDate=" + tranDate + ", orderid="
				+ orderid + ", payjnlno=" + payjnlno + ", txnAmt=" + txnAmt
				+ ", currency=" + currency + ", status=" + status
				+ ", accountNo=" + accountNo + "]";
	}


}
