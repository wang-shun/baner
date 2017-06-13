package org.inn.baner.serviceimp.PAB.FBS003;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BMerchantInfoData;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 为购汇准备数据
 * @author zhangxiaoyun
 * 2016年3月10日 下午6:25:13
 * <p>Company:ztkx</p>
 */
public class BuyFCPrepareBuss implements BusinessService {

	private Logger logger = Logger.getLogger(BuyFCPrepareBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		
		if(listMapData == null || listMapData.size()<=0){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			logger.error("business service exec exception ");
			throw new ServiceException("business service exec exception");
		}
		/**
		 * 1.生成购汇批次号
		 * 2.发起购汇申请
		 */
		String sequencePre = TimeUtil.getCurrentTime("MMddHHmmss");
		String batchNo = sequencePre + FlowNoPoolManager.getInstance().getSequence();
		if(logger.isDebugEnabled()){
			logger.debug("puchase foregin currency [ batchNo ] is ["+batchNo+"]");
		}
		//购汇批次
		context.setFieldStr("SEQ_NO", batchNo, CommonContext.SCOPE_GLOBAL);	
		//购汇笔数
		String num = String.valueOf(listMapData.size());
		context.setFieldStr("NUM", num, CommonContext.SCOPE_GLOBAL);
		
		
		//客户币种
		String CURRENCY = listMapData.get(0).getCurrency();
		context.setFieldStr("BUY_CCY", CURRENCY, CommonContext.SCOPE_GLOBAL);
		
		//总金额
		MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
		BigDecimal totalAmt = new BigDecimal(0,mathContext);
		List<Map<String,String>> loopData = new ArrayList<Map<String,String>>(listMapData.size());
		if(logger.isDebugEnabled()){
			logger.debug("puchase foregin currency [ NUM ] is ["+num+"]");
			logger.debug("puchase foregin currency [ CURRENCY ] is ["+CURRENCY+"]");
		}
		for (BMerchantOrder map : listMapData) {
			
			Map<String,String> rowData = new HashMap<String, String>();
			//交易序号
			String TRAN_SEQ_NO = sequencePre + FlowNoPoolManager.getInstance().getSequence();
			rowData.put("TRAN_SEQ_NO", TRAN_SEQ_NO);
			
			//购汇金额
			String TRAN_AMT = map.getPurchaseamount().toString();
			rowData.put("TRAN_AMT", TRAN_AMT);
			//购汇金额累加
			totalAmt = totalAmt.add(new BigDecimal(TRAN_AMT));
			
			//子客户号
			String CLIENT_NO = map.getMerchantid();
			rowData.put("CLIENT_NO", CLIENT_NO);
			
			/**
			 * 1.获取购买者标示
			 * 2.根据购买者标示和客户号查询用户信息
			 */
			String purchaserid = map.getPurchaserid();
			BUserInfoData userData = new BUserInfoData();
			List<Map<String,String>> resList = null;
			try {
				resList = userData.getUserInfo(CLIENT_NO, purchaserid);
			} catch (HandlerException e) {
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515, context);
				logger.error("query data exception");
				throw new ServiceException("query data exception",e);
			}
			//付款人名称
			String PAYER_NAME = null;
			//付款人证件号
			String PAYER_GLOBAL_ID = null;
			//商户所在国家代码
			String COUNTRY_CODE = null;
			//贸易类型
			String TRADE_TYPE = map.getTradeType();
			rowData.put("TRAN_TYPE", TRADE_TYPE);
			
			//贸易类型
			String TRADE_CODE = map.getTradeCode();
			rowData.put("TRAN_CODE", TRADE_CODE);
			
			if(resList.size()==1){
				PAYER_NAME = resList.get(0).get("REALNAME");
				rowData.put("PAYER_NAME", PAYER_NAME);
				PAYER_GLOBAL_ID = resList.get(0).get("IDNO");
				rowData.put("PAYER_GLOBAL_ID", PAYER_GLOBAL_ID);
			}else{
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000513, context);
				logger.error("user info not uniqueness");
				throw new ServiceException("user info not uniqueness");
			}
			
			BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
			try {
				resList = bMerchantInfoData.getBMerchantInfo(CLIENT_NO);
			} catch (HandlerException e) {
				context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
				logger.error("query data exception");
				throw new ServiceException("query data exception",e);
			}
			
			if(resList.size()==1){
				COUNTRY_CODE = resList.get(0).get("COUNTRY_CODE");
				rowData.put("COUNTRY_CODE", COUNTRY_CODE);
			}else{
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000514, context);
				logger.error("merchant info not uniqueness");
				throw new ServiceException("merchant info not uniqueness");
			}
			
			loopData.add(rowData);
			if(logger.isDebugEnabled()){
				logger.debug("row data is \n");
				logger.debug("puchase foregin currency [ TRAN_SEQ_NO ] is ["+TRAN_SEQ_NO+"]");
				logger.debug("puchase foregin currency [ TRAN_AMT ] is ["+TRAN_AMT+"]");
				logger.debug("puchase foregin currency [ CLIENT_NO ] is ["+CLIENT_NO+"]");
				logger.debug("puchase foregin currency [ PAYER_GLOBAL_ID ] is ["+PAYER_GLOBAL_ID+"]");
				logger.debug("puchase foregin currency [ PAYER_NAME ] is ["+PAYER_NAME+"]");
				logger.debug("puchase foregin currency [ TRADE_TYPE ] is ["+TRADE_TYPE+"]");
				logger.debug("puchase foregin currency [ COUNTRY_CODE ] is ["+COUNTRY_CODE+"]");
			}
		}
		context.setFieldStr("TOTAL_AMT", totalAmt.toString(),CommonContext.SCOPE_GLOBAL);
		context.setObj("HOResultSetFBS003R", loopData, CommonContext.SCOPE_GLOBAL);
		logger.info("purchase data prepare ok");
		return context;
	}
}
