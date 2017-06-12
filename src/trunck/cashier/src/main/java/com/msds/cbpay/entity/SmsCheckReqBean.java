package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class SmsCheckReqBean {
	private CashierHead head = new CashierHead();
	private SmsCheckReqBody body = new SmsCheckReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public SmsCheckReqBody getBody() {
		return body;
	}

	public void setBody(SmsCheckReqBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "accountNo", "phoneVerCode", "phoneToken", "purchaserId"}) 
	public static class SmsCheckReqBody {
		
		private String accountNo="";
		private String phoneVerCode="";
		private String phoneToken="";
		private String purchaserId="";
		
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getPhoneToken() {
			return phoneToken;
		}
		public void setPhoneToken(String phoneToken) {
			this.phoneToken = phoneToken;
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
