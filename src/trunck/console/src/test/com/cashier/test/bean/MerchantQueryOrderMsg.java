package com.cashier.test.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlAccessorType(value = XmlAccessType.PROPERTY) 
@XmlType(propOrder = {"head","body"})
public class MerchantQueryOrderMsg {
	private MerchantOrderHead head = new MerchantOrderHead();
	private MerchantQueryOrderBody body = new MerchantQueryOrderBody();
	public MerchantOrderHead getHead() {
		return head;
	}
	public void setHead(MerchantOrderHead head) {
		this.head = head;
	}
	public MerchantQueryOrderBody getBody() {
		return body;
	}
	public void setBody(MerchantQueryOrderBody body) {
		this.body = body;
	}
	@XmlType(propOrder = {"purchaserId","orderId","orderDate","backParam"})
	public static class MerchantQueryOrderBody {
		
		private String purchaserId="";
		private String orderId="";
		private String orderDate="";
		private String backParam="";
		/**
		 * 合并商品到订单表里
		 * @return
		 */
		public String getPurchaserId() {
			return purchaserId;
		}
		public void setPurchaserId(String purchaserId) {
			this.purchaserId = purchaserId;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getOrderDate() {
			return orderDate;
		}
		public void setOrderDate(String orderDate) {
			this.orderDate = orderDate;
		}
		public String getBackParam() {
			return backParam;
		}
		public void setBackParam(String backParam) {
			this.backParam = backParam;
		}
		
	}
}
