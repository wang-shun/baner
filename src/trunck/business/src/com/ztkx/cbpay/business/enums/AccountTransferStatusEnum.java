package com.ztkx.cbpay.business.enums;

/**
 * 账户划转状态
 * @author zhangxiaoyun
 * 2016年4月12日09:52:58
 * <p>Company:ztkx</p>
 */
public enum AccountTransferStatusEnum {
	
	INITSTATUS("00","初始状态"),	
	BUYBEFSTART("01","购汇前账户划转开始"),	
	BUYBEFSUCC("02","购汇前账户划转成功"),
	BUYBEFFAIL("03","购汇前账户划转失败"),
	BUYAFTSTART("04","购汇后账户划转开始"),
	BUYAFTSUCC("05","购汇后账户划转成功"),
	BUYAFTFAIL("06","购汇后账户划转失败"),
	PAYBEFSTART("07","付汇前账户划转开始"),	
	PAYBEFSUCC("08","付汇前账户划转成功"),
	PAYBEFFAIL("09","付汇前账户划转失败");
	
	private String serverStatus;//账户化妆
	private String statusDesc;	//状态描述
	
	private AccountTransferStatusEnum(String serverStatus, String statusDesc) {
		this.serverStatus = serverStatus;
		this.statusDesc = statusDesc;
	}
	
	public String getStatus(){
		return this.serverStatus;
	}

	public String getStatusDesc() {
		return statusDesc;
	}
	
}
