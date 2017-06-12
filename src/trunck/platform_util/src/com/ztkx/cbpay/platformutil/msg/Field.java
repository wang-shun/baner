package com.ztkx.cbpay.platformutil.msg;

import java.util.List;

public class Field {
	private String name;
	private FieldFormat fieldFormat;
	//Field 内部的listFiedl 为了适配执行银行响应接口中子标签为xml格式
	List<Field> listField = null;
	
	
	public List<Field> getListField() {
		return listField;
	}
	public void setListField(List<Field> listField) {
		this.listField = listField;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FieldFormat getFieldFormat() {
		return fieldFormat;
	}
	public void setFieldFormat(FieldFormat fieldFormat) {
		this.fieldFormat = fieldFormat;
	}
	@Override
	public String toString() {
		return "Field [name=" + name + ", fieldFormat=" + fieldFormat
				+ ", listField=" + listField + "]";
	}
	
}
