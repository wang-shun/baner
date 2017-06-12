package com.ztkx.cbpay.platformutil.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;

/**
 * 线程池管理类，目前做的比较简单，线程排队都先不做。等后期优化
 * 
 * @author zhangxiaoyun
 *
 */
public class ThreadPoolManager {

	private static final Logger log = Logger.getLogger(ThreadPoolManager.class);

	private int threadPoolSize = 100;

	private static ThreadPoolManager manager = null;

	private static ExecutorService es = null;

	private ThreadPoolManager() {

		String sizeStr = BaseConfig
				.getConfig(ConstantConfigField.THREADPOOLSIZE);
		if (StringUtils.isNotBlank(sizeStr)) {
			threadPoolSize = Integer.parseInt(sizeStr);
		}
		log.info("cbpay_thread_pool_size[" + threadPoolSize + "]");

		es = Executors.newFixedThreadPool(threadPoolSize, new ThreadFactory() {
			AtomicInteger num = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				String threadGroup = "cbpay_thread_pool";
				ThreadGroup tg = new ThreadGroup(threadGroup);
				int threadNum = num.incrementAndGet();
				return new Thread(tg, r, threadGroup + "_" + threadNum);
			}
		});
	}

	public static ThreadPoolManager getInstance() {
		if (manager == null) {
			synchronized (ThreadPoolManager.class) {
				if (manager == null) {
					manager = new ThreadPoolManager();
				}
			}
		}
		return manager;
	}

	/**
	 * 获取线程池
	 * 
	 * @return
	 */
	public static ExecutorService getExecutorService() {
		return es;
	}
}
