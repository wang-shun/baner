package com.ztkx.cbpay.business.enums;


/**
 * 购汇渠道
 * @author zhangxiaoyun
 * 2016年4月27日14:07:34
 * <p>Company:ztkx</p>
 */
public enum BuyExgChannelEnum {
	
	PAB("PAB_SVR","平安银行");
	
	private String code;
	private String desc;
	
	private BuyExgChannelEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString(){
		return code;
	}
}
