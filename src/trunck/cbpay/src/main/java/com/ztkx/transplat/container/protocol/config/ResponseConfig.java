package com.ztkx.transplat.container.protocol.config;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 协议xml的response标签
 * @author zhangxiaoyun
 *
 */
public class ResponseConfig {
	/**
	 * 报文长度定义
	 * fix:200						固定长度
	 * dynamic_len:str:0,end:5		动态长度
	 * specialc:@@@@				特殊字符结束
	 * over_flag					流结束标志
	 */
	private String policy;
	private int start;					
	private int end;
	private int msgHeadLen;				//消息头长度
	private String over_flag;			//特殊字符结束的标志   结束字符 len是specialc:@@@@的时候用
	private long maxLen;					//报文的最大长度      平安银行的报文长度是10位数，所以改成long类型
	private int readTimeout;		//协议读取响应超时时间
	private String dataParams;		//响应数据标签
	
	//以下属性htts专用
	private String encryption;			//https加密方式
	private String password;			//证书密码
	private String keystoreType;		//证书类型
	private String certificatePath;		//证书路径
	private String algorithm;			//证书算法		ibmX509/X509
	private DecimalFormat df;			//动态头的生成格式
	private int headLen;//定长报文头长度，如果定长报文头不为空的话 报文长度从报文头中截取
	
	public ResponseConfig() {
		
	}
	
	public ResponseConfig( int readTimeout,String dataParams,String encryption,String password,String keystoreType,String certificatePath,String algorithm,int headLen) {
		this.readTimeout = readTimeout;
		this.dataParams = dataParams;
		this.encryption = encryption;
		this.password = password;
		this.keystoreType = keystoreType;
		this.certificatePath = certificatePath;
		this.algorithm = algorithm;
		this.headLen = headLen;
	}
	
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getHeadLen()
	 */
	
	public int getHeadLen() {
		return headLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setHeadLen(int)
	 */
	
	public void setHeadLen(int headLen) {
		this.headLen = headLen;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getEncryption()
	 */
	
	public String getEncryption() {
		return encryption;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setEncryption(java.lang.String)
	 */
	
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getPassword()
	 */
	
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setPassword(java.lang.String)
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getKeystoreType()
	 */
	
	public String getKeystoreType() {
		return keystoreType;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setKeystoreType(java.lang.String)
	 */
	
	public void setKeystoreType(String keystoreType) {
		this.keystoreType = keystoreType;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getCertificatePath()
	 */
	
	public String getCertificatePath() {
		return certificatePath;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setCertificatePath(java.lang.String)
	 */
	
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getAlgorithm()
	 */
	
	public String getAlgorithm() {
		return algorithm;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setAlgorithm(java.lang.String)
	 */
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getDataParams()
	 */
	
	public String getDataParams() {
		return dataParams;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setDataParams(java.lang.String)
	 */
	
	public void setDataParams(String dataParams) {
		this.dataParams = dataParams;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getMsgHeadLen()
	 */
	
	public int getMsgHeadLen() {
		return msgHeadLen;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setMsgHeadLen(int)
	 */
	
	public void setMsgHeadLen(int msgHeadLen) {
		this.msgHeadLen = msgHeadLen;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getOver_flag()
	 */
	
	public String getOver_flag() {
		return over_flag;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setOver_flag(java.lang.String)
	 */
	
	public void setOver_flag(String over_flag) {
		this.over_flag = over_flag;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getStart()
	 */
	
	public int getStart() {
		return start;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setStart(int)
	 */
	
	public void setStart(int start) {
		this.start = start;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getEnd()
	 */
	
	public int getEnd() {
		return end;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setEnd(int)
	 */
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getPolicy()
	 */
	
	public long getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(long maxLen) {
		this.maxLen = maxLen;
	}

	public String getPolicy() {
		return policy;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setPolicy(java.lang.String)
	 */
	
	public void setPolicy(String policy) {
		if(StringUtils.isNotEmpty(policy)){
			this.policy = policy.split(":")[0];
		}
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getReadTimeout()
	 */
	
	public int getReadTimeout() {
		return readTimeout;
	}
	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setReadTimeout(int)
	 */
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#getDf()
	 */
	
	public DecimalFormat getDf() {
		return df;
	}

	/* (non-Javadoc)
	 * @see com.ztkx.transplat.container.protocol.config.ResConfig#setDf(java.text.DecimalFormat)
	 */
	
	public void setDf(DecimalFormat df) {
		this.df = df;
	}
	
}
