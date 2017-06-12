package com.ztkx.transplat.invoker.bean;

import java.util.List;

/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月4日 上午10:30:56 
 */
public class InvokerBean {
	private String id;
	private String desc;
	private String impl;
	private List<ExecutorBean> executorList = null;
	
	public InvokerBean() {
		
	}
	public InvokerBean(String id, String desc, String impl) {
		this.id = id;
		this.desc = desc;
		this.impl = impl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	public List<ExecutorBean> getExecutorList() {
		return executorList;
	}
	public void setExecutorList(List<ExecutorBean> executorList) {
		this.executorList = executorList;
	}
	
}
