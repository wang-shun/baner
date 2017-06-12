package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class OrderShowReqBean {
	private CashierHead head = new CashierHead();
	private OrderShowReqBody body = new OrderShowReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public OrderShowReqBody getBody() {
		return body;
	}

	public void setBody(OrderShowReqBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "tranFlow","pageReturnUrl", "offlineNotifyUrl", "clientIP","merchantId",
			"tradeType","poundageFlag","poundageRate","purchaserId", "merchantName","orderDesc","totalAmount", "currency", "validUnit","validNum",
			"tradeCode","isRef","backParam", "productName", "productId", "productDesc", "showUrl","invoiceNo"})  
	public static class OrderShowReqBody{
		
		private String tranFlow = "";//交易流水号
		private String pageReturnUrl="";//页面url
		private String offlineNotifyUrl="";//后台url
		private String clientIP="";//客户端地址
		private String merchantId="";//商户id
		private String tradeType = "";//业务类型
		private String poundageFlag="";//手续费标示 0商户出 1用户出
		private String poundageRate="";//手续费费率
		private String purchaserId="";//购买者标示
		private String merchantName="";//商户名称
		private String orderDesc="";//订单描述
		private String totalAmount="";//总金额
		private String currency="";//币种
		private String validUnit="";//有效单位
		private String validNum="";//有效时间
		private String tradeCode="";//交易代码
		private String isRef="";//是否保税
		private String backParam="";//返回参数
		private String showUrl="";
		private String productName="";
		private String productId="";
		private String productDesc="";
		private String invoiceNo="";
		/*private String productCount;
		private List<ProductBean> productBeanList;*/
		
		
		
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		public String getTranFlow() {
			return tranFlow;
		}
		public void setTranFlow(String tranFlow) {
			this.tranFlow = tranFlow;
		}
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
		
		public String getTradeType() {
			return tradeType;
		}
		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
		public String getPoundageFlag() {
			return poundageFlag;
		}
		public void setPoundageFlag(String poundageFlag) {
			this.poundageFlag = poundageFlag;
		}
		public String getClientIP() {
			return clientIP;
		}
		public void setClientIP(String clientIP) {
			this.clientIP = clientIP;
		}
		public String getPurchaserId() {
			return purchaserId;
		}
		public void setPurchaserId(String purchaserId) {
			this.purchaserId = purchaserId;
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
		public String getBackParam() {
			return backParam;
		}
		public void setBackParam(String backParam) {
			this.backParam = backParam;
		}
		
		public String getOrderDesc() {
			return orderDesc;
		}
		public void setOrderDesc(String orderDesc) {
			this.orderDesc = orderDesc;
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
		/*public String getProductCount() {
			return productCount;
		}
		public void setProductCount(String productCount) {
			this.productCount = productCount;
		}*/
		
		public String getPoundageRate() {
			return poundageRate;
		}
		public void setPoundageRate(String poundageRate) {
			this.poundageRate = poundageRate;
		}
		/*@XmlElement(name = "product")
		public List<ProductBean> getProductBeanList() {
			return productBeanList;
		}
		
		public void setProductBeanList(List<ProductBean> productBeanList) {
			this.productBeanList = productBeanList;
		}*/
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
		@Override
		public String toString() {
			return "OrderShowReqBody [tranFlow=" + tranFlow
					+ ", pageReturnUrl=" + pageReturnUrl
					+ ", offlineNotifyUrl=" + offlineNotifyUrl + ", clientIP="
					+ clientIP + ", merchantId=" + merchantId + ", tradeType="
					+ tradeType + ", poundageFlag=" + poundageFlag
					+ ", poundageRate=" + poundageRate + ", purchaserId="
					+ purchaserId + ", merchantName=" + merchantName
					+ ", orderDesc=" + orderDesc + ", totalAmount="
					+ totalAmount + ", currency=" + currency + ", validUnit="
					+ validUnit + ", validNum=" + validNum + ", tradeCode="
					+ tradeCode + ", isRef=" + isRef + ", backParam="
					+ backParam + ", showUrl=" + showUrl + ", productName="
					+ productName + ", productId=" + productId
					+ ", productDesc=" + productDesc + "]";
		}
		
	}
}
