package com.ztkx.cbpay.business.enums;

/**
 * 购汇状态
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum FileTypeEnum {
	
	KHXX("1","客户信息文件"),	
	FHFILE("5","付汇交易明细对照文件"),
	SHFILE("6","收汇交易明细对照文件"),
	GHCXFILE("3","购汇登记状态查询文件"),
	GHFILE("4","购汇交易明细对照文件"),
	JSHFILE("7","结汇收汇交易明细对照文件"),
	SHDZ("8","收汇到账文件主动通知"),
	SWIFTCODE("9","SWIFT CODE文件通知"),
	FYLIST("C","费用清单"),
	BCMSG("D","BC类消息通知");
	
	private String code;//服务端购汇状态
	private String desc;	//状态描述
	
	private FileTypeEnum(String code, String desc) {
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
