package com.ztkx.cbpay.platformutil.msg;

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
