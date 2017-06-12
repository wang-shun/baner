package com.ztkx.cbpay.container.protocol;

/**
 * 协议配置有关的xml标签和字段
 * @author lenovo
 *
 */
public class ProtocolConstantField {
	
	
	/**
	 * 协议类型
	 */
	public final static String PROTOCOL_TYPE_HTTP_CLIENT="httpclient";
	public final static String PROTOCOL_TYPE_HTTP_SERVER="httpserver";
	public final static String PROTOCOL_TYPE_HTTPS_CLIENT="httpsclient";
	public final static String PROTOCOL_TYPE_HTTPS_SERVER="httpsserver";
	public final static String PROTOCOL_TYPE_TCP_CLIENT="tcpclient";
	public final static String PROTOCOL_TYPE_JMS_CLIENT="jmsclient";
	
	
	/**
	 * 报文长度策略
	 */
	public final static String LEN_STREAM_OVER_FLAG="stream_over_flag";
	public final static String POLICY_FIX="fix";
	public final static String POLICY_DYNAMIC_LEN="dynamic_len";
	public final static String POLICY_SPECIALC="specialc";
	public final static String POLICY_STREAM_OVER_FLAG="stream_over_flag";
	public final static String POLICY_DATAPARAM="dataparam";
	
	/**
	 * 协议标志
	 */
	public final static String FLAG_CLIENT="client";
	public final static String FLAG_SERVER="server";
	
	public final static String ATTR_LEN_SEPARATOR=":";
	
	public final static String LABEL_COMMON="common";		
	public final static String LABEL_REQUEST="request";
	public final static String LABEL_RESPONSE="response";
	
	public final static String ATTR_ID="id";
	public final static String ATTR_TYPE="type";
	public final static String ATTR_INOUT="inOut";
	public final static String ATTR_MODEL="mode";
	public final static String ATTR_ENCODING="encoding";
	public final static String ATTR_SESSIONCOUNT="sessionCount";
	public final static String ATTR_ISTOWWAYVERIFY="isTowWayverify";
	public final static String ATTR_FLAG="flag";
	public final static String ATTR_HOST="host";
	public final static String ATTR_PORT="port";
	public final static String ATTR_POLICY="policy";
	public final static String ATTR_FILE="file";
	public final static String ATTR_ENCRYPTION="encryption";
	public final static String ATTR_CONNECTTIMEOUT="connectTimeout";
	public final static String ATTR_READTIMEOUT="readTimeout";
	public final static String ATTR_METHOD="method";
	public final static String ATTR_DATAPARAMS="dataParams";
	public final static String ATTR_PASSWORD="password";
	public final static String ATTR_KEYSTORETYPE="keystoreType";
	public final static String ATTR_CERTIFICATEPATH="certificatePath";
	public final static String ATTR_ALGORITHM="algorithm";
	public final static String ATTR_HEADLEN="headLen";
	
	/**
	 * jms协议专用
	 */
	public final static String ATTR_FACTORY="factory";
	public final static String ATTR_URL="url";
	public final static String ATTR_QUEUENAME="queueName";
	public final static String ATTR_OVERTIME="overTime";
	public final static String ATTR_ISTRANSACTION="isTransaction";
	public final static String ATTR_AUTOACKNOWLEDGE="autoAcknowledge";
	public final static String ATTR_MSGLISTENER="messageListener";
	/**
	 * jms协议专用
	 */
}
