package com.ztkx.cbpay.platformutil.msg;

public class FieldFormat {
	private String type;
	private int length;
	private String default_value;
	private boolean necessary=true;
	private String function;
	private String fill_type;
	private String convert;
	private int fill_char=-1;
	private boolean frommsg=true;		//表示当前字段是否需要从报文中拆出来
	private int level=-1;				//标签层级
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isFrommsg() {
		return frommsg;
	}
	public void setFrommsg(boolean frommsg) {
		this.frommsg = frommsg;
	}
	public String getConvert() {
		return convert;
	}
	public void setConvert(String convert) {
		this.convert = convert;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public boolean getNecessary() {
		return necessary;
	}
	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}
	public String getFill_type() {
		return fill_type;
	}
	public void setFill_type(String fill_type) {
		this.fill_type = fill_type;
	}
	public int getFill_char() {
		return fill_char;
	}
	public void setFill_char(int fill_char) {
		this.fill_char = fill_char;
	}
	@Override
	public String toString() {
		return "FieldFormat [type=" + type + ", length=" + length
				+ ", default_value=" + default_value + ", necessary="
				+ necessary + ", function=" + function + ", fill_type="
				+ fill_type + ", convert=" + convert + ", fill_char="
				+ fill_char + ", frommsg=" + frommsg + ", level=" + level + "]";
	}
	
	
}
