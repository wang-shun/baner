package com.ztkx.transplat.platformutil.flowno;


import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;
import com.ztkx.transplat.platformutil.zkutil.ZkFlowNoGenerator;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用zk实现全局唯一流水号
 * @author zhangxiaoyun
 * 2016年3月10日 下午7:55:42
 * <p>Company:ztkx</p>
 */
public class ZKFlowNoPoolManager {

	public static Logger log = Logger.getLogger(FlowNoPoolManager.class);
	private static ZKFlowNoPoolManager instance = null;
	private int poolSize = 100;
	private float divisor = 0.25f;
	private Map<String,ConcurrentLinkedQueue<String>> flowMap = new ConcurrentHashMap<String,ConcurrentLinkedQueue<String>>();
	private Map<String,AtomicBoolean> isloadingMap = new HashMap<String,AtomicBoolean>();
	private Map<String,Integer> maxSeqMap = new HashMap<String,Integer>();
	private Map<String,DecimalFormat> formatMap = new HashMap<String,DecimalFormat>();
	private volatile int currentSequence=-1;	//当前值
	private volatile int defInitSequence = 0;
	private String zkAddress = null;
	
	private ZKFlowNoPoolManager(){
		String sizeStr = BaseConfig.getConfig(ConstantConfigField.FLOWNOPOOLSIZE);
		zkAddress = BaseConfig.getConfig(ConstantConfigField.ZKADDRESS);
		log.info("zkAddress ["+zkAddress+"]");
		if(null != sizeStr && !sizeStr.equals("")){
			poolSize = Integer.parseInt(sizeStr);
			log.info("flowno poolsize is "+poolSize);
		}else{
			log.warn("short of system params "+ConstantConfigField.FLOWNOPOOLSIZE+" use default config "+poolSize);
		}
		ZkFlowNoGenerator.getInstance().setZkAddress(zkAddress);
		ZkFlowNoGenerator.getInstance().start();
		try {
			/**
			 * 加载流水号
			 */
			loadingFlowNo("6","default");
		} 
		catch (Exception e) {
			log.error("load flowno_conf.properties exception",e);
		}
		
	}

	/**
	 * 初始化流水号穿冲池
	 * 
	 * 2016年3月11日 上午10:25:54
	 * @author zhangxiaoyun
	 */
	private void loadingFlowNo(String flowNoLength,String sequenceName) {
		
		String seqName = sequenceName+"_"+flowNoLength;
		
		AtomicBoolean isloading = isloadingMap.get(seqName);
		if(isloading == null){
			isloading = new AtomicBoolean(false);
			isloadingMap.put(seqName, isloading);
		}
		if(isloading.compareAndSet(false, true)){
			isloadingMap.put(seqName, isloading);
			//开始装在流水号
			ConcurrentLinkedQueue<String> flowQue = flowMap.get(seqName);
			if(flowQue==null){
				flowQue = new ConcurrentLinkedQueue<String>();
				flowMap.put(seqName, flowQue);
			}
			
			DecimalFormat df = formatMap.get(seqName);
			if(df == null){
				int length = Integer.parseInt(flowNoLength);
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<length;i++){
					sb.append("0");
				}
				df = new DecimalFormat(sb.toString());
				formatMap.put(seqName, df);
			}
			//初始化最大序列号
			Integer maxSequence = maxSeqMap.get(seqName);
			if(maxSequence==null){
				int length = Integer.parseInt(flowNoLength);
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<length;i++){
					sb.append("9");
				}
				maxSequence = Integer.parseInt(sb.toString());
				maxSeqMap.put(seqName, maxSequence);
			}
			
			try {
				//对节点加锁
				while(!ZkFlowNoGenerator.getInstance().lock(seqName)){
					//每个10毫秒重复请求锁
					Thread.sleep(10);
				}
				//获取当前流水号
				byte[] flowNobyte = ZkFlowNoGenerator.getInstance().getCurrentFlowNo(seqName);
				//加载流水号
				currentSequence = Integer.parseInt(new String(flowNobyte));
				for(int i =0;i<poolSize;i++){
					currentSequence = currentSequence+1;
					//如果当前值超过最大值，将当前值置为初始值
					if(currentSequence>maxSequence){
						currentSequence = defInitSequence;
					}
					log.debug("currentSequence ["+currentSequence+"]");
					flowQue.add(df.format(currentSequence));
				}
				ZkFlowNoGenerator.getInstance().setCurrentFlowNo(seqName, String.valueOf(currentSequence).getBytes());
				flowMap.put(seqName, flowQue);
			} catch (Exception e) {
				log.error("generator flowno fail",e);
			}finally{
				ZkFlowNoGenerator.getInstance().unlock();
				if(isloading!=null){
					isloading.getAndSet(false);
					isloadingMap.put(seqName, isloading);
				}
			}
			log.info("flowNo pool init finesh");
		}else{
			log.warn("already start load flowNo,with no need for loading");
		}
	}
	
	/**
	 * 初始化类
	 * @return
	 */
	public static ZKFlowNoPoolManager getInstance(){
		if(instance == null){
			synchronized (ZKFlowNoPoolManager.class) {
				if(instance == null){
					instance = new ZKFlowNoPoolManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 默认获取6为长度的序列
	 * @return
	 */
	public String getSequence() {
		return getSequence("6","default");
	}
	
	
	/**
	 * 生成流水号序列
	 * 2016年3月11日 上午11:54:49
	 * @author zhangxiaoyun
	 * @param flowNoLength	序列长度
	 * @param sequenceName   序列名称
	 * @return
	 */
	public String getSequence(String flowNoLength,String sequenceName) {
		
		String flowNo = null;
		
		ConcurrentLinkedQueue<String> flowQue = flowMap.get(sequenceName+"_"+flowNoLength);
		if(flowQue == null){
			FlowNoCreater creater = new FlowNoCreater(flowNoLength,sequenceName);
			creater.run();
			flowQue = flowMap.get(sequenceName+"_"+flowNoLength);
		}
		int size = flowQue.size();
		if(size<divisor*poolSize){
			//如果当前池子中的流水号小于 总大小*0.25，就创建线程填充流水号池
			log.debug("flowNoPool size less than threshold size is  ["+size+"] start refill flowNo pool");
			FlowNoCreater creater = new FlowNoCreater(flowNoLength,sequenceName);
			ThreadPoolManager.getInstance().getExecutorService().execute(creater);
		} 
		while(flowNo==null){
			flowNo = flowQue.poll();
		}
		log.debug("obtain flowno is ["+flowNo+"]");
		
		return flowNo;
	}
	
	class FlowNoCreater implements Runnable{
		private String flowNoLength;
		private String sequenceName;
		public FlowNoCreater(String flowNoLength,String sequenceName){
			this.flowNoLength = flowNoLength;
			this.sequenceName = sequenceName;
		}
		@Override
		public void run() {
			loadingFlowNo(flowNoLength,sequenceName);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ZKFlowNoPoolManager.getInstance().getSequence());
		System.out.println(ZKFlowNoPoolManager.getInstance().getSequence("9","couponsId"));
	}
}
