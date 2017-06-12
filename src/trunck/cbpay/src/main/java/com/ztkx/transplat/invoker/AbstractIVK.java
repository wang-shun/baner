package com.ztkx.transplat.invoker;

import java.util.ArrayList;
import java.util.List;

import com.ztkx.transplat.invokerexecutor.InvokerExecutor;


/** 
 * @author  zhagnxiaoyun: 
 * @date 2016年6月30日 上午11:51:51 
 */
public abstract class AbstractIVK implements Invoker {
	protected String currentNode;	//当前结点
	protected List<InvokerExecutor> executorList = new ArrayList<InvokerExecutor>();
//	protected Exception exception = null;		//当前命令的异常
	
	@Override
	public void addCommandExecuter(InvokerExecutor exe){
		executorList.add(exe);
	}

	public String getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}
}
