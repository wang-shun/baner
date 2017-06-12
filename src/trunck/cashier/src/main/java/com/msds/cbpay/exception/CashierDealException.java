package com.msds.cbpay.exception;

public class CashierDealException extends Exception {

	/**
	 * 自定义商户异常
	 */
	private static final long serialVersionUID = 1L;
	public String offlineurl = null;
	public String onlineurl = null;
	public String merchantNo = null;
	public String clientTime = null;
	public String tranFlow = null;
	public String clientIP = null;
	public String respCode = null;
	public String respMsg = null;
	/**
	 * 
	 * @param offlineurl 后台通知商户地址（有就去通知，没有就不通知）
	 * @param onlineurl 前台界面地址（有就去通知，没有就不通知）
	 * @param merchantNo 商户号
	 * @param clientTime 客户端时间
	 * @param tranFlow 流水号(唯一标示）
	 * @param clientIP 客户端地址
	 * @param respCode 响应码
	 * @param respMsg 相应码描述
	 */
	public CashierDealException(String offlineurl,String onlineurl,String merchantNo,String clientTime,String tranFlow,String clientIP,String respCode,String respMsg){
        this.offlineurl = offlineurl;
        this.onlineurl = onlineurl;
        this.merchantNo = merchantNo;
        this.clientTime = clientTime;
        this.tranFlow = tranFlow;
        this.clientIP = clientIP;
        this.respCode = respCode;
        this.respMsg = respMsg;
    }
	@Override
	public String toString() {
		return "MerchantMsgException ["+ respMsg + "]";
	}
}
