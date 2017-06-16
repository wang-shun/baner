package com.ztkx.transplat.platformutil.msg;

public class FieldFormat {
	private String type;	//数据类型
	private int length;		//数据长度
	private String default_value;
	private boolean necessary=true;
	private String function;
	private String fill_type;
	private String convert;
	private int fill_char=-1;
	private boolean frommsg=true;		//表示当前字段是否需要从报文中拆出来
	private int level=-1;				//标签层级   从1开始，到循环报文里面层级从1开始重新计算
	private String super_field;			//上级标签
	private int super_level;			//上级标签的级别

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public int getSuper_level() {
		return super_level;
	}

	public void setSuper_level(int super_level) {
		this.super_level = super_level;
	}

	public String getSuper_field() {
		return super_field;
	}

	public void setSuper_field(String super_field) {
		this.super_field = super_field;
	}

	public int getLevel() {
		return level;
	}

	public boolean isFrommsg() {
		return frommsg;
	}

	public void setFrommsg(boolean frommsg) {
		this.frommsg = frommsg;
	}

	public void setLevel(int level) {
		this.level = level;
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
