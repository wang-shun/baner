package com.ztkx.cbpay.business.enanddecryption.handler;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.enanddecryption.EnAndDecryptHandler;
import com.ztkx.cbpay.container.initdata.ServerInfoData;
import com.ztkx.cbpay.container.initdata.TranFromInfoData;
import com.ztkx.cbpay.container.javabean.ServerInfo;
import com.ztkx.cbpay.container.javabean.TranFromInfo;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.baseUtil.BaseUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.enanddecrypt.Base64Util;
import com.ztkx.cbpay.platformutil.enanddecrypt.CertificateCoder;
import com.ztkx.cbpay.platformutil.enanddecrypt.RandomUtil;
import com.ztkx.cbpay.platformutil.enanddecrypt.ThreeDES;

/**
 * 商户加密加签，解密验签的功能实现类
 * 
 * @author tianguangzhao
 *
 */
public class MerchantEnAndDecryptHandler implements EnAndDecryptHandler {

	static Logger logger = Logger.getLogger(MerchantEnAndDecryptHandler.class);

	// 标识位和保留域，存放有是否需要解密等信息
	public static final int MESSAGE_FLAG_DIGIT = 10;
	// 是否需要解密的标示，如果为'E'标示报文中含有密文需要解密
	public static final char ENCRYPTION_FLAG = 'E';
	// 存放3des密码的标签
	public static final String THREEDES_PASSWORD_LABLE = "sessionkey";
	// 存放签名的对应标签
	public static final String ABSTRACTLABLE = "signature";
	// 协定的初始偏移量,暂定为8个“0”
	public static final byte[] keyiv = new byte[8];
	// 3des加密秘钥的长度
	public static final int THREEDES_PASSWORD_LENTH = 112;
	// 从秘钥库中获取的私钥和证书
	private static PrivateKey privateKey;
	private static X509Certificate privateCertificate;

	// 从外部读取的包含有公钥的证书
	private static Certificate publicCertificate;
	// 报文编码格式，默认值为 GBK
	public static String encoding = "GBK";
	// 初始化变量,默认为false，此类的静态变量被初始化后为true；
	public static boolean isInit = false;

	/**
	 * 初始化方法，将私钥和证书，以及公钥初始化
	 */
	private void init(CommonContext context) {
		// 容器名称
		String containername = "";
		// 秘钥库文件（包括目录和名称）
		String keyStoreFile = "";
		// 秘钥库的别名
		String keyStoreAlias = "";
		// 秘钥库的密码
		String keyStorePassword = "";
		// 公钥电子证书文件（包括目录和名称）
		String publicCertificateFile = "";
		// 服务标识值，和某个模块沟通确认的信息交互规则为一个服务。
		String key = "";

		try {
			containername = BaseConfig
					.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME);
		} catch (Exception e) {
			logger.error("get containername error !", e);
		}

