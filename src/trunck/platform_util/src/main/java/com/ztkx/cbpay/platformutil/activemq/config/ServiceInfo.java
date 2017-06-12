package com.ztkx.cbpay.platformutil.activemq.config;

/**
 * 存放启动ActiveMQ服务的相关参数信息
 * 
 * @author 田光照 2015/12/22
 *
 */
public class ServiceInfo {

	// 服务名称
	private String serviceName;
	// 队列名称
	private String queueName;
	// url地址
	private String url;
	// 需要建立的session的数量
	private int sessionCounts;
	// jms的conectionFactory的具体类。方便后期替换中间件时使用。
	private String Factory;
	//消息超时时间
	private int overTime;
	//是否自动确认
	private boolean autoAcknowledge;
	//是否开启事务
	private boolean isTransaction;
	
	//消息监听器
	private String msgListener;
	
	/**
	 * sunyoushan
	 * 添加消息选择器--common使用
	 */
	//消息选择器
	private String selector;
	
	public String getMsgListener() {
		return msgListener;
	}

	public void setMsgListener(String msgListener) {
		this.msgListener = msgListener;
	}

	public String getServiceName() {
		return serviceName;
	}

	public boolean isTransaction() {
		return isTransaction;
	}

	public void setTransaction(boolean isTransaction) {
		this.isTransaction = isTransaction;
	}

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}

	public boolean isAutoAcknowledge() {
		return autoAcknowledge;
	}

	public void setAutoAcknowledge(boolean autoAcknowledge) {
		this.autoAcknowledge = autoAcknowledge;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSessionCounts() {
		return sessionCounts;
	}

	public void setSessionCounts(int sessionCounts) {
		this.sessionCounts = sessionCounts;
	}

	public String getFactory() {
		return Factory;
	}

	public void setFactory(String factory) {
		Factory = factory;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	@Override
	public String toString() {
		return "ServiceInfo [serviceName=" + serviceName + ", queueName="
				+ queueName + ", url=" + url + ", sessionCounts="
				+ sessionCounts + ", Factory=" + Factory + ", overTime="
				+ overTime + ", autoAcknowledge=" + autoAcknowledge
				+ ", isTransaction=" + isTransaction + ", msgListener="
				+ msgListener + ", selector=" + selector + "]";
	}
	
}
