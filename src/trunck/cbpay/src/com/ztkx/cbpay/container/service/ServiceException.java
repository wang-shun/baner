package com.ztkx.cbpay.container.service;

import com.ztkx.cbpay.container.BaseException;

public class ServiceException extends BaseException {

	public ServiceException() {
		super();
	}
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
