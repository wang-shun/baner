package com.ztkx.transplat.platformutil.test;

import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;

public class BaseConfigTest {

	public void test() {
		BaseConfig.getInstence("E:\\svn_workspack\\transplat\\src\\trunck\\platform_util\\config\\baseConf.properties");
		System.out.println(BaseConfig.getConfig("aaa"));
	}

}
