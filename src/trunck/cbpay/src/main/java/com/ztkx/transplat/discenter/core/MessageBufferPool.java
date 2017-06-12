package com.ztkx.transplat.discenter.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;

/**
 * 实现discenter发出消息的缓冲
 * 比较简陋，先用着以后再做优化
 * @author zhangxiaoyun
 * 2016年1月28日 上午10:03:07
 * <p>Company:ztkx</p>
 */
public class MessageBufferPool {
	private static MessageBufferPool bufferPool = null;
	private int poolSize = 300;
	private Logger logger = Logger.getLogger(MessageBufferPool.class);
	private Map<String,Object> synObjPool = null;	//阻塞对象缓冲池
	private Map<String,byte[]> messagePool = null;	//消息缓冲池
	private MessageBufferPool(){
		String sizeStr = BaseConfig.getConfig(ContainerConstantField.MESSAGE_BUFFER_POOL_SIZE);

		if(null != sizeStr && !sizeStr.equals("")){
			poolSize = Integer.parseInt(sizeStr);
			logger.info("message_buffer_pool_size "+poolSize);
		}else{
			logger.warn("short of system params "+ContainerConstantField.MESSAGE_BUFFER_POOL_SIZE+" use default config "+poolSize);
		}
		synObjPool = new ConcurrentHashMap<String,Object>((int) (poolSize/0.75+1));
		messagePool = new ConcurrentHashMap<String,byte[]>((int) (poolSize/0.75+1));
	}
	
	public static MessageBufferPool getInstance(){
		if(bufferPool==null){
			synchronized (MessageDiscenter.class) {
				if(bufferPool==null){
					bufferPool = new MessageBufferPool();
				}
			}
		}
		return bufferPool;
	}
	
	/**
	 * 缓冲同步对象
	 * @param key
	 * @param value
	 */
	public void addSynObj(String key,Object value){
		synObjPool.put(key, value);
	}
	/**
	 * 获取同步对象
	 * @param key
	 * @return
	 */
	public Object getSynObj(String key){
		return synObjPool.get(key);
	}
	/**
	 * 删除同步对象
	 * @param key
	 * @return
	 */
	public Object removeSynObj(String key){
		return synObjPool.remove(key);
	}
	
	/**
	 * 缓冲消息体
	 * @param key
	 * @param value
	 */
	public void addMsg(String key,byte[] msg){
		messagePool.put(key, msg);
	}
	/**
	 * 删除消息
	 * @param key
	 * @return
	 */
	public byte[] removeMsg(String key){
		return messagePool.remove(key);
	}
	/**
	 * 获取消息
	 * @param key
	 * @return
	 */
	public Object getMsg(String key){
		return messagePool.get(key);
	}
}
