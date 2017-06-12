package com.ztkx.transplat.container.protocol.parser;

import com.ztkx.transplat.container.protocol.config.ProtocolConfig;

/**
 * 将xml配置解析为协议的config
 * @author zhangxiaoyun
 *
 */
public interface ProtocolConfParser {
	public ProtocolConfig parse(String xml) throws Exception;
}
