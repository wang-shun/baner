package com.ztkx.cbpay.business.enums;

/**
 * 购汇状态
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum DownUploadEnum {
	
	DOWNLOAD("1","下载"),	
	UPLOAD("2","上传");
	
	private String code;
	private String desc;
	
	private DownUploadEnum(String code, String desc) {
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
