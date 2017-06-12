package com.ztkx.cbpay.platformutil.test;

import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.cbpay.platformutil.threadpool.ThreadPoolManager;
import org.apache.log4j.PropertyConfigurator;

public class FlowNoPoolManagerTest {

	public void test() {
		
		BaseConfig.getInstence("E:\\svn_workspack\\cbpay\\src\\trunck\\platform_util\\config\\baseConf.properties");
		BaseConfig.setConfig("container_path", "E:\\svn_workspack\\cbpay\\src\\trunck\\platform_util\\config\\");
		PropertyConfigurator.configure(BaseConfig.getConfig(ConstantConfigField.CONFIGPATH)+"log4j.properties");
		ThreadPoolManager.getInstance();
		FlowNoPoolManager manager = FlowNoPoolManager.getInstance();
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
		System.out.println(manager.getSequence());
	}

}
