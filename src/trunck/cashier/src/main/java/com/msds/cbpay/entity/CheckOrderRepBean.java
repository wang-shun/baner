package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class CheckOrderRepBean {
	
	private CashierHead head = new CashierHead();
	private CheckOrderRepBody body = new CheckOrderRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	public CheckOrderRepBody getBody() {
		return body;
	}

	public void setBody(CheckOrderRepBody body) {
		this.body = body;
	}

	@XmlType(propOrder = {"poundageFlag","poundageRate"})
	public static class CheckOrderRepBody{
		private String poundageFlag ="";
		private String poundageRate="";
		public String getPoundageFlag() {
			return poundageFlag;
		}
	
		public void setPoundageFlag(String poundageFlag) {
			this.poundageFlag = poundageFlag;
		}
	
		public String getPoundageRate() {
			return poundageRate;
		}
	
		public void setPoundageRate(String poundageRate) {
			this.poundageRate = poundageRate;
		}
	}
}
