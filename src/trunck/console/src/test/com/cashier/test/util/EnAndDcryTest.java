package com.cashier.test.util;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class EnAndDcryTest {
	public static final int MESSAGE_LENGTH_DIGIT = 6;
	public static final int MESSAGE_FLAG_DIGIT = 10;
	public static final String THREEDES_PASSWORD_LABLE = "sessionkey";
	public static final String ABSTRACTLABLE = "signature";
	public static final String ENCRYPTION_FLAG = "E";

	public static final int THREEDES_PASSWORD_LENTH = 112;

	public static final byte[] keyiv = new byte[8];

	private static PrivateKey privateKey;
	private static X509Certificate privateCertificate;

	private static Certificate publicCertificate;
	public static String encoding = "gbk";
	/*public static String encoding = "GBK";*/
	//public static String encoding = "UTF-8";
	public static boolean isInit = false;

	private void init() {

		try {
			KeyStore ks = CertificateCoder.getKeyStore(CashierTestConstant.keyStoreFile,
					CashierTestConstant.keyStorePassword);

			privateKey = CertificateCoder.getPrivateKeyByKeyStore(ks,
					CashierTestConstant.keyStoreAlias, CashierTestConstant.keyStorePassword);

			privateCertificate = CertificateCoder.getCertificate(ks,
					CashierTestConstant.keyStoreAlias);

			publicCertificate = CertificateCoder
					.getCertificate(CashierTestConstant.publicCertificateFile);

			isInit = true;

			System.out.println("init RSA success ! ");
		} catch (Exception e) {
			System.out.println("init RSA error ! ");
		}

	}

	public String reductionMessage(String data) {
		init();
		String leftMessage = data.substring(6);
		if (leftMessage.length() > MESSAGE_FLAG_DIGIT) {

			String flagStr = leftMessage.substring(0, MESSAGE_FLAG_DIGIT);

			leftMessage = leftMessage.substring(MESSAGE_FLAG_DIGIT);

			debugLogPrint("get  message flag success ! flagStr is [" + flagStr
					+ "] , leftMessage = [" + leftMessage + "]");

			String flagChar = flagStr.substring(0, 1);
			if (flagChar.equals(ENCRYPTION_FLAG)) {

				try {
					String key = data.substring(
							data.indexOf("<" + THREEDES_PASSWORD_LABLE + ">")
									+ (THREEDES_PASSWORD_LABLE.length() + 2),
							data.indexOf("</" + THREEDES_PASSWORD_LABLE + ">"));

					leftMessage = leftMessage.substring(leftMessage
							.indexOf("</" + THREEDES_PASSWORD_LABLE + ">")
							+ THREEDES_PASSWORD_LABLE.length() + 3);

					debugLogPrint("get  3des key success ! key = [" + key
							+ "] , leftMessage = [" + leftMessage + "]");

					byte[] threeDESpassword = CertificateCoder
							.decryptByPrivateKey(
									Base64Util.decode(key.getBytes(encoding)),
									privateKey);
					byte[] abstractAndMessage = ThreeDES.des3DecodeCBC(
							threeDESpassword, keyiv,
							Base64Util.decode(leftMessage.getBytes(encoding)));

					leftMessage = new String(abstractAndMessage, encoding);

					String signencode = leftMessage.substring(
							leftMessage.indexOf("<" + ABSTRACTLABLE + ">")
									+ ABSTRACTLABLE.length() + 2,
							leftMessage.indexOf("</" + ABSTRACTLABLE + ">"));
					leftMessage = leftMessage.substring(leftMessage
							.indexOf("</" + ABSTRACTLABLE + ">")
							+ ABSTRACTLABLE.length() + 3);
					byte[] sign = Base64Util.decode(signencode.getBytes());
					boolean flag = CertificateCoder.verify(
							leftMessage.getBytes(encoding), sign,
							(X509Certificate) publicCertificate);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
		return leftMessage;

	}

	public String formatMessage(String returnMessage) {

		try {
			init();

			byte[] autograph = CertificateCoder.sign(
					returnMessage.getBytes(encoding), privateCertificate,
					privateKey);
			String finalAutograph = new String(Base64Util.encode(autograph),
					encoding);

			returnMessage = "<" + ABSTRACTLABLE + ">" + finalAutograph + "</"
					+ ABSTRACTLABLE + ">" + returnMessage;

			String randomString = RandomUtil
					.genRandomNum(THREEDES_PASSWORD_LENTH);

			debugLogPrint("get 3des password success ! password = ["
					+ randomString + "]");

			returnMessage = new String(Base64Util.encode(ThreeDES
					.des3EncodeCBC(randomString.getBytes(encoding), keyiv,
							returnMessage.getBytes(encoding))));

			debugLogPrint("3des Encode message success ! returnMessage = ["
					+ returnMessage + "]");
			byte[] passwordbytes = CertificateCoder.encryptByPublicKey(
					randomString.getBytes(encoding), publicCertificate);

			String passwords = new String(Base64Util.encode(passwordbytes));

			returnMessage = "<" + THREEDES_PASSWORD_LABLE + ">" + passwords
					+ "</" + THREEDES_PASSWORD_LABLE + ">" + returnMessage;

			String flagStr = BaseUtil.fillZeroRight(
					String.valueOf(ENCRYPTION_FLAG), MESSAGE_FLAG_DIGIT);
			returnMessage = flagStr + returnMessage;
//			String messageLenth = BaseUtil.fillZeroLeft(String.valueOf(returnMessage.length()),MESSAGE_LENGTH_DIGIT);

//			returnMessage = messageLenth + returnMessage;

			debugLogPrint("format message success ! returnMessage = ["
					+ returnMessage + "]");

		} catch (Exception e) {
			System.out.println("format message error ! ");
		}

		return returnMessage;
	}

	public void debugLogPrint(String message) {
		System.out.println(message);
	}

	/*public static String readFile() throws Exception {
		InputStream in = new FileInputStream("D:\\msg");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int res = -1;
		byte[] buff = new byte[8192];
		while ((res = in.read(buff)) != -1) {
			bos.write(buff, 0, res);
		}
		return bos.toString(encoding);
	}

	public static void main(String[] args) throws Exception {
		EnAndDcryTest service = new EnAndDcryTest();
		String message = EnAndDcryTest.readFile();
		String msg = service.formatMessage(message);
		System.out.println("����:\n" + msg);
		System.err.println("����:\n" + service.reductionMessage(msg));

	}*/
}
