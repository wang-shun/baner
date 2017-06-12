package com.ztkx.cbpay.business.constant;

/**
 * 报文格式常量类
 * 
 * @author tianguangzhao
 *
 */
public class BusinessMessageFormateConstant {

	/**
	 * 平台中自己用于向context中注入值的常量
	 */
	public static final String CBPAY_PURCHASER_ID = "purchaserId";// 购买者标识;

	/**
	 * 宝易互通报文格式常量公共报文头部分
	 */
	public static final String UMB_MSG_TYP = "msgtype"; // 报文类型，请求报文是0001，相应报文0002.
	public static final String UMB_MERCHANT_NO = "merchantno"; // 商户号
	public static final String UMB_CHANNEL_NO = "channelno"; // 渠道号
	public static final String UMB_TRAN_DATE = "trandate"; // 交易日期
	public static final String UMB_TRAN_TIME = "trantime"; // 交易时间
	public static final String UMB_BUSS_FLOW_NO = "bussflowno"; // 交易流水号
	public static final String UMB_RESP_CODE = "respcode"; // 响应码
	public static final String UMB_RESP_MSG = "respmsg"; // 响应码

	/**
	 * 收银台公共报文头
	 */
	public static final String CASH_TRAN_CODE = "tranCode";// 交易码
	public static final String CASH_MERCHANT_NO = "merchantNo";// 商户号
	public static final String CASH_ORDER_ID = "orderId";// 订单号
	public static final String CASH_TRAN_DATE = "tranDate";// 交易日期
	public static final String CASH_TRAN_TIME = "tranTime";// 交易时间
	public static final String CASH_BUSS_FLOW_NO = "bussFlowNo";// 交易流水号
	public static final String CASH_RESP_CODE = "respCode";// 响应码
	public static final String CASH_RESP_MSG = "respMsg";// 响应信息

	/**
	 * 定时任务公共报文头
	 * 
	 */
	public static final String CTB_TRAN_CODE = "tranCode";// 交易码
	public static final String CTB_TRAN_DATE = "trandate";// 交易日期
	public static final String CTB_TRAN_TIME = "trantime";// 交易时间
	public static final String CTB_FLOW_NO = "flowno";// 交易流水号
	public static final String CTB_RESP_CODE = "respcode";// 响应码
	public static final String CTB_RESP_MSG = "respmsg";// 返回信息

	/**
	 * console发起交易的公共报文头
	 */
	public static final String CONSOLE_TRAN_CODE = "tranCode";// 交易码
	public static final String CONSOLE_TRAN_DATE = "trandate";// 交易日期
	public static final String CONSOLE_TRAN_TIME = "trantime";// 交易时间
	public static final String CONSOLE_FLOW_NO = "flowno";// 交易流水号
	public static final String CONSOLE_RESP_CODE = "respcode";//响应码
	public static final String CONSOLE_RESP_MSG = "respmsg";// 响应报文
	
