package cn.msec.cbpay.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class COL002Req {
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
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "TradeSn","FileName","FileDate" })
	public static class Body {
		// 原交易流水号
		
		@XmlElement(name="TradeSn")
		private String TradeSn = "";
		@XmlElement(name="FileName")
		private String FileName = "";
		@XmlElement(name="FileDate")
		private String FileDate = "";
		
		
		public String getTradeSn() {
			return TradeSn;
		}
		public void setTradeSn(String tradeSn) {
			TradeSn = tradeSn;
		}
		public String getFileName() {
			return FileName;
		}
		public void setFileName(String fileName) {
			FileName = fileName;
		}
		public String getFileDate() {
			return FileDate;
		}
		public void setFileDate(String fileDate) {
			FileDate = fileDate;
		}
	}
}
