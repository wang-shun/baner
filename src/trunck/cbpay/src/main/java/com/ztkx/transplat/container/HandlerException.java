package com.ztkx.transplat.container;

import com.ztkx.transplat.container.service.ServiceException;

/**
 * handler异常类型
 * @author zhangxiaoyun
 * 2016年3月2日 下午1:35:48
 * <p>Company:ztkx</p>
 */
public class HandlerException extends ServiceException {

	public HandlerException() {
		super();
	}

	public HandlerException(String message, Throwable cause) {
		super(message, cause);
	}

	public HandlerException(String message) {
		super(message);
	}

	public HandlerException(Throwable cause) {
		super(cause);
	}

}
