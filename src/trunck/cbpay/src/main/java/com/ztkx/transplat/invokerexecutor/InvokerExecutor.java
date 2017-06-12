package com.ztkx.transplat.invokerexecutor;

import java.util.Map;

import com.ztkx.transplat.container.HandlerException;



public interface InvokerExecutor {

	/**
	 * 获取当前执行者的名称
	 * 2016年7月1日 下午5:56:46
	 * @author zhangxiaoyun
	 * @return
	 * @return String
	 */
	public String getName();
	/**
	 * 执行响应操作
	 * 2016年6月30日 上午11:31:51
	 * @author zhangxiaoyun
	 * @return void
	 */
	public boolean doCommand(Map<String,String> commandparam) throws HandlerException;
	
	/**
	 * 确认上一次操作
	 * 2016年7月4日 上午9:15:03
	 * @author zhangxiaoyun
	 * @throws HandlerException
	 * @return void
	 */
	public void confirmOpr() throws HandlerException;
	
	/**
	 * 取消上一次知悉
	 * 2016年7月4日 上午9:11:04
	 * @author zhangxiaoyun
	 * @return
	 * @throws HandlerException
	 * @return boolean
	 */
	public void cancleCommand() throws HandlerException;
}
