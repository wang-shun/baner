package com.ztkx.transplat.platformutil.context.imp;

import java.io.Serializable;

public class CommonSDO implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -686355327184103546L;
	
	public String tranFrom;		//交易来源
	public String TRANCODE;
	public String TRAN_OPR;
	public String CHANNEL_DIY;
	public String OVERTIME;       //前端超时时间
	public String Sub_oprcode;
	public String ordernum;
	public String responseCode;	//业务响应码
	public String systemId;
	public String serverId;		//通道id
	public String serverCode;
	public String server_DIY;
	public String Server_OVERTIME;	//后端超时时间
	public String Sys_resCode;		//平台响应码
	public String merchantId;		//商户id
	public String RouteType;
	public String Server_key;
	public String isEncrypt;
	public String address;		//通道通讯地址
	public String PORT;
	public String Encrypt_algorithm;
	public String Public_key_file;
	public String isSignature;
	public String Signature_algorithm;
	public String Remittance_fee;	
	public String enCodeing;
	public String flowNo;
	public String flatDate;
	public String tranType;
	public String routeType;		//路由类型
	public String svrErrUnpack;		//交易错误拆包
	/**
	 * 初始化sdo信息
	 */
	public void clear(){
		tranFrom=null;
		TRANCODE=null;
		TRAN_OPR=null;
		 CHANNEL_DIY=null;
		OVERTIME=null;       //前端超时时间
		Sub_oprcode=null;
		ordernum=null;
		responseCode=null;	//业务响应码
		systemId=null;
		serverId=null;		//通道id
		serverCode=null;
		server_DIY=null;
		Server_OVERTIME=null;	//后端超时时间
		Sys_resCode=null;		//平台响应码
		merchantId=null;		//商户id
		RouteType=null;
		Server_key=null;
		isEncrypt=null;
		address=null;		//通道通讯地址
		PORT=null;
		Encrypt_algorithm=null;
		Public_key_file=null;
		isSignature=null;
		Signature_algorithm=null;
		Remittance_fee=null;
		enCodeing = null;
		flowNo = null;
		flatDate = null;
		tranType = null;
		routeType = null;
		svrErrUnpack = null;
	}

	@Override
	public CommonSDO clone() {
		CommonSDO sdo = null;
		try {
			sdo = (CommonSDO)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return sdo;
	}
}
