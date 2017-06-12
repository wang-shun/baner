package com.ztkx.cbpay.business.constant;

/**
 * 错误码常量类
 * 
 * @author tianguangzhao
 *
 */
public class BusinessErrorCodeConstant {
	/**
	 * 订单状态相关错误
	 */
	public static final String BUSS_PLA000500 = "PLA000500";// 订单重复
	public static final String BUSS_PLA000501 = "PLA000501";// 订单不存在
	public static final String BUSS_PLA000502 = "PLA000502";// 订单已经开始购汇，不能重复购汇
    public static final String BUSS_PLA000503 = "PLA000503";// 订单购汇未完成，不能付汇
    public static final String BUSS_PLA000504 = "PLA000504";// 订单已开始付汇，不能重复付汇
    public static final String BUSS_PLA000505 = "PLA000505";// 购汇前账户划转不成功，不能购汇
    public static final String BUSS_PLA000506 = "PLA000506";// 付汇前账户划转不成功，不能付汇
    public static final String BUSS_PLA000507 = "PLA000507";// 订单未完成支付
    
    /**
     * 商户相关错误
     */
	public static final String BUSS_PLA000508 = "PLA000508";// 商户不存在
	public static final String BUSS_PLA000509 = "PLA000509";// 商户不支持此币种
	public static final String BUSS_PLA000510 = "PLA000510";// 商户没有此权限
	public static final String BUSS_PLA000511 = "PLA000511";// (某商户的)用户信息不存在
    public static final String BUSS_PLA000512 = "PLA000512";// (某商户的)用户卡信息不存在
    public static final String BUSS_PLA000513 = "PLA000513";// 用户信息重复
    public static final String BUSS_PLA000514 = "PLA000514";// 商户信息重复 
    
    /**
     * 系统级别错误
     */
    public static final String BUSS_PLA000515 = "PLA000515";// 查询数据异常
	public static final String BUSS_PLA000516 = "PLA000516";// 业务服务执行异常
	public static final String BUSS_PLA000517 = "PLA000517";// 插入数据异常
	public static final String BUSS_PLA000518 = "PLA000518";// 更新数据异常
	public static final String BUSS_PLA000519 = "PLA000519";// 转码失败
	public static final String BUSS_PLA000520 = "PLA000520";// 系统特殊处理失败
	
	/**
	 * 对账相关错误
	 */
	public static final String BUSS_PLA000521 = "PLA000521";// 对账失败
	public static final String BUSS_PLA000522 = "PLA000522";// 平台多交易
	public static final String BUSS_PLA000523 = "PLA000523";// 平台少交易
	public static final String BUSS_PLA000524 = "PLA000524";// 交易状态不一致
	public static final String BUSS_PLA000525 = "PLA000525";// 已经完成对账
	
	/**
	 * 数据错误
	 */
	public static final String BUSS_PLA000526 = "PLA000526";// 汇率表里没有最近两天数据 
	public static final String BUSS_PLA000527 = "PLA000527";// 文件状态错误
	public static final String BUSS_PLA000528 = "PLA000528";// 获取最小汇率服务方异常
	public static final String BUSS_PLA000529 = "PLA000529";// 账户划转报文准备异常
    public static final String BUSS_PLA000530 = "PLA000530";// 支付流水不存在
    public static final String BUSS_PLA000531 = "PLA000531";// 账户划转信息不存在
    
    
    /**
     * 报文校验错误
     */
    public static final String BUSS_PLA000532 = "PLA000532";// 商户支付报文中没有币种
    public static final String BUSS_PLA000533 = "PLA000533";// 交易状态错误
    public static final String BUSS_PLA000534 = "PLA000534";// 账户划转类型错误
    public static final String BUSS_PLA000535 = "PLA000535";// 商户上送tradecode不合法
    public static final String BUSS_PLA000536 = "PLA000536";// 商户报文验签失败
    public static final String BUSS_PLA000537 = "PLA000537";// 对账摘要校验失败
    public static final String BUSS_PLA000538 = "PLA000538";// 文件类型错误不能完成路由
    public static final String BUSS_PLA000539 = "PLA000539";// 报文类型错误
    public static final String BUSS_PLA000540 = "PLA000540";// 文件不存在
}
