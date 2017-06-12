package com.ztkx.transplat.container.javabean;

/**
 * ping台相应码描述表
 * 
 * @author tianguangzhao
 *
 */
public class Platrescodedesc {
	
	private String errorcode;// 错误码
	private String errordesc;// 错误信息

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrordesc() {
		return errordesc;
	}

	public void setErrordesc(String errordesc) {
		this.errordesc = errordesc;
	}

	@Override
	public String toString() {
		return "Platrescodedesc [errorcode=" + errorcode + ", errordesc=" + errordesc + "]";
	}

}
