package com.msds.cbpay.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.msds.cbpay.constant.ConstantField;
import com.msds.cbpay.entity.MerchantOrderMsg;
import com.msds.cbpay.service.AsynSendMerchantService;
import com.msds.cbpay.util.SendMsg;
import com.msds.cbpay.util.StartUpLoader;
import com.msds.cbpay.util.XmlReversalBean;

public class SendToMerchant {
	static Logger logger = Logger.getLogger(SendToMerchant.class);
	/**
	 * 发送返回报文
	 * @param offlineurl 后台通知商户
	 * @param merchantNo 商户号
	 * @param clientTime 客户端时间
	 * @param tranFlow 流水号 唯一标示
	 * @param clientIP 客户端ip
	 * @param respCode 响应码
	 * @param respMsg 响应时间
	 * 
	 * @return 返回商户的报文
	 * @throws Exception
	 */
	public static String httpSend(String offlineurl,String merchantNo,String clientTime,String tranFlow,String clientIP,String respCode,String respMsg) throws Exception {
		// TODO Auto-generated method stub
		MerchantOrderMsg merchantOrderMsg = SendToMerchant.packMerchant(merchantNo,clientTime, tranFlow, clientIP, respCode, respMsg);
		String repmsg = XmlReversalBean.beanToXml(merchantOrderMsg,MerchantOrderMsg.class);
		if(logger.isDebugEnabled()){
			logger.debug("chs009 reqmsg["+repmsg+"]");
		}
		repmsg = SendMsg.encodeOrg(repmsg,ConstantField.CHS009);
		if(logger.isDebugEnabled()){
			logger.debug("chs009 repmsg["+repmsg+"]");
		}
		logger.info("return to merchant");
		repmsg = StartUpLoader.format.format(repmsg.length())+repmsg;
		if(logger.isDebugEnabled()){
			logger.debug("to merchant msg["+repmsg+"]");
		}
		if(StringUtils.isNotEmpty(offlineurl)){
			String reqmsg = ConstantField.PARAM+ConstantField.PARAM_SEPARATE+repmsg;//给后台组装通知
			new Thread(new AsynSendMerchantService(offlineurl,reqmsg.getBytes(StartUpLoader.baseParamBean.getEncode()))).start();
		}
		return repmsg;
	}
	
	/**
	 * 组装返回报文
	 * @param merchantNo
	 * @param clientTime
	 * @param tranFlow
	 * @param clientIP
	 * @param respCode
	 * @param respMsg
	 * @return
	 */
	public static MerchantOrderMsg packMerchant(String merchantNo,String clientTime,String tranFlow,String clientIP,String respCode,String respMsg){
		MerchantOrderMsg merchantOrderMsg = new MerchantOrderMsg();
		merchantOrderMsg.setBody(null);
		merchantOrderMsg.getHead().setMerchantNo(merchantNo);
		merchantOrderMsg.getHead().setClientTime(clientTime);
		merchantOrderMsg.getHead().setTranFlow(tranFlow);
		merchantOrderMsg.getHead().setClientIP(clientIP);
		if(StringUtils.isNotEmpty(respCode)){
			merchantOrderMsg.getHead().setRespCode(respCode);
			merchantOrderMsg.getHead().setRespMsg(respMsg);
		}else{
			merchantOrderMsg.getHead().setRespCode(ConstantField.DEFAULT_ERROR_RESPCODE);
			merchantOrderMsg.getHead().setRespMsg(ConstantField.DEFAULT_ERROR_RESPDES);
		}
		Date nowTime=new Date(); 
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		merchantOrderMsg.getHead().setServerTime(time.format(nowTime));
		merchantOrderMsg.getHead().setMsgType(ConstantField.MSG_TYPE_REP);
		merchantOrderMsg.getHead().setTranCode(ConstantField.TRANCODE_PAY);
		return merchantOrderMsg;
	}
}
