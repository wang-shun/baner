package com.ztkx.cbpay.business.serviceimp.UMBpay;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.BPaymentFlowData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 查询支付交易状态，得到宝易互通返回的信息后，更新该笔交易的状态！
 * 
 * @author tianguangzhao
 *
 */
public class QueryPayProgressBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(QueryPayProgressBusinessService.class);
	private static final String default_value = "";

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug(" QueryPayProgressBusinessService  beginning...");
		}
		// 取普通字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String respCode = context.getResponseCode(); // 响应吗

//	    update by tianguangzhao 20160524 是否为空的判断交给拆包模块进行
//        if(msgType==null || msgType.trim().equals("")||respCode==null || respCode.trim().equals("")){
//        	logger.error("get message error ! respCode or msgType is null ! please check !");
//        	context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
//			return context;
//        }
    	if (logger.isDebugEnabled()) {
			logger.debug("get message success ! respCode = [" + respCode + "] , msgType = [" + msgType + "] ! ");
		}
		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			//报文类型错误注入错误码（相应报文错误）
			logger.error(" msgType error msgType is [" + msgType + "] !");
			//update by tianguangzhao 20160524 修改错误码为“响应报文类型错误”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		// PLA000000表示交易成功，其他码交易失败
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			// 如果响应吗为失败，则直接返回，不再进行后续操作
			logger.error(" trade failed ! responese code is ["+respCode+"] !");
			return context;
		} else {
			//update by tianguangzhao 2016/5/3 增加body标签中 响应码的判断
			String subRespCode = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0002_TRAN_RESP_CODE,CommonContext.SCOPE_GLOBAL);//body标签中的响应码
			// update by tianguangzhao 是否为空的判断由拆包服务完成
			// if (subRespCode == null || subRespCode.trim().equals("")) {
			// logger.error(" get lable "+BusinessMessageFormateConstant.UMB_CP0002_TRAN_RESP_CODE+" error ! please check  !");
			// context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
			// return context;
			// }

			// 根据返回结果确认
			String tranState = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0002_TRAN_STATE,CommonContext.SCOPE_GLOBAL); // 支付状态
			String payorderid = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0002_ORGTRANFLOW,CommonContext.SCOPE_GLOBAL); // 原支付流水号
			//update by tianguangzhao 2016/4/26 宝易互通不会返回流水号，去掉该常量和表中的字段
			//String payjnlno = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0002_PAY_FLOW_NO,CommonContext.SCOPE_GLOBAL); // 宝易互通支付流水号

			// 支付流水状态，由于 宝易互通和我们的状态不一致,所以需要在代码中进行转换
			String flowStatus = "";
			// 订单表中的订单状态
			String orderStatus = null;
			//update by tianguangzhao 2016/4/28 除了交易成功之外，其他状态都为转为失败
			if (tranState.equals(BusinessConstantField.UMB_TRADING_INIT)) {
				// 交易处理初始化,
				//flowStatus = BusinessConstantField.TRADING_INIT;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
				// 交易处理完成
				flowStatus = BusinessConstantField.TRADING_SUCCESS;
				orderStatus = BusinessConstantField.ORDER_PD;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
				// 交易处理中
				//flowStatus = BusinessConstantField.TRADING_PROCESSING;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
				// 交易处理失败
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else {
				//如果返回的交易状态不是以上四个状态，则表示报文有误，注入错误码（相应报文错误）
				logger.error("tranState error ! tranState = [" + tranState + "] !");
				//update by tianguangzhao 20160524 错误码 改为“交易类型错误”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000533, context);;
				return context;
			}
			//将本平台的交易状态码注入context中，以便后续返回给调用方
			context.setFieldStr(BusinessMessageFormateConstant.CTB_CTB002_TRAN_STATE, flowStatus,CommonContext.SCOPE_GLOBAL);
			
			//查询数据库检查状态是否正确，规则为，此次返回的状态码（转换为本平台状态码后）必须大于等于上次的状态码 
			try {
				int flag = checkFlowStatus(payorderid, flowStatus, context);
				if (logger.isDebugEnabled()) {
					logger.debug("get flag success flag = [" + flag + "] ! ");
				}
				if (flag == -1) {
					//本次状态码小于上次状态码，则表示宝易互通返回报文有误，注入错误码（响应报文错误）
					//update by tianguangzhao 20160524 错误码 改为“响应报文中交易状态错误”
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000533,context);
					return context;
				} else if (flag == 1) {
					//本次状态码大于上次状态码
					BPaymentFlowData bpaymentFlowData = new BPaymentFlowData();
					BMerchantOrderData bmerchantOrderData = new BMerchantOrderData();
					//加入事务控制
					Connection conn = bpaymentFlowData.getConnection();
					bmerchantOrderData.setConnection(conn);
					DataHandlerUtil.setAutoCommit(conn, false);
					//获取宝易互通返回的交易日期和交易时间
					String paytime=context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_TIME,CommonContext.SCOPE_GLOBAL); 
					String paydate=context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_DATE,CommonContext.SCOPE_GLOBAL); 
					//更新表中的数据
					try {
						// 更新支付流水表中的状态
						updateFlowStatus(bpaymentFlowData,payorderid, flowStatus,paytime,paydate);
						// 判断是否是最终状态，如果是，更新订单表状态
						if (tranState.equals(BusinessConstantField.UMB_TRADING_SUCCESS)|| tranState.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
							// 获取商户号
							String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO,
									CommonContext.SCOPE_GLOBAL) == null ? default_value : context
									.getFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO, CommonContext.SCOPE_GLOBAL);

							updateOrderStatus(bmerchantOrderData,payorderid,merchantNo, orderStatus);
						}
						//最后手动提交更新内容。如果发生异常会直接抛出ServiceException，被最外层try catch抓住!
						DataHandlerUtil.commitConn(conn);
					} catch (HandlerException e) {
						//先将事务回滚，如果回滚发生异常会直接抛出ServiceException，被最外层try catch抓住!
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
						DataHandlerUtil.rollbakConn(conn);
						throw new ServiceException(e);
					}finally{
						//释放资源，如果释放资源时发生异常，会直接抛出ServiceException，被最外层try catch抓住!
						DataHandlerUtil.releaseSource(bmerchantOrderData);
						DataHandlerUtil.releaseSource(bpaymentFlowData);
					}
				} else {
					// 本次状态码和上次相同，所以不进行任何操作，打印依据日志即可
					logger.info(" no need to update database nowstatus = laststatus");
				}
			} catch (Exception e) {
				// 如果更新交易流水表和订单状态表信息失败时，注入错误码（更新数据库异常）
				//update by tianguangzhao 20160525 将此处注入的错误码为“业务服务执行异常”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				throw new ServiceException(e);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" QueryPayProgressBusinessService  success...");
			}
			return context;
		}
	}

	/**
	 * 更具订单号更新订单状态
	 * 
	 * @param payjnlno
	 *            ,订单号
	 * @param tranState
	 *            ，订单状态
	 * @throws ServiceException
	 */
	private void updateOrderStatus(BMerchantOrderData bmerchantOrderData,String payorderid,String merchantNo, String status) throws ServiceException {
		
		int count = 0 ;
		try {
			count = bmerchantOrderData.updateOrderStatusByPayorderid(payorderid, merchantNo, status);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
        //判断更新条数 
		if (count != 1) {
			String message = "update OrderStatus error !  update count = [" + count + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
		
	}

	/**
	 * 更新交易流水表中的信息
	 * @param bpaymentFlowData 操作交易流水表的data类
	 * @param payorderid 支付流水号
	 * @param tranState 交易状态
	 * @param paytime 支付完成时间
	 * @param paydate 支付完成日期
	 * @throws ServiceException 
	 */

	private void updateFlowStatus(BPaymentFlowData bpaymentFlowData,String payorderid, String tranState,String paytime,String paydate) throws ServiceException {
		int count = 0 ;
		
		try {
			count = bpaymentFlowData.updatePayFlowStatus(payorderid, tranState,paydate, paytime);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		// 根据支付流水号来更新的时候，数据库中应该只有一条数据被更新，如果出现更新的条数不是一条，则表示数据或程序出现错误，
		if (count != 1) {
			String message = "updateFlowStatus error !  update count =[" + count + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
	}

	/**
	 * 判断数据库中的支付状态
	 * 
	 * @param payorderid
	 *            支付流水号
	 * @param tranState
	 *            宝易互通新返回的状态
	 * @param paydate
	 *            支付日期
	 * @param payjnlno
	 *            宝易互通支付流水号
	 * @throws ServiceException
	 */
	private int checkFlowStatus(String payorderid, String tranState,CommonContext context) throws ServiceException {
		int flag = 1;
		BPaymentFlowData bpfd = new BPaymentFlowData();
		List<Map<String, String>> list = null;
		try {
			list = bpfd.getPaymentFlowByPayorderid(payorderid);
		} catch (HandlerException e) {
			//查询数据库错误，注入错误码(查询数据异常)
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if (list == null || list.size() == 0) {
			//如果根据支付流水号无法查询到订单信息，注入错误码（表中无有效数据）
			//update by tianguangzhao 20160524 修改错误码为”支付信息不存在“
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000530,context);
			String message = "can not get order info ! payorderid =["+payorderid+"] !";
			logger.error(message);
			throw new ServiceException(message);
		} else if (list.size() == 1) {
			Map<String, String> map1 = list.get(0);
			String payStatus = map1.get("PAYSTATUS");

			if (logger.isDebugEnabled()) {
				logger.debug("get status success lastStatus =[" + (payStatus == null ? "null" : payStatus) + "] , nowStatus =[" + tranState + "] !");
			}

			try {
				int lastStatus = Integer.parseInt(payStatus);
				int nowStatus = Integer.parseInt(tranState);
				if (lastStatus > nowStatus) {
					flag = -1;
				} else if (lastStatus == nowStatus) {
					flag = 0;
				}
			} catch (NumberFormatException e) {
				// 我们保存到数据的状态码都是数字，如果转换失败说明数据存在问题，注入错误码（数据异常）
				//update by tianguangzhao 2016/5/24 改为不注入错误码 有上游处理
				//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
				logger.error("data error ! ", e);
				throw new ServiceException(e);
			}
		} else {
			//根据支付流水号查询订单时，数据库中只应该有一条数据，如果出现多条，注入错误码（数据异常）
			//update by tianguangzhao 2016/5/24 将错误码改为"订单重复"BUSS_PLA000500
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000500,context);
			String message = "order info error  order info list size =[" + list.size()+ "] ! payorderid =["+payorderid+"] !";
			logger.error(message);
			throw new ServiceException();
		}

		return flag;

	}
}
