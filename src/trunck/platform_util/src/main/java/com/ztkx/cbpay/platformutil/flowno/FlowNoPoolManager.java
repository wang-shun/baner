package com.ztkx.cbpay.platformutil.flowno;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.threadpool.ThreadPoolManager;
/**
 * 平台流水号池，管理器
 * @author zhangxiaoyun
 * 2016年3月10日 下午7:55:42
 * <p>Company:ztkx</p>
 */
public class FlowNoPoolManager {
	
	private static FlowNoPoolManager instance = null;
	private int poolSize = 100;
	public static Logger logger = Logger.getLogger(FlowNoPoolManager.class);
	private String config_path = BaseConfig.getConfig(ConstantConfigField.CONFIGPATH);
	private String conf_fileName=config_path+File.separator+"flowno_conf.properties";
	private float divisor = 0.25f;
	private ConcurrentLinkedQueue<String> flowNoPool = new ConcurrentLinkedQueue<String>();
	DecimalFormat df = new DecimalFormat("000000");
	private int maxSequence = -1;
	private int stepLength=-1;		//增加的步长
	private volatile int currentSequence=-1;	//当前值
	private volatile int initSequence = -1;
	private Properties confPro = null;	//流水配置文件
	private AtomicBoolean isloading = new AtomicBoolean(false);	//是否正在装在流水号
	
	private FlowNoPoolManager(){
		String sizeStr = BaseConfig.getConfig(ConstantConfigField.FLOWNOPOOLSIZE);
		
		if(null != sizeStr && !sizeStr.equals("")){
			poolSize = Integer.parseInt(sizeStr);
			logger.info("flowno poolsize is "+poolSize);
		}else{
			logger.warn("short of system params "+ConstantConfigField.FLOWNOPOOLSIZE+" use default config "+poolSize);
		}
		
		confPro = new Properties();
		try {
			confPro.load(new FileInputStream(conf_fileName));
			stepLength = Integer.parseInt(confPro.getProperty(ConstantConfigField.FLOWNO_STEPLENGTH));
			maxSequence = Integer.parseInt(confPro.getProperty(ConstantConfigField.FLOWNO_MAXSEQUENCE));
			initSequence = Integer.parseInt(confPro.getProperty(ConstantConfigField.FLOWNO_INITSEQUENCE));
			currentSequence = Integer.parseInt(confPro.getProperty(ConstantConfigField.FLOWNO_CURRENTSEQUENCE));
			String flowNoTmp = df.format(currentSequence);
			flowNoPool.add(flowNoTmp);
		} 
		catch (IOException e) {
			logger.error("load flowno_conf.properties exception",e);
		}
		
		/**
		 * 加载流水号
		 */
		loadingFlowNo();
		
		
	}

	/**
	 * 初始化流水号穿冲池
	 * 
	 * 2016年3月11日 上午10:25:54
	 * @author zhangxiaoyun
	 */
	private void loadingFlowNo() {
		//开始装在流水号
		if(isloading.compareAndSet(false, true)){
			//加载流水号
			for(int i =0;i<poolSize;i++){
				currentSequence = currentSequence+stepLength;

				//如果当前值超过最大值，将当前值置为初始值
				if(currentSequence>maxSequence){
					currentSequence = initSequence;
				}
				if(logger.isDebugEnabled()){
					logger.debug("currentSequence ["+currentSequence+"]");
				}
				flowNoPool.add(df.format(currentSequence));
			}
			
			confPro.setProperty(ConstantConfigField.FLOWNO_CURRENTSEQUENCE, String.valueOf(currentSequence));
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(conf_fileName);
				confPro.store(out, "auto save");
			} catch (Exception e) {
				logger.error("rewrite config properties fail",e);
			}finally{
				try {
					out.close();
				} catch (IOException e) {
					logger.error("close outputStream error",e);
				}
				//保存配置wenjia
				saveConfFile();
			}
			isloading.getAndSet(false);
			logger.info("flowNo pool init finesh");
		}else{
			logger.warn("already start load flowNo,with no need for loading");
		}
	}
	
	/**
	 * 初始化类
	 * @return
	 */
	public static FlowNoPoolManager getInstance(){
		if(instance == null){
			synchronized (FlowNoPoolManager.class) {
				if(instance == null){
					instance = new FlowNoPoolManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 生成流水号序列
	 * @return
	 * 2016年3月11日 上午11:54:49
	 * @author zhangxiaoyun
	 */
	public String getSequence() {
		String flowNo = null;
		int size = flowNoPool.size();
		
		if(size<divisor*poolSize){
			//如果当前池子中的流水号小于 总大小*0.25，就创建线程填充流水号池
			if(logger.isDebugEnabled()){
				logger.debug("flowNoPool size less than threshold size is  ["+size+"] start refill flowNo pool");
			}
			FlowNoCreater creater = new FlowNoCreater();
			ThreadPoolManager.getExecutorService().execute(creater);
		} 
		
		while(flowNo==null){
			flowNo = flowNoPool.poll();
		}
		if(logger.isDebugEnabled()){
			logger.debug("obtain flowno is "+flowNo);
		}
		return flowNo;
	}
	
	/**
	 * 线程结束的时候保持配置文件
	 * 
	 * 2016年3月11日 上午11:49:21
	 * @author zhangxiaoyun
	 */
	public void saveConfFile(){
		logger.info("start save flowno config file");
		confPro.setProperty(ConstantConfigField.FLOWNO_CURRENTSEQUENCE, String.valueOf(currentSequence));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(conf_fileName);
			confPro.store(out, ConstantConfigField.FLOWNO_CURRENTSEQUENCE);
		} catch (Exception e) {
			logger.error("rewrite config properties fail",e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				logger.error("close outputStream error",e);
			}
		}
		logger.info("flowno config file save finish");
	}
	
	class FlowNoCreater implements Runnable{
		@Override
		public void run() {
			loadingFlowNo();
		}
	}
}
