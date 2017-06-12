package com.ztkx.cbpay.business.serviceimp.PAB.FBS005;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.bean.BServerParam;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.container.initdata.PlatParamsData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 8.准备付汇组包
 * @author zhangxiaoyun
 * 2016-3-24 15:16:09
 * <p>Company:ztkx</p>
 */
public class PreparePayExg implements BusinessService{

	Logger logger = Logger.getLogger(PreparePayExg.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		/**
		 * 获取数据
		 */
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		BigDecimal totalAmt =(BigDecimal)context.getObj("totalAmt", CommonContext.SCOPE_GLOBAL);//总金额
		String TradeSn = context.getFieldStr("TradeSn",CommonContext.SCOPE_GLOBAL);//付汇批次号
		Map<String,String> merchantInfo = (Map<String,String>)context.getObj("merchantInfo");//商户信息
		
		String currencyType = listMapData.get(0).getCurrency();//交易币种
		String remark = context.getFieldStr("REMARK", CommonContext.SCOPE_GLOBAL);//交易附言
		try{
			/**
			 * 组包需要数据 
			 */
			//购汇批次号
			context.setFieldStr("SEQ_NO", TradeSn, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("REMIT_CCY", currencyType, CommonContext.SCOPE_GLOBAL);
			//汇款金额
			context.setFieldStr("REMIT_AMT", totalAmt.toString(),CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("COST_TYPE", "OUR",CommonContext.SCOPE_GLOBAL);
			//收款人账号
			context.setFieldStr("PAYEE_ACCT_NO", merchantInfo.get("PAYEE_ACCT_NO"), CommonContext.SCOPE_GLOBAL);
			//收款人名称
			context.setFieldStr("PAYEE_CLIENT_NAME", merchantInfo.get("MERCHANTNAME"), CommonContext.SCOPE_GLOBAL);
			//收款人地址
			context.setFieldStr("PAYEE_ADDRESS", merchantInfo.get("ADDRESS"), CommonContext.SCOPE_GLOBAL);
			//汇款人账号

			BServerParam bServerParam = BServerParamData.getInstance().getPIAAccount(context.getSDO().serverId, currencyType);
			context.setFieldStr("PAYER_ACCT_NO", bServerParam.getParavalue(), CommonContext.SCOPE_GLOBAL);
			//汇款人名称
			context.setFieldStr("REMITTER_NAME", PlatParamsData.getInstance().getParamValue(BusinessConstantField.PLA_NAME), CommonContext.SCOPE_GLOBAL);
			//汇款人地址
			context.setFieldStr("PAYER_ADDRESS", PlatParamsData.getInstance().getParamValue(BusinessConstantField.PLA_ADDRESS), CommonContext.SCOPE_GLOBAL);
			//收款人开户行
			context.setFieldStr("PAYEE_ACCT_OPEN_BRANCH_ID", merchantInfo.get("OPEN_BRANCH_ID"), CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("REMARK", remark, CommonContext.SCOPE_GLOBAL);
			//跨境中间行
			context.setFieldStr("CLEAR_BANK_BIC", "", CommonContext.SCOPE_GLOBAL);
			
			
			
			/**
			 * 响应报文需要数据
			 */
			//总笔数
			context.setFieldStr("Count", String.valueOf(listMapData.size()), CommonContext.SCOPE_GLOBAL);
			//总金额
			context.setFieldStr("totalAmt", totalAmt.toString(), CommonContext.SCOPE_GLOBAL);
			//币种
			context.setFieldStr("currency_type",currencyType, CommonContext.SCOPE_GLOBAL);
			//购汇批次号
			context.setFieldStr("TradeSn", TradeSn, CommonContext.SCOPE_GLOBAL);
		}catch(ServiceException e){
			logger.error("business service exe  fail",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			throw new ServiceException(e);
		}
		return context;
	}
}
