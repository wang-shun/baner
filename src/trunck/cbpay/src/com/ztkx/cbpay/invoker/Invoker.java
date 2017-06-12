package com.ztkx.cbpay.invoker;

import java.io.Serializable;
import java.util.Map;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.invokerexecutor.InvokerExecutor;

/**
 * @ClassName: Invoker
 * @Description: 命令接口
 * @author zhangxiaoyun
 * @date 2016年6月30日 上午11:23:22
 */
public interface Invoker extends Serializable {
	/**
	 * 将当命令和命令的执行者绑定
	 * 2016年6月30日 上午11:29:31
	 * @author zhangxiaoyun
	 * @param exe
	 * @return void
	 */
	public void addCommandExecuter(InvokerExecutor exe);
	/**
	 * 调用命令执行调用命令的执行者去执行命令
	 * 2016年6月30日 上午11:30:10
	 * @author zhangxiaoyun
	 * @return boolean
	 */
	public boolean execute(Map<String,String> commandparam);
	
	/**
	 * 命令确认
	 * 2016年7月4日 上午9:36:35
	 * @author zhangxiaoyun
	 * @return void
	 */
	public boolean cmdConfirm();
	/**
	 * 回滚当前命令
	 * 2016年7月1日 下午5:53:55
	 * @author zhangxiaoyun
	 * @return
	 * @throws HandlerException
	 * @return boolean
	 */
	public boolean undo();
}
