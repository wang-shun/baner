package com.ztkx.transplat.platformutil.time;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author tianguangzhao
 * update by zhangxiaoyun 2016年1月11日14:17:30
 * 1.工具类没必要做成单利
 * 2.新增getCurrentTime方法
 */

public class TimeUtil {
	static Logger logger = Logger.getLogger(TimeUtil.class);
	private TimeUtil() {

	}

	public static String dateFormate(String formate,Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}
	/**
	 * 获取当前时间
	 * @param pattern 时间格式
	 * @return
	 */
	public static String getCurrentTime(String pattern){
		if(pattern == null){
			pattern = "yyyyMMdd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public static long getCurrentTime(){
		return new Date().getTime();
	}
	
	public static Timestamp getTimeStamp(){
		return new Timestamp(getCurrentTime());
	}
	
	/**
	 * 获取前一天日期
	 * @return
	 */
	public static String getLastDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		return sdf.format(calendar.getTime());
	}
	

	/**
	 * 判断时间是否超时
	 * 
	 * @param sendTime
	 *            ，发送时间
	 * @param TimeoutMillisecond
	 *            ，超时时长（毫秒）
	 * @return 如果现在已经超时返回false，未超时返回true
	 */
	public static  boolean compareWithCurrentTime(long sendTime, long timeoutMillisecond) {
		Date overTime = new Date(sendTime + timeoutMillisecond);
		Date nowTime = new Date();
		if (logger.isDebugEnabled()) {
			logger.debug("overTime = " + overTime.toString()
					+ " | nowTime= " + nowTime.toString());
		}
		if (overTime.after(nowTime)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否超时
	 * 
	 * @param sendTime
	 *            ，发送时间
	 * @param TimeoutMillisecond
	 *            ，超时时长（毫秒）
	 * @return 如果现在已经超时返回true，未超时返回false
	 */
	public static  boolean isOverTime(long startTime, long timeoutMillisecond) {
		long currentTime = System.currentTimeMillis();
		boolean isOverTime = false;
		isOverTime = startTime+timeoutMillisecond < currentTime;
		if(isOverTime){
			logger.error("currentTime is ["+currentTime+"] startTime is ["+startTime+"] timeoutMillisecond is ["+timeoutMillisecond+"]");
		}
		return isOverTime;
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getCurrentTime("HH:mm:ss"));
		System.out.println(getLastDate());
	}
}
