package com.ztkx.transplat.container.constant;

/**
 * 容器常量类
 * 
 * @author zhangxiaoyun
 *
 */
public class ContainerConstantField {
	public static String TRANFROM = "tranfrom"; // 交易来源
	public static String TRANFCODE = "trancode"; // 交易码
	public static String JMS_MSG_OBJ = "jms_msg_obj"; // jms消息
//	public static String ORIGINAL_MSG = "original_msg"; // 原始报文
	public static String FLOW_NO = "flow_no"; // 平台流水号
	public static String TRAN_DATE = "tran_date"; // 平台日期
	public static String MSG_ORDER = "msg_order"; // 消息顺序
	public static String MSG_SECRET = "secret"; // 消息顺序
	public static String FINAL_MESSAGE = "fianl_msg"; // 消息内容,即解密后得到的最终报文
	// public static String OUT_PROTOCOL_ID = "out_protocol_id"; // 接出协议id

	/**
	 * 利用ActiveMQ交互信息时需要的参数名称
	 */
	public static String MESSAGE_BUFFER_POOL_SIZE = "message_buffer_pool_size"; // 消息缓冲池大小
	public static String MESSAGE_OVER_TIME = "message_over_Time"; // 消息超时时间
	public static String JMS_MESSAGE_ID = "jms_message_id"; // 消息id
	public static final String JMS_MESSAGE = "jms_message"; // jmsmessage,在in和out中传递的信息，在context中的key值
	public static final String JMS_MESSAGE_FROM = "jmsfrom"; // jms的message报文中存放消息源的字段名,在容器发送信息时需要将本模块的名称注入该字段中
	public final static String PROTOCOL_SERVICE_NAME_IN_OUT = "in_out"; // in容器向out容器发消息时服务名称
	public final static String PROTOCOL_SERVICE_NAME_IN_DISCENTER = "in_discenter"; // 向discenter容器发消息时服务名称
	public final static String PROTOCOL_SERVICE_NAME_OUT_IN = "out_in"; // out容器向in容器发消息时服务名称
	public final static String PROTOCOL_SERVICE_NAME_CONSOLE_OTHER = "console_other"; // out容器向in容器发消息时服务名称
	public final static String PROTOCOL_SERVICE_NAME = "protocol_service_name"; // 想mq上发送消息的服务名称

	/**
	 * 错误码
	 */
	public static final String CONTAINER_IN_ERROR_CODE = "errorcode";

	/**
	 * 消息顺序
	 */
	public final static String MSG_ORDER_FIRST = "1";
	public final static String MSG_ORDER_SECEND = "2";
	public final static String MSG_ORDER_THREE = "3";
	public final static String MSG_ORDER_FOUR = "4";
	/**
	 * 协议id
	 */
	public final static String PROTOCOL_ID = "protocolid";

	/**
	 * url发送协议判断
	 */
	public final static String PARAMS = "params";
	public final static String URL_PARAMS = "url_params";

	public final static String REQ_URL = "req_url"; // servlet的请求url
	/**
	 * 错误级别
	 */
	public final static String ERROR_LEVEL = "error_level";

	// 时间格式
	public static final String TIMESTAMP_FORMATE = "yyyyMMddHHmmss"; // 数据库时间戳的格式
	public static final String PLA_TIME_FORMATE = "HHmmss"; // 数据库保存时间的格式
	public static final String PLA_DATE_FORMATE = "yyyyMMdd"; // 数据库保存日期的格式的格式
	
	
	
}
