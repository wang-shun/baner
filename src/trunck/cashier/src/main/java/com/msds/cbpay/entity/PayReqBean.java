package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class PayReqBean {
	private CashierHead head = new CashierHead();
	private PayReqBody body = new PayReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public PayReqBody getBody() {
		return body;
	}

	public void setBody(PayReqBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "accountNo", "phoneToken"}) 
	public static class PayReqBody {
		private String accountNo="";
		private String phoneToken="";
		
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
	}
}
