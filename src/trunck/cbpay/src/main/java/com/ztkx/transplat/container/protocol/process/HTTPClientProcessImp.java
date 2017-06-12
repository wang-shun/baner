package com.ztkx.transplat.container.protocol.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.protocol.ProtocolUtil;
import com.ztkx.transplat.container.protocol.config.CommonConfig;
import com.ztkx.transplat.container.protocol.config.ProtocolConfig;
import com.ztkx.transplat.container.protocol.config.RequestConfig;
import com.ztkx.transplat.container.protocol.config.ResponseConfig;
import com.ztkx.transplat.platformutil.context.CommonContext;

public class HTTPClientProcessImp implements ProtocolProcess {

	Logger logger = Logger.getLogger(HTTPClientProcessImp.class);
	private ProtocolConfig protocolConfig = null;
	private CommonConfig commonConfig;
	private RequestConfig requestConfig;
	private ResponseConfig responseConfig;
	private boolean isStart = false;

	@Override
	public void start(ProtocolConfig conifg) {
		isStart = true;
		this.protocolConfig = conifg;
		commonConfig = this.protocolConfig.getCommonConfig();
		requestConfig = this.protocolConfig.getRequestConfig();
		responseConfig = this.protocolConfig.getResponseConfig();
		logger.info(conifg.getCommonConfig().getId()
				+ " protocol start success");
	}

	@Override
	public void stop() {
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId() + " protocol already stop");
	}

	@Override
	public byte[] send(CommonContext context) throws Exception {
		if (!isStart) {
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}
		String ctype = "application/x-www-form-urlencoded;charset="
				+ protocolConfig.getCommonConfig().getEncoding();
		
		String file = null;
		/**
		 * 当起一次请求后，如果file的值配置为param，则file的值会被改变，下次进入该方法时，file的值为上一次请求的URL_PARAMS
		 * 。不在是param。 再将URL_PARAMS的值注入到file中
		 */
		if (ContainerConstantField.PARAMS.equals(protocolConfig.getRequestConfig().getFile())) {
			file = context.getFieldStr(ContainerConstantField.URL_PARAMS);
		} else {
			file = protocolConfig.getRequestConfig().getFile();
		}
		// 获取链接
		HttpURLConnection connect = ProtocolUtil.getHttpConnection(protocolConfig, ctype,file);

		OutputStream out = connect.getOutputStream();
		byte[] msg = context.getOrginalField().getBytes(commonConfig.getEncoding());
		try {
			ProtocolUtil.writebyHttp(protocolConfig, msg, out);
		} catch (IOException e) {
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000006);
			return new byte[0];
		} finally {
			out.close();
		}

		byte[] resMsg = null;

		InputStream errIn = connect.getErrorStream();
		InputStream in = connect.getInputStream();
		if (errIn != null) {
			try {
				resMsg = ProtocolUtil.getErrorMsg(errIn);
			} finally {
				errIn.close();
			}

			if (resMsg != null) {
				throw new IOException("recive error msg is "+ new String(resMsg));
			} else {
				throw new IOException(connect.getResponseCode() + ":"+ connect.getResponseMessage());
			}
		} else {
			try {
				resMsg = ProtocolUtil.read(protocolConfig, in);
			} finally {
				in.close();
			}
		}
		// TODO资源没有释放

		return resMsg;
	}

	@Override
	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	@Override
	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	@Override
	public void setStatus(boolean status) {
		this.isStart = status;
	}

	@Override
	public boolean isStart() {
		return isStart;
	}

	@Override
	public byte[] send(CommonContext context, Map<String, String> pro)
			throws Exception {
		// TODO 不需要实现
		return null;
	}
}
