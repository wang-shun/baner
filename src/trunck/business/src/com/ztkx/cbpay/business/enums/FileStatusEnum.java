package com.ztkx.cbpay.business.enums;


/**
 * 购汇状态
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum FileStatusEnum {
	
	CREATED("1","文件创建"),	
	UPLOADING("2","文件开始上传"),
	UPLOAD_COMPLETE("3","文件上传成功"),
	DOWNLOADING("4","文件下载开始"),
	DOWNLOAD_COMPLETE("5","文件下载完成");
	
	private String code;
	private String desc;
	
	private FileStatusEnum(String code, String desc) {
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
