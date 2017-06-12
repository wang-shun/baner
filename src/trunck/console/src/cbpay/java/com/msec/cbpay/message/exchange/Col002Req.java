package com.msec.cbpay.message.exchange;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.msec.cbpay.message.Head;

/**
 * console发起上传付汇文件交易的请求报文实体类，待修改
 * 
 * @author tianguangzhao
 *
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class Col002Req {
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

	@XmlType(propOrder = { "tradeSn", "fileName", "fileDate" })
	public static class Body {

		private String tradeSn;
		private String fileName;
		private String fileDate;

		@XmlElement(name = "TradeSn")
		public String getTradeSn() {
			return tradeSn;
		}

		public void setTradeSn(String tradeSn) {
			this.tradeSn = tradeSn;
		}

		@XmlElement(name = "FileName")
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		@XmlElement(name = "FileDate")
		public String getFileDate() {
			return fileDate;
		}

		public void setFileDate(String fileDate) {
			this.fileDate = fileDate;
		}

		@Override
		public String toString() {
			return "Body [tradeSn=" + tradeSn + ", fileName=" + fileName
					+ ", fileDate=" + fileDate + "]";
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
