package com.msec.cbpay.message.checking;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.msec.cbpay.message.Head;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class RecheckingReq {
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

	public static class Body {

		private String jobdate;

		public String getjobdate() {
			return jobdate;
		}

		public void setjobdate(String jobdate) {
			this.jobdate = jobdate;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Body [jobdate=");
			builder.append(jobdate);
			builder.append("]");
			return builder.toString();
		}

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecheckingReq [head=");
		builder.append(head);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
