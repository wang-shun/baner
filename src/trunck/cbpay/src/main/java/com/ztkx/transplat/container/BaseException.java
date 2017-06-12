package com.ztkx.transplat.container;

/**
 * 平台的基础异常类
 * @author zhangxiaoyun
 * 2016年3月2日 上午11:46:38
 * <p>Company:ztkx</p>
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -2842869001528876424L;

	public BaseException() {
		super();
	}
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
}
