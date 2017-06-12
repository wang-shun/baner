package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class TiedCardReqBean {
	private CashierHead head = new CashierHead();
	private TiedCardReqBody body = new TiedCardReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public TiedCardReqBody getBody() {
		return body;
	}

	public void setBody(TiedCardReqBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "bankName", "accountNo","accountName","accountType",
			 "certType", "certNo","mobileNo","email","nickName","purchaserId","phoneVerCode","phoneToken"}) 
	public static class TiedCardReqBody {
		private String bankName="";//银行名称
		private String accountNo="";//银行卡号
		private String accountName="";//姓名
		private String accountType="0";//0对私，1对公
		private String certType="";//证件类型
		private String certNo="";//证件号
		private String mobileNo="";//手机号
		private String email="";
		private String nickName="";//用户昵称
		private String purchaserId="";//购买者标示
		private String phoneVerCode="";//短信
		private String phoneToken="";//短信令牌
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public String getAccountType() {
			return accountType;
		}
		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}
		public String getCertType() {
			return certType;
		}
		public void setCertType(String certType) {
			this.certType = certType;
		}
		public String getCertNo() {
			return certNo;
		}
		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getPhoneToken() {
			return phoneToken;
		}
		public void setPhoneToken(String phoneToken) {
			this.phoneToken = phoneToken;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getPurchaserId() {
			return purchaserId;
		}
		public void setPurchaserId(String purchaserId) {
			this.purchaserId = purchaserId;
		}
		public String getPhoneVerCode() {
			return phoneVerCode;
		}
		public void setPhoneVerCode(String phoneVerCode) {
			this.phoneVerCode = phoneVerCode;
		}
		
	}	
}
