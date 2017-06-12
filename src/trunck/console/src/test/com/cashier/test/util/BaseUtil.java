package com.cashier.test.util;


/**
 * 基础工具类，提供在编码和调试时用到的比较频繁的操作�?
 * 
 * @author tianguangzhao
 *
 */
public class BaseUtil {

	/**
	 * 为传入的字符串左端补�?”，�?��到达到长度要�?
	 * 
	 * @param src
	 *            原字符串
	 * @param length
	 *            �?��长度
	 * @return String �?��字符�?
	 */
	public static String fillZeroLeft(String src, int length) {

		if (src == null || length == 0) {
			return src;
		} else if (src.length() == length) {
			return src;
		} else if (src.length() < length) {
			StringBuffer zeroStr = new StringBuffer();
			int loopNum = length - src.length();
			for (int i = 0; i < loopNum; i++) {
				zeroStr.append("0");
			}
			return zeroStr.toString() + src;
		} else {
			return src;
		}
	}

	/**
	 * 为传入的字符串右端补�?”，�?��到达到长度要�?
	 * 
	 * @param src
	 *            原字符串
	 * @param length
	 *            �?��长度
	 * @return String �?��字符�?
	 */
	public static String fillZeroRight(String src, int length) {

		if (src == null || length == 0) {
			return src;
		} else if (src.length() == length) {
			return src;
		} else if (src.length() < length) {
			StringBuffer zeroStr = new StringBuffer();
			int loopNum = length - src.length();
			for (int i = 0; i < loopNum; i++) {
				zeroStr.append("0");
			}
			return src + zeroStr.toString();
		} else {
			return src;
		}
	}
}
