package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class PayRepBean {
	private CashierHead head = new CashierHead();
	private PayRepBody body = new PayRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public PayRepBody getBody() {
		return body;
	}

	public void setBody(PayRepBody body) {
		this.body = body;
	}
	public static class PayRepBody {
		private String tranState;//
	
		public String getTranState() {
			return tranState;
		}
	
		public void setTranState(String tranState) {
			this.tranState = tranState;
		}
	}
}
