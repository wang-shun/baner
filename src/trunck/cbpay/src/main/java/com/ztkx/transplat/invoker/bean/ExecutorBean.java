package com.ztkx.transplat.invoker.bean;
/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月4日 上午10:31:34 
 */
public class ExecutorBean implements Comparable<ExecutorBean> {
	private String type;
	private String id;
	private int sequence;
	
	public ExecutorBean() {
		
	}
	public ExecutorBean(String type, String id, int sequence) {
		this.type = type;
		this.id = id;
		this.sequence = sequence;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	@Override
	public int compareTo(ExecutorBean o) {
		return (this.sequence - o.getSequence());
	}
}
