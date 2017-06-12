package com.ztkx.transplat.container.initload;

import java.util.List;

/**
 * 初始化工厂接口
 * @author zhangxiaoyun
 * 2016年2月2日 下午1:54:29
 * <p>Company:ztkx</p>
 */
public interface InitFactory {
	public void factory(List<InitialBean> list);
}
