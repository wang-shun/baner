package com.ztkx.transplat.invoker.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.ztkx.transplat.invoker.bean.InvokerParams;

/**
 * @ClassName: InvokerParamQueue
 * @Description: 客户端从此队列获取命令的执行结果
 * @author zhangxiaoyun
 * @date 2016年7月5日 下午3:32:10
 */
public class InvokerParamQueue {
	
	private BlockingQueue<InvokerParams> queue = null;
	
	public static InvokerParamQueue instance = new InvokerParamQueue();
	
	private InvokerParamQueue(){
		queue = new LinkedBlockingQueue<InvokerParams>();
	}
	
	/**
	 * 从队列中添加执行结果
	 * 2016年7月5日 下午3:33:28
	 * @author zhangxiaoyun
	 * @param params
	 * @return
	 * @return boolean
	 */
	public boolean add(InvokerParams params){
		return queue.offer(params);
	}
	
	/**
	 * 从队列中获取执行结果
	 * 2016年7月5日 下午3:33:43
	 * @author zhangxiaoyun
	 * @param timeout
	 * @return
	 * @return InvokerParams
	 */
	public InvokerParams take(long timeout){
		try {
			return queue.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
