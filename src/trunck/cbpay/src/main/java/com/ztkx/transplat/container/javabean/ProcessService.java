package com.ztkx.transplat.container.javabean;

public class ProcessService {
	private String serviceid;
	private String servicetype;
	private boolean mustrun;
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	
	public boolean isMustrun() {
		return mustrun;
	}
	public void setMustrun(boolean mustrun) {
		this.mustrun = mustrun;
	}
	@Override
	public String toString() {
		return "ProcessService [serviceid=" + serviceid + ", servicetype="
				+ servicetype + ", mustrun=" + mustrun + "]";
	}
	
}
