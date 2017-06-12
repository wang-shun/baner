package com.ztkx.cbpay.business.serviceimp.UMBpay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BAccountTransferFlow;
import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.bean.BServerParam;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.enums.AccountTransferStatusEnum;
import com.ztkx.cbpay.business.handledata.BMerchantInfoData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.initdata.PlatParamsData;
import com.ztkx.cbpay.container.javabean.PlatParams;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 账户划转交易，的预处理类，根据前台传入的信息，查询数据库组装宝易互通所需的信息。
 * 2016/4/13
 * @author tianguangzhao
 *
 */
public class TransferAccountbeforehandBusinessService implements BusinessService {
	private Logger logger = Logger.getLogger(TransferAccountbeforehandBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("TransferAccountbeforehandBusinessService start ! ");
		}
		
		try {
			//获取请求信息中的transType标签值，确定划转类型
			String transType = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_TRANS_TYPE, CommonContext.SCOPE_GLOBAL);
//			update by tianguangzhao 20160525 是否为空交给拆包服务判断
//			if(transType ==null || transType.trim().equals("")){
//				//如果获取标签值失败，则注入错误码（请求报文错误）
//				logger.error("get msg type error ! ");
//				context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//				return context;
//			}else 
			if(transType.equals(BAccountTransferFlow.TRANS_TYPE_BEFORE_FEP)){
				//表示购汇前的请求
				 prepareMessageBeforeFEP(context);
			}else if(transType.equals(BAccountTransferFlow.TRANS_TYPE_AFTER_FEP)){
				//表示购汇完成后的账户划转
				prepareMessageAfterFEP(context);
			}else if(transType.equals(BAccountTransferFlow.TRANS_TYPE_BEFORE_PFE)){
			    //表示付汇前的账户划转
				prepareMessageBeforePFE(context);
			}else{
				//如果transType 的值，不在以上三个常量之内，则表示请求报文存在问题，注入错误码（请求报文异常），并返回。
				logger.error("transType error ! transType =["+transType+"] ! ");
				//update by  tianguangzhao 20160525 错误码修改为 “账户划转类型错误”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000534,context);
				return context;
			}
		} catch (ServiceException e) {
			// update by tianguangzhao 20160525 错误码改为“账户划转报文准备异常”
				logger.error("prepare message error !", e);
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529,context);
			throw e;
		}		
		if (logger.isDebugEnabled()) {
			logger.debug("TransferAccountbeforehandBusinessService end ! ");
		}
		return context;
	}

	/**
	 * 此方法准备购汇前的账户划转所需的报文信息
	 * @param context
	 * @throws ServiceException 
	 */
	private void prepareMessageBeforeFEP(CommonContext context) throws ServiceException{
		//此时为购汇前，账户划转的方向为，从商户的本币账户，划转到跨境支付的中间账户 ，每笔订单产生一笔划转记录
		//首先定义存放数据的list和map，以及存放数量的count
		int count = 0;
		List<Map<String,String>> transInfoList = new ArrayList<Map<String,String>>();
		//开始解析收到的报文
		List<Map<String,String>> transMessageList = null;
		try {
			transMessageList = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE, CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			//获取请求报文中的循环数据失败，则表示请求报文有误（不考虑拆包有误的情况，拆包出现问题，不应该继续向下运行程序），注入错误码（请求报文异常），并返回。
		    logger.error("get list error ! lablename =["+BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+"] !",e);
		    //update by tianguangzhao 20160525 改为不注入错误码 由上游抓住异常后处理
		    //context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
		    throw new ServiceException(e);
		}
		//update by tianguangzhao 是否为空交给拆包服务处理
//		if(transMessageList == null || transMessageList.size()==0){
//			//获取请求中的循环数据为空，则表示请求报文存在错误，注入错误码（请求报文异常），并返回。
//			String  message = "get cycle list error ! lable = ["	+ BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+ "] ! get null list !";
//			logger.error(message);
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//			 throw new ServiceException(message);
//		}else{
			for(Map<String,String> map : transMessageList){
				Map<String,String> transInfoMap = new LinkedHashMap<String, String>();
				String orderId = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO);
				String merchantId = map.get(BusinessMessageFormateConstant.CTB_CTB003_MERCHANT_NO);
				String orderdate = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_DATE);
				BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
				BMerchantOrder bMerchantOrder= null ;
				try {
					bMerchantOrder = bMerchantOrderData.getOrderInfo(merchantId, orderId, orderdate);
				} catch (HandlerException e) {
					//查询数据时抛出异常，注入错误码（查询数据异常），抛出异常；
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
				    throw new ServiceException(e);
				}
				if (bMerchantOrder == null) {
					String message = "cant not get order info  ! orderid =["+orderId+"] , merchantId =["+merchantId+"] , orderdate=["+orderdate+"]";
					logger.error(message);
					// 查询不到数据则注入错误码(表中无有效数据),并返回！
					//update by tianguangzhao 201604524 错误码改为”订单不存在“
					context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501);
					throw new ServiceException(message);
				} else {
					//首先进行判断判断状态是否正常
					String transStatus = bMerchantOrder.getMountchangestatus();
					String payStatus = bMerchantOrder.getOrderstatus();
					String buyBatStatus = bMerchantOrder.getBuybatstatus();
					if(StringUtils.isEmpty(transStatus)||StringUtils.isEmpty(payStatus)||StringUtils.isEmpty(buyBatStatus)){
						//首先判断所需的状态是否为空，如果是则注入错误码（数据异常），并返回！
						String message = "order status error ! some status is null ,please check ! column name is[Mountchangestatus,Orderstatus,Buybatstatus]";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码由上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000543);
						throw new ServiceException(message);
					}
					//状态应为，支付完成，未购汇，账户划转初始状态
					if(!(payStatus.equals(BusinessConstantField.ORDER_PD)&&buyBatStatus.equals(BusinessConstantField.PURCHASESTATUS_00)&&(transStatus.equals(AccountTransferStatusEnum.INITSTATUS.getStatus())||transStatus.equals(AccountTransferStatusEnum.BUYBEFFAIL.getStatus())))){
						//判断该笔交易的各个状态是否正确，如果不正确，则注入错误码（数据异常），并返回！
						String message = "order status error ! please check ! column name is[Mountchangestatus,Orderstatus,Buybatstatus] !";
						logger.error(message);
						//update by tianguangzhao 201605325 修改为不注入错误码交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					
                    //获取订单信息成功，则开始准备所需信息
					BigDecimal mount = bMerchantOrder.getAcceptancemount();//获取收单金额
					String currency = bMerchantOrder.getAcceptancycurrency();//获取收单币种
					BigDecimal rate = bMerchantOrder.getAcceptancerate();// 获取收单汇率，是否用到待定!
					if(mount==null||currency==null||currency.equals("")||rate==null){
						//判断划转金额，币种，汇率是否为空，如果为空，则注入错误码（数据异常），并返回！
						String message = "order info error ! please check ! Acceptancemount=["+ mount + "] ,Acceptancycurrency =[" + currency + "], Acceptancerate =["+rate+"]";
						logger.error(message);
						//update by tianguangzhao 修改为不注入错误码有上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					//先将得到的信息注入map中
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, String.valueOf(mount));
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid用于保存到划转信息表中
					transInfoMap.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					//从商户信息表中获取本币虚账号
					BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
					List<Map<String, String>> merchantInfo = null;
					try {
						merchantInfo = bMerchantInfoData.getBMerchantInfo(merchantId);
					} catch (HandlerException e) {
		                //如果查询数据库时抛出异常，则注入错误码 (查询数据异常)，并向上级抛出异常!
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
					    throw new ServiceException(e);
					}
					if(merchantInfo == null || merchantInfo.size()==0){
						//如果无法查询到商户信息，则注入错误码（表中无有效数据），并返回
						String message = "can not get merchant info ! merchantid =[" + merchantId + "] !";
						logger.error(message);
						//update by tianguangzhao 20160524 修改错误码为”商户不存在“
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000508,context);
						throw new ServiceException(message);
					} else if (merchantInfo.size() > 1) {
						//商户号是唯一的，如果获取到多条记录则表示，数据存在问题,注入错误码（数据异常），并返回
						String message = " get merchant info error ! merchantid =[" + merchantId + "] ! has more than one record !";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码 由上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					} else {
	 					Map<String,String> merchantInfoMap =merchantInfo.get(0); 
						String payaccount=merchantInfoMap.get("LOCAL_CURRENCY_ACCOUNT_NO");
						//将付款账号和付款人户名注入map中
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payaccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						//String payName = merchantInfoMap.get("MERCHANTNAME");
						//transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
						//update by tianguangzhao 2016/4/25 增加收付款人客户号
						String customerNo = merchantId;
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
					}
					
					//从平台参数表中获取跨境支付的中间账户
					PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_inter_account");
					if(platparam == null){
						//如果查询不到账户信息，则表示数据库中的数据存在问题。
						String message = "cant get plat params param name =[cbpay_inter_account] !";
						logger.error(message);
						//update by tianguangzhao 修改为不注入错误码 由上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}else{
						String recAccount = platparam.getParamsValue();
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT,recAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String recName = platparam.getParamDesc();
						//transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME,recName);
						
						//update by tianguangzhao 2016/4/25 增加收付款人账户号
						String customerNo = PlatParamsData.getInstance().getParam("cbpay_customer_id").getParamsValue();
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
					}
				}
				//将划转信息插入list
				transInfoList.add(transInfoMap);
				count++;
			}
			
			//将最终信息注入context中
			context.setObj(BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL, transInfoList, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_LIST_SIZE, String.valueOf(count), CommonContext.SCOPE_GLOBAL);
			//将备注的值注入context中,从请求报文中取出，注入到宝易互通所需字段中
			String bak = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_BAK,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_BAK, bak, CommonContext.SCOPE_GLOBAL);
		//}
	}

	/**
	 * 此方法准备购汇后的账户划转所需的报文信息
	 * @param context
	 * @throws ServiceException 
	 */
	private void prepareMessageAfterFEP(CommonContext context) throws ServiceException{
		//此时为购汇前，账户划转的操作涉及跨境支付的中间账户，商户外币账户，跨境支付损益账户，跨境支付手续费账户。每笔订单将产生4笔划转记录
		//首先初始化存放数据用到的list，map以及记录数据条数的count
		int count = 0;
		List<Map<String,String>> transInfoList = new ArrayList<Map<String,String>>();	
		//解析报文中的数据
		List<Map<String,String>> transMessageList = null;
		try {
			transMessageList = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE, CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			//获取请求报文中的循环数据失败，则表示请求报文有误（不考虑拆包有误的情况，拆包出现问题，不应该继续向下运行程序），注入错误码（请求报文异常），并返回。
			logger.error("get list error ! column name =[" + BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE + "] ！", e);
			//update by tianguangzhao 20160525 改为不注错误码由上游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
			throw new ServiceException(e);
		}
//是否为空的判断交由上游模块处理
//		if (transMessageList == null || transMessageList.size() == 0) {
//			//获取请求中的循环数据为空，则表示请求报文存在错误，注入错误码（请求报文异常），并返回。
//			String message = "get cycle list error ! lable = ["+ BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE + "] ! get null list !";
//			logger.error(message);
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//			throw new ServiceException(message);
//		} else {
			
			for(Map<String,String> map : transMessageList){
				//首先获取第一笔划转请求的商户号，订单号，订单日期
				String orderId = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO);
				String merchantId = map.get(BusinessMessageFormateConstant.CTB_CTB003_MERCHANT_NO);
				String orderdate = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_DATE);	
				BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
				BMerchantOrder bMerchantOrder= null ;
				try {
					bMerchantOrder=bMerchantOrderData.getOrderInfo(merchantId,orderId, orderdate);
				} catch (HandlerException e) {
					//如果查询订单信息时，注入错误码（查询数据异常），抛出异常；
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
				    throw new ServiceException(e);
				}
				if (bMerchantOrder == null) {
					// 没有查到数据注入查询数据库失败的错误码(表中无有效数据),并返回
					String message = "cant not get order info  ! orderid =["+orderId+"] , merchantId =["+merchantId+"] , orderdate=["+orderdate+"] !";
					logger.error(message);
					//update by tiangunagzhao 20160524 错误码修改为 ”订单不存在“
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501,context);
					throw new ServiceException(message);
				} else {					
					//首先判断状态是否正常，此时状态应为，支付完成，购汇完成，划转状态时购汇前划转成功
					String transStatus = bMerchantOrder.getMountchangestatus();
					String payStatus = bMerchantOrder.getOrderstatus();
					String buyBatStatus = bMerchantOrder.getBuybatstatus();
					
					if(StringUtils.isEmpty(transStatus)||StringUtils.isEmpty(payStatus)||StringUtils.isEmpty(buyBatStatus)){
						//首先判断所需状态信息是否为空，如果为空注入错误吗（数据异常），并返回
						String message = "order status error ! some status is null ,please check ! column name is [Mountchangestatus,Orderstatus,Buybatstatus ]";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码 交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					
					if(!(payStatus.equals(BusinessConstantField.ORDER_PD)&&buyBatStatus.equals(BusinessConstantField.PURCHASESTATUS_06)&&(transStatus.equals(AccountTransferStatusEnum.BUYBEFSUCC.getStatus())||transStatus.equals(AccountTransferStatusEnum.BUYAFTFAIL.getStatus())))){
						//判断该笔订单的各个状态是否正常，如果不正常，注入错误码（数据异常），并返回！
						String message = "order status error ! please check ! column name is [ Mountchangestatus,Orderstatus,Buybatstatus ] !";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码 由上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					
					//开始准备第一笔划转信息
					Map<String,String> transInfoMapFirst = new LinkedHashMap<String, String>();
					//每次循环都会产生四笔划转记录，首先第一笔记录从跨境支付中间账户到中间账户的对手账户的划转，此笔交易付款方为跨境支付中间账户,
					//update by tianguangzhao 2016/4/20  此金额改为实际购汇所花的人民币金额
					BigDecimal totalMount = bMerchantOrder.getAcceptancemount();//获取收单金额
					String currency = bMerchantOrder.getAcceptancycurrency();//获取收单币种
					BigDecimal rate = bMerchantOrder.getBuybatrate();// 获取实际购汇时的汇率
					BigDecimal poundage = bMerchantOrder.getMerchantpoundage();// 获取手续费
					BigDecimal upOrDownMount = bMerchantOrder.getUpordownmount();// 获取损益金额

					if(totalMount==null||currency==null||currency.equals("")||rate==null||poundage ==null ||upOrDownMount ==null){
						//首先判断所需信息是否为空，如果为空，注入错误码（数据异常），并返回
						String message = "order info error ! please check ! Acceptancemount ,Acceptancycurrency , Acceptancerate,Merchantpoundage,Upordownmount";
						logger.error(message);
						//update  by tianguangzhao 20160525 修改为不注入错误码 交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					//计算实际购汇所用人民币金额，收单金额 - 手续费 - 我方损益金额
					String amount = String.valueOf(totalMount.subtract(poundage.add(upOrDownMount)));
					
					transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, amount);
					transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
					
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid方便入库使用
					transInfoMapFirst.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					//查询付款方账号和户名,即跨境支付中间账户
					PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_inter_account");
					if(platparam == null){
						//判断付款方账号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
						String message = "cant get plat params param name =[cbpay_inter_account] !";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
						throw new ServiceException(message);
					}else{
						String payAccount = platparam.getParamsValue();
						transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String payName = platparam.getParamDesc();
						//transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号,从参数表中获取
						platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断付款方客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160524 修改为不注入错误码 由上游处理
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT,customerNo);
						}
					}
					//查询收款方账号和户名，即跨境支付中间账户的对手账户
				    platparam= PlatParamsData.getInstance().getParam("cbpay_inter_match_account");
					if(platparam == null){
						// 判断收款方账号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
						String message = "cant get plat params param name =[cbpay_inter_match_account] !";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码由上游处理 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
						throw new ServiceException(message);
					}else{
						String recAccount= platparam.getParamsValue();
						transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT, recAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String recName = platparam.getParamDesc();
						//transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME, recName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号,从参数表中获取!
						platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断收款方客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160524  修改为不注入错误码有上游处理
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMapFirst.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT,customerNo);
						}
					}
				
					//将第一笔划转信息注入list中,并将记录划转条数的count+1
					transInfoList.add(transInfoMapFirst);
					count++;
				}

				//第二笔划转信息
				//此处其实并不需要判断以下类同，如果为null，则在前面的程序中已经跳出程序，在此加上这条判断，只是为了方便区分各个划转信息的收付款账号，汇率等变量只为{}内部使用，避免变量值混乱造成的后期维护难度加大
				if(bMerchantOrder!=null){
					//开始准备第二笔划转信息
					Map<String,String> transInfoMapSecond = new LinkedHashMap<String, String>();
					// 第二笔划转信息，记录从手续费账户的对手账户转到到手续费账户划转信息,交易金额为手续费字段，币种是收单币种，汇率使用购汇汇率
					BigDecimal mount = bMerchantOrder.getMerchantpoundage(); //获取商户手续费
					String currency = bMerchantOrder.getAcceptancycurrency();//获取收单币种
					BigDecimal rate = bMerchantOrder.getBuybatrate(); //获取购汇汇率
					
					if(mount==null||currency==null||currency.equals("")||rate==null){
						//如果所需信息存在为空的情况注入错误码（表中无有效数据），并返回！
						String message = "order info error ! please check ! Merchantpoundage=[" + mount + "] ,Acceptancycurrency =[" + currency+ "], Buybatrate =["+rate+"]";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码，交给上游处理
						//ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000543,context);
						throw new ServiceException(message);
					}
					
					transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, String.valueOf(mount));
					transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
					
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid方便入库使用
					transInfoMapSecond.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					
					//获取付款方信息，
					//查询付款方账号和户名,即跨境支付中间账户
					//update by tianguangzhao 2016/4/20 去掉手续费账户的对手账户，改为从跨境支付的中间账户中划转到手续费账户
					PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_inter_account");
					if(platparam == null){
						//如果中间账户无法查询到，注入错误码（表中无有效数据），并返回！ 
						String message = "cant get plat params param name =[cbpay_inter_account] !";
						logger.error(message);
						//
						//update by tianguangzhao 20160524  修改为不注入错误码 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
						throw new ServiceException(message);
					}else{
						String payAccount = platparam.getParamsValue();

						transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						//String payName = platparam.getParamDesc();
						//transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号
						platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160524  修改为不注入错误码交给上游处理 ！
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
						}					
					}
					//获取收款方信息，即手续费账户
				    platparam= PlatParamsData.getInstance().getParam("cbpay_poundage_account");
					if(platparam == null){
						// 判断手续费账户，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
						String message = "cant get plat params param name =[cbpay_poundage_account] !";
						logger.error(message);
						//update by tianguangzhao 20160524  修改为不注入错误码 交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
						throw new ServiceException(message);
					}else{
						String recAccount= platparam.getParamsValue();
						transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT, recAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String recName = platparam.getParamDesc();
						//transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME, recName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号
						platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160524  修改为不注入错误码，交给上游处理!
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMapSecond.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
						}										
					}
				
					//将第二笔划转信息注入list中,并将记录划转条数的count+1
					transInfoList.add(transInfoMapSecond);
					count++;
				}
			
				//第三笔划转信息
				if(bMerchantOrder!=null){
					//开始准备第三笔划转信息
					Map<String,String> transInfoMapThree = new LinkedHashMap<String, String>();
					// 第三笔划转信息，从跨境支付外币的账户转到商户的外币账户,金额是购汇金额，汇率使用购汇汇率，币种使用商品标注币种
					BigDecimal mount = bMerchantOrder.getPurchaseamount();//获取购汇金额
					BigDecimal rate = bMerchantOrder.getBuybatrate();//获取购汇汇率
					String currency = bMerchantOrder.getCurrency();//商户订单币种
					
					if(mount==null||currency==null||currency.equals("")||rate==null){
				        //判断所需信息是否为空，如果为空，注入错误码（表中无有效数据），并返回!
						String message = "order info error ! please check ! Purchaseamount=[" + mount + "] ,Currency =[" + currency + "], Buybatrate =["+rate+"]";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码，由上游处理
						//ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000543,context);
						throw new ServiceException(message);
					}
					
					transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, String.valueOf(mount));
					transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
				
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid方便入库使用
					transInfoMapThree.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					
					//查询付款人信息，即跨境外币账户
					PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_merchant_match_account");
					if(platparam == null){
						//判断付款方信息为空，则注入错误码（表中无有效数据），并返回
						String message = "cant get plat params param name =[cbpay_merchant_match_account] !";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码 交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
						throw new ServiceException(message);
					}else{
						String payAccount = platparam.getParamsValue();
						transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						//String payName = platparam.getParamDesc();
						//transInfoMapThreed.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号
						platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160524 错误码修改为不注入错误码交给上游处理
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
						}									
					}
					
					//查询收款账户，即商户的外币虚拟账户
					BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
					List<Map<String, String>> merchantInfo = null;
					try {
					     merchantInfo=bMerchantInfoData.getBMerchantInfo(merchantId);
					} catch (HandlerException e) {
						// 如果查询数据库时抛出异常，则注入错误码 (查询数据异常)，并向上级抛出异常!
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
						throw new ServiceException(e);
					}
					if (merchantInfo == null || merchantInfo.size() == 0) {
						// 如果无法查询到商户信息，则注入错误码（表中无有效数据），并返回
						String message = "can not get merchant info ! merchantid =[" + merchantId + "] !";
						logger.error(message);
						// update by tianguangzhao 20160524 将错误码修改为 ”商户不存在“
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000508,context);
						throw new ServiceException(message);
					} else if (merchantInfo.size() > 1) {
						// 商户号是唯一的，如果获取到多条记录则表示，数据存在问题,注入错误码（数据异常），并返回
						String message = " get merchant info error ! merchantid =["+ merchantId + "] ! has more than one record !";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码 交给上游处理
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					} else {
						Map<String,String> merchantInfoMap =merchantInfo.get(0); 
						String recAccount=merchantInfoMap.get("FOREIGN_CURRENCY_ACCOUNT_NO");
						//将付款账号和付款人户名注入map中
						transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT, recAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String recName = merchantInfoMap.get("MERCHANTNAME");
						//transInfoMapThreed.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME, recName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号
						String customerNo = merchantId;
						transInfoMapThree.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
					}
				
					//将第三笔划转信息注入list中,并将记录划转条数的count+1
					transInfoList.add(transInfoMapThree);
					count++;
				}
				
				//第四笔划转信息
				if(bMerchantOrder!=null){
					//开始准备第四笔划转信息
					Map<String,String> transInfoMapFour = new LinkedHashMap<String, String>();
					// 第四笔划转信息，记录从损益账户到损益账户对手账户的划转记录,划转金额为损益金额，汇率为购汇汇率，币种为收单币种
					BigDecimal mount = bMerchantOrder.getUpordownmount(); //获取损益金额
					//要根据损益的正负，收付款方会不同首先获取标识用于下一步组织收付款人信息
					int flag = mount.compareTo(new BigDecimal(0));
					
					String currency = bMerchantOrder.getAcceptancycurrency();//获取收单币种
					BigDecimal rate = bMerchantOrder.getBuybatrate(); //获取购汇汇率
					
					if(mount==null||currency==null||currency.equals("")||rate==null){
						//如果所需信息为空，则注入错误码（表中无有效数据），并返回!
						String message = "order info error ! please check ! Upordownmount=[" + mount + "] ,Acceptancycurrency =[" + currency+ "], Buybatrate =["+rate+"]";
						logger.error(message);
						//update by tianguangzhao 20160524 修改为不注入错误码，交给上游处理
						//ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000543,context);
						throw new ServiceException(message);
					}
					
					transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, String.valueOf(mount.abs()));
					transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
					
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid方便入库使用
					transInfoMapFour.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					
					//如果损益>=0 ,则由损益账户最为收款方
					if (flag >= 0) {
                     //获取付款方账户，即损益账户的对手账户
						PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_upordown_match_account");
						if(platparam == null){
							//判断账户信息是否存在，如无法查询到注入错误信息（表中无有效数据），并返回
							String message = "cant get plat params param name =[cbpay_upordown_match_account] !";
							logger.error(message);
							//update by tianguangzhao 20160524 修改为不注入错误码 交给上游处理 
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						}else{
							String payAccount = platparam.getParamsValue();
							transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
							//update by tiangunangzhao 2016/4/25 暂时去掉收付款人户名，以后如有需要在添加
							// transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
							// String payName = platparam.getParamDesc();						
							//update by tianguangzhao 2016/4/25 新增收付款客户号
							platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
							if (platparam == null) {
								// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
								String message = "cant get plat params param name =[cbpay_customer_id] !";
								logger.error(message);
								//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
								//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
								throw new ServiceException(message);
							} else {
								String customerNo = platparam.getParamsValue();
								transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
							}					
							
						}	
					
					   //获取收款方账户，即损益账户
						platparam = PlatParamsData.getInstance().getParam("cbpay_upordown_account");
						if (platparam == null) {
							//判断账户信息是否存在，如无法查询到注入错误信息（表中无有效数据），并返回
							String message = "cant get plat params param name =[cbpay_upordown_account] !";
							logger.error(message);
							//update by tianguangzhao 20160524 修改为不注入错误码 交给上游处理
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String recAccount = platparam.getParamsValue();
							transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT,recAccount);
							//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
							//String recName = platparam.getParamDesc();
							//transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME,recName);
							
							//update by tianguangzhao 2016/4/25 新增收付款客户号
							platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
							if (platparam == null) {
								// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
								String message = "cant get plat params param name =[cbpay_customer_id] !";
								logger.error(message);
								//update by tianguangzhao 20160524 修改为不注入错误码 ，交给上游处理 
								//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
								throw new ServiceException(message);
							} else {
								String customerNo = platparam.getParamsValue();
								transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
							}			
						}
					} else {
						 //获取付款方账户,即损益账户
						PlatParams platparam= PlatParamsData.getInstance().getParam("cbpay_upordown_account");
						if(platparam == null){	
							//判断账户信息是否存在，如无法查询到注入错误信息（表中无有效数据），并返回
							String message = "cant get plat params param name =[cbpay_upordown_account] !";
							logger.error(message);
							//update by tianguangzhao 20160524 修改为不注入错误码，交给上游处理 ！
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						}else{
							String payAccount = platparam.getParamsValue();
							transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
							//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
							// String payName = platparam.getParamDesc();
							//transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
							
							//update by tianguangzhao 2016/4/25 新增收付款客户号
							platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
							if (platparam == null) {
								// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
								String message = "cant get plat params param name =[cbpay_customer_id] !";
								logger.error(message);
								//update by tianguangzhao 20160524 修改为不注入错误码交给上游处理
								//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
								throw new ServiceException(message);
							} else {
								String customerNo = platparam.getParamsValue();
								transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
							}									
						}
						
						// 获取收款方账户 ，即损益账户的对手账户
						platparam = PlatParamsData.getInstance().getParam("cbpay_upordown_match_account");
						if (platparam == null) {
							//判断账户信息是否存在，如无法查询到注入错误信息（表中无有效数据），并返回
							String message = "cant get plat params param name =[cbpay_upordown_match_account] !";
							logger.error(message);
							//update by tianguangzhao 20160524 修改为不注入错误码 交给上游处理
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String recAccount = platparam.getParamsValue();
							transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT,recAccount);
							//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
							//String recName = platparam.getParamDesc();
							//transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME,recName);
							
							//update by tianguangzhao 2016/4/25 新增收付款客户号
							platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
							if (platparam == null) {
								// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
								String message = "cant get plat params ! param name =[cbpay_customer_id] !";
								logger.error(message);
								//update by tianguangzhao 20160524 修改为不注入错误码交给上游处理
								//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
								throw new ServiceException(message);
							} else {
								String customerNo = platparam.getParamsValue();
								transInfoMapFour.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
							}
						}
					}
					//将第四笔划转信息注入list中,并将记录划转条数的count+1
					transInfoList.add(transInfoMapFour);
					count++;
				}
			
			}		
			//将最终信息注入context中
			context.setObj(BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL, transInfoList, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_LIST_SIZE, String.valueOf(count), CommonContext.SCOPE_GLOBAL);
			//将备注的值注入context中,从请求报文中取出，注入到宝易互通所需字段中
			String bak = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_BAK,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_BAK, bak, CommonContext.SCOPE_GLOBAL);
		//}
	}
	
	/**
	 * 此方法付汇前的账户划转所需的报文信息
	 * @param context
	 * @throws ServiceException 
	 */
	private void prepareMessageBeforePFE(CommonContext context) throws ServiceException{
		//此时为付汇前，涉及到的账户只有两种，银行外币账户，商户外币账户.方向为从商户外币账户转到银行外币账户,每笔订单产生一笔划转记录
		//首先定义存放数据的list和map，以及存放数量的count
		int count = 0;
		List<Map<String,String>> transInfoList = new ArrayList<Map<String,String>>();
		//开始解析收到的报文
		List<Map<String,String>> transMessageList = null;
		try {
			transMessageList = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE, CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			//获取标签失败，则表示请求报文异常！注入错误码(请求报文异常)，并抛出异常！ 
	        logger.error("get cycle list error ! lable = ["+BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+"] ! ");
	        // update by tianguangzhao 20160525  改为不注入错误码 交给上游处理
	    	//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
			throw new ServiceException(e);
		}
//		update by tianguangzhao 20160525 是否为空的判断交给拆包服务处理
//		if(transMessageList == null || transMessageList.size()==0){
//			//如果获取到的循环信息为空，则表示请求报文异常！注入错误码(请求报文异常)，并返回！ 
//			String message = "get cycle list error ! lable = ["	+ BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE + "] ! get null list !";
//			logger.error(message);
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//			throw new ServiceException(message);
//		}else{
			for(Map<String,String> map : transMessageList){
				Map<String,String> transInfoMap = new LinkedHashMap<String, String>();
				String orderId = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO);
				String merchantId = map.get(BusinessMessageFormateConstant.CTB_CTB003_MERCHANT_NO);
				String orderdate = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_DATE);
				
				BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
				BMerchantOrder bMerchantOrder= null ;
				try {
					bMerchantOrder=bMerchantOrderData.getOrderInfo(merchantId,orderId, orderdate);
				} catch (HandlerException e) {
					//如果查询订单信息时，注入错误码（查询数据异常），抛出异常；
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
				    throw new ServiceException(e);
				}
				if (bMerchantOrder == null) {
					// 没有查到数据注入查询数据库失败的错误码(表中无有效数据),并返回
					String message = "cant not get order info  ! orderid =["+orderId+"] , merchantId =["+merchantId+"] , orderdate=["+orderdate+"]";
					logger.error(message);
					//update by tianguangzhao 20160524 错误码 修改为”订单不存在“
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000501,context);
					throw new ServiceException(message);
				} else {
					//首先判断状态是否正常,状态应为支付完成，购汇成功，未付汇， 账户划转状态为购汇后成功
					String transStatus = bMerchantOrder.getMountchangestatus();
					String payStatus = bMerchantOrder.getOrderstatus();
					String buyBatStatus = bMerchantOrder.getBuybatstatus();
					String paybatStatus = bMerchantOrder.getPaybatstatus();
					if(StringUtils.isEmpty(transStatus)||StringUtils.isEmpty(payStatus)||StringUtils.isEmpty(buyBatStatus)||StringUtils.isEmpty(paybatStatus)){
						//所需信息为空，注入错误码（数据异常），并返回！
						String message = "order status error ! some status is null ,please check ! column name is[Mountchangestatus,Orderstatus,Buybatstatus,paybatStatus]";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					if(!(payStatus.equals(BusinessConstantField.ORDER_PD)&&buyBatStatus.equals(BusinessConstantField.PURCHASESTATUS_06)&&(transStatus.equals(AccountTransferStatusEnum.BUYAFTSUCC.getStatus())||transStatus.equals(AccountTransferStatusEnum.PAYBEFFAIL.getStatus()))&&paybatStatus.equals(BusinessConstantField.B_MERCHANT_ORDER_PAYBATSTATUS_00))){
						//该笔订单的状态异常，注入错误码（数据异常），并返回！
						String message = "order status error ! please check ! column name is[Mountchangestatus,Orderstatus,Buybatstatus,paybatStatus] !";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					
					
					//付款方账号是商户外币虚账户，收款方账号是银行的外币虚账户。 首先获取购汇金额，购汇币种，购汇汇率信息
					BigDecimal mount = bMerchantOrder.getPurchaseamount(); //购汇金额
					BigDecimal rate = bMerchantOrder.getBuybatrate();//购汇汇率
					String currency = bMerchantOrder.getCurrency();//购汇币种
					
					if(mount==null||currency==null||currency.equals("")||rate==null){
						//所需信息为空，注入错误码（数据异常），并返回！
						String message = "order info error ! please check ! Purchaseamount=[" + mount + "] ,Currency =[" + currency + "], Buybatrate =["+rate+"]";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					}
					
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT, String.valueOf(mount));
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_EXRATE, String.valueOf(rate));
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY, currency);
					
					//update by tianguangzhao 2016/4/14 增加单笔流水号和商户号
					String transFlowNo = merchantId+TimeUtil.getCurrentTime(BusinessConstantField.TIMESTAMP_FORMATE) + FlowNoPoolManager.getInstance().getSequence();
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO, transFlowNo);
					transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_TRANS_MERCHANT_NO, merchantId);
					//保存orderid方便入库使用
					transInfoMap.put(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO, orderId);
					
					//查询付款方账号和户名
					BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
					List<Map<String,String>> merchantInfoList = null;
					try {
						merchantInfoList=bMerchantInfoData.getBMerchantInfo(merchantId);
					} catch (HandlerException e) {
		                //如果查询数据库时抛出异常，则注入错误码 (查询数据异常)，并向上级抛出异常!
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
					    throw new ServiceException(e);
					}
					if(merchantInfoList == null || merchantInfoList.size()==0){
						// 如果无法查询到商户信息，则注入错误码（表中无有效数据），并返回
						String message = "can not get merchant info ! merchantid =[" + merchantId + "] !";
						logger.error(message);
						//update by tianguangzhao 20160524 错误码修改为 ”商户不存在“
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000508,context);
						throw new ServiceException(message);
					} else if (merchantInfoList.size() > 1) {
						//商户号是唯一的，如果获取到多条记录则表示，数据存在问题,注入错误码（数据异常），并返回
						String message = " get merchant info error ! merchantid =[" + merchantId + "] ! has more than one record !";
						logger.error(message);
						//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
						//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
						throw new ServiceException(message);
					} else{
						Map<String,String> merchantInfoMap = merchantInfoList.get(0);
						String payAccount = merchantInfoMap.get("FOREIGN_CURRENCY_ACCOUNT_NO");
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT, payAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						// String payName = merchantInfoMap.get("MERCHANTNAME");
						//transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME, payName);
						
						//update by tianguangzhao 2016/4/25 新增收付款客户号
						String customerNo = merchantId;
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_PAY_CLIENT, customerNo);
					}
					
					//查询收款方账号和户名,update by tianguangzhao 2016/4/27 修改账户保存地址，改为保存到
					BServerParam bServerParam = BServerParamData.getInstance().getParamsValue(bMerchantOrder.getChannel(), "foreign_virtual_account");
					if(bServerParam ==null){
						String message = "get server accountNo error ! serverId=["+bMerchantOrder.getChannel()+"] , param name = [foreign_virtual_account] !";
						logger.error(message);
						//update by tianguangzhao 20160525 错误码修改为“商户信息不存在”
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511,context);
						throw new ServiceException(message);
					}else{
						String recAccount= bServerParam.getParavalue();
						transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT, recAccount);
						//update by tianguangzhao 2016/4/25  先将收付款人姓名，取消，暂不保存，以后如果有需要在修改此处；
						//String recName = serverInfo.getServerdesc();
						//transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME, recName);
						//update by tianguangzhao 2016/4/25 新增收付款客户号,此时的收款方账户只是一个虚拟的账户，并无实际意义属于跨境支付系统
						PlatParams platparam = PlatParamsData.getInstance().getParam("cbpay_customer_id");
						if (platparam == null) {
							// 判断跨境支付在宝易互通的客户号，如果查询不到信息，则注入错误码（表中无有效数据），并返回！
							String message = "cant get plat params param name =[cbpay_customer_id] !";
							logger.error(message);
							//update by tianguangzhao 20160525 修改为不注入错误码，交给上游处理 
							//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000542);
							throw new ServiceException(message);
						} else {
							String customerNo = platparam.getParamsValue();
							transInfoMap.put(BusinessMessageFormateConstant.UMB_CP0039_REC_CLIENT, customerNo);
						}					
					}	
				}
				transInfoList.add(transInfoMap);
				count++;
			}		
			//将最终信息注入context中
			context.setObj(BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL, transInfoList, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_LIST_SIZE, String.valueOf(count), CommonContext.SCOPE_GLOBAL);
			//将备注的值注入context中,从请求报文中取出，注入到宝易互通所需字段中
			String bak = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_BAK,CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0039_BAK, bak, CommonContext.SCOPE_GLOBAL);
		//}
		
	}
	
}
