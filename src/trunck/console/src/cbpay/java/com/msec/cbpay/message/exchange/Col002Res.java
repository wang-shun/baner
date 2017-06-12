package com.msec.cbpay.message.exchange;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.msec.cbpay.message.Head;

/**
 * console发起上传付汇文件交易的反馈报文实体类，待修改
 * 
 * @author tianguangzhao
 *
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class Col002Res {
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

		@Override
		public String toString() {
			return "Body[]";
		}

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Col001Res [head=");
		builder.append(head);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
