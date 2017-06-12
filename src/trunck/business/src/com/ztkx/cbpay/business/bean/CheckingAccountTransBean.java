package com.ztkx.cbpay.business.bean;

import java.math.BigDecimal;

/**
 * 账户划转流水对账实体类，用于存放对账时所需的信息
 * 
 * @author tianguangzhao
 *
 */
public class CheckingAccountTransBean {
	private String txndate; // 交易日期
	private String accoutno; // 交易账户
	private String jnlno; // 划转子流水号
	private String transferflg; // 借贷标志
	private String currency; // 币种
	private String transferStatus; // 划转状态
	private String accountname; // 户名
	private BigDecimal txnAmt;// 交易金额
	private String transBatchNo; // 划转批次号
	private String businessType; // 业务类型， 现在未用到，暂时保留
	
	public String getTxndate() {
		return txndate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public String getAccoutno() {
		return accoutno;
	}

	public void setAccoutno(String accoutno) {
		this.accoutno = accoutno;
	}

	public String getJnlno() {
		return jnlno;
	}

	public void setJnlno(String jnlno) {
		this.jnlno = jnlno;
	}

	public String getTransferflg() {
		return transferflg;
	}

	public void setTransferflg(String transferflg) {
		this.transferflg = transferflg;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingAccountTransBean other = (CheckingAccountTransBean) obj;
		if (accountname == null) {
			if (other.accountname != null)
				return false;
		} else if (!accountname.equals(other.accountname))
			return false;
		if (accoutno == null) {
			if (other.accoutno != null)
				return false;
		} else if (!accoutno.equals(other.accoutno))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (jnlno == null) {
			if (other.jnlno != null)
				return false;
		} else if (!jnlno.equals(other.jnlno))
			return false;
		if (transferStatus == null) {
			if (other.transferStatus != null)
				return false;
		} else if (!transferStatus.equals(other.transferStatus))
			return false;
		if (transferflg == null) {
			if (other.transferflg != null)
				return false;
		} else if (!transferflg.equals(other.transferflg))
			return false;
		if (txnAmt == null) {
			if (other.txnAmt != null)
				return false;
		} else if (txnAmt.compareTo(other.txnAmt) != 0)
			return false;
		if (txndate == null) {
			if (other.txndate != null)
				return false;
		} else if (!txndate.equals(other.txndate))
			return false;
		if (transBatchNo == null) {
			if (other.transBatchNo != null)
				return false;
		} else if (!transBatchNo.equals(other.transBatchNo))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		return true;
	}

	public String getTransBatchNo() {
		return transBatchNo;
	}

	public void setTransBatchNo(String transBatchNo) {
		this.transBatchNo = transBatchNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Override
	public String toString() {
		return "CheckingAccountTransBean [txndate=" + txndate + ", accoutno="
				+ accoutno + ", jnlno=" + jnlno + ", transferflg="
				+ transferflg + ", currency=" + currency + ", transferStatus="
				+ transferStatus + ", accountname=" + accountname + ", txnAmt="
				+ txnAmt + ", transBatchNo=" + transBatchNo + ", businessType="
				+ businessType + "]";
	}



	

}
