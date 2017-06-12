package com.ztkx.transplat.container.frame;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.initdata.ServicesAdapterData;
import com.ztkx.transplat.container.intercepter.AfterIntercepter;
import com.ztkx.transplat.container.intercepter.BeforeIntercepter;
import com.ztkx.transplat.container.javabean.ProcessService;
import com.ztkx.transplat.container.javabean.ServicesAdapter;
import com.ztkx.transplat.container.protocol.ProtocolManager;
import com.ztkx.transplat.container.protocol.process.ProtocolProcess;
import com.ztkx.transplat.container.service.ServiceException;
import com.ztkx.transplat.container.service.ServiceManager;
import com.ztkx.transplat.container.service.intface.Services;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.context.imp.ContextManager;
import com.ztkx.transplat.platformutil.log.FlowNoContainer;

/**
 * 容器同步运行时同步框架
 * @author sunyoushan
 */
public class RunnableAsynAdapterFrame implements AdapterFrame{
	Logger logger = Logger.getLogger(RunnableAsynAdapterFrame.class);
	private CommonContext context = null;
	private int current_step = -1;		//记录当前第几步
	private ServicesAdapter servicesAdapter = null;
	@Override
	public void run() {
		/**
		 * 检查当前context是否有流水号号
		 * 如果没有流水号生成流水号
		 */
		checkFlowNo(context);
		
		/**
		 * 给当前线程注入交易流水号
		 */
		FlowNoContainer.setFlowNo(context.getSDO().flowNo);
		try{
			logger.info("RunnableAsynAdapterFrame.before start!");
			this.before(this.context);
			logger.info("RunnableAsynAdapterFrame.before stop!");
			
			logger.info("RunnableAsynAdapterFrame.invoke start!");
			this.invoke(this.context);
			logger.info("RunnableAsynAdapterFrame.invoke stop!");
			
			logger.info("RunnableAsynAdapterFrame.after start!");
			this.after(this.context);
			logger.info("RunnableAsynAdapterFrame.after stop!");
		}
		finally  {
			//释放当前context
			ContextManager.getInstance().relase(context);
		}
	}

	@Override
	public void setContext(CommonContext context) {
		this.context = context;
	}

	@Override
	public void before(CommonContext context) {
		//执行前连接器，trancode    tranfrom
		current_step = context.getObj(ConstantConfigField.CONTAINER_IN_CURRENT_STEP, CommonContext.SCOPE_GLOBAL)!=null?
				(int)context.getObj(ConstantConfigField.CONTAINER_IN_CURRENT_STEP, CommonContext.SCOPE_GLOBAL):0;
		BeforeIntercepter intercepter = new BeforeIntercepter();
		intercepter.intercepter(context);
	}

	@Override
	public void after(CommonContext context) {
		logger.debug("msg_order:"+context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL));
		//如果是第一阶段，需要发送到OUT容器
		if(ContainerConstantField.MSG_ORDER_FOUR.equalsIgnoreCase(context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL))){
			AfterIntercepter afterIntercepter = new AfterIntercepter();
			afterIntercepter.intercepter(context);
		}
	}

	@Override
	public void invoke(CommonContext context) {
		servicesAdapter = ServicesAdapterData.getInstance().getServiceAdapter(context.getSDO().tranFrom, context.getSDO().TRANCODE);
		
		if(servicesAdapter==null){
			logger.error("servicesAdapter is null!");
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000001, context);
			return;
		}
		if(logger.isDebugEnabled()){
			logger.debug("servicesAdapter:"+ servicesAdapter.getProcesslist());
		}
		
		//根据当前步骤号判断 交易到哪一个阶段
		if(current_step ==0){
			context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_FIRST, CommonContext.SCOPE_GLOBAL);
		}else if (current_step>servicesAdapter.getBoundary()){
			context.remove(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_FOUR, CommonContext.SCOPE_GLOBAL);
		}
		
		ProcessService processService = servicesAdapter.getProcessservice().get(current_step);
				
		for(;current_step<servicesAdapter.getProcessservice().size();){
			
			if(!isProcess(context,processService)){
				current_step++;//当前步骤号加1
				processService = servicesAdapter.getProcessservice().get(current_step);
				//如果此process不需要执行,跳过本次操作
				continue;
			}
			
			//判断是不是协议服务，是协议服务结束此次循环
			if( current_step == servicesAdapter.getBoundary() && null==context.getErrorCode() && servicesAdapter.getBoundary()!=0){
				context.remove(ConstantConfigField.CONTAINER_IN_CURRENT_STEP, CommonContext.SCOPE_GLOBAL);
				context.setObj(ConstantConfigField.CONTAINER_IN_CURRENT_STEP, current_step+1,CommonContext.SCOPE_GLOBAL);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put(ContainerConstantField.JMS_MESSAGE_FROM, BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
				map.put(ContainerConstantField.PROTOCOL_SERVICE_NAME, ContainerConstantField.PROTOCOL_SERVICE_NAME_IN_OUT);
				ProtocolProcess process = ProtocolManager.getInstance().getProcotol(processService.getServiceid());
				try {
					process.send(context,map);
					break;
				} catch (Exception e) {
					logger.error("container in send context error", e);
				}
			}			
			
			//执行交易流程
			logger.info("process now["+processService.getServiceid()+"]");
			context = this.service(context,processService);
			
			if(context.getErrorCode()!=null){
				logger.error(processService.getServiceid() +" service errorCode is ["+context.getErrorCode()+"]");
			}
			current_step++;//当前步骤号加1
			//如果当前步骤大于分界线，将当前流程改为第四阶段
			if (current_step>servicesAdapter.getBoundary()){
				context.remove(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
				context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_FOUR, CommonContext.SCOPE_GLOBAL);
			}
			if(current_step<servicesAdapter.getProcessservice().size())
				processService = servicesAdapter.getProcessservice().get(current_step);
		}
		
	}
	/**
	 * 判断是否运行服务程序
	 * @param service
	 */
	private boolean isProcess(CommonContext context, ProcessService service) {
		// 表中processlist的值是必须的
		if (service.isMustrun()) {
			return true;
		}
		// 错误码是空的
		if (null == context.getErrorCode()) {
			return true;
		}
		// context的结果是正确的
		return false;
	}
	/**
	 * 进行服务程序
	 * @param context
	 * @param serviceArr
	 */
	private CommonContext service(CommonContext context,ProcessService service){
		Services services = ServiceManager.getService(service.getServiceid(), service.getServicetype());
		try {
			 services.service(context);
		} catch (ServiceException e) {
			logger.error("ServiceException is error");
		}
		return context;
	}
	/**
	 * 检测context是否有流水号
	 * @param context
	 */
	private void checkFlowNo(CommonContext context){
		if(context.getSDO().flowNo == null){
			context.getSDO().flowNo = FlowNoGenerator.instance.getFlowNo();
		}
	}
	

}
