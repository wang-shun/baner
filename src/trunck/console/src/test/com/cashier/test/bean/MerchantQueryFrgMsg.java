package com.cashier.test.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlAccessorType(value = XmlAccessType.PROPERTY) 
@XmlType(propOrder = {"head","body"})
public class MerchantQueryFrgMsg {
	private MerchantOrderHead head = new MerchantOrderHead();
	private MerchantQueryFrgBody body = new MerchantQueryFrgBody();
	public MerchantOrderHead getHead() {
		return head;
	}
	public void setHead(MerchantOrderHead head) {
		this.head = head;
	}
	public MerchantQueryFrgBody getBody() {
		return body;
	}
	public void setBody(MerchantQueryFrgBody body) {
		this.body = body;
	}
	@XmlType(propOrder = {"currency","exQuoteDate"})
	public static class MerchantQueryFrgBody {
		
		private String currency="";
		private String exQuoteDate="";
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getExQuoteDate() {
			return exQuoteDate;
		}
		public void setExQuoteDate(String exQuoteDate) {
			this.exQuoteDate = exQuoteDate;
		}
		
	}
}
