package com.ztkx.cbpay.container.msg;

import com.ztkx.cbpay.platformutil.context.CommonContext;

public interface MessageIdentifier {
	public String identify(CommonContext context);
}
