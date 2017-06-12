package com.msds.cbpay.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder = {"head","body"})
public class SmsSendReqBean {
	private CashierHead head = new CashierHead();
	private SmsSendReqBody body = new SmsSendReqBody();
	
	public CashierHead getHead() {
		return head;
	}

	public void setHead(CashierHead head) {
		this.head = head;
	}

	
	public SmsSendReqBody getBody() {
		return body;
	}

	public void setBody(SmsSendReqBody body) {
		this.body = body;
	}
	@XmlType(propOrder = { "verJnlNo", "verChnl", "accountName","mobileNo","verifyCodeType"}) 
	public static class SmsSendReqBody {
		private String verJnlNo="";//流水暂定空
		private String verChnl="";//web
		private String accountName="";//姓名
		private String mobileNo="";//手机号
		private String verifyCodeType="1";//1短信,2语音
		public String getVerJnlNo() {
			return verJnlNo;
		}
		public void setVerJnlNo(String verJnlNo) {
			this.verJnlNo = verJnlNo;
		}
		public String getVerChnl() {
			return verChnl;
		}
		public void setVerChnl(String verChnl) {
			this.verChnl = verChnl;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getVerifyCodeType() {
			return verifyCodeType;
		}
		public void setVerifyCodeType(String verifyCodeType) {
			this.verifyCodeType = verifyCodeType;
		}
		
	}
}
