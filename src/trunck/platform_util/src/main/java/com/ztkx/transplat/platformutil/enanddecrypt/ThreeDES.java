package com.ztkx.transplat.platformutil.enanddecrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3des加密工具类
 * 
 * @author liuzhaomin。 update by tianguangzhao 2016/1/27。将加解密公共部分抽出
 *
 */
public class ThreeDES {

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		return des3ENCDecCBC(key, keyiv, data, Cipher.ENCRYPT_MODE);
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		return des3ENCDecCBC(key, keyiv, data, Cipher.DECRYPT_MODE);
	}

	/**
	 * 3des加解密步骤一致，只有一个参数不一致，所以将公共部分抽出
	 * 
	 * @param key
	 * @param keyiv
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] des3ENCDecCBC(byte[] key, byte[] keyiv, byte[] data,
			int mode) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		// 此处如果使用cbc则必须使用此种方式获取cipher.
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(mode, deskey, ips);
		return cipher.doFinal(data);

	}

}
