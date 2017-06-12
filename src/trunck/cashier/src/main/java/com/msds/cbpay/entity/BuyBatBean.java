package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head"})
public class BuyBatBean {
	private CashierHead head = new CashierHead();

	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}
	
}
