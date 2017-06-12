package com.ztkx.cbpay.container.protocol.process;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.protocol.ProtocolUtil;
import com.ztkx.cbpay.container.protocol.config.CommonConfig;
import com.ztkx.cbpay.container.protocol.config.ProtocolConfig;
import com.ztkx.cbpay.container.protocol.config.RequestConfig;
import com.ztkx.cbpay.container.protocol.config.ResponseConfig;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class HTTPSClientProcessImp implements ProtocolProcess{
	
	static Logger logger = Logger.getLogger(HTTPSClientProcessImp.class);
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
		
		SSLContext sslcontext = getSSLContext(conifg);
		requestConfig.setSslcontext(sslcontext);
		
		logger.info(conifg.getCommonConfig().getId()+" protocol start success");
	}
	
	@Override
	public void stop(){
		isStart = false;
		this.protocolConfig = null;
		logger.info(commonConfig.getId()+" protocol already stop");
	}
	
	@Override
	public byte[] send(CommonContext context) throws Exception {
		if(!isStart){
			logger.error("protocol not startup ");
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000005);
			throw new Exception("protocol not startup");
		}
		String ctype = "application/x-www-form-urlencoded;charset=" + protocolConfig.getCommonConfig().getEncoding();
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
		//获取链接
		HttpsURLConnection connect = ProtocolUtil.getHttpsConnection(protocolConfig,ctype,file);
		
		OutputStream out = connect.getOutputStream();
		byte[] msg = context.getOrginalField().getBytes(commonConfig.getEncoding());
		try{
			ProtocolUtil.writebyHttp(protocolConfig, msg,out);
		}finally{
			out.close();
		}
		
		byte[] resMsg = null;
		
		InputStream errIn = connect.getErrorStream();
		InputStream in = connect.getInputStream();
		if (errIn != null) {
			try{
				resMsg = ProtocolUtil.getErrorMsg(errIn);
			}finally{
				errIn.close();
			}
			
			if(resMsg!=null){
				throw new IOException("recive error msg is "+new String(resMsg));
			}else{
				throw new IOException(connect.getResponseCode() + ":" + connect.getResponseMessage());
			}
		} else {
			try{
				resMsg = ProtocolUtil.read(protocolConfig,in);
			}finally{
				in.close();
			}
		}
		//TODO 资源释放
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
	public byte[] send(CommonContext context, Map<String, String> pro) throws Exception {
		// TODO 不需要实现
		return null;
	}
	/**
	  * 获得KeyStore.
	  * @param keyStorePath
	  *            密钥库路径
	  * @param password
	  *            密码
	  * @return 密钥库
	  * @throws Exception
	  */
	 public static KeyStore getKeyStore(String password, String keyStorePath,String keystoreType)
	   throws Exception {
	  // 实例化密钥库
	  KeyStore ks = KeyStore.getInstance(keystoreType);
	  // 获得密钥库文件流
	  logger.info("keyStorePath["+keyStorePath+"]");
	  FileInputStream is = new FileInputStream(keyStorePath);
	  // 加载密钥库
	  ks.load(is, password.toCharArray());
	  // 关闭密钥库文件流
	  is.close();
	  return ks;
	 }
	 
	 /**
	  * 获得SSLSocketFactory.
	  * @param password
	  *            密码
	  * @param keyStorePath
	  *            密钥库路径
	  * @param trustStorePath
	  *            信任库路径
	  * @return SSLSocketFactory
	  * @throws Exception
	  */
	public static SSLContext getSSLContext(ProtocolConfig config){
		try{  
		// 实例化密钥库
		  KeyManagerFactory keyManagerFactory = KeyManagerFactory
		    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
		  // 获得密钥库
		  KeyStore keyStore = getKeyStore(config.getRequestConfig().getPassword(), config.getRequestConfig().getCertificatePath(),config.getRequestConfig().getKeystoreType());
		  // 初始化密钥工厂
		  keyManagerFactory.init(keyStore, config.getRequestConfig().getPassword().toCharArray());
		// 实例化SSL上下文
		  SSLContext ctx = SSLContext.getInstance(config.getRequestConfig().getEncryption());
		  if(config.getCommonConfig().isTowWayverify()){
			  // 实例化信任库
			  TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			  // 获得信任库
			  KeyStore trustStore = getKeyStore(config.getResponseConfig().getPassword(), config.getResponseConfig().getCertificatePath(),config.getRequestConfig().getKeystoreType());
			  // 初始化信任库
			  trustManagerFactory.init(trustStore);
			// 初始化SSL上下文
			  ctx.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(), new SecureRandom());
		  }else{
			  //不验证全部信任
			  ctx.init(keyManagerFactory.getKeyManagers(),new TrustManager[]{new My509TrustManager()}, new SecureRandom());
		  }
		  
		  //ctx.init(new KeyManager[0], new TrustManager[0], null);
		  // 获得SSLSocketFactory
		  return ctx;
		}catch(Exception e){
			logger.error("load SSLContext is error", e);
		}
		return null;
	 }
	
}
