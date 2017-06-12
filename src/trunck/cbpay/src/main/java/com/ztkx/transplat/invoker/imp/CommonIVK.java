package com.ztkx.transplat.invoker.imp;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.invoker.AbstractIVK;
import com.ztkx.transplat.invokerexecutor.InvokerExecutor;

/**
 * 公共的命令类
 * 
 * @author zhagnxiaoyun:
 * @date 2016年6月30日 上午11:41:03
 */
public class CommonIVK extends AbstractIVK {

	/**
	* @Fields serialVersionUID 
	*/ 
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CommonIVK.class);

	@Override
	public boolean execute(Map<String,String> commandparam) {
		boolean res = false;
		InvokerExecutor commdExecutor = null;
		try {
			for (int i = 0; i < executorList.size(); i++) {
				commdExecutor = executorList.get(i);
				logger.info(commdExecutor.getName() + " start job ");
				if (!commdExecutor.doCommand(commandparam)) {
					logger.error(commdExecutor.getName() + " job fail ");
					break;
				}
			}
			res = true;
		} catch (HandlerException e) {
			logger.error(commdExecutor.getName() + " job fail ", e);
			res = false;
		}
		return res;
	}

	@Override
	public boolean undo() {
		InvokerExecutor commdExecutor = null;
		boolean res = true;
		for (int i = 0; i < executorList.size(); i++) {
			try {
				commdExecutor = executorList.get(i);
				logger.info(commdExecutor.getName() + " start cancle ");
				commdExecutor.cancleCommand();
			} catch (HandlerException e) {
				res = false;
				logger.error(commdExecutor.getName() + " job cancle fail ", e);
			}
		}
		return res;

	}

	@Override
	public boolean cmdConfirm() {
		boolean res = false;
		InvokerExecutor commdExecutor = null;
		try {
			for (int i = 0; i < executorList.size(); i++) {
				commdExecutor = executorList.get(i);
				logger.info(commdExecutor.getName() + " start confirm ");
				commdExecutor.confirmOpr();
				logger.info(commdExecutor.getName() + " confirm succ ");
			}
			res = true;
		} catch (HandlerException e) {
			logger.error(commdExecutor.getName() + " job fail ", e);
			res = false;
		}
		return res;
	}
}
