package cn.msec.cbpay.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="message")
@XmlType(propOrder={"head","body"})
public class COL007Res{
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
	@XmlType(propOrder={"PROCESS_STATUS"})
	public static class Body{
		private String PROCESS_STATUS;

		public String getPROCESS_STATUS() {
			return PROCESS_STATUS;
		}

		public void setPROCESS_STATUS(String pROCESS_STATUS) {
			PROCESS_STATUS = pROCESS_STATUS;
		}
		
	}
}
