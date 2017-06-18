package com.ztkx.transplat.platformutil.baseconfig;

/**
 * 配置相关常量定义
 * 
 * @author zhangxiaoyun
 *
 */
public class ConstantConfigField {

	public static final String CONTEXTPOOLSIZE = "contextPoolSize"; // 交易上下文初始化大小配置
	public static final String FLOWNOPOOLSIZE = "flowNoPoolSize"; // 流水号缓冲池大小配置
	public static final String THREADPOOLSIZE = "threadPoolSize"; // 交易上下文初始化大小配置
	public static final String CONFIGPATH = "container_path"; // 配置主目录
	public static final String HOMEDIR = "HOME_DIR";
	public static final String MSGLISTERNER = "messagelistener"; // 消息接收器
	public static final String INIT_DATACONFIGFILE = "init.xml"; // 加载初始化数据配置文件
	public static final String PRE_LOADCONFIGFILE = "preload.xml"; // 加载初始化数据配置文件
	public static final String INIT_SERVICECONFIGFILE = "services.xml";
	public static final String ACTIVEMQ_CONFIGFILE = "ActiveMQconfig.xml"; // 消息接收器
																			// //消息接收器

	public static final String MQ_QUEUE_SEPARATOR = "_"; // 队列key的分隔字符
	public static final String MQ_RECONNECTION_INTERVAL = "mq.reconnection.interval"; // mq重连建个
	/**
	 * 数据库里配置
	 */
	public static final String SERVICE_TYPE_BUS = "businessservice";// 业务服务
	public static final String SERVICE_TYPE_BASE = "baseservice";// 基础服务
	public static final String SERVICE_TYPE_PRO = "protocolservice";// 协议服务

	public static final String DB_POOLCONFIGFILE = "dbpool.xml";
	public static final String DB_MYBATISCONFIGFILE = "mybatis.xml";
	public static final String KEYMSG_CONFIGFILE = "keymsg.xml";
	public static final String MODULES_CONFIGFILE = "modules.xml";
	public static final String INVOKER_CONFIGFILE = "invokers.xml";

	/**
	 * 容器名称
	 */
	public static final String CONTAINER_NAME_IN = "IN";
	public static final String CONTAINER_NAME_CASHIER = "CASHIER";
	public static final String CONTAINER_NAME_OUT = "OUT";
	public static final String CONTAINER_NAME_PROCESS = "PROCESS";
	public static final String CONTAINER_NAME_DISCENTER = "DISCENTER";
	public static final String CONTAINER_NAME_CONSOLE = "CONSOLE";

	/**
	 * TABLE 相关的常量
	 */
	public static final String CONF_XML_FORMATE_TYPE_PACK = "1"; // 消息接收器
	public static final String CONF_XML_FORMATE_TYPE_UNPACK = "0"; // 消息接收器
	public static final String APP_ADDRESS_TYPE_IN = "IN";
	public static final String APP_ADDRESS_TYPE_OUT = "OUT";
	public static final String APP_ADDRESS_APPSTATUS_ON = "1"; // 应用状态ok
	public static final String APP_ADDRESS_APPSTATUS_OFF = "0"; // 应用状态关闭
	
	
	public static final String TRANS_INFO_TRAN_TYPE_1 = "1"; // 同步交易
	public static final String TRANS_INFO_TRAN_TYPE_2 = "2"; // 异步交易

