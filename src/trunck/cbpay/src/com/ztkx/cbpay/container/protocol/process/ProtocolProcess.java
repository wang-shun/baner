package com.ztkx.cbpay.container.protocol.process;

import java.util.Map;

import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 容器内所有协议的实现接口
 * @author zhangxiaoyun
 *
 */
public interface ProtocolProcess {
	
	public void start(ProtocolConfig conifg);
	public byte[] send(CommonContext context) throws Exception;
	public byte[] send(CommonContext context,Map<String,String> pro) throws Exception;
	public void stop();
	public void setProtocolConfig(ProtocolConfig protocolConfig);
	public ProtocolConfig getProtocolConfig();
	public void setStatus(boolean status);
	public boolean isStart();
}
