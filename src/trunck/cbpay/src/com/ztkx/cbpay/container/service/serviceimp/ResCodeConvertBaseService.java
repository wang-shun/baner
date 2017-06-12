package com.ztkx.cbpay.container.service.serviceimp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.initdata.PlatToChanelResCodeData;
import com.ztkx.cbpay.container.initdata.ServerInfoData;
import com.ztkx.cbpay.container.initdata.ServerResCodeData;
import com.ztkx.cbpay.container.initdata.TranFromInfoData;
import com.ztkx.cbpay.container.javabean.PlatToChanelResCode;
import com.ztkx.cbpay.container.javabean.ServerResCode;
import com.ztkx.cbpay.container.javabean.TranFromInfo;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BaseService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;

public class ResCodeConvertBaseService  implements BaseService{
	
	Logger logger = Logger.getLogger(ResCodeConvertBaseService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		logger.info("resCodeConvert is begin...");
		//如果在out，将服务方响应码转换为系统响应码
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT)){
			/*out 响应码装换流程
			 * 1.从serverinfo表中获取当前服务配置的响应码字段
			 * 	1.1如果字段没有配置,注响应码转换异常错误码
			 * 2.从context获取响应码字段的值
			 * 	2.1如果服务方返回为空响应码转换异常
			 * 3.根据从context获取响应码值从serverrescode表中获取对应的平台响应码
			 */
			
			String serverreskey = null;
			try{
				String systemId = context.getSDO().serverId;
				serverreskey = ServerInfoData.getInstance().getServerInfo(systemId).getRes_code();//获取系统响应码的key
				if(StringUtils.isEmpty(serverreskey)){
					logger.error("system ["+systemId+"] res_code config is null");
					throw new ServiceException("server res to plat exception");
				}
				
				String serverrescode = context.getFieldStr(serverreskey,CommonContext.SCOPE_GLOBAL);
				logger.info("server rescode is ["+serverrescode+"]");
				
				if(StringUtils.isEmpty(serverrescode)){
					//如果服务方没有返回响应码
					logger.error("system ["+systemId+"] response code is null ");
					throw new ServiceException("server res to plat exception");
				}else{
					ServerResCode  serverResCode = ServerResCodeData.getInstance().getPlatCode(systemId, serverrescode);
					if(serverResCode!=null){
						ContextUtil.setResponseCode(serverResCode.getPlatcode(), context);
						logger.info("plat res code is ["+context.getResponseCode()+"] plat res msg is ["+serverResCode.getServerresdes()+"]");
					}else{
						logger.error("system ["+systemId+"] server rescode ["+serverrescode+"] is not find!");
						throw new ServiceException("server res to plat exception");
					}
				}
			}catch(Exception e){
				logger.error("server to plat res code exception", e);
				ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000011, context);
			}
		}else{
			//如果在in，将系统响应码转换为平台响应码
			String chanelcodekey = null;
			String chaneldeskey = null;
			String tranFrom = context.getSDO().tranFrom;
			try{
				TranFromInfo tranFromInfo = TranFromInfoData.getInstance().getTranFromInfoByFromId(tranFrom);
				chanelcodekey = tranFromInfo.getChanel_code();
				chaneldeskey = tranFromInfo.getChanel_des();
				logger.info("channel ["+tranFrom+"] res code key ["+chanelcodekey+"] res des key ["+chaneldeskey+"]");
				/**
				 * 响应码转换
				 */
				convertResCode(context, chanelcodekey, chaneldeskey, tranFrom);
			}catch(Exception e){
				ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000011, context);
				//出现异常在做一次响应码响应码转换
				convertResCode(context, chanelcodekey, chaneldeskey, tranFrom);
			}
		}
		logger.info("resCodeConvert is succ");
		return context;
	}
	
	private void convertResCode(CommonContext context, String chanelcodekey,
			String chaneldeskey, String tranFrom) throws ServiceException {
		//判断errorCode
		String errorCode = context.getErrorCode();
		String responseCode = context.getResponseCode();
		logger.info("channel ["+tranFrom+"]  errorcode ["+errorCode+"] responseCode ["+responseCode+"]");
		
		PlatToChanelResCode chanelBean = getBean(errorCode, responseCode,tranFrom);
		
		if(chanelBean!=null){
			setResCode(context, chanelcodekey, chaneldeskey, chanelBean);
		}else{
			logger.warn("can not find config tranfrom ["+tranFrom+"] platcode ["+errorCode+"] responseCode ["+responseCode+"]");
			
			chanelBean = getBean(errorCode, responseCode,ConstantConfigField.DEFAULT_TRANFROM_ERROR);
			if(chanelBean!=null){
				setResCode(context, chanelcodekey, chaneldeskey,chanelBean);
			}else{
				logger.error("can not find config tranfrom ["+ConstantConfigField.DEFAULT_TRANFROM_ERROR+"] platcode ["+errorCode+"] responseCode ["+responseCode+"]");
				throw new ServiceException("can not find config tranfrom ["+tranFrom+"] platcode ["+errorCode+"] ");
			}
		}
	}
	private void setResCode(CommonContext context, String chanelcodekey,
			String chaneldeskey, PlatToChanelResCode chanelBean) {
		String chanelcode = chanelBean.getChanelcode();
		String chaneldes = chanelBean.getChaneldes();
		context.setFieldStr(chanelcodekey, chanelcode,CommonContext.SCOPE_GLOBAL);
		context.setFieldStr(chaneldeskey, chaneldes,CommonContext.SCOPE_GLOBAL);
		logger.info("res code convert succ chanelcode ["+chanelcode+"]  chaneldes ["+chaneldes+"]");
	}
	private PlatToChanelResCode getBean(String errorCode, String responseCode,String tranFrom) {
		PlatToChanelResCode chanelBean;
		if(StringUtils.isNotEmpty(errorCode)){
			//有错误吗
			chanelBean = PlatToChanelResCodeData.getInstance().getBean(tranFrom, errorCode);
		}else if(StringUtils.isNotEmpty(responseCode)){
			//没有错误码,有响应码
			chanelBean = PlatToChanelResCodeData.getInstance().getBean(tranFrom, responseCode);
		}else{
			//错误码和响应码都没有的时候默认交易成功
			chanelBean = PlatToChanelResCodeData.getInstance().getBean(tranFrom, ErrorCodeConstant.BASE_PLA000000);
		}
		return chanelBean;
	}

}
