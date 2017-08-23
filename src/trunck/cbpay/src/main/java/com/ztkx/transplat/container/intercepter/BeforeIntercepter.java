package com.ztkx.transplat.container.intercepter;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.enanddecryption.EnAndDecryptHandler;
import com.ztkx.transplat.container.enanddecryption.EnAndDecryptionFactory;
import com.ztkx.transplat.container.initdata.ConfOprationData;
import com.ztkx.transplat.container.initdata.PlatDateParamData;
import com.ztkx.transplat.container.initdata.TranFromInfoData;
import com.ztkx.transplat.container.initdata.TransInfoData;
import com.ztkx.transplat.container.intercepter.intface.CommonIntercepter;
import com.ztkx.transplat.container.javabean.ConfOpration;
import com.ztkx.transplat.container.javabean.TransInfo;
import com.ztkx.transplat.container.msg.MessageIdentifier;
import com.ztkx.transplat.container.msg.MessageIdentifyFactory;
import com.ztkx.transplat.container.preload.KeyMsgConfPreloader;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.container.util.MessageUtil;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.msg.Field;
import com.ztkx.transplat.platformutil.msg.KeyMsgDescriber;
import com.ztkx.transplat.platformutil.msg.MsgConstantField;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
/**
 * in和out容器钱拦截器
 * in容器前拦截器作用：
 * 	1.识别交易码
 * out容器前拦截器作用：
 * 
 * @author zhangxiaoyun
 *
 */
