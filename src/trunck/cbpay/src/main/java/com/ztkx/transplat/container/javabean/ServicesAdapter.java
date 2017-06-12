package com.ztkx.transplat.container.javabean;

import java.util.List;

import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;

public class ServicesAdapter extends ServicesAdapterKey {
    private String processlist;

	private String owner;
	
	private List<ProcessService> processservice;
	private int boundary;//分界线

	public String getProcesslist() {
		return processlist;
	}

	public void setProcesslist(String processlist) {
		this.processlist = processlist == null ? null : processlist.trim();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner == null ? null : owner.trim();
	}

	public List<ProcessService> getProcessservice() {
		return processservice;
	}
	/**
	 * 添加服务，并计算分界线
	 * @param ps
	 */
	public void addProcessService(ProcessService ps){
		processservice.add(ps);
		if(ps.getServicetype().equals(ConstantConfigField.SERVICE_TYPE_PRO)){
			boundary=processservice.size()-1;
		}
	}

	public void setProcessservice(List<ProcessService> processservice) {
		this.processservice = processservice;
	}

	public int getBoundary() {
		return boundary;
	}
}