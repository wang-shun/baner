package com.ztkx.transplat.platformutil.zkutil;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 互斥锁<br>
 * 如果当前锁需要跨线程<pre>
 * 请调用getLock(String lockPath)和unlock(InterProcessLock lock)
 * 需要客户端在获取锁对象后调用acquire方法尝试锁定当前对象
 * 使用完锁后调用锁的release方法释放当前锁
 * </pre>
 * 如果当前锁对象不需要跨线程<pre>
 * 调用lock(String lockPath)锁定当前对象
 * 调用unlock()释放当前锁
 * </pre>
 */
public class ZkMutexLock {

	private static Logger log = Logger.getLogger(ZkMutexLock.class);
	private String lockRootPath;
	private CuratorFramework zkClient;
	ThreadLocal<InterProcessLock> lockLocal = new  ThreadLocal<InterProcessLock>();
	private int getLockWaitTime;//重试次数
	
	private static ZkMutexLock instance = null;
	
	public static ZkMutexLock getInstance(String lockRootPath){
		if(instance==null){
			synchronized (ZkMutexLock.class) {
				if(instance==null){
					instance = new ZkMutexLock(lockRootPath);
				}
			}
		}
		return instance;
	}

	private ZkMutexLock(String lockRootPath) {
		//链接zk的时候，如果链接失败间隔一秒重试，共重试3次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		zkClient = CuratorFrameworkFactory.newClient(BaseConfig.getConfig(ConstantConfigField.ZKADDRESS), retryPolicy);
		this.getLockWaitTime = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.LOCKWAITTIME));
		this.lockRootPath = lockRootPath;
		this.zkClient.start();
	}
	
	public boolean lock(String lockPath){
		boolean ret = false;
        try {
        	InterProcessLock lock = new InterProcessMutex(zkClient, lockRootPath+"/"+lockPath);
        	ret = lock.acquire(getLockWaitTime,TimeUnit.MILLISECONDS);
        	lockLocal.set(lock);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return ret;
	}
	
	
	public void unlock(){
		try {
			lockLocal.get().release();
			lockLocal.remove();
		} catch (Exception e) {
			log.error("get lock ["+lockLocal.get()+"] exception",e);
			lockLocal.remove();
		}
	}
	
	
	public InterProcessLock getLock(String lockPath){
		InterProcessLock lock = null;
        try {
        	lock = new InterProcessMutex(zkClient, lockRootPath+"/"+lockPath);
        } catch (Exception e) {  
            log.error("get lock ["+lockPath+"] exception",e);  
        }
		return lock;
	}
	public void unlock(InterProcessLock lock){
		try {
			lock.release();
		} catch (Exception e) {
			log.error("release lock ["+lock+"] exception",e); 
		}
	}
	
//	public static void main(String[] args) {
//		ZkServerConfig config = new ZkServerConfig();
//		config.setGetLockWaitTime(3000);
//		config.setServerAddress("127.0.0.1:2181");
//		ZkMutexLock ttt = ZkMutexLock.getInstance(config, "/zxyTest");
//		System.out.println(ttt.lock("CF000123"));
//		ttt.unlock();
//		System.out.println(ttt.lock("CF000123"));
//	}
	
}
