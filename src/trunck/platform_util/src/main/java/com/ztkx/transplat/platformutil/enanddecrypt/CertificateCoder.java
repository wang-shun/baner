package com.ztkx.transplat.platformutil.enanddecrypt;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

public class CertificateCoder {

	// 证书的类型X509
	public static final String CERT_TYPE = "X.509";

	static Logger logger = Logger.getLogger(CertificateCoder.class);

	/**
	 * 从秘钥库中获取私钥
	 * 
	 * @param ks
	 *            秘钥库
	 * @param alias
	 *            生成秘钥库时指定的别名
	 * @param password
	 *            生成秘钥库时指定的密码
	 * @return PrivateKey 私钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyByKeyStore(KeyStore ks, String alias,
			String password) throws Exception {
		PrivateKey privateKey = null;
		try {
			// 获取外部秘钥库
			privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
		} catch (Exception e) {
			logger.error("getPrivateKeyByKeyStore error ! ", e);
			throw e;
		}

		return privateKey;
	}

	/**
	 * 通过证书获取公钥
	 * 
	 * @param certificate
	 *            电子证书
	 * @return PublicKey 公钥
	 */
	private static PublicKey getPublicKeyByCertificate(Certificate certificate) {
		PublicKey publicKey = null;

		try {
			publicKey = certificate.getPublicKey();
		} catch (Exception e) {
			logger.error("get PublicKey from Certificate error !", e);
		}

		return publicKey;
	}

	/**
	 * 从外部文件获取证书
	 * 
	 * @param certificatePath
	 *            证书路径
	 * @return Certificate 证书实例
	 * @throws Exception
	 */
	public static Certificate getCertificate(String certificatePath)
			throws Exception {
		Certificate certificate = null;

		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance(CERT_TYPE);
			FileInputStream in = new FileInputStream(certificatePath);
			certificate = (Certificate) certificateFactory
					.generateCertificate(in);
			in.close();
		} catch (Exception e) {
			logger.error(
					"get Certificate from out side error ! certificatePath = ["
							+ certificatePath + "]", e);
			throw e;
		}

		return certificate;
	}

	/**
	 * 从秘钥库中获取证书，
	 * 
	 * @param ks
	 *            秘钥库
	 * @param alias
	 *            导出秘钥库时指定的别名 秘钥库密码
	 * @return Certificate 证书
	 * @throws KeyStoreException
	 */
	public static X509Certificate getCertificate(KeyStore ks, String alias)
			throws KeyStoreException {
		X509Certificate certificate = null;

		try {
			certificate = (X509Certificate) ks.getCertificate(alias);
		} catch (KeyStoreException e) {
			logger.error("get certificate from KeyStore error ! alias = ["
					+ alias + "]", e);
			throw e;
		}

		return certificate;
	}

	/**
	 * 从外部读取秘钥库
	 * 
	 * @param keytorePath
	 *            秘钥库位置
	 * @param password
	 *            密码
	 * @return KeyStore 秘钥库实例
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String keytorePath, String password)
			throws Exception {
		KeyStore ks = null;

		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream in = new FileInputStream(keytorePath);
			ks.load(in, password.toCharArray());
			in.close();
		} catch (Exception e) {
			logger.error("getKeyStore error ! keytorePath = [" + keytorePath
					+ "] , password = [" + password + "]", e);
			throw e;
		}

		return ks;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密的数据
	 * @param privateKey
	 *            私钥
	 * @return byte[] 加密后得到的数据
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) {
		byte[] bytes = null;

		try {
			Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			bytes = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("encrypt By PrivateKey error ! ", e);
		}

		return bytes;
	}

	/**
	 * 私钥进行解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param privateKey
	 *            私钥
	 * @return byte[] 解密后得到的数据
	 */
	public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) {
		byte[] bytes = null;

		try {
			Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			bytes = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("decrypt By PrivateKey error !", e);
		}

		return bytes;

	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            带数据加密
	 * @param certificate
	 *            证书
	 * @return byte[] 加密数据
	 */
	public static byte[] encryptByPublicKey(byte[] data, Certificate certificate) {
		byte[] bytes = null;

		try {
			PublicKey publicKey = getPublicKeyByCertificate(certificate);
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			bytes = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("encrypt By PublicKey error !", e);
		}

		return bytes;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param certificate
	 *            证书
	 * @return byte[] 解密后的数据
	 */
	public static byte[] decryptByPublicKey(byte[] data, Certificate certificate) {
		byte[] bytes = null;

		try {
			PublicKey publicKey = getPublicKeyByCertificate(certificate);
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			bytes = cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("decrypt By PublicKey error !", e);
		}

		return bytes;
	}

	/**
	 * 签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param x509Certificate
	 *            证书
	 * @param privateKey
	 *            私钥
	 * @return byte[] 得到的签名数据
	 */
	public static byte[] sign(byte[] data, X509Certificate x509Certificate,
			PrivateKey privateKey) {
		byte[] bytes = null;
		Signature signature;
		try {
			signature = Signature.getInstance(x509Certificate.getSigAlgName());
			signature.initSign(privateKey);
			signature.update(data);
			bytes = signature.sign();
		} catch (Exception e) {
			logger.error("sign data error !", e);
		}
		return bytes;
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            原始数据
	 * @param sign
	 *            签名
	 * @param x509certificate
	 *            证书
	 * @return boolean 比对结果。
	 */
	public static boolean verify(byte[] data, byte[] sign,
			X509Certificate x509certificate) {
		boolean flag = false;
		try {
			Signature signature = Signature.getInstance(x509certificate
					.getSigAlgName());
			signature.initVerify(x509certificate);
			signature.update(data);
			flag = signature.verify(sign);
		} catch (Exception e) {
			logger.error("verify sign error !", e);
		}

		return flag;
	}

}
