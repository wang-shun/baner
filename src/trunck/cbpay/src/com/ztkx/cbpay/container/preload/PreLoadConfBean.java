package com.ztkx.cbpay.container.preload;

public class PreLoadConfBean implements Comparable<PreLoadConfBean>{
	private String id;
	private String type;
	private String impl;
	private int sequence;		//preload加载顺序
	
	public PreLoadConfBean(String id, String type, String impl,int sequence) {
		this.id = id;
		this.type = type;
		this.impl = impl;
		this.sequence = sequence;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	
	@Override
	public String toString() {
		return "PreLoadConfBean [id=" + id + ", type=" + type + ", impl="
				+ impl + ", sequence=" + sequence + "]";
	}
	
	@Override
	public int compareTo(PreLoadConfBean obj) {
		if(this.sequence==obj.sequence || this == obj){
			return 0;
		}else{
			if(this.sequence>obj.sequence){
				return 1;
			}else{
				return 0;
			}
		}
	}
}
