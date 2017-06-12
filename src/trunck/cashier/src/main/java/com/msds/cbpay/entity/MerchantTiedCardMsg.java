package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlAccessorType(value = XmlAccessType.PROPERTY) 
@XmlType(propOrder = {"head","body"})
public class MerchantTiedCardMsg {
	private MerchantOrderHead head;
	private MerchantTiedCardBody body;
	public MerchantOrderHead getHead() {
		return head;
	}
	public void setHead(MerchantOrderHead head) {
		this.head = head;
	}
	public MerchantTiedCardBody getBody() {
		return body;
	}
	public void setBody(MerchantTiedCardBody body) {
		this.body = body;
	}
	
}