	/**
	 * 支付交易用到的宝易互通字段名称trancode cp0035 - > cp0001 
	 * update by tianguangzhao 2016/4/22 cp0035是测试暂时使用的，最终的交易码为cp0001
	 */
	public static final String UMB_CP0001_MER_PLAT_ACCT_ALIAS = "merPlatAcctAlias";// 商户平台别名
	public static final String UMB_CP0001_PROTOCOL_NO = "merPlatAcctAlias";// 协议号
	public static final String UMB_CP0001_BANK_NAME = "bankName";// 银行名称
	public static final String UMB_CP0001_ACCOUNT_NO = "accountNo";// 银行账号
	public static final String UMB_CP0001_ACCOUNT_NAME = "accountName";// 银行账户名称
	public static final String UMB_CP0001_ACCOUNT_TYPE = "accountType";// 账户类型
	public static final String UMB_CP0001_OPEN_PROVINCE = "openProvince";// 开户行所在省
	public static final String UMB_CP0001_OPEN_CITY = "openCity";// 开户行所在市
	public static final String UMB_CP0001_OPEN_NAME = "openName";// 开户行名称
	public static final String UMB_CP0001_TRANAMT = "tranAmt";// 交易金额
	public static final String UMB_CP0001_CURTYPE = "curType";// 交易币种
	public static final String UMB_CP0001_BSN_TYPE = "bsnType";// 业务类型
	public static final String UMB_CP0001_CERT_TYPE = "certType";// 开户证件类型
	public static final String UMB_CP0001_CERT_NO = "certNo";// 证件号
	public static final String UMB_CP0001_MOBILE_NO = "mobileNo";// 手机号
	public static final String UMB_CP0001_PRODUCT_INFO = "prodInfo";// 商品信息
	public static final String UMB_CP0001_MSG_EXT = "msgExt";// 附加信息
	// 以下为返回报文
	public static final String UMB_CP0001_TRAN_STATE = "tranState";// 交易状态
	public static final String UMB_CP0001_PAY_FLOW_NO = "payjnlno";// 原交易流水号
																	// 宝易互通支付流水号，是否返回待确定
	/**
	 * 宝易互通验证码下发交易 cp0032
	 */
	public static final String UMB_CP0032_ACCOUNT_NAME = "accountName";// 账户名称
	public static final String UMB_CP0032_MOBILE_NO = "mobileNo";// 手机号码
	public static final String UMB_CP0032_CURRENT_TRAN_CODE = "currentTranCode";// 当前交易码
	public static final String UMB_CP0032_VERIFY_CODE_TYPE = "verifyCodeType";// 验证码输出类型
	// 以下为返回报文
	public static final String UMB_CP0032_PHONE_TOKEN = "phoneToken";// 手机验证码令牌
	public static final String UMB_CP0032_PHONE_VER_CODE = "phoneVerCode";// 手机验证码。已废弃
	/**
	 * 宝易互通用户实名认证交易 cp0031 -> cp0030 
	 * update by tianguangzhao 测试用交易码改为实际交易码
	 */
	public static final String UMB_CP0030_BANK_NAME = "bankName";// 银行名称
	public static final String UMB_CP0030_ACCOUNT_NO = "accountNo";// 银行名称
	public static final String UMB_CP0030_ACCOUNT_NAME = "accountName";// 账户名称
	public static final String UMB_CP0030_ACCOUNT_TYPE = "accountType";// 账户类型
	public static final String UMB_CP0030_CERT_TYPE = "certType";// 开户证件类型
	public static final String UMB_CP0030_CERT_NO = "certNo";// 开户证件号
	public static final String UMB_CP0030_MOBILE_NO = "mobileNo";// 手机号
	public static final String UMB_CP0030_PHONE_VER_CODE = "phoneVerCode";// 手机验证码
	public static final String UMB_CP0030_PHONE_TOKEN = "phoneToken";// 手机验证码令牌
	// 以下为响应报文
	public static final String UMB_CP0030_TRAN_STATE = "tranState";// 交易状态

	/**
	 * 宝易互通收单交易查询，cp0033 -> cp0002
	 * update by tianguangzhao 2016/4/22 测试交易码改为实际交易码,并增加商户号
	 */
	public static final String UMB_CP0002_MERCHANT_NO = "merchantNo";// 商户号
	public static final String UMB_CP0002_ORGTRANFLOW = "orgTranFlow";// 原交易流水号
	// 以下为响应报文
	public static final String UMB_CP0002_TRAN_STATE = "tranState";// 交易状态
	//update by tianguangzhao 2016/4/26 宝易互通不会返回该字段，去掉即可
	//public static final String UMB_CP0002_PAY_FLOW_NO = "payjnlno";// 原交易流水号
	
	//update by tiangunagzhao 2016/4/29 新增报文体中的响应码和相应信息字段
	public static final String UMB_CP0002_TRAN_RESP_CODE = "tranRespCode";// 响应码
	public static final String UMB_CP0002_TRAN_RESP_MSG = "tranRespMsg";// 响应信息

	/**
	 * 宝易互通账户划转交易查询，cp0036 - > cp0040
	 * update by tianguangzhao  测试交易码改为实际交易码 
	 */
	//update by tianguangzhao 修改原交易流水号字段名称为merOrderId
	//public static final String UMB_CP0040_ORGTRANFLOW = "orgTranFlow";// 原交易流水号
	public static final String UMB_CP0040_ORGTRANFLOW = "merOrderId";// 原交易流水号
	// 以下为响应报文
	public static final String UMB_CP0040_TRAN_STATE = "state";// 交易状态
	public static final String UMB_CP0040_PAY_FLOW_NO = "payjnlno";//宝易互通交易流水号  // 宝易互通支付流水号，是否返回待确定

	/**
	 * 宝易互通账户划转交易,cp0034 - > cp0039
	 * update by tiangunagzhao 2016/4/22 测试交易码改为实际交易码
	 */
	public static final String UMB_CP0039_LIST_SIZE = "listsize";// 循环次数,宝易互通不需要
	public static final String UMB_CP0039_CYCLE_LABEL = "list";// 循环标签
	public static final String UMB_CP0039_PAY_ACCT = "payeracct";// 付款账号
	public static final String UMB_CP0039_PAY_NAME = "payname";// 付款户名
	public static final String UMB_CP0039_REC_NAME = "recname";// 收款户名
	public static final String UMB_CP0039_AMOUNT = "amount";// 金额
	public static final String UMB_CP0039_REC_ACCT = "payeeacct";// 收款款账号
	public static final String UMB_CP0039_CURRENCY = "currency";// 币种
	public static final String UMB_CP0039_TRANS_FLOW_NO = "subflowno";// 账户划转子流水号
	public static final String UMB_CP0039_TRANS_MERCHANT_NO = "transMerchantNo";//划转交易涉及的商户的编号
	public static final String UMB_CP0039_EXRATE = "exrate";// 汇率
	public static final String UMB_CP0039_BAK = "bak";// 备注
	//update by tianguangzhao 2016/4/22 新增收付款人商户号
	public static final String UMB_CP0039_REC_CLIENT = "payeeclient";// 收款人商户号
	public static final String UMB_CP0039_PAY_CLIENT = "payerclient";// 付款人商户号
	// 以下为返回报文
	public static final String UMB_CP0039_TRAN_STATE = "tranState";// 交易状态
	public static final String UMB_CP0039_PAY_FLOW_NO = "payjnlno";// 宝易互通交易流水号  // 宝易互通支付流水号，是否返回待确定
	
