package cn.msec.cbpay.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder={"head","body"})
public class COL008Req{
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
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder={"totalCount","SEQ_NO","paydate"})
	public static class Body{
		private int totalCount;
		private String SEQ_NO;
		private String paydate;	//付汇日期
		
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public String getSEQ_NO() {
			return SEQ_NO;
		}
		public void setSEQ_NO(String sEQ_NO) {
			SEQ_NO = sEQ_NO;
		}
		public String getPaydate() {
			return paydate;
		}
		public void setPaydate(String paydate) {
			this.paydate = paydate;
		}
	}
}
