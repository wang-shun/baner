package com.ztkx.cbpay.business.enums;

/**
 * 付汇交易状态
 * @author zhangxiaoyun
 * 2016年3月20日 下午10:03:21
 * <p>Company:ztkx</p>
 */
public enum PayExgStatusEnum {
	
	T("T","付汇成功","03"),	
	F("F","付汇失败","04"),
	O("O","待交割","05");
	
	private String serverStatus;//服务端购汇状态
	private String statusDesc;	//状态描述
	private String code;//状态码
	
	private PayExgStatusEnum(String serverStatus, String statusDesc,String code) {
		this.serverStatus = serverStatus;
		this.statusDesc = statusDesc;
		this.code = code;
	}
	
	public String getStatusDesc() {
		return statusDesc;
	}



	public String getStatus(){
		return this.serverStatus;
	}
	public String getCode(){
		return this.code;
	}
}
