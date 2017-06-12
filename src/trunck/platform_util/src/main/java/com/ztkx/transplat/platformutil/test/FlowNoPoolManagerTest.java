package com.ztkx.transplat.platformutil.test;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.transplat.platformutil.threadpool.ThreadPoolManager;
import org.apache.log4j.PropertyConfigurator;

public class FlowNoPoolManagerTest {

	public void test() {
		
		BaseConfig.getInstence("E:\\svn_workspack\\transplat\\src\\trunck\\platform_util\\config\\baseConf.properties");
		BaseConfig.setConfig("container_path", "E:\\svn_workspack\\transplat\\src\\trunck\\platform_util\\config\\");
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
