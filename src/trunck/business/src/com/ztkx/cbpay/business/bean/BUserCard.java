package com.ztkx.cbpay.business.bean;

public class BUserCard {
	//正常状态
	public static final String VALID_NORMAL="0";
	//废弃状态
	public static final String VALID_DISABLE="1";
	
	private String merchantid;
	private String purchaserId;
	private String cardNo;
	private String valid;
	private String createdate;
	private String createtime;
	private String bankName;
	private String openprovince;
	private String opencity;
	private String openname;
	private String tmsmp;

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOpenprovince() {
		return openprovince;
	}

	public void setOpenprovince(String openprovince) {
		this.openprovince = openprovince;
	}

	public String getOpencity() {
		return opencity;
	}

	public void setOpencity(String opencity) {
		this.opencity = opencity;
	}

	public String getOpenname() {
		return openname;
	}

	public void setOpenname(String openname) {
		this.openname = openname;
	}

	public String getTmsmp() {
		return tmsmp;
	}

	public void setTmsmp(String tmsmp) {
		this.tmsmp = tmsmp;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "BUserCard [merchantid=" + merchantid + ", purchaserId="
				+ purchaserId + ", cardNo=" + cardNo + ", valid=" + valid
				+ ", createdate=" + createdate + ", createtime=" + createtime
				+ ", bankName=" + bankName + ", openprovince=" + openprovince
				+ ", opencity=" + opencity + ", openname=" + openname
				+ ", tmsmp=" + tmsmp + "]";
	}
	
}
