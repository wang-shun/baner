package com.ztkx.cbpay.business.bean;

public class BServerParam {
	
	private String serverid;
	private String paraname;
	private String paravalue;
	private boolean useflg;
	private String paramdesc;
	
	public String getParamdesc() {
		return paramdesc;
	}
	public void setParamdesc(String paramdesc) {
		this.paramdesc = paramdesc;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getParaname() {
		return paraname;
	}
	public void setParaname(String paraname) {
		this.paraname = paraname;
	}
	public String getParavalue() {
		return paravalue;
	}
	public void setParavalue(String paravalue) {
		this.paravalue = paravalue;
	}
	public boolean isUseflg() {
		return useflg;
	}
	public void setUseflg(boolean useflg) {
		this.useflg = useflg;
	}
}
