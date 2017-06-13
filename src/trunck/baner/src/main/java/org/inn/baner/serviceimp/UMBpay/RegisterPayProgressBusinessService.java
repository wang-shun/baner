package org.inn.baner.serviceimp.UMBpay;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BPaymentFlow;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BPaymentFlowData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 支付交易，调用宝易互通进行支付之前，将交易流水信息入库保存。交易流水状态置为初始状态
 * 
 * @author tianguangzhao
 */
public class RegisterPayProgressBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(RegisterPayProgressBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug(" RegisterPayProgressBusinessService beginning ...");
		}
		try {
			// 将交易流水插入到数据库中
			insertPayProgress(context);
		} catch (ServiceException e) {
			logger.error("insert pay progress error !", e);
			//update by tianguangzhao 修改错误码为“业务服务执行失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" RegisterPayProgressBusinessService success ...");
		}
		return context;
	}

	/**
	 * 将收单支付信息入库
	 * @param context
	 * @throws ServiceException
	 */
	private void insertPayProgress(CommonContext context) throws ServiceException {
		BPaymentFlowData bpfd = new BPaymentFlowData();
		BPaymentFlow bf = new BPaymentFlow();
		bf.setPayorderid(context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,
						CommonContext.SCOPE_GLOBAL));
		bf.setTrandate(PlatDateParamData.getInstance().getPlatDate());
		bf.setTrantime(TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE));
		bf.setTxnamt(context.getFieldStr(
				BusinessMessageFormateConstant.UMB_CP0001_TRANAMT,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_CP0001_TRANAMT,
						CommonContext.SCOPE_GLOBAL));
		//update by tianguangzhao 修改bug ，币种存入错误！
		bf.setCurrency(context.getFieldStr(
				BusinessMessageFormateConstant.UMB_CP0001_CURTYPE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(
						BusinessMessageFormateConstant.UMB_CP0001_CURTYPE,
						CommonContext.SCOPE_GLOBAL));
		bf.setMerchantid(context.getFieldStr(
				BusinessMessageFormateConstant.UMB_MERCHANT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO,
						CommonContext.SCOPE_GLOBAL));

		// 订单号只能从收银台的报文中取到
		bf.setOrderid(context.getFieldStr(BusinessMessageFormateConstant.CASH_ORDER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_ORDER_ID,
						CommonContext.SCOPE_GLOBAL));
		// 订单日期和订单时间从收银台报文中获取
		bf.setOrderdate(context.getFieldStr(
				BusinessMessageFormateConstant.CASH_TRAN_DATE,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE,
						CommonContext.SCOPE_GLOBAL));
		bf.setOrdertime(context.getFieldStr(
				BusinessMessageFormateConstant.CASH_TRAN_TIME,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_TIME,
						CommonContext.SCOPE_GLOBAL));
		// 付款渠道，现在只有宝易互通
		bf.setChannel("UMB");
		// 第一次登记支付流水时，状态为初始化
		bf.setPaystatus(BPaymentFlow.TRADING_INIT);
		// 获取购买者标识，
		bf.setPurchaserid(context.getFieldStr(BusinessMessageFormateConstant.CBPAY_PURCHASER_ID,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(BusinessMessageFormateConstant.CBPAY_PURCHASER_ID,CommonContext.SCOPE_GLOBAL));
		// 第一次插入记录时，对账状态为初始状态
		bf.setCheckingstatus(BPaymentFlow.CHECKING_INIT);
		// 付款卡号
		String paycard = context.getFieldStr(
				BusinessMessageFormateConstant.CASH_CHS006_ACCOUNT_NO,
				CommonContext.SCOPE_GLOBAL) == null ? default_value : context
				.getFieldStr(
						BusinessMessageFormateConstant.CASH_CHS006_ACCOUNT_NO,
						CommonContext.SCOPE_GLOBAL);
		bf.setPaycard(paycard);

		try {
			bpfd.getConnection();
			bpfd.insertData(bf);
		} catch (HandlerException e) {
			//插入数据库失败，注入错误码(插入数据异常)
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517,context);
			throw new ServiceException(e);
		} finally {
			//调用工具类释放资源
			DataHandlerUtil.releaseSource(bpfd);
		}
	}
}