	public static final String TABLE_PRI_KEY_SEPARATOR = "_"; // 队列key的分隔字符
	public static final String ISVALID = "1"; // 有效标示
	public static final String NOTVALID = "0"; // 失效标示
	public static final String TABLE_VALUE_SEPARATOR_COMMA = ",";// 逗号分隔符
	public static final String TABLE_VALUE_SEPARATOR_BAR = "|";
	/**
	 * baseConf.properties中配置项
	 */
	public static final String BASECONF_CONTAINER_NAME = "CONTAINER_NAME"; // 当前容器名称
	public static final String BASECONF_CLUSTER = "cluster"; // 集群节点
	public static final String OVER_TIME = "JMS_MESSAGE_OVER_TIME"; // jms信息的超时时间
	public static final String TRANS_LOG_SWITCH = "TRANS_LOG_SWITCH";// 是否需要将交易信息记录到数据库中的标识，如果为"on"则插入报文日志
	public static final String TRANS_INFO_SWITCH = "TRANS_INFO_SWITCH";// 是否需要将交易信息（包括服务方返回的）记录到数据库中的标识，如果为"on"则插入报文日志
	public static final String TRANS_TIMESTAMP_SWITCH = "TRANS_TIMESTAMP_SWITCH";// 是否需要将交易的处理进度更新到数据库中。如果为"on"则更新交易处理进度
	public static final String CERTIFICATE_PTAH = "CERTIFICATE_PTAH"; // 存放证书和秘钥库的位置
	/**
	 * Rout_Type 路由类型
	 */
	public static final String ROUTETYPE_AMT = "AMT"; // 交易额
	public static final String ROUTETYPE_MER = "MER"; // 商户id
	public static final String ROUTETYPE_RAT = "RAT"; // 按汇率
	public static final String ROUTETYPE_TRA = "TRA"; // 按交易
	
	/**
	 * Route_Type 路由主键类型
	 */
	public static final String ROUTEKEY_MER = "MER"; // 商户
	public static final String ROUTEKEY_TCD = "TCD"; //交易码
	/**
	 * Rate_policy 汇率策略
	 */
	public static final String RATE_POLICE_MAX = "3"; // 最大汇率
	public static final String RATE_POLICE_MIN = "1"; // 最小汇率
	/**
	 * 主程序进行的的状态key
	 */
	public static final String CONTAINER_IN_CURRENT_STEP = "in_current_step";
	public static final String CONTAINER_OUT_CURRENT_STEP = "out_current_step";

	/**
	 * 判断baseservice是否必须运行
	 */
	public static final String CONTAINER_MUST_RUN_TRUE = "true";
	public static final String CONTAINER_MUST_RUN_FALSE = "false";
	/**
	 * 错误码
	 */
	public static final String CONTAINER_ERROR_CODE = "errorcode";
	public static final String CONTAINER_RESPONSE_CODE = "responsecode";
	public static final String ORIGINAL_MSG = "original_msg"; // 原始报文
	public static final String ORIGINAL_MSG_BYTE_ARRAY = "original_msg_byte_array"; // 原始报文byte数组
	public static final String RETURN_MESSAGE = "return_msg"; // 消息内容,响应报文
	/**
	 * 商户id
	 */
	public static final String SDO_MERCHANTID = "merchantId";

	/**
	 * 
	 */
	public final static String MSG_PROPERTIES= "msg_properties";
	/**
	 * 用于一个平台在本系统的转换（例如汇率下发银行做客户端，查询汇率时银行作为服务方）
	 */
	public final static String CLIENT_FLAG = "_CLI";
	public final static String SERVER_FLAG = "_SVR";
	
	
	/**
	 * 流水号配置
	 */
	public final static String FLOWNO_STEPLENGTH = "steplength";		//每路的步长
	public final static String FLOWNO_CURRENTSEQUENCE = "currentsequence";	//当前序列号
	public final static String FLOWNO_INITSEQUENCE = "initsequence";	//当前序列号
	public final static String FLOWNO_MAXSEQUENCE = "maxsequence";	//当前序列号
	/**
	 * 
	 */
	public final static String DEFAULT_TRANFROM_ERROR = "ANY";  //错误码转化时没有找到有效数据时，注入默认查找服务方
	
	/**
	 * 命令相关常量
	 */
	public final static String CONTAINID = "containid";
	public final static String SOURCE_CONTAINID = "source_containid";
	public static String RONDOM = "rondom";
	
	/**
	 * ftp配置使用
	 */
	public final static String FTPUSERNAME = "ftpUserName";
	public final static String FTPPWD = "ftppwd";
	public final static String FTPSERVERHOST = "ftpServerHost";	//ftp主机
	public final static String FTPSERVERPORT = "ftpServerPort";	//ftp主机端口
	public final static String FTPCONNECTIONTIMEOUT = "ftpConnectionTimeOut"; // ftp链接超时时间
	public final static String DATATRANSFERTIMEOUT = "dataTransferTimeOut"; // 文件传输超时时间
	public final static String LOCAL_FILE_PATH = "local_file_path"; // 本地文件路径
	public final static String REMOTE_FILE_PATH = "remote_file_path"; // 远端文件路径
}
