/**
 * 
 */
package com.msds.cbpay.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64缂栫爜瑙ｇ爜宸ュ叿绫�Copyright: Copyright (c) 2014 Create Date: 2014骞�鏈�7鏃�Company:
 * www.minshengec.com
 * 
 * @author luxueqiang@minshengec.com
 * @version $Id: Base64Util.java,v 1.0 Eric.Lu Exp $
 */
public class Base64Util {
	/**
	 * Base64缂栫爜
	 * <p>
	 * Create Date: 2014骞�鏈�7鏃�
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String charset;
	public static byte[] encode(byte[] str) {
		byte[] result = null;

		if (str != null) {
			result = Base64.encodeBase64(str);
			// result = Base64.encodeBase64URLSafe(str) ;
		}
		return result;
	}

	/**
	 * Base64瑙ｅ瘑
	 * <p>
	 * Create Date: 2014骞�鏈�7鏃�
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] decode(byte[] str) {
		byte[] result = null;
		if (str != null) {
			result = Base64.decodeBase64(str);
		}
		return result;
	}
	
	
	public static String encode(String str) {
		String result = null;
		if (str != null) {
			try {
				result = new String(Base64.encodeBase64(str.getBytes(charset)),charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public static String decode(String str) {
		String result = null;
		if (str != null) {
			try {
				result = new String(Base64.decodeBase64(str.getBytes(charset)),charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
