package com.ztkx.cbpay.container.frame;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.initdata.ServicesAdapterData;
import com.ztkx.cbpay.container.intercepter.AfterIntercepter;
import com.ztkx.cbpay.container.intercepter.BeforeIntercepter;
import com.ztkx.cbpay.container.javabean.ProcessService;
import com.ztkx.cbpay.container.javabean.ServicesAdapter;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.ServiceManager;
import com.ztkx.cbpay.container.service.intface.Services;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.context.imp.ContextManager;
import com.ztkx.cbpay.platformutil.log.FlowNoContainer;

/**
 * 容器异步运行时同步框架
 * in容器使用
 * @author sunyoushan
 */
public class RunnableSynAdapterFrame implements AdapterFrame{

	Logger logger = Logger.getLogger(RunnableSynAdapterFrame.class);
	private CommonContext context = null;
	private int current_step = -1;		//记录当前第几步
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
			logger.info("RunnableSynAdapterFrame.before start!");
			this.before(this.context);
			logger.info("RunnableSynAdapterFrame.before stop!");
			
			logger.info("RunnableSynAdapterFrame.invoke start!");
			this.invoke(this.context);
			logger.info("RunnableSynAdapterFrame.invoke stop!");
			
			logger.info("RunnableSynAdapterFrame.after start!");
			this.after(this.context);
			logger.info("RunnableSynAdapterFrame.after stop!");
		}
		finally  {
			ContextManager.getInstance().relase(context);
		}
	}

	@Override
	public void setContext(CommonContext context) {
		this.context = context;
	}

	@Override
	public void before(CommonContext context) {
		BeforeIntercepter intercepter = new BeforeIntercepter();
		intercepter.intercepter(context);
	}

	@Override
	public void after(CommonContext context) {
		//执行后拦截器
		AfterIntercepter afterIntercepter = new AfterIntercepter();
		afterIntercepter.intercepter(context);
		
		//是放context资源
		
	}

	@Override
	public void invoke(CommonContext context) {
		ServicesAdapter servicesAdapter = ServicesAdapterData.getInstance().getServiceAdapter(context.getSDO().serverId, context.getSDO().serverCode);
		if(servicesAdapter==null){
			logger.error("servicesAdapter is null!");
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000001, context);
			return;
		}
		//为baseservice结果添加一个默认初始化值
		logger.debug("servicesAdapter:"+ servicesAdapter.getProcesslist());
		
		//将当前执行步骤记为0
		current_step = context.getObj(ConstantConfigField.CONTAINER_OUT_CURRENT_STEP)!=null?
				(int)context.getObj(ConstantConfigField.CONTAINER_OUT_CURRENT_STEP):0;
				
		context.remove(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
		context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_SECEND, CommonContext.SCOPE_GLOBAL);
		
		for(;current_step<servicesAdapter.getProcessservice().size();){
			ProcessService processService = servicesAdapter.getProcessservice().get(current_step);
			logger.info("process now["+processService.getServiceid()+"]");
			boolean flag = this.isProcess(context,processService);
			
			if(flag){
				//运行此次服务
				logger.debug(processService.getServiceid() + " is run");
				Services services = ServiceManager.getService(processService.getServiceid(), processService.getServicetype());
				try {
					context = services.service(context);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					logger.error("ServiceException is error");
				}
				if(context.getErrorCode()!=null){
					logger.error(processService.getServiceid() +" service errorCode is ["+context.getErrorCode()+"]");
				}
			}
			//当前步骤号自增
			current_step++;
			
			//如果当前步骤大于分界线，将当前流程改为第三阶段
			String msg_order = context.getFieldStr(ContainerConstantField.MSG_ORDER, CommonContext.SCOPE_GLOBAL);
			if (!msg_order.equals(ContainerConstantField.MSG_ORDER_THREE) && current_step>servicesAdapter.getBoundary()){
				context.remove(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
				context.setFieldStr(ContainerConstantField.MSG_ORDER, ContainerConstantField.MSG_ORDER_THREE,CommonContext.SCOPE_GLOBAL);
			}
		}
		//赋值顺序号
		context.remove(ConstantConfigField.CONTAINER_OUT_CURRENT_STEP);
		context.setObj(ConstantConfigField.CONTAINER_OUT_CURRENT_STEP, current_step);
		context.remove(ContainerConstantField.PROTOCOL_SERVICE_NAME);
		context.setFieldStr(ContainerConstantField.PROTOCOL_SERVICE_NAME,ContainerConstantField.PROTOCOL_SERVICE_NAME_OUT_IN);
	}
	/**
	 * 判断是否运行服务程序
	 * @param service
	 */
	private boolean isProcess(CommonContext context,ProcessService service){
			//表中processlist的值是必须的
			if(service.isMustrun()){
				return true;
			}
			//错误码是空的
			if(null==context.getErrorCode()){
				return true;
			}
		//context的结果是正确的
		return false;
	}
	/**
	 * 业务进行服务程序
	 * @param context
	 * @param serviceArr
	 */
	private CommonContext busservice(CommonContext context,ProcessService service){
		Services services = ServiceManager.getBusService(service.getServiceid());
		try {
			services.service(context);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error("busservice is error ");
		}
		return context;
	}
	/**
	 * 基础服务进行服务程序
	 * @param context
	 * @param serviceArr
	 */
	private CommonContext baseservice(CommonContext context,ProcessService service){
		Services services = ServiceManager.getBaseService(service.getServiceid());
		try {
			 services.service(context);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error("baseservice is error");
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
