package com.ztkx.cbpay.business.enums;

/**
 * 购汇状态
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum BuyFCStatusEnum {
	
	T("T","购汇成功","06"),	
	F("F","购汇失败","05"),
	O("O","待交割","04");
	
	private String serverStatus;//服务端购汇状态
	private String statusDesc;	//状态描述
	private String code;//状态码
	
	private BuyFCStatusEnum(String serverStatus, String statusDesc,String code) {
		this.serverStatus = serverStatus;
		this.statusDesc = statusDesc;
		this.code = code;
	}
	
	public String getStatus(){
		return this.serverStatus;
	}
	public String getCode(){
		return this.code;
	}
}
