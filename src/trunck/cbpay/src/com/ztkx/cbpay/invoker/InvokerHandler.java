package com.ztkx.cbpay.invoker;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.invoker.bean.ExecutorBean;
import com.ztkx.cbpay.invoker.bean.InvokerBean;
import com.ztkx.cbpay.invoker.bean.InvokerParams;
import com.ztkx.cbpay.invokerexecutor.ExecutorFactory;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;
import com.ztkx.cbpay.platformutil.threadpool.ThreadPoolManager;

/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年7月4日 下午5:53:01 
 */
public class InvokerHandler {
	private static Logger logger = Logger.getLogger(InvokerHandler.class);
	/**
	 * 异步处理任务
	 * 2016年7月5日 上午11:21:58
	 * @author zhangxiaoyun
	 * @param params
	 * @return
	 * @return boolean
	 */
	public static boolean handlerAsyn(final InvokerParams params){
		
		ThreadPoolManager.getExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				handlerSyn(params);
			}
		});
		return true;
	}
	
	/**
	 * 同步处理任务
	 * 2016年7月5日 上午11:22:19
	 * @author zhangxiaoyun
	 * @param params
	 * @return
	 * @return boolean
	 * @throws Exception  
	 */
	public static boolean handlerSyn(InvokerParams params){
		
		Invoker invoker;
		boolean res = false;
		try {
			
			String cmdId = params.getInvokerId();
			logger.info("start init invoker ["+cmdId+"] opr ["+params.getOperator()+"]");
			InvokerBean invokerBean =  InvokerManager.getInstance().getInvokerById(cmdId);
			//获取invoker的实现对象
			String impl = invokerBean.getImpl();
			logger.info("invoker impl is ["+impl+"]");
			invoker = (Invoker) Class.forName(impl).newInstance();
			//获取invoker的executor 的list
			List<ExecutorBean> executorList = invokerBean.getExecutorList();
			for (ExecutorBean executorBean : executorList) {
				logger.info("invoker's  executor type is ["+executorBean.getType()+"] and executor id is ["+executorBean.getId()+"]");
				InvokerExecutor executor = ExecutorFactory.getExecutory(executorBean.getType(), executorBean.getId());
				invoker.addCommandExecuter(executor);
			}
			String oper = params.getOperator();
			
			switch (oper) {
			case InvokerConstant.OPR_PRELOAD: {
				res = invoker.execute(params.getCommandparam());
				break;
			}
			case InvokerConstant.OPR_RELOAD: {
				res = invoker.cmdConfirm();
				break;
			}
			case InvokerConstant.OPR_ROLLBAK: {
				res = invoker.undo();
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			res = false;
			logger.error("invoker ["+params.getInvokerId()+"] opr ["+params.getOperator()+"] execut fail ",e);
		}
		logger.info("invoker ["+params.getInvokerId()+ "] opr ["+params.getOperator()+"]  res is ["+res+"]");
		return res;
	}
}
