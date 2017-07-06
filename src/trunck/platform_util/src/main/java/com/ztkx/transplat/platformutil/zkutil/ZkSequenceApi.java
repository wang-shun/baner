package com.ztkx.transplat.platformutil.zkutil;

import com.msds.config.ZkServerConfig;
import lombok.Setter;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZkSequenceApi {

	@Setter
	private ZkServerConfig config;

	@Setter
	private String seqRootPath;
	
	private static final Set<String> sequencePathCache = new HashSet<String>();
	
	private static final Lock lock = new ReentrantLock();
	
	private ZkClient zkClient = null;
	
	@PostConstruct
	public void init(){
		zkClient = new ZkClient(config.getServerAddress(), config.getSessionTimeout(),config.getConnectTimeout());
		zkClient.createPersistent(seqRootPath, true);
	}
	
	/**
	 * 
	 * 获取到的sequence形如 0000000001、0000000002
	 * @param sequenceName
	 * @return
	 */
	public String getNextSeq(String sequenceName){

		String sequencePath = seqRootPath + "/" + sequenceName;
		if(!sequencePathCache.contains(sequencePath)){
			lock.lock();
			try {
				if(!sequencePathCache.contains(sequencePath)){
					zkClient.createPersistent(sequencePath,true);
				}
			} catch (ZkNodeExistsException e) {
				sequencePathCache.add(sequencePath);
				//TODO warning log
			}finally{
				lock.unlock();
			}
		}
		
		String totalStr = zkClient.createEphemeralSequential(sequencePath+"/",null);
		zkClient.delete(totalStr);
		int seqIdx = totalStr.lastIndexOf("/")+1;
		return totalStr.substring(seqIdx);
	}
	
	@PreDestroy
	public void destroy(){
		zkClient.close();
	}

}
