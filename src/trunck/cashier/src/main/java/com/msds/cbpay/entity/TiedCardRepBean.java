package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class TiedCardRepBean {
	private CashierHead head = new CashierHead();
	private TiedCardRepBody body = new TiedCardRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public TiedCardRepBody getBody() {
		return body;
	}

	public void setBody(TiedCardRepBody body) {
		this.body = body;
	}
	public static class TiedCardRepBody {
		private String tranState;//
	
		public String getTranState() {
			return tranState;
		}
	
		public void setTranState(String tranState) {
			this.tranState = tranState;
		}
	}
}
