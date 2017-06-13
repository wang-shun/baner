package org.inn.baner.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 此工具类用于计算hash值，返回相应的字符串
 * @author tianguangzhao
 *
 */
public class HashUtil {

	static Logger logger = Logger.getLogger(HashUtil.class);

	/**
	 * 通过SHA计算hash值
	 *
	 * @param data 原数据
	 * @param type  hashcode类型
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getSHA(String data, String encode,String type) throws Exception {
		byte[] bytes = null;
		if (data == null || data.equals("")) {
			return bytes;
		} else {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(type);
				// 传入要加密的字符串
				if (StringUtils.isEmpty(encode)) {
					messageDigest.update(data.getBytes());
				} else {
					messageDigest.update(data.getBytes(encode));
				}
				// 得到类型结果
				bytes = messageDigest.digest();
			} catch (NoSuchAlgorithmException e) {
				logger.error("type error !",e);
				throw e;
			} catch (UnsupportedEncodingException e) {
				logger.error("encode error ! ",e);
				throw e;
			}
		}

		return bytes;
	}

	
}
