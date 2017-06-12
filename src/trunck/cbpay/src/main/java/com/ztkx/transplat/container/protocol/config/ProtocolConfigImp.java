package com.ztkx.transplat.container.protocol.config;


/**
 * 通讯协议的xml配置
 * @author zhangxiaoyun
 *
 */
public class ProtocolConfigImp implements ProtocolConfig {
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	
	@Override
	public CommonConfig getCommonConfig() {
		return commonConfig;
	}
	@Override
	public void setCommonConfig(CommonConfig commonConfig) {
		this.commonConfig = commonConfig;
	}
	@Override
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
	@Override
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	@Override
	public ResponseConfig getResponseConfig() {
		return responseConfig;
	}
	@Override
	public void setResponseConfig(ResponseConfig responseConfig) {
		this.responseConfig = responseConfig;
	}
}
