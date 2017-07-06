package com.ztkx.transplat.platformutil.zkutil;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * 用zk实现分布式锁，获取全局唯一流水号
 * @author zhangxiaoyun
 */
public class ZkFlowNoGenerator {
	
	// /zkflowno/data     /zkflowno/lock
	private String lockRootPath="/zkflowno";
	private static ZkFlowNoGenerator instance = null;
	private CuratorFramework zkClient;
	private int getLockWaitTime=3;//等待3毫秒
	private String zkAddress = null;
    private ThreadLocal<InterProcessLock> lockLocal = new  ThreadLocal<InterProcessLock>();
    
    public void setZkAddress(String zkAddress){
    	this.zkAddress = zkAddress;
    }
    
    public static ZkFlowNoGenerator getInstance(){
		if(instance==null){
			synchronized (ZkFlowNoGenerator.class) {
				if(instance==null){
					instance = new ZkFlowNoGenerator();
				}
			}
		}
		return instance;
	}
    
    
    private ZkFlowNoGenerator() {
		
	}
    
    public void start(){
    	//链接zk的时候，如果链接失败间隔一秒重试，共重试3次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		zkClient = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
		this.zkClient.start();
    }
    
    
    
    
    /**
     * 获取/zkflowno/data/flowNoLength节点的数据<br>
     * 获取当前节点上的数据<br>
     * 需要先调用lock(String flowNoLength)对节点加锁<br>
     * @param flowNoLength 节点名称
     * @return
     * @throws Exception
     */
    public byte[] getCurrentFlowNo(String flowNoLength) throws Exception{
    	byte[] currentFlowNo = null;
		try {
			Stat stat = zkClient.checkExists().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength);
			if(stat == null){
				//如果节点不存在，需要创建节点并初始化流水号
				zkClient.create().creatingParentsIfNeeded().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength, "0".getBytes());
				currentFlowNo = "0".getBytes();
			}else{
				currentFlowNo = zkClient.getData().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength);
			}
		} catch (Exception e) {
			//解锁
			unlock();
			throw e;
		}
    	return currentFlowNo;
    }
    
    /**
	 * 设置/zkflowno/data/flowNoLength节点的数据
	 * @param flowNoLength 节点名称
	 * @param data 数据
	 * @return 设置是否成功
	 * @throws Exception
	 */
	public boolean setCurrentFlowNo(String flowNoLength,byte[] data) throws Exception{
		boolean result = false;
		try {
			Stat stat = zkClient.checkExists().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength);
			if(stat == null){
				//如果节点不存在，需要创建节点并初始化流水号
				zkClient.create().creatingParentsIfNeeded().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength, data);
			}else{
				stat = zkClient.setData().forPath(lockRootPath+"/"+"data"+"/"+flowNoLength,data);
			}
			result = true;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
    
	/**
	 * 在/zkflowno/lock/flowNoLength节点上加锁
	 * @param flowNoLength
	 * @return
	 * @throws Exception 
	 */
	public boolean lock(String flowNoLength) throws Exception{
		boolean ret = false;
        try {
        	InterProcessLock lock = new InterProcessMutex(zkClient, lockRootPath+"/"+"lock"+"/"+flowNoLength);
        	ret = lock.acquire(getLockWaitTime,TimeUnit.MILLISECONDS);
        	lockLocal.set(lock);
        } catch (Exception e) {
        	throw e;
        }
		return ret;
	}
	/**
	 * 释放锁
	 */
	public void unlock(){
		try {
			lockLocal.get().release();
			lockLocal.remove();
		} catch (Exception e) {
			lockLocal.remove();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ZkFlowNoGenerator.getInstance().setZkAddress("127.0.0.1:2181");
		ZkFlowNoGenerator.getInstance().start();
		System.out.println(ZkFlowNoGenerator.getInstance().lock("6"));
		byte[] currentFlow = ZkFlowNoGenerator.getInstance().getCurrentFlowNo("6");
		System.out.println(new String(currentFlow));
		int current = Integer.parseInt(new String(currentFlow));
		current+=1;
		String a = String.valueOf(current);
		ZkFlowNoGenerator.getInstance().setCurrentFlowNo("6", a.getBytes());
		Thread.sleep(100000);
		ZkFlowNoGenerator.getInstance().unlock();
//		CloseableUtils.closeQuietly(curator);
	}
	
}
