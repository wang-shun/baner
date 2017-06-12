package com.ztkx.cbpay.business.serviceimp.PAB;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 查询客户号
 * @author zhangxiaoyun
 * 2016年3月21日 下午3:42:03
 * <p>Company:ztkx</p>
 */
public class QueryCustNo implements BusinessService{

	Logger logger = Logger.getLogger(QueryCustNo.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		String custNo = BServerParamData.getInstance().getParamsValue(context.getSDO().serverId,BusinessConstantField.PAB_BECIF).getParavalue();
		context.setFieldStr("BECIF",custNo,CommonContext.SCOPE_GLOBAL);
		logger.info("custNo ["+custNo+"]");
		return context;
	}

}
