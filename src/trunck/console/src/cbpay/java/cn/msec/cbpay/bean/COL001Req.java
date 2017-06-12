package cn.msec.cbpay.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class COL001Req {
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

	@XmlType(propOrder = { "merchantId","supplierId","listSize","REMARK","payBatList" })
	public static class Body {
		// 原交易流水号
		private String merchantId = "";
		private String supplierId = "";
		private int listSize;
		private String REMARK = "";
		private List<OrderList> payBatList = new ArrayList<OrderList>();
		
		public String getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}

		public String getSupplierId() {
			return supplierId;
		}

		public void setSupplierId(String supplierId) {
			this.supplierId = supplierId;
		}

		public int getListSize() {
			return listSize;
		}

		public void setListSize(int listSize) {
			this.listSize = listSize;
		}

		public String getREMARK() {
			return REMARK;
		}

		public void setREMARK(String rEMARK) {
			REMARK = rEMARK;
		}

		public List<OrderList> getPayBatList() {
			return payBatList;
		}

		public void setPayBatList(List<OrderList> payBatList) {
			this.payBatList = payBatList;
		}

		@XmlType(propOrder = { "orderId","orderDate" })
		public static class OrderList{
			private String orderDate = "";
			private String orderId = "";
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
