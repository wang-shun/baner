package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class CheckOrderReqBean {
	private CashierHead head = new CashierHead();
	private CheckOrderReqBody body = new CheckOrderReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public CheckOrderReqBody getBody() {
		return body;
	}

	public void setBody(CheckOrderReqBody body) {
		this.body = body;
	}


	@XmlType(propOrder = {"currency","merchantTranCode","tradeCode","invoiceNo"})
	public static class CheckOrderReqBody{
		private String currency ="";
		private String merchantTranCode="";
		private String tradeCode ="";
		private String invoiceNo="";
		public String getCurrency() {
			return currency;
		}
	
		public void setCurrency(String currency) {
			this.currency = currency;
		}
	
		public String getMerchantTranCode() {
			return merchantTranCode;
		}
	
		public void setMerchantTranCode(String merchantTranCode) {
			this.merchantTranCode = merchantTranCode;
		}

		public String getTradeCode() {
			return tradeCode;
		}

		public void setTradeCode(String tradeCode) {
			this.tradeCode = tradeCode;
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}
		
		
	}
}
