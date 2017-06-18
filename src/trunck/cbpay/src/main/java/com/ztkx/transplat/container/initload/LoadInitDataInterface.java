package com.ztkx.transplat.container.initload;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.reload.ReloadAble;

/**
 * 加载初始化数据接口
 * @ClassName: LoadInitDataInterface
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoyun
 * @date 2016年6月30日 下午3:54:22
 */
public interface LoadInitDataInterface extends ReloadAble,TransactionMJDBC {
	/**
	 * 初始化的时候加载数据
	 * 2016年6月30日 下午2:13:18
	 * @author zhangxiaoyun
	 * @return void
	 */
	public void load() throws HandlerException;
}
