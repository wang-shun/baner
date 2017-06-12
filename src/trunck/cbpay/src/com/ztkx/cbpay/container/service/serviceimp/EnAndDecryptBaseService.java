package com.ztkx.cbpay.container.service.serviceimp;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.enanddecryption.EnAndDecryptHandler;
import com.ztkx.cbpay.container.enanddecryption.EnAndDecryptionFactory;
import com.ztkx.cbpay.container.service.intface.BaseService;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 加解密基础服务
 * 
 * @author tianguangzhao
 *
 */
public class EnAndDecryptBaseService implements BaseService {

	private String containername = "";

	static Logger logger = Logger.getLogger(EnAndDecryptBaseService.class);

	/**
	 * 初始化时将容器名称读入，避免每次请求都获取一次
	 */
	public EnAndDecryptBaseService() {
		// 初始化时获取容器名称
		try {
			containername = BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME);

			if (logger.isDebugEnabled()) {
				logger.debug("get containername success ! containername = [" + containername + "]");
			}
		} catch (Exception e) {
			logger.error("get containername error !", e);
		}
	}

	@Override
	public CommonContext service(CommonContext context) {
		// 交易步骤
		String msgOrder = context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);

		if (logger.isDebugEnabled()) {
			logger.debug("get msgOrder success ! msgOrder = [" + msgOrder + "]");
		}
		// 加解密工具类存入内存时对应的key值，根据此key值，获取相应处理类
		String key = "";

		// 首先进行容器判断
		if (containername.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_OUT)) {
			// out容器中的key值存放于serverid属性中
			key = context.getSDO().serverId;

			if (msgOrder.equals(ContainerConstantField.MSG_ORDER_SECEND)) {
				// 当在out容器中运行到第2步时，需要进行加密加签等操作，此时需要和第三方交互，必须按照预先商定的规则，将报文进行处理，才能发送给服务方
				context = encryptMessage(context, key);
			} else if (msgOrder.equals(ContainerConstantField.MSG_ORDER_THREE)) {
				// 当在out容器中运行到第3步时，需要进行解密操作，此时的报文是由第三方反馈的报文，需要先完成解密验签等操作，才能使用。
				context = decryptMessage(context, key);
			} else {
				// out容器中只有2,3步，如果msgOrder是其他的值，则是系统注入错误。
				logger.info(" msgOrder error ！  containername = [" + containername + "] , msgOrder = [" + msgOrder + "]");
			}

		} else if (containername.equalsIgnoreCase(ConstantConfigField.CONTAINER_NAME_IN)) {
			// in容器中的key值存放于tranfrom属性中
			key = context.getSDO().tranFrom;

			if (msgOrder.equals(ContainerConstantField.MSG_ORDER_FIRST)) {
				// 当在in容器中运行到第1步时，需要进行解密操作，此时的报文是客户端发起的原始报文，需要先完成解密验签等操作，才能使用。
				context = decryptMessage(context, key);
			} else if (msgOrder.equals(ContainerConstantField.MSG_ORDER_FOUR)) {
				// 当在in容器中运行到第4步时，需要进行加密加签等操作，此时需要将报文返回给客户端，必须按照预先商定的规则，将报文进行处理，才能发送给客户端
				context = encryptMessage(context, key);
			} else {
				// in容器中只有1,4步，如果msgOrder是其他的值，则是系统注入错误。
				logger.info(" msgOrder error ！  containername = [" + containername + "] , msgOrder = [" + msgOrder + "]");
			}

		} else {
			logger.info("error container ! containername = [" + containername + "]");
		}

		return context;
	}

	/**
	 * 完成解密和验签等工作，从得到的报文中，解析出原始消息
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	private CommonContext decryptMessage(CommonContext context, String key) {
		EnAndDecryptHandler handler = null;

		// 根据key值，得到相应的处理类实例
		handler = EnAndDecryptionFactory.getHandler(key);

		try {
			context = handler.decryptMessage(context);
		} catch (Exception e) {
			logger.error("decrypt Message error !", e);
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000014);
			return context;
		}

		return context;
	}

	/**
	 * 加工信息，完成加密和加签工作,对需要返回的报文进行加工和组装，使其符合双方约定的格式
	 * 
	 * @param context
	 * @return
	 */
	private CommonContext encryptMessage(CommonContext context, String key) {
		EnAndDecryptHandler handler = null;

		// 根据key值，得到相应的处理类实例
		handler = EnAndDecryptionFactory.getHandler(key);
		try {
			context = handler.encryptMessage(context);
		} catch (Exception e) {
			logger.error("encrypt Message error !", e);
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000015);
			return context;
		}

		return context;
	}
}
