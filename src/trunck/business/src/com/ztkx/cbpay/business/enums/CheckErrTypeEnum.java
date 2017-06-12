package com.ztkx.cbpay.business.enums;


/**
 * 对账错误类型
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum CheckErrTypeEnum {
	
	STATUS_ERR("0","状态不符"),	
	PLAT_MUCH("1","平台多账"),
	CHANNEL_MUCH("2","合作渠道多账");
	
	private String code;
	private String desc;
	
	private CheckErrTypeEnum(String code, String desc) {
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
}
