package com.ztkx.cbpay.container.enanddecryption;


import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 负责对和各个渠道交互的信息进行加工处理。
 * 
 * @author tianguangzhao
 *
 */
public interface EnAndDecryptHandler {

	/**
	 * 处理接收到的报文，完成解密验签工作,从对方的报文中还原出原始信息
	 * 
	 * @param context
	 */
	public CommonContext decryptMessage(CommonContext context) throws HandlerException;

	/**
	 * 加工信息，完成加密和加签工作,对需要返回的报文进行加工和组装，使其符合双方约定的格式
	 * 
	 * @param context
	 * @return
	 */
	public CommonContext encryptMessage(CommonContext context) throws HandlerException;

}
