package com.ztkx.transplat.container.protocol.config;

/**
 * 协议xml配置的common标签
 * @author lenovo
 *
 */
public class CommonConfig {
	private String id;			//协议id
	private String type;		//协议的类型，	
	private String flag;		//client 客户端 		server  服务端
	private String encoding;	//协议编码模式
	private String inOut;		//DataIn或者DataOut或者DataIn/DataOut
	private String model;		//协议模式  syn/asyn
	private int sessionCount;	//连接数，只有长连接起作用
	private String serverId;	//使用当前协议的系统
	private boolean isTowWayverify;	//是否双向验证证书
	
	
	/**
	 * jsm协议专用
	 */
	private String factory;		//初始化链接工厂
	private String url;			//链接url
	private String queueName;	//队列名称
	private int overTime;	//超时时间
	private boolean isTransaction;		//是否开启事务
	private boolean autoAcknowledge;		//是否自动提交
	private String msgListener;
	/**
	 * jms协议专用
	 */
	
	public CommonConfig() {
		
	}
	public CommonConfig(String id, String type, String encoding, String inOut,
			String model, int sessionCount,boolean isTowWayverify,String flag,String factory,String url,String queueName,int overTime,boolean isTransaction,boolean autoAcknowledge,String msgListener) {
		this.id = id;
		this.type = type;
		this.encoding = encoding;
		this.inOut = inOut;
		this.model = model;
		this.sessionCount = sessionCount;
		this.isTowWayverify = isTowWayverify;
		this.flag = flag;
		
		this.factory = factory;
		this.url = url;
		this.queueName = queueName;
		this.overTime = overTime;
		this.isTransaction = isTransaction;
		this.autoAcknowledge = autoAcknowledge;
		this.msgListener = msgListener;
	}
	
	public String getMsgListener() {
		return msgListener;
	}
	public void setMsgListener(String msgListener) {
		this.msgListener = msgListener;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public int getOverTime() {
		return overTime;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public boolean isTransaction() {
		return isTransaction;
	}
	public void setTransaction(boolean isTransaction) {
		this.isTransaction = isTransaction;
	}
	public boolean isAutoAcknowledge() {
		return autoAcknowledge;
	}
	public void setAutoAcknowledge(boolean autoAcknowledge) {
		this.autoAcknowledge = autoAcknowledge;
	}
	public boolean isTowWayverify() {
		return isTowWayverify;
	}
	public void setTowWayverify(boolean isTowWayverify) {
		this.isTowWayverify = isTowWayverify;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getSessionCount() {
		return sessionCount;
	}
	public void setSessionCount(int sessionCount) {
		this.sessionCount = sessionCount;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
