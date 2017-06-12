package com.ztkx.transplat.platformutil.activemq;

import javax.jms.MessageListener;
import javax.jms.Session;

/**
 * 自定义的MessageListener父类，所有自己写的MessageListener都需继承它，以便注入session。
 * 
 * @author tianguangzhao
 *
 */
public abstract class CBpayListener implements MessageListener {

	protected Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
