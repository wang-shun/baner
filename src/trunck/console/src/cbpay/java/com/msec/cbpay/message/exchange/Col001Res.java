package com.msec.cbpay.message.exchange;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.msec.cbpay.message.Head;

/**
 * console发起付汇请求的反馈报文实体类
 * 
 * @author tianguangzhao
 *
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class Col001Res {
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

	
	@XmlType(propOrder = { "merchantid", "startdate", "enddate", "fileName",
			"fileDate", "count", "totalAmt", "currency_type", "tradeSn" })
	public static class Body {
		
		private String merchantid;

		private String startdate;

		private String enddate;

		private String fileName;

		private String fileDate;

		private String count;

		private String totalAmt;

		private String currency_type;

		private String tradeSn;

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

		@XmlElement(name = "FileName")
		public String getfileName() {
			return fileName;
		}

		public void setfileName(String fileName) {
			this.fileName = fileName;
		}

		@XmlElement(name = "FileDate")
		public String getfileDate() {
			return fileDate;
		}

		public void setfileDate(String fileDate) {
			this.fileDate = fileDate;
		}

		@XmlElement(name = "Count")
		public String getcount() {
			return count;
		}

		public void setcount(String count) {
			this.count = count;
		}

		public String getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(String totalAmt) {
			this.totalAmt = totalAmt;
		}

		public String getCurrency_type() {
			return currency_type;
		}

		public void setCurrency_type(String currency_type) {
			this.currency_type = currency_type;
		}

		@XmlElement(name = "TradeSn")
		public String gettradeSn() {
			return tradeSn;
		}

		public void settradeSn(String tradeSn) {
			this.tradeSn = tradeSn;
		}

		@Override
		public String toString() {
			return "Body {merchantid=[" + merchantid + "], startdate=["
					+ startdate + "], enddate=[" + enddate + "], fileName=["
					+ fileName + "], fileDate=[" + fileDate + "], count=["
					+ count + "], totalAmt=[" + totalAmt + "], currency_type=["
					+ currency_type + "], tradeSn=[" + tradeSn + "]}";
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
