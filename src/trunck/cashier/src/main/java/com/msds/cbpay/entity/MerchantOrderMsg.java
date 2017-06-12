package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlAccessorType(value = XmlAccessType.PROPERTY) 
@XmlType(propOrder = {"head","body"})
public class MerchantOrderMsg {
	private MerchantOrderHead head = new MerchantOrderHead();
	private MerchantOrderBody body = new MerchantOrderBody();
	public MerchantOrderHead getHead() {
		return head;
	}
	public void setHead(MerchantOrderHead head) {
		this.head = head;
	}
	public MerchantOrderBody getBody() {
		return body;
	}
	public void setBody(MerchantOrderBody body) {
		this.body = body;
	}
	@XmlType(propOrder = {"pageReturnUrl","offlineNotifyUrl","purchaserId","orderId","orderDate",
			"orderTime","orderDesc","tradeType","merchantName","totalAmount","currency","validUnit","validNum","tradeCode","isRef","backParam"
			, "productName", "productId", "productDesc", "showUrl","invoiceNo"})
	
	
	
	public static class MerchantOrderBody {
		
		private String pageReturnUrl="";
		private String offlineNotifyUrl="";
		private String purchaserId="";
		private String orderId="";
		private String orderDate="";
		private String orderTime="";
		private String orderDesc="";
		private String tradeType="";
		private String merchantName="";
		private String totalAmount="";
		private String currency="";
		private String validUnit="";
		private String validNum="";
		private String tradeCode="";
		private String isRef="";
		private String backParam="";
		private String productName="";
		private String productId="";
		private String productDesc="";
		private String showUrl="";
		private String invoiceNo="";
		/**
		 * 合并商品到订单表里
		 * @return
		 */
		public String getPageReturnUrl() {
			return pageReturnUrl;
		}
		public void setPageReturnUrl(String pageReturnUrl) {
			this.pageReturnUrl = pageReturnUrl;
		}
		public String getOfflineNotifyUrl() {
			return offlineNotifyUrl;
		}
		public void setOfflineNotifyUrl(String offlineNotifyUrl) {
			this.offlineNotifyUrl = offlineNotifyUrl;
		}
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
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		
		public String getOrderDesc() {
			return orderDesc;
		}
		public void setOrderDesc(String orderDesc) {
			this.orderDesc = orderDesc;
		}
		
		public String getTradeType() {
			return tradeType;
		}
		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getValidUnit() {
			return validUnit;
		}
		public void setValidUnit(String validUnit) {
			this.validUnit = validUnit;
		}
		public String getValidNum() {
			return validNum;
		}
		public void setValidNum(String validNum) {
			this.validNum = validNum;
		}
		public String getTradeCode() {
			return tradeCode;
		}
		public void setTradeCode(String tradeCode) {
			this.tradeCode = tradeCode;
		}
		public String getIsRef() {
			return isRef;
		}
		public void setIsRef(String isRef) {
			this.isRef = isRef;
		}
		public String getBackParam() {
			return backParam;
		}
		public void setBackParam(String backParam) {
			this.backParam = backParam;
		}
		public String getShowUrl() {
			return showUrl;
		}
		public void setShowUrl(String showUrl) {
			this.showUrl = showUrl;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductDesc() {
			return productDesc;
		}
		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}
		public String getInvoiceNo() {
			return invoiceNo;
		}
		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}
		
	}
}
