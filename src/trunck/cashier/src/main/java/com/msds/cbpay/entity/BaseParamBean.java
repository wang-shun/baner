package com.msds.cbpay.entity;

public class BaseParamBean {
	private int connectTimeout;
	private int readTimeout;
	private int discenter_port;
	private String discenter_host;
	private String encode;
	private String file_encode;
	private String suffix;
	private String param;
	private int maxLength;
	private int msgheadlength;
	private String nowbuybat;
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public int getDiscenter_port() {
		return discenter_port;
	}
	public void setDiscenter_port(int discenter_port) {
		this.discenter_port = discenter_port;
	}
	public String getDiscenter_host() {
		return discenter_host;
	}
	public void setDiscenter_host(String discenter_host) {
		this.discenter_host = discenter_host;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public int getMsgheadlength() {
		return msgheadlength;
	}
	public void setMsgheadlength(int msgheadlength) {
		this.msgheadlength = msgheadlength;
	}
	public String getNowbuybat() {
		return nowbuybat;
	}
	public void setNowbuybat(String nowbuybat) {
		this.nowbuybat = nowbuybat;
	}
	public String getFile_encode() {
		return file_encode;
	}
	public void setFile_encode(String file_encode) {
		this.file_encode = file_encode;
	}
	
}
