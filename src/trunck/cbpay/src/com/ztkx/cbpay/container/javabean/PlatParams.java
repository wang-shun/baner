package com.ztkx.cbpay.container.javabean;

/**
 * 系统参数表
 * @author zhangxiaoyun
 * 2016年3月11日 下午2:36:24
 * <p>Company:ztkx</p>
 */
public class PlatParams {
	private String paramsName;			//参数名称
	private String paramsValue;			//参数值
	private String useFlag;				//启用状态  1 启用   0 停用
	private String paramDesc;           //参数描述
	public String getParamsName() {
		return paramsName;
	}
	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}
	public String getParamsValue() {
		return paramsValue;
	}
	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
}
