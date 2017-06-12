package com.ztkx.transplat.container.protocol.config;


/**
 * 协议的配置接口，没有任何具体方法只是为了平台内部数据传输统一接口
 * @author zhangxiaoyun
 *
 */
public interface ProtocolConfig {

	public  CommonConfig getCommonConfig();

	public void setCommonConfig(CommonConfig commonConfig);

	public RequestConfig getRequestConfig();

	public void setRequestConfig(RequestConfig requestConfig);

	public ResponseConfig getResponseConfig();

	public void setResponseConfig(ResponseConfig responseConfig);

}