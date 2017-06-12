package com.ztkx.transplat.reload;

import com.ztkx.transplat.container.HandlerException;

/** 
 * 可重载接口
 * @author  zhagnxiaoyun: 
 * @date 2016年6月30日 下午2:21:25 
 */
public interface ReloadAble {
	/**
	 * 重载的时候预加载表数据
	 * 2016年6月30日 下午2:13:30
	 * @author zhangxiaoyun
	 * @return void
	 */
	public boolean preload() throws HandlerException;
	
	/**
	 * 如果预加载成功，加载最新的数据到内存中
	 * 2016年6月30日 下午2:14:06
	 * @author zhangxiaoyun
	 * @return void
	 */
	public void reload() throws HandlerException;
	
	/**
	 * 重载失败处理
	 * 2016年6月30日 下午2:17:06
	 * @author zhangxiaoyun
	 * @return void
	 */
	public void failHand() throws HandlerException;
}
