package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class SmsSendRepBean {
	private CashierHead head = new CashierHead();
	private SmsSendRepBody body = new SmsSendRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public SmsSendRepBody getBody() {
		return body;
	}

	public void setBody(SmsSendRepBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "verJnlNo", "phoneToken"}) 
	public static class SmsSendRepBody {
		private String verJnlNo = "";
		private String phoneToken = "";
		public String getVerJnlNo() {
			return verJnlNo;
		}
		public void setVerJnlNo(String verJnlNo) {
			this.verJnlNo = verJnlNo;
		}
		public String getPhoneToken() {
			return phoneToken;
		}
		public void setPhoneToken(String phoneToken) {
			this.phoneToken = phoneToken;
		}
	}
}
