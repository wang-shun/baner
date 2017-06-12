/**
 * 
 */
package com.cashier.test.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64编码解码工具�?Copyright: Copyright (c) 2014 Create Date: 2014�?�?7�?Company:
 * www.minshengec.com
 * 
 * @author luxueqiang@minshengec.com
 * @version $Id: Base64Util.java,v 1.0 Eric.Lu Exp $
 */
public class Base64Util {
	/**
	 * Base64编码
	 * <p>
	 * Create Date: 2014�?�?7�?
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] encode(byte[] str) {
		byte[] result = null;

		if (str != null) {
			result = Base64.encodeBase64(str);
			// result = Base64.encodeBase64URLSafe(str) ;
		}
		return result;
	}

	/**
	 * Base64解密
	 * <p>
	 * Create Date: 2014�?�?7�?
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
}
