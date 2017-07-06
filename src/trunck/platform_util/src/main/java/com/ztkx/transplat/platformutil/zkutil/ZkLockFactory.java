package com.ztkx.transplat.platformutil.zkutil;

import com.msds.config.ZkServerConfig;
import lombok.Setter;
import org.I0Itec.zkclient.ZkClient;

public class ZkLockFactory {

	@Setter
	private ZkServerConfig config;

	@Setter
	private String lockRootPath;
	
	
    public ZkLockFactory() {
		super();
	}
	
	public ZkLock getZkLock(String lockPath){

		ZkClient zkClient = new ZkClient(config.getServerAddress(), config.getSessionTimeout(),config.getConnectTimeout());
		zkClient.createPersistent(lockRootPath, true);
		
		return new ZkLock(lockRootPath, lockPath, zkClient);
	}
	
	/**
	 * 获取互斥锁<br>
	 * 在lockpath上加锁
	 * @param lockPath
	 * @return
	 */
	public ZkMutexLock getMutexLock(){
		return ZkMutexLock.getInstance(config, lockRootPath);
	}
}