	/**
	 * 交易流水对账cp0023,
	 */
	public static final String UMB_CP0023_REQ_CYCLE_LABLE = "orderList";// 请求报文中循环开始标签
	public static final String UMB_CP0023_REQ_ORDER_SIZE = "orderSize";// 请求报文中循环条数
	public static final String UMB_CP0023_REQ_ROW_LABLE = "row";// 请求报文中每次循环对应的标签
	public static final String UMB_CP0023_SUB_MERCHANT_NO = "merchantNo";// 商户号，body标签中的商户号表名该笔订单的所属商户，报文头中的商户号为平台商户号
	public static final String UMB_CP0023_SUB_TRAN_CODE = "tranCode";// 交易码，cp0001为代收，cp0003,代收，all表示全部交易
	public static final String UMB_CP0023_TRAN_STATE = "tranState";// 交易状态，00，受理成功，01，支付成功，02，处理中，03，处理失败
	public static final String UMB_CP0023_START_DATE = "startDate";// 开始日期
	public static final String UMB_CP0023_END_DATE = "endDate"; // 结束日期

	// 以下是返回报文
	//public static final String UMB_CP0023_RSP_CYCLE_NUM = "listSize"; // 返回报文中的循环标签个数，暂时未用到
	public static final String UMB_CP0023_RSP_CYCLE_LABLE = "dataList"; // 返回报文中的循环标签名称
	public static final String UMB_CP0023_RSP_DATA_SIZE = "dataSize"; // 返回报文中的循环条数
	public static final String UMB_CP0023_RSP_ROW_LABLE = "row"; // 返回报文中每次循环对应的标签名
	public static final String UMB_CP0023_TOTAL_NUM = "totalNum";// 数据条数
	public static final String UMB_CP0023_HASH_CODE = "hashCode";// 内容摘要
	public static final String UMB_CP0023_DATA = "data"; // 数据内容
	
	/**
	 * 账户划转流水对账
	 */
	public static final String UMB_CP0025_REQ_CYCLE_LABLE = "orderList";// 请求报文中循环开始标签
	public static final String UMB_CP0025_REQ_ORDER_SIZE = "orderSize";// 请求报文中循环条数
	public static final String UMB_CP0025_REQ_ROW_LABLE = "row";// 请求报文中每次循环对应的标签
	public static final String UMB_CP0025_SUB_MERCHANT_NO = "merchantNo";// 商户号，body标签中的商户号表名该笔订单的所属商户，报文头中的商户号为平台商户号
	public static final String UMB_CP0025_ACCTNO = "acctNo";// 虚账户，账户号
	public static final String UMB_CP0025_START_DATE = "startDate";// 开始日期
	public static final String UMB_CP0025_END_DATE = "endDate"; // 结束日期

	// 以下是返回报文
	//public static final String UMB_CP0025_RSP_CYCLE_NUM = "listSize"; // 返回报文中的循环标签个数，暂时未用到
	public static final String UMB_CP0025_RSP_CYCLE_LABLE = "dataList"; // 返回报文中的循环标签名称
	public static final String UMB_CP0025_RSP_DATA_SIZE = "dataSize"; // 返回报文中的循环条数
	public static final String UMB_CP0025_RSP_ROW_LABLE = "row"; // 返回报文中每次循环对应的标签名
	public static final String UMB_CP0025_TOTAL_NUM = "totalNum";// 数据条数
	public static final String UMB_CP0025_HASH_CODE = "hashCode";// 内容摘要
	public static final String UMB_CP0025_DATA = "data"; // 数据内容
	/**
	 * 收银台发起收单交易shs006
	 */
	public static final String CASH_CHS006_ACCOUNT_NO = "accountNo";// 账号
	public static final String CASH_CHS006_PHONE_TOKEN = "phoneToken";// 验证码
	// 以下为响应报文
	public static final String CASH_CHS006_TRAN_STATE = "tranState";// 交易状态

