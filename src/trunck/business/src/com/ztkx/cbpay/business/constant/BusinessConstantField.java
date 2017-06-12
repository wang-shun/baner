package com.ztkx.cbpay.business.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BusinessConstantField {
	public static BigDecimal divideValue = new BigDecimal(100);// 收单汇率习惯以100为单位
	public static int scale = 2;// 计算金额后小数点保留的位数。
	public static RoundingMode roundMode = RoundingMode.UP;// 保留位数后的舍入模式
	public static String China_currency = "CNY"; // 人民币币种
	public static int BATCHNUM = 30; // 批量执行sql的时候提交数量

	/**
	 * 订单状态
	 */
	public static String ORDER_WF = "00";// 等待付款
	public static String ORDER_PD = "01";// 支付完成
	public static String ORDER_CZ = "02";// 订单关闭
	public static String ORDER_EX = "03";// 订单过期
	public static String ORDER_CA = "04";// 交易取消
	public static String ORDER_RE = "05";// 订单退款

	/**
	 * 订单购汇状态
	 */
	public static String PURCHASESTATUS_00 = "00";// 未购汇
	public static String PURCHASESTATUS_01 = "01";// 购汇登记中
	public static String PURCHASESTATUS_02 = "02";// 购汇登记成功
	public static String PURCHASESTATUS_03 = "03";// 购汇登记失败
	public static String PURCHASESTATUS_04 = "04";// 待交割
	public static String PURCHASESTATUS_05 = "05";// 购汇失败
	public static String PURCHASESTATUS_06 = "06";// 购汇成功

	/**
	 * 付汇状态
	 */
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_00 = "00";// 未付汇
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_01 = "01";// 生成付汇文件
																	// generate
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_02 = "02";// 付汇登记中
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_03 = "03";// 付汇登记成功
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_04 = "04";// 付汇登记失败
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_05 = "05";// 付汇待交割
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_06 = "06";// 付汇失败
	public static String B_MERCHANT_ORDER_PAYBATSTATUS_07 = "07";// 付汇成功
	// 订单支付状态
	public static final String TRADING_INIT = "00"; // 交易支付初始化
	public static final String TRADING_PROCESSING = "01"; // 支付处理中
	public static final String TRADING_SUCCESS = "02"; // 支付成功
	public static final String TRADING_FAILED = "03"; // 支付失败

	// 时间格式
	public static final String TIMESTAMP_FORMATE = "yyyyMMddHHmmss"; // 数据库时间戳的格式
	public static final String PLA_TIME_FORMATE = "HHmmss"; // 数据库保存时间的格式
	public static final String PLA_DATE_FORMATE = "yyyyMMdd"; // 数据库保存日期的格式的格式
	
	/**
	 * 平台参数表常量
	 */
	public static final String PLA_NAME = "name"; //平台参数表中的name字段
	public static final String PLA_ADDRESS = "address"; //平台参数表中的address字段

	public static final String MERCHANT_ORDER_OBJ = "merchant_order_obj"; // 商户订单对象
	public static final String PAB_FILE_SEPERATOR = "|$|"; // 商户订单对象

	public static final String MSG_TYPE_REQ = "0001"; // 请求类型
	public static final String MSG_TYPE_REP = "0002"; // 相应类型

	public static final String PAB_BECIF = "BECIF"; // 外联客户代码，由用户分配
	public static final String PAB_OUTSIDE_CUST_NO = "OUTSIDE_CUST_NO"; // 外联客户号
	public static final String ISTRANSFERFILE = "istransferfile"; // 是否传输文件

	// 用户实名认证时，支付交易需要进行实名认证时的对应交易码
	public static final String PAY_CERTIFIED_TRANCODE = "chs008"; //

	// 对账文件存放位置
	public static final String CHECKING_FILE_PATH = "CHECKING_FILE_PATH"; // 配置文件中，对账文件目录对应的参数名称
	// 平安银行渠道前处理的流水号
	public static final String PAB_MSGHEAD_FLOWNO = "pab_msghead_flowno";
	// 平安银行报文头中日期字段
	public static final String PAB_TRAN_DATE = "pab_tran_date";
	public static final String PAB_TRAN_TIME = "pab_tran_time";
	public static final String PAB_RES_CODE = "pab_res_code";
	public static final String PAB_RES_MSG = "pab_res_msg";
	// 平安银行报文头
	public static final String PAB_MSG_HEAD = "pab_msg_head";

	// 宝易互通交易状态码
	public static final String UMB_TRADING_INIT = "00"; // 交易支付初始化
	public static final String UMB_TRADING_SUCCESS = "01"; // 支付成功
	public static final String UMB_TRADING_PROCESSING = "02"; // 支付处理中
	public static final String UMB_TRADING_FAILED = "03"; // 支付失败
}
