package cn.msec.cbpay.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class COL005Req {
	private Head head = new Head();
	private Body body = new Body();

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

	@XmlType(propOrder = { "transType", "listSize","orderList" })
	public static class Body {
		// 原交易流水号
		private String transType = "";
		private int listSize;
		private List<OrderList> orderList = new ArrayList<OrderList>();

		public String getTransType() {
			return transType;
		}
		public void setTransType(String transType) {
			this.transType = transType;
		}
		public int getListSize() {
			return listSize;
		}
		public void setListSize(int listSize) {
			this.listSize = listSize;
		}
		public List<OrderList> order() {
			return orderList;
		}
		public void setOrderList(List<OrderList> orderList) {
			this.orderList = orderList;
		}
		
		public List<OrderList> getOrderList() {
			return orderList;
		}

		@XmlType(propOrder = { "merchantId", "orderDate","orderId" })
		public static class OrderList{
			private String merchantId = "";
			private String orderDate = "";
			private String orderId = "";
			public String getMerchantId() {
				return merchantId;
			}
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}
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
