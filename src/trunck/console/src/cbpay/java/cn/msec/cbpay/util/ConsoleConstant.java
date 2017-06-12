package cn.msec.cbpay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleConstant {
	public static final String CONFIG_FILE_PATH = "baseConf.properties"; // 配置文件路径
	public static final String TCPSERVERHOST = "tcpserverhost"; // discenter 的ip
	public static final String TCPSERVERPORT = "tcpserverport"; // discenter的监听端口
	public static final String TCPCONNECTTIMEOUT = "tcpconnecttimeout";// 链接超时
	public static final String TCPREADTIMEOUT = "tcpreadtimeout"; // 读超时
	public static final String JOBDATA = "jobdata"; // 读超时
	public static String encoding = "GBK"; // 设定和discenter交互时的编码格式
	
	public static String COL005 = "col005";//账户划转
	public static String COL001 = "col001";//发起付汇
	public static String COL002 = "col002";//确认付汇
	
	public static String TRANSTYPE02 = "02";//
	
	public static String RESPCODE_SUCC = "CBP000";//
	
	public static int num = 0;
	public static String getRondom(){
		Date nowTime=new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss");
		String text = time.format(nowTime)+num;
		num++;
		if(num>100){
			num = 0;
		}
		return text;
	} 
}
