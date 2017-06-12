package com.ztkx.cbpay.invoker;


public class InvokerConstant {
	/**
	 * 命令键
	 */
	public static String flag = "flag";
	public static String appid = "appid";
	public static String appstatus = "appstatus";
	public static String result = "result";
	public static String servicekey = "servicekey";
	
	public static String command = "command";
	
	
	/**
	 * 命令之
	 */
	public static String succ = "succ";
	public static String fail = "fail";

	/**
	 * 重载表数据相关
	 */
	public static final String OPR_PRELOAD = "preload"; // 预加载数据
	public static final String OPR_ROLLBAK = "rollbak"; // 重载失败时，回滚命令
	public static final String OPR_RELOAD = "reload"; // 重新加载数据，确认命令
	
	/**
	 * 分隔符号
	 */
	public static final String SEPARATE_ONE = "::";
	public static final String SEPARATE_TWO = "__";
	public static final String SEPARATE_THREE = "-";
}
