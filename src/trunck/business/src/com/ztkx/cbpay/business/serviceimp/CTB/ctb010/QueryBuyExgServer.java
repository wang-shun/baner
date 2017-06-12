package com.ztkx.cbpay.business.serviceimp.CTB.ctb010;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BForeignCurrencyRateData;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 查询币种最小汇率的服务方
 * @author zhangxiaoyun
 * 2016年3月21日 下午3:42:03
 * <p>Company:ztkx</p>
 */
public class QueryBuyExgServer implements BusinessService{

	Logger logger = Logger.getLogger(QueryBuyExgServer.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		String currencyCode = context.getFieldStr("currencyCode", CommonContext.SCOPE_GLOBAL);
		String platdate = PlatDateParamData.getInstance().getPlatDate();
		//查询该币种的最小汇率
		BForeignCurrencyRateData dataHandler = new BForeignCurrencyRateData();
		String server = dataHandler.getMinRateSvr(platdate, currencyCode);
		logger.info("get min exg server is ["+server+"]");
		if(server == null){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000528);
		}
		context.getSDO().serverId=server;
		return context;
	}
}