public class BeforeIntercepter implements CommonIntercepter {
	private static Logger logger = Logger.getLogger(BeforeIntercepter.class);
	@Override
	public CommonContext intercepter(CommonContext context) {
		logger.info("begin exec BeforeIntercepter");
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN))
		{
			
			//如果当前容器是in容器执行以下代码
			String tranchannel = context.getSDO().tranFrom;
			String tranCodeTmp = context.getSDO().TRANCODE;
			String tranOprTmp = context.getSDO().TRAN_OPR;
			
			KeyMsgDescriber keyMsgDescriber = null;
			if(tranCodeTmp!=null && tranOprTmp!=null){
				//如果交易码和业务码都不为空，只需要重新设置encoding字段
				keyMsgDescriber =KeyMsgConfPreloader.getKeyMsgDescriber(tranchannel);
				context.getSDO().enCodeing=keyMsgDescriber.getEncoding();
				return context;
			}
			
			//根据交易渠道找到当前交易的keyMsg配置
			keyMsgDescriber = KeyMsgConfPreloader.getKeyMsgDescriber(tranchannel);
			if(keyMsgDescriber==null){
				logger.error("not find keyMsgDescriber tranchannel ["+tranchannel+"]");
			}
			//1.从keyMsgDescriber中获取keyField的list
			List<Field> listField = keyMsgDescriber.getKeyField().getList();
			String msgFormat = keyMsgDescriber.getFormat();
			String KeyMsgfrom = keyMsgDescriber.getFrom();
			context.getSDO().enCodeing=keyMsgDescriber.getEncoding();
			//交易码字段
			
			
			/**
			 * 2016年3月6日
			 * 因为和宝易互通没有加解密，所有以前在加解密部分处理的byte数组到String的转换挪出来放到in的前拦截器里和协议服务里
			 */
			//add start by zhangxiaoyun 
			// 将context中的原始报文取出并转换成字符串
			String data = null;
			try {
				byte[] original_msg = context.getByteArray(ConstantConfigField.ORIGINAL_MSG,CommonContext.SCOPE_GLOBAL);
				//将原始byte数组注入上下文
				context.setByteArray(ConstantConfigField.ORIGINAL_MSG_BYTE_ARRAY, original_msg, CommonContext.SCOPE_GLOBAL);
				data = new String(original_msg, context.getSDO().enCodeing);
				if (logger.isDebugEnabled()) {
					logger.debug("get Orginal message success ! msg = [" + data + "]");
				}
				context.setOrginalField(data);
			} catch (UnsupportedEncodingException e2) {
				logger.error("byte msg convert String msg exception",e2);
				ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
				return context;
			}
			
			//add end
			logger.info("tranchannel ["+tranchannel+"] msgFormat ["+msgFormat+"] KeyMsgfrom ["+KeyMsgfrom+"] identify ["+keyMsgDescriber.getIdentify()+"]");
			
			BytesMessage byteMsg = (BytesMessage) context.getObj(ContainerConstantField.JMS_MSG_OBJ);
			try {
				Map<String,String> map = (Map<String,String>) byteMsg.getObjectProperty(ConstantConfigField.MSG_PROPERTIES);
				context.setFieldStr(ContainerConstantField.REQ_URL, map.get(ContainerConstantField.REQ_URL));
				context.setFieldStr(ContainerConstantField.JMS_MESSAGE_ID, map.get(ContainerConstantField.JMS_MESSAGE_ID), CommonContext.SCOPE_GLOBAL);
			} catch (JMSException e) {
				e.printStackTrace();
			}
			switch (KeyMsgfrom) {
			case MsgConstantField.ATTR_FROM_URL:
			{
				
				String identify = keyMsgDescriber.getIdentify();
				MessageIdentifier identifier = MessageIdentifyFactory.getIdentify(identify);
				String tmpTranCode = identifier.identify(context);
				if(tmpTranCode==null || "".equals(tmpTranCode)){
					logger.error("MessageIdentifier exec fail trancode is null");
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
					return context;
				}
				
				/**
				 * 如果交易码需要从消息的properties中获取,
				 * 2.从context获取jms消息对象，
				 * 3.轮询从jms对象中获取交易码字段
				 */
				context.getSDO().TRANCODE=tmpTranCode;
				break;
			}
			case MsgConstantField.ATTR_FROM_MSG:
			{
				/**
				 * 如果交易码需要从消息头里获取
				 * 1.判断是否要解密，如果需要解密，调用当前渠道 的解密handler
				 * 2.从context获取渠道上送的报文
				 * 3.从原始报文中拆xml
				 */
				StringBuilder tranCode = new StringBuilder();
				
				//如果当前渠道需要加密和验签
				if(TranFromInfoData.getInstance().isEncryption(tranchannel)&&TranFromInfoData.getInstance().isSignature(tranchannel)){
					EnAndDecryptHandler handler = EnAndDecryptionFactory.getHandler(tranchannel);
					try {
						handler.decryptMessage(context);
					} catch (HandlerException e) {
						// TODO Auto-generated catch block
						logger.error("HandlerException is error", e);
					}
					if(context.getErrorCode()!=null){
						logger.error("invoker EnAndDecryptHandler error");
						return context;
					}
				}
				
				//判断是否需要执行特殊处理
				String identify = keyMsgDescriber.getIdentify();
				if(StringUtils.isNotBlank(identify)){
					MessageIdentifier identifier = MessageIdentifyFactory.getIdentify(identify);
					String tmpTranCode = identifier.identify(context);
					if(tmpTranCode==null || "".equals(tmpTranCode)){
						logger.error("MessageIdentifier exec fail trancode is null");
						ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
						return context;
					}else{
						//将响应码注册到sdo中
						context.getSDO().TRANCODE=tmpTranCode;
					}
				}else{
					//获取原始报文
					String msg  = context.getOrginalField();
					if(listField.size()==1){
						String fieldName = listField.get(0).getName();
						if(msgFormat.equals(MsgConstantField.ATTR_FORMAT_XML))
						{
							//如果报文格式是xml
							byte[] msgByte =null;
							try {
								msgByte = msg.getBytes(context.getSDO().enCodeing);
								tranCode.append(MessageUtil.getTranCode(msgByte,fieldName));
							} catch (Exception e) {
								logger.error("String msg convert byte exception",e);
								ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
								return context;
							}
						} else if (msgFormat.equals(MsgConstantField.ATTR_FORMAT_JSON)) {
							try {
								tranCode.append(MessageUtil.getTranCodeByJson(msg,fieldName));
							} catch (Exception e) {
								logger.error("String msg convert byte exception",e);
								ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
								return context;
							}
						}
					}else if(listField.size()>1){
						/**
						 * 如果渠道的交易码是多个字段拼接而成
						 * 代码以后再补
						 */
					}
					context.getSDO().TRANCODE=tranCode.toString();
				}
				break;
			}
			default:
				break;
			}
			//从traninfo表中获取orpcode
			TransInfo ti = TransInfoData.getInstance().getTransInfo(context.getSDO().tranFrom, context.getSDO().TRANCODE);
			if(ti == null){
				logger.error("not find TransInfo tranFrom ["+context.getSDO().tranFrom+"] TRANCODE ["+context.getSDO().TRANCODE +"]");
			}
			context.getSDO().TRAN_OPR=ti.getTran_opr();
			context.getSDO().tranType = ti.getTranType();
			context.getSDO().flatDate=PlatDateParamData.getInstance().getPlatDate();
			logger.info("tran_code ["+context.getSDO().TRANCODE+"] tran_oprcode ["+ti.getTran_opr()+"] tran_type ["+ti.getTranType()+"]");
			
		}
		else if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT))
		{
			//如果当前容器是out容器执行以下代码
			//服务识别
			String serverId = context.getSDO().serverId;
			String serverCode = context.getSDO().serverCode;
			//如果前面路由没有识别出服务和交易码
			if(serverCode == null){
				String tranCode = context.getSDO().TRANCODE;
				String tranOpr = context.getSDO().TRAN_OPR;
				ConfOpration confOpration = ConfOprationData.getInstance().getConfOpration(tranCode, tranOpr,serverId);
				if(confOpration==null){
					logger.error("table CONF_OPRATION config error can not find data by tranCode ["+tranCode+"] tranOpr ["+tranOpr+"]");
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000010, context);
					return context;
				}
				serverId=  confOpration.getServerid();
				serverCode = confOpration.getServercode();
				
				context.getSDO().serverId = serverId;
				context.getSDO().serverCode = serverCode;
				
			}
			
			logger.info("serverId ["+serverId+"]");
			logger.info("serverCode ["+serverCode+"]");
			if(serverId!=null && serverCode!=null){
				KeyMsgDescriber keyMsgDescriber =KeyMsgConfPreloader.getKeyMsgDescriber(serverId);
				context.getSDO().enCodeing=keyMsgDescriber.getEncoding();
			}
		}
		return context;
	}
	
}
