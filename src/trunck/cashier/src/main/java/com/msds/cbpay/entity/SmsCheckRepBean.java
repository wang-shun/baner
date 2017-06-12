package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class SmsCheckRepBean {
	private CashierHead head = new CashierHead();
	private SmsCheckRepBody body = new SmsCheckRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public SmsCheckRepBody getBody() {
		return body;
	}

	public void setBody(SmsCheckRepBody body) {
		this.body = body;
	}
	public static class SmsCheckRepBody {
		private String tranState;//
	
		public String getTranState() {
			return tranState;
		}
	
		public void setTranState(String tranState) {
			this.tranState = tranState;
		}
	}
}
