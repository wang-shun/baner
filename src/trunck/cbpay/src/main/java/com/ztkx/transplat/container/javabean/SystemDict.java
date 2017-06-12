package com.ztkx.transplat.container.javabean;
/**
 * 跨境系统配置信息表实体类
 * @author tianguangzhao
 *
 */
public class SystemDict {
	private String key; 
	private String name;
	private String desc;
	private String type;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SystemDict [key=" + key + ", name=" + name + ", desc=" + desc
				+ ", type=" + type + "]";
	}

}