	/**
	 * 收银台发起收单交易shs004
	 */
	public static final String CASH_CHS004_BANK_NAME = "bankName";// 银行名称
	public static final String CASH_CHS004_ACCOUNT_NO = "accountNo";// 账号
	public static final String CASH_CHS004_ACCOUNT_NAME = "accountName";// 账户名称
	public static final String CASH_CHS004_ACCOUNT_TYPE = "accountType";// 账户类型
	public static final String CASH_CHS004_CERT_TYPE = "certType";// 开户证件类型
	public static final String CASH_CHS004_CERT_NO = "certNo";// 开户证件号
	public static final String CASH_CHS004_MOBILE_NO = "mobileNo";// 手机号
	public static final String CASH_CHS004_PHONE_TOKEN = "phoneToken";// 手机验证码令牌
	public static final String CASH_CHS004_EMAIL = "email";// 邮箱地址
	public static final String CASH_CHS004_NICK_NAME = "nickName";// 昵称，暂时未用到.
	public static final String CASH_CHS004_PURCHASER_ID = "purchaserId";// 购买者标识
	// 以下为响应报文
	public static final String CASH_CHS004_TRAN_STATE = "tranState";// 交易状态

	/**
	 * 收银台发起短信验证码下发chs005
	 */
	public static final String CASH_CHS005_FLOW_NO = "verJnlNo";// 验证流水号，未用到
	public static final String CASH_CHS005_CHANNEL_NO = "verChnl";// 验证渠道，暂时未用到
	public static final String CASH_CHS005_ACCOUNT_NAME = "accountName";// 户名
	public static final String CASH_CHS005_MOBILE_NO = "mobileNo";// 手机号
	public static final String CASH_CHS005_VER_TYP = "verifyCodeType";// 验证类型，用户注册，或支付

	/**
	 * 收银台发起短信验证码下发chs008
	 */
	public static final String CASH_CHS008_ACCOUNT_NO = "accountNo";// 账号
	public static final String CASH_CHS008_PHONE_TOKEN = "phoneToken";// 验证码
	public static final String CASH_CHS008_PURCHASER_ID = "purchaserId";// 购买者标识

	/**
	 * 定时任务发起的支付交易查询 ctb002
	 */
	public static final String CTB_CTB002_ORG_TRAN_FLOW = "orgTranFlow";// 原交易流水号
	public static final String CTB_CTB002_MERCHANT_NO = "merchantNo";// 商户号
	// 以下为响应报文
	public static final String CTB_CTB002_TRAN_STATE = "tranState";// 交易状态

	/**
	 * 定时任务发起的账户划转报文 ctb003,请求报文部分待补充
	 */
	public static final String CTB_CTB003_PLA_MERCHANT_NO="merchantNo" ;//平台
	public static final String CTB_CTB003_TRANS_TYPE = "transType";//请求类型 
	public static final String CTB_CTB003_LIST_SIZE = "listSize";// 循环条数
	public static final String CTB_CTB003_CYCLE_LABLE = "orderList";// 循环标签
	public static final String CTB_CTB003_MERCHANT_NO = "merchantId";// 商户号
	public static final String CTB_CTB003_ORDER_DATE = "orderDate";// 订单日期
	public static final String CTB_CTB003_ORDER_NO = "orderId";// 订单号
	public static final String CTB_CTB003_BAK = "bak";// 备注
	// 以下为响应报文
	public static final String CTB_CTB003_TRAN_STATE = "tranState";// 交易状态
	
	/**
	 * 定时任务发起的账户划转交易查询 ctb004
	 */
	public static final String CTB_CTB004_TRANS_TYPE = "transType";//请求类型 
	public static final String CTB_CTB004_ORG_TRAN_FLOW = "orgTranFlow";// 原交易流水号
	// 以下为响应报文
	public static final String CTB_CTB004_TRAN_STATE = "tranState";// 交易状态

	
	/**
	 * 定时任务发起交易流水对账ctb005
	 */
	public static final String CTB_CTB005_JOB_DATE = "jobdate";// 任务日期
	public static final String CTB_CTB005_JOB_TYPE = "jobType";// 对账类型
	public static final String CTB_CTB005_FILE_NAME = "filename";// 文件名称

	/**
	 * 定时任务发起账户划转对账ctb006
	 */
	public static final String CTB_CTB006_JOB_DATE = "jobdate";// 任务日期
	public static final String CTB_CTB006_FILE_NAME = "filename";// 文件名称
	
	/**
	 * console发起交易流水对账col003
	 */
	public static final String CONSOLE_COL003_ORG_FLOW_NO = "jnlno";// 原交易流水号
	
	/**
	 * console发起交易流水对账col004
	 */
	public static final String CONSOLE_COL004_ORG_FLOW_NO = "jnlno";// 原交易流水号
}
