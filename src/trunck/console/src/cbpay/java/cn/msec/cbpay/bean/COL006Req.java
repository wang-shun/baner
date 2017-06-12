package cn.msec.cbpay.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class COL006Req {
	private Head head;
	private Body body;
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	@XmlType(propOrder = {"listSize","currencyCode","merchantId","buyBatList" })
	public static class Body {
		private int listSize;
		private String currencyCode;
		private String merchantId;
		
		private List<BuyBatList> buyBatList;

		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		public int getListSize() {
			return listSize;
		}
		public void setListSize(int listSize) {
			this.listSize = listSize;
		}
		public List<BuyBatList> getBuyBatList() {
			return buyBatList;
		}
		public void setBuyBatList(List<BuyBatList> buyBatList) {
			this.buyBatList = buyBatList;
		}
		
		@XmlType(propOrder = { "orderId","orderDate" })
		public static class BuyBatList{
			private String orderId;
			private String orderDate;
			public String getOrderDate() {
				return orderDate;
			}
			public void setOrderDate(String orderDate) {
				this.orderDate = orderDate;
			}
			public String getOrderId() {
				return orderId;
			}
			public void setOrderId(String orderId) {
				this.orderId = orderId;
			}
		}
	}
}
