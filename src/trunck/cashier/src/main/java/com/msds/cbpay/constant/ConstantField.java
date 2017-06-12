package com.msds.cbpay.constant;

public class ConstantField {
	public final static String METHOD_POST = "POST";
	public final static String CHS001 = "chs001";
	public final static String CHS002 = "chs002";
	public final static String CHS003 = "chs003";
	public final static String CHS004 = "chs004";
	public final static String CHS005 = "chs005";
	public final static String CHS006 = "chs006";
	public final static String CHS007 = "chs007";
	public final static String CHS008 = "chs008";
	public final static String CHS009 = "chs009";
	public final static String SUCC = "CBP000";
	public final static String PARAM_SEPARATE = "=";
	public final static String TAG_TRANCODE_BEGIN="<tranCode>";
	public final static String TAG_TRANCODE_END="</tranCode>";
	public final static String TAG_SECRET_BEGIN="<secret>";
	public final static String TAG_SECRET_END="</secret>";
	public final static String TAG_MSG_BEGIN="<message>";
	public final static String TAG_MSG_END="</message>";
	public final static String XML_HEAD="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/**
	 * 支付结果
	 */
	public final static String RESULT_WF="01";
	public final static String RESULT_SU="02";
	public final static String RESULT_JS="00";
	public final static String TRANCODE_PAY="cbp001";
	
	public final static String MSG_TYPE_REQ="0001";
	public final static String MSG_TYPE_REP="0002";
	public final static String PARAM="param";
	
	public final static String STATUS_SU = "00";
	public final static String STATUS_CD = "01";
	
	public final static String  BUYBAT_YES= "0";
	public final static String  BUYBAT_NO= "1";
	
	public final static String DEFAULT_ERROR_RESPCODE="CBP019";
	public final static String DEFAULT_ERROR_RESPDES="系统错误";
	public final static String MSG_ERROR_RESPCODE_CBP002="CBP002";
	public final static String MSG_ERROR_RESPDES_CBP002="账户余额不足";
	public final static String MSG_ERROR_RESPCODE_CBP003="CBP003";
	public final static String MSG_ERROR_RESPDES_CBP003="输入信息有误";
	public final static String MSG_ERROR_RESPCODE_CBP004="CBP004";
	public final static String MSG_ERROR_RESPDES_CBP004="无效卡";
	public final static String MSG_ERROR_RESPCODE_CBP014="CBP004";
	public final static String MSG_ERROR_RESPDES_CBP014="报文错误";
}
