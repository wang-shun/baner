package org.inn.baner.serviceimp.UMBpay;

import java.sql.Connection;

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
 * 更新支付流水服务，当调用宝易互通记性账户划转之后，根据宝易互通反馈的数据更新数据库中的交易状态
 * 
 * @author tianguangzhao
 *
 */
public class UpdatePayProgressBusinessService implements BusinessService {

	private Logger logger = Logger.getLogger(UpdatePayProgressBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug(" UpdatePayProgressBusinessService  beginning...");
		}

		// 取普通字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String respCode = context.getResponseCode(); // 响应吗
		// update by tianguangzhao 20160524 是否为空的判断交给拆包服务完成
		// if(msgType==null||respCode==null||respCode.trim().equals("")||msgType.trim().equals("")){
		// //判断必要信息是否都存在，如果存在为空的情况，则注入错误码（响应报文错误），并返回！
		// logger.error("response msg error ! respCode = [" + respCode +
		// "] , msgType = [" + msgType + "]");
		// context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
		// return context;
		// }
		if (logger.isDebugEnabled()) {
			logger.debug(" get response msg success ! respCode = [" + respCode + "] , msgType = [" + msgType + "] ! ");
		}

		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			logger.error(" msgType error msgType is [" + msgType + "]");
			//update by tianguangzhao 20160524 错误码修改为“响应报文类型错误”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		// PLA000000表示交易成功，其他码交易失败
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			// 如果响应吗为失败，则直接返回，不再进行后续操作
			logger.error(" trade failed ! responese code is ["+respCode+"] !");
			return context;
		} else {
			// 根据返回结果确认
			String tranState = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0001_TRAN_STATE,CommonContext.SCOPE_GLOBAL); // 支付状态
			String payorderid = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL); // 支付流水号，此处为宝易互通流水号
			//update by tianguangzhao 2016/4/26 宝易互通不返回该字段，所以去掉!
			//String payjnlno = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0001_PAY_FLOW_NO,CommonContext.SCOPE_GLOBAL); // 支付流水号，宝易互通返回
			String merchantNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_MERCHANT_NO,CommonContext.SCOPE_GLOBAL); // 商户号
			//订单号号订单日期在宝易互通的报文中无法获取，只能从收银台的报文中获取
			String orderId = context.getFieldStr(BusinessMessageFormateConstant.CASH_ORDER_ID,CommonContext.SCOPE_GLOBAL); // 订单号
			String trandate = context.getFieldStr(BusinessMessageFormateConstant.CASH_TRAN_DATE,CommonContext.SCOPE_GLOBAL); // 订单日期,现在我们自己的报文头和宝易互通一致，以后注意修改，此处需要取商户上送的日期,作为订单日期使用
			// 支付流水状态，由于 宝易互通和我们的状态不一致，所以需要我们转换，暂时在类中转换，以后改进
			String flowStatus = "";
			// 订单表中的订单状态
			String orderStatus = null;
			//update by tianguangzhao 2016/4/28 除了交易成功之外，其他状态都为转为失败
			if (tranState.equals(BusinessConstantField.UMB_TRADING_INIT)) {
				//flowStatus = BusinessConstantField.TRADING_INIT;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
				// 如果交易状态时01则表示，交易已经完成
				flowStatus = BusinessConstantField.TRADING_SUCCESS;
				orderStatus = BusinessConstantField.ORDER_PD;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
				// 如果状态时03,则表示交易处理失败
				//flowStatus = BusinessConstantField.TRADING_PROCESSING;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else if (tranState.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
				// 如果状态时03,则表示交易处理失败
				flowStatus = BusinessConstantField.TRADING_FAILED;
				orderStatus = BusinessConstantField.ORDER_CZ;
			} else {
				//如果宝易互通返回的状态码，不在以上状态码之内，则表示返回报文有误，注入错误码（响应报文错误）,并返回！；
				logger.error("tranState error ! tranState = [" + tranState+ "]");
				//update by tianguangzhao 20160524 错误码修改为“交易状态错误”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000533,context);
				return context;
			}
			// 将自己的状态码注入context中,以后改进
			context.setFieldStr(BusinessMessageFormateConstant.CASH_CHS006_TRAN_STATE, flowStatus,CommonContext.SCOPE_GLOBAL);

			// 更新支付流水表状态和订单表状态
			try {
				BPaymentFlowData bPaymentFlowData = new BPaymentFlowData();
				BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
				//开启事务
				Connection conn = bPaymentFlowData.getConnection();
				bMerchantOrderData.setConnection(conn);
				//关闭自动提交
				DataHandlerUtil.setAutoCommit(conn, false);
						
				try {
					String doneDate = context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_DATE,CommonContext.SCOPE_GLOBAL);
					String doneTime = context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_TIME,CommonContext.SCOPE_GLOBAL);
					updatePayFlowStatus(bPaymentFlowData,payorderid, flowStatus,doneDate,doneTime);
					// 判断是否是最终状态，如果是，更新订单表状态
					if (tranState.equals(BusinessConstantField.UMB_TRADING_SUCCESS)|| tranState.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
						updateOrderInfo(bMerchantOrderData,orderId, merchantNo, trandate,orderStatus, payorderid,doneDate,doneTime);
					}
					//手动提交
					DataHandlerUtil.commitConn(conn);		
				} catch (ServiceException e) {
					//注入错误码 （更新数据库错误）
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
					DataHandlerUtil.rollbakConn(conn);
					throw e;
				} finally {
					// 调用工具类释放资源
					DataHandlerUtil.releaseSource(bMerchantOrderData);
					DataHandlerUtil.releaseSource(bPaymentFlowData);
				}
			} catch (Exception e) {
				//如果发生未知错误，则注入错误码“业务服务执行异常”
				logger.error("update pay progress error ! ",e);
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				throw new ServiceException(e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" UpdatePayProgressBusinessService  success...");
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
	private void updatePayFlowStatus(BPaymentFlowData bPaymentFlowData,String payorderid, String tranState,String paydate,String paytime) throws ServiceException {

		int	count=0;
		try {
			count = bPaymentFlowData.updatePayFlowStatus(payorderid, tranState,paydate, paytime);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
	    //校验更新条数,如果更新条数不为1，则抛出异常！
		if (count != 1) {
			String message = "updateOrderStatic error !  update count =[" + count + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
	}

	/**
	 * 更新订单信息表，将订单状态改为最新状态
	 * 
	 * @param orderId
	 *            订单号
	 * @param merchantNo
	 *            商户号
	 * @param tranDate
	 *            交易日期
	 * @param status
	 *            交易状态
	 * @throws ServiceException
	 */
	private void updateOrderInfo(BMerchantOrderData bMerchantOrderData,String orderId, String merchantNo,String tranDate, String status,String payorderid,String payDate,String payTime) throws ServiceException {

		int count = 0;
		try {
			count=bMerchantOrderData.updateOrderInfoByOrderID(orderId,merchantNo, tranDate, status, payDate, payTime,payorderid);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
	    //判断更新条数是否正确
		if (count != 1) {
			String message = "updateOrderInfo error !  update count =[" + count + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}	
		
	}
}
