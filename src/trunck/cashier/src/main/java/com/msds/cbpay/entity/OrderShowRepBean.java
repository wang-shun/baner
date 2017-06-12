package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class OrderShowRepBean {
	private CashierHead head = new CashierHead();
	private OrderShowRepBody body = new OrderShowRepBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public OrderShowRepBody getBody() {
		return body;
	}

	public void setBody(OrderShowRepBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "acceptanceMount", "acceptanceRate", "acceptancyCurrency",
			"merchantPoundage","checkInfoExistgFlag", "realName","cardId", "telnum"})
	public static class OrderShowRepBody {
		private String acceptanceMount;
		private String acceptanceRate;
		private String acceptancyCurrency;
		private String merchantPoundage;
		private String checkInfoExistgFlag;
		private String realName;
		private String cardId;
		private String telnum;
		public String getAcceptanceMount() {
			return acceptanceMount;
		}
		public void setAcceptanceMount(String acceptanceMount) {
			this.acceptanceMount = acceptanceMount;
		}
		public String getAcceptanceRate() {
			return acceptanceRate;
		}
		public void setAcceptanceRate(String acceptanceRate) {
			this.acceptanceRate = acceptanceRate;
		}
		public String getAcceptancyCurrency() {
			return acceptancyCurrency;
		}
		public void setAcceptancyCurrency(String acceptancyCurrency) {
			this.acceptancyCurrency = acceptancyCurrency;
		}
		public String getMerchantPoundage() {
			return merchantPoundage;
		}
		public void setMerchantPoundage(String merchantPoundage) {
			this.merchantPoundage = merchantPoundage;
		}
		public String getCheckInfoExistgFlag() {
			return checkInfoExistgFlag;
		}
		public void setCheckInfoExistgFlag(String checkInfoExistgFlag) {
			this.checkInfoExistgFlag = checkInfoExistgFlag;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public String getCardId() {
			return cardId;
		}
		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
		public String getTelnum() {
			return telnum;
		}
		public void setTelnum(String telnum) {
			this.telnum = telnum;
		}
		
	}
}
