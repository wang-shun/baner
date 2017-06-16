package com.ztkx.transplat.platformutil.msg;

import java.util.ArrayList;
import java.util.List;
/**
 * 报文中循环体
 * @author zhangxiaoyun
 * 2016年3月6日 下午2:40:43
 * <p>Company:ztkx</p>
 */
public class CompositField {
	private String name;
	private String sizeRef;
	private List<Field> list = null;
	private boolean outPut = false;			//组包的时候是否输出到xml报文中
	private String super_field;			//上级标签
	private int super_level;			//上级标签的级别


	public String getSuper_field() {
		return super_field;
	}

	public void setSuper_field(String super_field) {
		this.super_field = super_field;
	}

	public int getSuper_level() {
		return super_level;
	}

	public void setSuper_level(int super_level) {
		this.super_level = super_level;
	}

	public boolean isOutPut() {
		return outPut;
	}
	public void setOutPut(boolean outPut) {
		this.outPut = outPut;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSizeRef() {
		return sizeRef;
	}
	public void setSizeRef(String sizeRef) {
		this.sizeRef = sizeRef;
	}
	public Field getField(int index) {
		if(list == null){
			return null;
		}
		return list.get(index);
	}
	public void setField(Field field) {
		if(list == null){
			list = new ArrayList<Field>();
		}
		this.list.add(field);
	}
	public List<Field> getList(){
		return list;
	}
}
