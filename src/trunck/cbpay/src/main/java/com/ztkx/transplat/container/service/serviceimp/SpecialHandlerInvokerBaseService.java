package com.ztkx.transplat.container.service.serviceimp;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.intface.BaseService;
import com.ztkx.transplat.container.system.SystemHandler;
import com.ztkx.transplat.container.system.SystemHandlerFactory;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * 系统特殊处理类的调度器
 * @author zhangxiaoyun
 * 2016年2月18日 下午4:17:13
 * <p>Company:ztkx</p>
 */
public class SpecialHandlerInvokerBaseService  implements BaseService{
	Logger logger = Logger.getLogger(SpecialHandlerInvokerBaseService.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException{
		logger.info("SpecialHandlerInvokerBaseService is begin...");
		//获取当前步骤号
		String msg_order = context.getFieldStr(ContainerConstantField.MSG_ORDER, CommonContext.SCOPE_GLOBAL);
		if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_IN)){
			//如果是in容器
			String tranFrom = context.getSDO().tranFrom;
			SystemHandler handler = SystemHandlerFactory.getHandler(tranFrom);
			if(handler==null){
				logger.info("can not find system ["+tranFrom+"] special handler");
				return context;
			}
			switch (msg_order) {
			case ContainerConstantField.MSG_ORDER_FIRST:
			{
				try {
					handler.beforeHandler(context);
				} catch (HandlerException e) {
					logger.error("handler is error",e);
				}
				break;
			}
			case ContainerConstantField.MSG_ORDER_FOUR:
			{
				try {
					handler.afterHandler(context);
				} catch (HandlerException e) {
					logger.error("handler is error",e);
				}
				break;
			}
			default:
				break;
			}
		}else if(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME).equals(ConstantConfigField.CONTAINER_NAME_OUT)){
			//如果是out容器
			String serverId = context.getSDO().serverId;
			SystemHandler handler = SystemHandlerFactory.getHandler(serverId);
			if(handler==null){
				logger.info("can not find system ["+serverId+"] special handler");
				return context;
			}
			switch (msg_order) {
			case ContainerConstantField.MSG_ORDER_SECEND:
			{
				try {
					handler.beforeHandler(context);
				} catch (HandlerException e) {
					logger.error("handler is error",e);
				}
				break;
			}
			case ContainerConstantField.MSG_ORDER_THREE:
			{
				try {
					handler.afterHandler(context);
				} catch (HandlerException e) {
					logger.error("handler is error",e);
					
				}
				break;
			}
			default:
				break;
			}
		}
		logger.info("SpecialHandlerInvokerBaseService is succ");
		
		return context;
	}

}