		// 根据容器不同，读取不同的配置表
		if (containername
				.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_OUT)) {
			// out容器key标识存放在serverId属性中，读取SERVER_INFO表得到所需参数
			key = context.getSDO().serverId;
			ServerInfo serverInfo = ServerInfoData.getInstance().getServerInfo(key);
			keyStoreFile = serverInfo.getKey_store_file();
			keyStoreAlias = serverInfo.getKey_store_alias();
			keyStorePassword = serverInfo.getKey_store_password();
			publicCertificateFile = serverInfo.getPublic_key_file();
		} else if (containername
				.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_IN)) {
			// in容器中的key值存放于tranfrom属性中，读取TRAN_FROM_INFO表得到所需参数
			key = context.getSDO().tranFrom;
			TranFromInfo tranFromInfo = TranFromInfoData.getInstance().getTranFromInfoByFromId(key);
			keyStoreFile = tranFromInfo.getKey_store_file();
			keyStoreAlias = tranFromInfo.getKey_store_alias();
			keyStorePassword = tranFromInfo.getKey_store_password();
			publicCertificateFile = tranFromInfo.getPublic_key_file();

		} else {
			logger.info("wrong containername ! containername =["
					+ containername + "]");
		}

		// 打印从数据库的配置中获取配置
		if (logger.isDebugEnabled()) {
			logger.debug("start init MerchantEnAndDecryptHandler ! ");
			logger.debug("keyStoreFile = [" + keyStoreFile + "]");
			logger.debug("keyStoreAlias = [" + keyStoreAlias + "]");
			logger.debug("keyStorePassword = [" + keyStorePassword + "]");
			logger.debug("publicCertificateFile = [" + publicCertificateFile
					+ "]");
		}
		try {
			// 首先读取外部keystore,获取私钥和自己的证书，并保存到内存中
			KeyStore ks = CertificateCoder.getKeyStore(keyStoreFile,
					keyStorePassword);

			// 从秘钥库中获取私钥，用于加签和解密操作
			privateKey = CertificateCoder.getPrivateKeyByKeyStore(ks,
					keyStoreAlias, keyStorePassword);

			// 从秘钥库中获取自己的证书 实例，用于加签操作
			privateCertificate = CertificateCoder.getCertificate(ks,
					keyStoreAlias);

			// 从对方的电子证书中获取证书实例,用于加密操作验签操作
			publicCertificate = CertificateCoder
					.getCertificate(publicCertificateFile);

			// 将初始化标识置为true
			isInit = true;
			if (logger.isDebugEnabled()) {
				logger.debug("init MerchantEnAndDecryptHandler success ! ");
			}

		} catch (Exception e) {
			logger.error("init MerchantEnAndDecryptHandler error ! ", e);
		}

	}

	/**
	 * 处理接收到的报文，完成解密验签工作
	 * 
	 * @param context
	 */
	public CommonContext decryptMessage(CommonContext context) {

		// 判断所需常量是否已经初始化，如果没有初始化，则调用初始化方法
		if (!isInit) {
			init(context);
		}

		String data = "";
		// 获取报文的编码格式
		encoding = context.getSDO().enCodeing;
		// 将context中的原始报文取出并转换成字符串
		data = context.getOrginalField();
		if (logger.isDebugEnabled()) {
			logger.debug("get original message success ! message is [" + data
					+ "] , encoding = [" + encoding + "]");
		}

		// 报文转换成功，开始后续操作
		// leftMessage为处理后得到的数据，随着处理的进行也会发生变化.
		String leftMessage = data;
		// 判断报文长度，如果报文长度小于标识位长度则报文错误
		if (leftMessage.length() > MESSAGE_FLAG_DIGIT) {

			// 截取报文的加密标识位，判断是否需要解密,
			String flagStr = leftMessage.substring(0, MESSAGE_FLAG_DIGIT);
			leftMessage = leftMessage.substring(MESSAGE_FLAG_DIGIT);
			// 获取加密标识，如果是“E”，则是加密数据需要进行解密操作
			char flagChar = flagStr.charAt(0);

			if (flagChar == ENCRYPTION_FLAG) {

				try {
					// 首先截取3DES加密时所用的密码
					String key = data.substring(
							data.indexOf("<" + THREEDES_PASSWORD_LABLE + ">")
									+ (THREEDES_PASSWORD_LABLE.length() + 2),
							data.indexOf("</" + THREEDES_PASSWORD_LABLE + ">"));

					leftMessage = leftMessage.substring(leftMessage
							.indexOf("</" + THREEDES_PASSWORD_LABLE + ">")
							+ THREEDES_PASSWORD_LABLE.length() + 3);

					// 使用RSA先解密3DES的密码。
					byte[] threeDESpassword = CertificateCoder
							.decryptByPrivateKey(
									Base64Util.decode(key.getBytes(encoding)),
									privateKey);

					// 使用3des的密码对剩余数据进行解密
					byte[] abstractAndMessage = ThreeDES.des3DecodeCBC(
							threeDESpassword, keyiv,
							Base64Util.decode(leftMessage.getBytes(encoding)));

					leftMessage = new String(abstractAndMessage, encoding);

					// 从解密后的字符串中获取，签名和明文
					String signencode = leftMessage.substring(
							leftMessage.indexOf("<" + ABSTRACTLABLE + ">")
									+ ABSTRACTLABLE.length() + 2,
							leftMessage.indexOf("</" + ABSTRACTLABLE + ">"));
					leftMessage = leftMessage.substring(leftMessage
							.indexOf("</" + ABSTRACTLABLE + ">")
							+ ABSTRACTLABLE.length() + 3);

					// 将获取到的RSA签名还原。
					byte[] sign = Base64Util.decode(signencode
							.getBytes(encoding));

					// 对数据进行验签,RSA公钥验签
					boolean flag = CertificateCoder.verify(
							leftMessage.getBytes(encoding), sign,
							(X509Certificate) publicCertificate);

					if (flag) {
						// 验签完成之后，将还原的出来的原始报文存入context中，以便进行以后的操作
						context.setOrginalField(leftMessage);

						// debug打印报文
						if (logger.isDebugEnabled()) {
							logger.debug("decrypt message success ! Message = ["
									+ leftMessage + "]");

						}
					} else {
						logger.info("message verify failed ! ");
						ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000014, context);
						return context;
					}

				} catch (Exception e) {
					logger.error("decrypt message error ! ", e);
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000014, context);
					return context;
				}

			} else {
				// 中信银行的报文，现在定为加密报文。非加密报文处理待定。
				logger.info(" no need to decrypt ! flagChar = [" + flagChar
						+ "]");

			}

		} else {
			// 如果报文长度有误，则返回错误！
			logger.info("message  error ! can not get message flag digit ! messageFlagDigit =["
					+ MESSAGE_FLAG_DIGIT + "]");
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000014, context);
			return context;
		}

		return context;

	};

	/**
	 * 加工信息，完成加密和加签工作
	 * 
	 * @param context
	 */
	public CommonContext encryptMessage(CommonContext context) {

		// 判断所需常量是否已经初始化，如果没有初始化，则调用初始化方法
		if (!isInit) {
			init(context);
		}

		// 首先获取需要返回的报文和对方用到的编码格式
		String returnMessage = context.getOrginalField();

		encoding = context.getSDO().enCodeing;

		try {
			// 首先使用自己的私钥对报文进行签名
			byte[] autograph = CertificateCoder.sign(
					returnMessage.getBytes(encoding), privateCertificate,
					privateKey);
			String finalAutograph = new String(Base64Util.encode(autograph),
					encoding);

			// 拼接签名和明文
			returnMessage = "<" + ABSTRACTLABLE + ">" + finalAutograph + "</"
					+ ABSTRACTLABLE + ">" + returnMessage;

			// 生成3des加密需要的随机112位随机密码
			String randomString = RandomUtil
					.genRandomNum(THREEDES_PASSWORD_LENTH);

			// 打印随机密码
			if (logger.isDebugEnabled()) {
				logger.debug("get 3des password success ! password = ["
						+ randomString + "]");
			}
			// 使用3des对上一步产生的返回报文加密
			returnMessage = new String(Base64Util.encode(ThreeDES
					.des3EncodeCBC(randomString.getBytes(encoding), keyiv,
							returnMessage.getBytes(encoding))));

			// 使用接收方公钥对3des加密的密码，进行加密
			byte[] passwordbytes = CertificateCoder.encryptByPublicKey(
					randomString.getBytes(encoding), publicCertificate);

			String passwords = new String(Base64Util.encode(passwordbytes));

			// 拼装现有报文;
			returnMessage = "<" + THREEDES_PASSWORD_LABLE + ">" + passwords
					+ "</" + THREEDES_PASSWORD_LABLE + ">" + returnMessage;

			// 按照中信银行的要求组装报文, 先写出实现步骤，后续考虑优化，拼装加密标示并将补“0”
			String flagStr = BaseUtil.fillZeroRight(
					String.valueOf(ENCRYPTION_FLAG), MESSAGE_FLAG_DIGIT);
			returnMessage = flagStr + returnMessage;

			if (logger.isDebugEnabled()) {
				logger.debug("encrypt message success ! returnMessage =["
						+ returnMessage + "]");
			}

			// 将最终报文放入context中
			context.setOrginalField(returnMessage);

		} catch (Exception e) {
			logger.info("encrypt message error ! ", e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000015, context);
			return context;
		}

		return context;
	};

}
