package cn.msec.cbpay.bean;

import javax.xml.bind.annotation.XmlType;

import com.ztkx.cbpay.platformutil.time.TimeUtil;

@XmlType(propOrder={"tranCode","trandate","trantime","respcode","respmsg","flowno"})
public class Head {
	private String tranCode;
	private String trandate = TimeUtil.getCurrentTime("yyyyMMdd");
	private String trantime = TimeUtil.getCurrentTime("HHmmss");
	private String respcode="";
	private String respmsg="";
	private String flowno="";		//系统流水号
	
	
	
	
	public String getFlowno() {
		return flowno;
	}
	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public String getTrantime() {
		return trantime;
	}
	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}
	public String getRespcode() {
		return respcode;
	}
	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}
	public String getRespmsg() {
		return respmsg;
	}
	public void setRespmsg(String respmsg) {
		this.respmsg = respmsg;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Head [tranCode=");
		builder.append(tranCode);
		builder.append(", trandate=");
		builder.append(trandate);
		builder.append(", trantime=");
		builder.append(trantime);
		builder.append(", respcode=");
		builder.append(respcode);
		builder.append(", respmsg=");
		builder.append(respmsg);
		builder.append(", flowno=");
		builder.append(flowno);
		builder.append("]");
		return builder.toString();
	}
}
