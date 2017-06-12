package com.ztkx.cbpay.platformutil.msg;

/**
 * keymsg.xml文件中一条配置的描述
 * @author zhangxiaoyun
 *
 */
public class KeyMsgDescriber {
	private String id ;		//来源id
	private String from ;	//交易码从报文中拆取还是直接从消息头里的属性中获取
	private String type ;	//当前是服务还是渠道
	private String format ;	//报文格式
	private String encoding ;	//报文编码
	private KeyField keyField;	//交易码字段
	private String identify;	//识别交易码特殊处理的id
	
	
	
	public KeyMsgDescriber() {
		
	}
	public KeyMsgDescriber(String id, String from, String type, String format,String encoding,String identify) {
		this.id = id;
		this.from = from;
		this.type = type;
		this.format = format;
		this.encoding = encoding;
		this.identify = identify;
	}
	
	
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public KeyField getKeyField() {
		return keyField;
	}
	public void setKeyField(KeyField keyField) {
		this.keyField = keyField;
	}
	@Override
	public String toString() {
		return "KeyMsgDescriber [id=" + id + ", from=" + from + ", type="
				+ type + ", format=" + format + ", encoding=" + encoding
				+ ", keyField=" + keyField + ", identify=" + identify + "]";
	}
	
	
}
