package com.ztkx.transplat.platformutil.zkutil;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collections;
import java.util.List;


public class ZkLock {
	private String lockRootPath;
	
	private ZkClient zkClient;

	private String lockPath;


    ZkLock(String lockRootPath,String lockPath,ZkClient zkClient) {
    	this.lockRootPath = lockRootPath;
		this.lockPath = lockPath;
		this.zkClient = zkClient;
	}
	
	
	public boolean lock(){
		boolean ret = false;
		
		try {
			zkClient.createPersistent(this.lockRootPath+"/"+this.lockPath,true);
		} catch (ZkNodeExistsException e) {
		}
		String thisTotalPath = zkClient.createEphemeralSequential(this.lockRootPath+"/"+this.lockPath+"/", null);
		
		String thisPath = thisTotalPath.substring(thisTotalPath.lastIndexOf("/")+1);
		List<String> childPaths = zkClient.getChildren(this.lockRootPath+"/"+this.lockPath);
		
		if(childPaths.size()==1){
			ret = true;
		}else{
            Collections.sort(childPaths);  
            int index = childPaths.indexOf(thisPath);  
            if (index == -1) {
                // never happened  
            } else if (index == 0) {
    			ret = true;
            } else {
            	
            }  
		}
		if(!ret){
			try {
				zkClient.close();
			} catch (Exception e) {
				//TODO warn log
			}
		}
		return ret;
		
	}
	
	
	public void unlock(){
		try {
			zkClient.close();
		} catch (Exception e) {
			//TODO already closed
		}
	}
	
}
