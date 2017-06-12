package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.initdata.ServerInfoData;
import com.ztkx.cbpay.container.initdata.TranFromInfoData;
import com.ztkx.cbpay.container.javabean.ServerInfo;
import com.ztkx.cbpay.container.javabean.TranFromInfo;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.enanddecrypt.Base64Util;
import com.ztkx.cbpay.platformutil.enanddecrypt.CertificateCoder;
import com.ztkx.cbpay.platformutil.enanddecrypt.RandomUtil;
import com.ztkx.cbpay.platformutil.enanddecrypt.ThreeDES;

public class EncryptBusinessService implements BusinessService {

	static Logger logger = Logger.getLogger(EncryptBusinessService.class);

	// 标识位和保留域，存放有是否需要解密等信息
	public static final int MESSAGE_FLAG_DIGIT = 16;
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
			logger.debug("start init CashierEnAndDecryptHandler ! ");
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
				logger.debug("init CashierEnAndDecryptHandler success ! ");
			}

		} catch (Exception e) {
			logger.error("init CashierEnAndDecryptHandler error ! ", e);
		}

	}

	/**
	 * 处理接收到的报文，完成解密验签工作
	 * 
	 * @param context
	 */
	public CommonContext service(CommonContext context) throws ServiceException {


		// 判断所需常量是否已经初始化，如果没有初始化，则调用初始化方法
		if (!isInit) {
			init(context);
		}

		// 首先获取需要返回的报文和对方用到的编码格式

		encoding = context.getSDO().enCodeing;
		
		String returnMessage = null;

		try {
			returnMessage = new String(Base64Util.decode(((String)context.getObj(ContainerConstantField.MSG_SECRET,CommonContext.SCOPE_GLOBAL)).getBytes(encoding)), encoding);
			
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
			/*String flagStr = BaseUtil.fillZeroRight(
					String.valueOf(ENCRYPTION_FLAG), MESSAGE_FLAG_DIGIT);
			returnMessage = flagStr + returnMessage;*/

			if (logger.isDebugEnabled()) {
				logger.debug("encrypt message success ! final Message =["
						+ returnMessage + "]");
			}

			// 将最终报文放入context中
			context.setOrginalField(returnMessage);

		} catch (Exception e) {
			logger.info("encrypt message error ! ", e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			return context;
		}

		logger.info("there to update "+ContainerConstantField.MSG_ORDER+"["+context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL)+"] to ["+ContainerConstantField.MSG_ORDER_FOUR+"]");
		context.remove(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
		context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_FOUR, CommonContext.SCOPE_GLOBAL);
		
		return context;
	};

}
