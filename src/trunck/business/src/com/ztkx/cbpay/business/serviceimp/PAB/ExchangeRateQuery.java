package com.ztkx.cbpay.business.serviceimp.PAB;

import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BForeignCurrencyRate;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BForeignCurrencyRateData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.db.DBUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 汇率查询
 * 将服务方响应的汇率入库
 * @author zhangxiaoyun
 * 2016年3月21日 下午3:38:23
 * <p>Company:ztkx</p>
 */
public class ExchangeRateQuery implements BusinessService {

	private Logger logger = Logger.getLogger(ExchangeRateQuery.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		BForeignCurrencyRateData dataOper = null;	//汇率表操作句柄
		Connection connection = null;
		try {
			/**
			 * 1.登记汇率表表
			 */
			logger.info("start register table b_foreign_currency_rate ");
			BForeignCurrencyRate bForeignCurrencyRate = new BForeignCurrencyRate();
			
			bForeignCurrencyRate.setFromId(context.getSDO().serverId);
			
			bForeignCurrencyRate.setCurrencyCode(context.getFieldStr("BUY_CCY", CommonContext.SCOPE_GLOBAL));
			//获取系统日期
			String date_time = TimeUtil.getCurrentTime("yyyyMMddHHmmss");
			String recv_date = date_time.substring(0, 8);
			bForeignCurrencyRate.setRecvDate(recv_date);
			
			//平安银行没有牌价日期，用系统日期填充牌价日期
			bForeignCurrencyRate.setExquotedate(recv_date);
			bForeignCurrencyRate.setRecvTime(date_time.substring(8));
			//市场价
			String PRICE = context.getFieldStr("PRICE", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(PRICE)){
				bForeignCurrencyRate.setPrice(new BigDecimal(PRICE));
			}
			//汇率方向
			bForeignCurrencyRate.setDirectionFlag(context.getFieldStr("DIRECTION_FLAG", CommonContext.SCOPE_GLOBAL));
			//另一方金额
			String TRAN_AMT = context.getFieldStr("TRAN_AMT", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(TRAN_AMT)){
				bForeignCurrencyRate.setTranAmt(new BigDecimal(TRAN_AMT));
			}
			
			date_time = context.getFieldStr("DATE_TIME", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(date_time)){
				bForeignCurrencyRate.setExquotedate(date_time.substring(0, 8));//牌价日期
				bForeignCurrencyRate.setExquotetime(date_time.substring(8));//牌价时间
			}
			
			//客户端成交汇率
			String CLIENT_EXCHANGE_RATE = context.getFieldStr("CLIENT_EXCHANGE_RATE", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(CLIENT_EXCHANGE_RATE)){
				bForeignCurrencyRate.setClientExchangeRate(new BigDecimal(CLIENT_EXCHANGE_RATE));
			}
			
			//默认优惠方式
			String DISCOUNT_TYPE = context.getFieldStr("DISCOUNT_TYPE", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(DISCOUNT_TYPE)){
				bForeignCurrencyRate.setDiscountType(DISCOUNT_TYPE);
			}
			
			//优惠值
			String DIS_AMT = context.getFieldStr("DIS_AMT", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(DIS_AMT)){
				bForeignCurrencyRate.setDisAmt(new BigDecimal(DIS_AMT));
			}
			
			//市场金额
			String AMT = context.getFieldStr("AMT", CommonContext.SCOPE_GLOBAL);
			if(!StringUtils.isEmpty(AMT)){
				BigDecimal big = new BigDecimal(AMT);
				bForeignCurrencyRate.setAmt(big);
				bForeignCurrencyRate.setExsellprice(big);
			}
			
			
			dataOper = new BForeignCurrencyRateData();
			//初始化链接
			connection  = dataOper.getConnection();
			dataOper.insertData(bForeignCurrencyRate);
			logger.info("register b_foreign_currency_rate table succ");
			DBUtil.commit(connection);
		} catch (Exception e) {
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			logger.error("buss service ExchangeRateQuery exec exception");
			throw new ServiceException("buss service ExchangeRateQuery exec exception",e);
		}finally{
			//释放资源
			dataOper.relaceResource();
		}
		return context;
	}
}
