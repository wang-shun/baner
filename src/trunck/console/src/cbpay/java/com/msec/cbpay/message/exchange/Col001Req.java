package com.msec.cbpay.message.exchange;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.msec.cbpay.message.Head;

/**
 * console发起付汇请求的请求报文实体类
 * 
 * @author tianguangzhao
 *
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class Col001Req {
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

	@XmlType(propOrder = { "merchantid", "startdate", "enddate", "REMARK" })
	public static class Body {

		private String merchantid;
		private String startdate;
		private String enddate;
		private String REMARK;

		public String getMerchantid() {
			return merchantid;
		}

		public void setMerchantid(String merchantid) {
			this.merchantid = merchantid;
		}

		public String getStartdate() {
			return startdate;
		}

		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}

		public String getEnddate() {
			return enddate;
		}

		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}

		public String getREMARK() {
			return REMARK;
		}

		public void setREMARK(String REMARK) {
			this.REMARK = REMARK;
		}

		@Override
		public String toString() {
			return "Body [merchantid=" + merchantid + ", startdate="
					+ startdate + ", enddate=" + enddate + ", REMARK=" + REMARK
					+ "]";
		}

	
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Col001Req [head=");
		builder.append(head);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
