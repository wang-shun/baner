package com.ztkx.cbpay.container.protocol.config;

import java.text.DecimalFormat;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;

/**
 * 协议xml的request标签
 * 
 * @author zhangxiaoyun
 *
 */
public class RequestConfig {
	private String host; // 请求地址
	private int port; // 请求端口
	/**
	 * 报文长度定义 fix:200 固定长度 dynamic_len:str:0,end:5 动态长度 specialc:@@@@ 特殊字符结束
	 * over_flag 流结束标志
	 */
	private String policy;
	private int start;
	private int end;
	private long maxLen; // 报文的最大长度
	private int msgHeadLen; // 消息头长度
	private String over_flag; // 特殊字符结束的标志 结束字符 len是specialc:@@@@的时候用

	private int connectTimeout; // 连接超时时间
	private String file; // 请求文根类型，
	private String fileName; // 具体请求文根 update by tianguangzhao 20160308
	private String method; // http请求方法
	private String dataParams; // 请求数据标签

	// 以下属性htts专用
	private String encryption; // https加密方式
	private String password; // 证书密码
	private String keystoreType; // 证书类型
	private String certificatePath; // 证书路径
	private String algorithm; // 证书算法 ibmX509/X509

	private SSLContext sslcontext; // 生成证书

	private DecimalFormat df;// 动态头的生成格式
	
	private int headLen;//定长报文头长度，如果定长报文头不为空的话 报文长度从报文头中截取
	public RequestConfig() {

	}

	public RequestConfig(String host, int port, String encryption,
			int connectTimeout, String file, String method, String dataParams,
			String password, String keystoreType, String certificatePath,
			String algorithm,int headLen) {
		super();
		this.host = host;
		this.port = port;
		this.encryption = encryption;
		this.connectTimeout = connectTimeout;
		this.file = file;
		this.method = method;
		this.dataParams = dataParams;
		this.password = password;
		this.keystoreType = keystoreType;
		this.certificatePath = certificatePath;
		this.algorithm = algorithm;
		this.headLen = headLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getHeadLen()
	 */
	public int getHeadLen() {
		return headLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setHeadLen(int)
	 */
	
	public void setHeadLen(int headLen) {
		this.headLen = headLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getAlgorithm()
	 */
	
	public String getAlgorithm() {
		return algorithm;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setAlgorithm(java.lang.String)
	 */
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getDataParams()
	 */
	
	public String getDataParams() {
		return dataParams;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setDataParams(java.lang.String)
	 */
	
	public void setDataParams(String dataParams) {
		this.dataParams = dataParams;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getMsgHeadLen()
	 */
	
	public int getMsgHeadLen() {
		return msgHeadLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setMsgHeadLen(int)
	 */
	
	public void setMsgHeadLen(int msgHeadLen) {
		this.msgHeadLen = msgHeadLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getMethod()
	 */
	
	public String getMethod() {
		return method;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setMethod(java.lang.String)
	 */
	
	public void setMethod(String method) {
		this.method = method;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getOver_flag()
	 */
	
	public String getOver_flag() {
		return over_flag;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setOver_flag(java.lang.String)
	 */
	
	public void setOver_flag(String over_flag) {
		this.over_flag = over_flag;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getStart()
	 */
	
	public int getStart() {
		return start;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setStart(int)
	 */
	
	public void setStart(int start) {
		this.start = start;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getEnd()
	 */
	
	public int getEnd() {
		return end;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setEnd(int)
	 */
	
	public void setEnd(int end) {
		this.end = end;
	}
	public long getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(long maxLen) {
		this.maxLen = maxLen;
	}
	public String getHost() {
		return host;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setHost(java.lang.String)
	 */
	
	public void setHost(String host) {
		this.host = host;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getPort()
	 */
	
	public int getPort() {
		return port;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setPort(int)
	 */
	
	public void setPort(int port) {
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getPolicy()
	 */
	
	public String getPolicy() {
		return policy;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setPolicy(java.lang.String)
	 */
	
	public void setPolicy(String len) {
		if(StringUtils.isNotEmpty(len)){
			this.policy = len.split(":")[0];
		}
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getEncryption()
	 */
	
	public String getEncryption() {
		return encryption;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setEncryption(java.lang.String)
	 */
	
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getConnectTimeout()
	 */
	
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setConnectTimeout(int)
	 */
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getFile()
	 */
	
	public String getFile() {
		return file;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setFile(java.lang.String)
	 */
	
	public void setFile(String file) {
		this.file = file;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getFileName()
	 */
	
	public String getFileName() {
		return fileName;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setFileName(java.lang.String)
	 */
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getPassword()
	 */
	
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setPassword(java.lang.String)
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getKeystoreType()
	 */
	
	public String getKeystoreType() {
		return keystoreType;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setKeystoreType(java.lang.String)
	 */
	
	public void setKeystoreType(String keystoreType) {
		this.keystoreType = keystoreType;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getCertificatePath()
	 */
	
	public String getCertificatePath() {
		return certificatePath;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setCertificatePath(java.lang.String)
	 */
	
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getSslcontext()
	 */
	
	public SSLContext getSslcontext() {
		return sslcontext;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setSslcontext(javax.net.ssl.SSLContext)
	 */
	
	public void setSslcontext(SSLContext sslcontext) {
		this.sslcontext = sslcontext;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#getDf()
	 */
	
	public DecimalFormat getDf() {
		return df;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.cbpay.container.protocol.config.ReqConfig#setDf(java.text.DecimalFormat)
	 */
	
	public void setDf(DecimalFormat df) {
		this.df = df;
	}

}
