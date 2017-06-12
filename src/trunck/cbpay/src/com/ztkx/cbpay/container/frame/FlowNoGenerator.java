package com.ztkx.cbpay.container.frame;

import java.text.DecimalFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 交易平台流水号生成器
 * @author zhangxiaoyun
 *
 */
public class FlowNoGenerator {
	private int sequence = 0;
	private DecimalFormat df = new DecimalFormat("000000");
	private int max_value = 1000000;
	public static FlowNoGenerator instance = new FlowNoGenerator();
	private Lock lock = new ReentrantLock();
	
	private FlowNoGenerator(){
		
	}
	
	/**
	 * 获取唯一序列号
	 * @return
	 */
	private String getSequnce() {
		lock.lock();
		int seq = 0;
		try {
			seq = ++sequence;
			if (seq >= max_value) {
				sequence = 0;
				seq = ++sequence;
			}
		} finally {
			lock.unlock();
		}
		String formatRes = df.format(seq);
		return formatRes;
	}
	
	/**
	 * 获取流水号
	 * 流水号规则：容器名称_集群节点id_yyyyMMddHHmmssSSS六位的序列号
	 * @return
	 */
	public String getFlowNo(){
		StringBuffer sb = new StringBuffer(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
		sb.append(ConstantConfigField.TABLE_PRI_KEY_SEPARATOR);
		sb.append(BaseConfig.getConfig(ConstantConfigField.BASECONF_CLUSTER));
		sb.append(ConstantConfigField.TABLE_PRI_KEY_SEPARATOR);
		sb.append(TimeUtil.getCurrentTime("yyyyMMddHHmmssSSS"));
		sb.append(getSequnce());
		return sb.toString();
	}
}
