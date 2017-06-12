package cn.msec.cbpay.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = { "head", "body" })
public class COL001Res {
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
	@XmlType(propOrder = { "merchantId","FileName","FileDate","Count","totalAmt","currency_type","TradeSn" })
	public static class Body {
		// 原交易流水号
		private String merchantId = "";
		@XmlElement(name="FileName")
		private String FileName = "";
		@XmlElement(name="FileDate")
		private String FileDate = "";
		private String Count = "";
		private String totalAmt = "";
		private String currency_type = "";
		private String TradeSn = "";

		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
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
		public String getCount() {
			return Count;
		}
		public void setCount(String count) {
			Count = count;
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

		
		public String getTradeSn() {
			return TradeSn;
		}
		public void setTradeSn(String tradeSn) {
			TradeSn = tradeSn;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Body [merchantId=");
			builder.append(merchantId);
			builder.append(", FileName=");
			builder.append(FileName);
			builder.append(", FileDate=");
			builder.append(FileDate);
			builder.append(", Count=");
			builder.append(Count);
			builder.append(", totalAmt=");
			builder.append(totalAmt);
			builder.append(", currency_type=");
			builder.append(currency_type);
			builder.append(", TradeSn=");
			builder.append(TradeSn);
			builder.append("]");
			return builder.toString();
		}
	}
}
