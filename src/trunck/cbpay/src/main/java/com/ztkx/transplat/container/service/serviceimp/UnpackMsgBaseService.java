package com.ztkx.transplat.container.service.serviceimp;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.msg.unpack.XmlMsgUnpackerEngine;
import com.ztkx.transplat.container.preload.ErrXmlFilePreloader;
import com.ztkx.transplat.container.preload.KeyMsgConfPreloader;
import com.ztkx.transplat.container.preload.XmlFilePreloader;
import com.ztkx.transplat.container.service.intface.BaseService;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.msg.KeyMsgDescriber;
import com.ztkx.transplat.platformutil.msg.MsgConstantField;
import com.ztkx.transplat.platformutil.msg.MsgXmlDescriber;
/**
 * 容器拆包调用基础服务
 * @author zhangxiaoyun
 *
 */
public class UnpackMsgBaseService implements BaseService {
	private Logger logger = Logger.getLogger(UnpackMsgBaseService.class);
	@Override
	public CommonContext service(CommonContext context) {
		logger.info("start exec UnpackMsgBaseService");
		//如果是IN容器走下面代码
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN))
		{
			//交易来源
			String tranchannel = context.getSDO().tranFrom;
			//交易码
			String tranCode = context.getSDO().TRANCODE;
			
			KeyMsgDescriber keyMsgDescriber = KeyMsgConfPreloader.getKeyMsgDescriber(tranchannel);
			//获取报文格式
			String format = keyMsgDescriber.getFormat();
			//获取商户渠道编码方式
			context.getSDO().enCodeing=keyMsgDescriber.getEncoding();
			
			MsgXmlDescriber msgXmlDescriber =  XmlFilePreloader.getUnpackDesc(tranchannel+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+tranCode);
			
			if(msgXmlDescriber == null){
				logger.error("gain MsgXmlDescriber is fail tranchannel ["+tranchannel+"]  tranCode ["+tranCode+"]");
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000012);
				return context;
			}
						
			logger.info("start unpack msg MsgXmlDescriber is ["+msgXmlDescriber.getFilePaht()+"]");
			//根据报文格式调起响应的拆包引擎
			switch (format) {
			case MsgConstantField.ATTR_FORMAT_XML:
				//如果是xml报文，调起xml拆包引擎
				/**
				 * 1.先从byte报文中将所有字段拆到context中
				 * 2.检查字段的格式
				 * 3.检查字段是否配置有特殊函数,执行函数
				 */
				try {
					XmlMsgUnpackerEngine.unpack(context, msgXmlDescriber);
				} catch (Exception e) {
					if(context.getErrorCode() == null){
						context.setErrorCode(ErrorCodeConstant.BASE_PLA000008);
					}
					logger.error("unpack exception",e);
				}
				break;
			case MsgConstantField.ATTR_FORMAT_FIX:
				//如果是定长报文
				break;
			default:
				break;
			}
			
		}
		//如果是OUT容器走下面代码
		else if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT))
		{
			//服务系统
			String serverId = context.getSDO().serverId;
			//服务交易码
			String serverCode = context.getSDO().serverCode;
			
			KeyMsgDescriber keyMsgDescriber = KeyMsgConfPreloader.getKeyMsgDescriber(serverId);
			//获取报文格式
			String format = keyMsgDescriber.getFormat();
			//获取商户渠道编码方式
			context.getSDO().enCodeing=keyMsgDescriber.getEncoding();
			
			MsgXmlDescriber msgXmlDescriber = null;
			
			if(context.getSDO().svrErrUnpack!=null){
				msgXmlDescriber =  ErrXmlFilePreloader.getUnpackDesc(context.getSDO().svrErrUnpack);
			}else{
				msgXmlDescriber =  XmlFilePreloader.getUnpackDesc(serverId+ConstantConfigField.TABLE_PRI_KEY_SEPARATOR+serverCode);
			}
			
			if(msgXmlDescriber == null){
				logger.error("gain MsgXmlDescriber is fail tranchannel ["+serverId+"]  tranCode ["+serverCode+"]");
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000012);
				return context;
			}
						
			logger.info("start unpack msg MsgXmlDescriber is ["+msgXmlDescriber.getFilePaht()+"]");
			//根据报文格式调起响应的拆包引擎
			switch (format) {
			case MsgConstantField.ATTR_FORMAT_XML:
				//如果是xml报文，调起xml拆包引擎
				/**
				 * 1.先从byte报文中将所有字段拆到context中
				 * 2.检查字段的格式
				 * 3.检查字段是否配置有特殊函数,执行函数
				 */
				try {
					XmlMsgUnpackerEngine.unpack(context, msgXmlDescriber);
				} catch (Exception e) {
					if(context.getErrorCode() == null){
						context.setErrorCode(ErrorCodeConstant.BASE_PLA000008);
					}
					logger.error("unpack exception",e);
				}
				break;
			case MsgConstantField.ATTR_FORMAT_FIX:
				//如果是定长报文
				break;
			default:
				break;
			}
		}
		logger.info("UnpackMsgBaseService exec finish");
		return context;
	}

}
