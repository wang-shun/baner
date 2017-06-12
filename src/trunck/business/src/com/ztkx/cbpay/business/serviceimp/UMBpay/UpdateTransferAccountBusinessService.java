package com.ztkx.cbpay.business.serviceimp.UMBpay;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BAccountTransferFlow;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.enums.AccountTransferStatusEnum;
import com.ztkx.cbpay.business.handledata.BAccountTransferFlowData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 账户划转交易，宝易互通返回处理结果之后，根据宝易互通返回的信息更新数据库中的交易状态
 * 
 * @author tianguangzhao
 *
 */
public class UpdateTransferAccountBusinessService implements BusinessService {
	private Logger logger = Logger.getLogger(UpdateTransferAccountBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("UpdateTransferAccountBusinessService start ! ");
		}

		// 取普通字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String state = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0039_TRAN_STATE, CommonContext.SCOPE_GLOBAL); // 返回信息

		String respCode = context.getResponseCode(); // 响应吗

		// 账户划转状态
		String transStatus = "";
		//账户划转结果，在得到终态时赋值，用于更细订单表中的账户划转状态
		String transResult = null;

		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则注入错误码（响应报文错误），并直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			logger.error(" msgType error msgType is [" + msgType + "] ！");
			//update by tianguangzhao 20160524 错误码修改为 “响应报文类型错误”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		// PLA000000表示交易成功，其他码交易失败
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			// 如果响应吗为失败，则直接返回，不再进行后续操作
			logger.error(" trade failed ! responese code is ["+respCode+"] !");
			return context;
		}
		String  transType = (String) context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_TRANS_TYPE,CommonContext.SCOPE_GLOBAL);
	   
//		是否为空的判断交给拆包服务完成
//		if(transType ==null || transType.equals("")){
//			//如果获取划转类型失败，则表示请求报文有错误！,注入错误码（ 请求报文异常），并返回。
//			logger.error("can not get transType ! lable=["+BusinessMessageFormateConstant.CTB_CTB003_TRANS_TYPE+"] ! ");
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//			return context;
//		}
		
		try {
			//update by tianguangzhao 2016/4/28 除了交易成功之外，其他状态都为转为失败
			if (state.equals(BusinessConstantField.UMB_TRADING_INIT)) {
				//交易初始化
				//transStatus = BusinessConstantField.TRADING_INIT;
				transStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);;
			} else if (state.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
				//交易完成
				transStatus = BusinessConstantField.TRADING_SUCCESS;
				transResult = getTransStatus(transType,true);
			} else if (state.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
				//交易进行中
				//transStatus = BusinessConstantField.TRADING_PROCESSING;
				transStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);;
			} else if (state.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
				//交易失败
				transStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);;
			} else {
				//如果宝易互通返回的状态码不正确则，注入错误码（响应报文错误），并返回 ！
				String message = "get state error ! state = [" + state + "] ! ";
				logger.error(message);
				//update by tianguangzhao 20160524 错误码修改为 “响应报文状态码错误“
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000533,context);
				return context;
			}
		} catch (ServiceException e) {
		   //如果转化状态码失败，则表示请求报文中的划转类型错误,注入错误码（请求报文异常）
			//update by tianguangzhao 20160525 错误码改为“业务服务执行异常！”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw e;
		}
		//将转化后的状态码注入context ，方便返回给调用方!
		context.setFieldStr(BusinessMessageFormateConstant.CTB_CTB003_TRAN_STATE, transStatus,CommonContext.SCOPE_GLOBAL);
		// 更新划转信息表
		try {
			BAccountTransferFlowData bAcountTransferFlowData = new BAccountTransferFlowData();
			BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
			//开启事务
			Connection conn = bAcountTransferFlowData.getConnection();
			bMerchantOrderData.setConnection(conn);
			DataHandlerUtil.setAutoCommit(conn, false);
			
			// 获取宝易互通返回的日期和时间，作为支付完成时间和支付完成日期,为了避免对账时时间不一致！
			String donetime =  context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_TIME,CommonContext.SCOPE_GLOBAL);;
			String donedate =  context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_DATE,CommonContext.SCOPE_GLOBAL);;
			try {
				updateTransferAccountFlow(bAcountTransferFlowData, context,transStatus, donedate, donetime);
				// 判断是否得到终态，如果transResult不为null则表示已经得到终态
				if (transResult != null) {
					updateMerchantOrderTransStatus(bMerchantOrderData, context,transResult);
				}
				// 手动提交事务
				DataHandlerUtil.commitConn(conn);
			} catch (ServiceException e) {
				//update by tianguangzhao 20160525 注入错误码 （更新数据异常）
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			   //如果出现异常，首先回滚事务,然后抛出异常
				DataHandlerUtil.rollbakConn(conn);
				throw e ;
			}finally{
				//调用工具类，释放资源
				DataHandlerUtil.releaseSource(bMerchantOrderData);
				DataHandlerUtil.releaseSource(bAcountTransferFlowData);
			}
		} catch (ServiceException e) {
			logger.error("UpdateTransferAccountBusinessService error ！",e);
			// 如果更新数据库数据时，如果出现错误，则先判断是否已存在错误码，如果不存在则注入注入错误码，并抛出异常
			//update by tianguangzhao 20160525 错误码改为“业务服务执行失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("UpdateTransferAccountBusinessService success ! ");
		}
		return context;
	}

	/**
	 * 更新账户划转流水表中的wach时间和状态
	 * @throws ServiceException 
	 */
	private void updateTransferAccountFlow(BAccountTransferFlowData bAcountTransferFlowData,CommonContext context, String status,String donedate,String donetime) throws ServiceException {
		
		String transferBatchNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL); // 划转流水号从宝易互通的报文中获取
		int count = 0 ;
		try {
			count=bAcountTransferFlowData.updateAcountTransferResult(transferBatchNo, donedate, donetime, status);
		} catch (HandlerException e1) {
			//注入错误码“更新数据异常”，并抛出异常
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw new ServiceException(e1);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("update account transflow success ! update count = [" + count + "] ! ");
		}
		String listsize = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0039_LIST_SIZE,CommonContext.SCOPE_GLOBAL);
       int orgCount = 0;
		try {
			orgCount = Integer.parseInt(listsize);
		} catch (NumberFormatException e) {
			//listsize中保存的是数据条数，如果强转int失败，则说明请求报文有问题
			logger.error("get list size error ! listsize ="+(listsize==null?"null":listsize)+"] ! ",e);
			//update by tianguangzhao 20160525 修改为不注入错误码由上游处理
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
			throw new ServiceException(e);
		}
		//判断更新的记录条数是否和穿给宝易互通的条数一致。
		if(count != orgCount){
			String message = "update account trans flow error ! update count =["+count+"] , orgCount = ["+orgCount+"] !";
		    logger.error(message);  
		    //如果更新的条数和账户划转交易的条数不同则注入错误码“更新数据异常”
		    ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw new ServiceException(message);
		}
	}

	/**
	 * 更新订单表中的账户划转状态为初始状态
	 * @param context
	 * @throws ServiceException 
	 */
	private void updateMerchantOrderTransStatus(BMerchantOrderData bMerchantOrderData,CommonContext context,String status) throws ServiceException{		
		List<Map<String,String>> list = null;
		
		try {
			list = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE,CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			//获取list的值失败，则表示请求报文存在异常！
		    logger.error("get list error ! lablename =["+BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+"] !",e);
		    //update by tiangunagzhao 20160525 改为不注入错误码（由上游处理）
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
		    throw new ServiceException(e);
		}
//      update by tianguangzhao 20160525 是否为空的判断交给拆包服务完成		
//		if (list == null || list.size() == 0) {
//			String message = "get list error ! list name is ["+BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+"]";
//			logger.error(message);
//			//获取list的值为空，则表示请求报文存在异常！
//			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
//			throw new ServiceException(message);
//		}else{
			list = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE,CommonContext.SCOPE_GLOBAL);
			
			int count = 0 ;
		try {
			count = bMerchantOrderData.updateMountChangeStatus(list, status);
		} catch (HandlerException e1) {
			// 注入错误码“更新数据失败”，并抛出异常
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
			throw new ServiceException(e1);
		}
			String listsize = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_LIST_SIZE,CommonContext.SCOPE_GLOBAL);
			int orgCount = 0;
			try {
				orgCount = Integer.parseInt(listsize);
			} catch (NumberFormatException e) {
				// listsize中保存的是数据条数，如果强转int失败，则说明请求报文有问题
				logger.error("get list size error ! listsize ="+ (listsize == null ? "null" : listsize) + "] ! ", e);
				//update by tianguangzhao 20160525 改为不注入错误码，由上游处理
				//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
				throw new ServiceException(e);
			}
			// 判断更新的条数！ ，如果和请求信息条数不符，则注入错误码（更新数据库错误），然后抛出异常！
			if (count != orgCount) {
				String message = "update merchanrOder trans status error ! update count =[" + count + "] ! ";
				logger.error(message);
				//注入错误码 “更新数据失败”,并抛出异常
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
				throw new ServiceException(message);
			}
		//}

	}
	/**
	 * 根据划转类型和成功失败标识判断账户划转状态
	 * @param transType 账户划转类型
	 * @param flag  成功，失败标识，用于标记账户划转的最终状态是成功还是失败
	 * @return
	 * @throws ServiceException 
	 */
	private String getTransStatus(String transType, boolean flag) throws ServiceException {

		String status = null;
		// 首先根据请求类型判断应该插入什么状态
		if (transType.equals(BAccountTransferFlow.TRANS_TYPE_BEFORE_FEP)) {
			// 如果是购汇前划转
			if (flag) {
				status = AccountTransferStatusEnum.BUYBEFSUCC.getStatus();
			} else {
				status = AccountTransferStatusEnum.BUYBEFFAIL.getStatus();
			}
		} else if (transType.equals(BAccountTransferFlow.TRANS_TYPE_AFTER_FEP)) {
			// 如果是购汇后划转
			if (flag) {
				status = AccountTransferStatusEnum.BUYAFTSUCC.getStatus();
			} else {
				status = AccountTransferStatusEnum.BUYAFTFAIL.getStatus();
			}
		} else if (transType.equals(BAccountTransferFlow.TRANS_TYPE_BEFORE_PFE)) {
			// 如果是付汇前的划转
			if (flag) {
				status = AccountTransferStatusEnum.PAYBEFSUCC.getStatus();
			} else {
				status = AccountTransferStatusEnum.PAYBEFFAIL.getStatus();
			}
		} else {
			String message = "get transType error ! transType=[" + transType + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
		return status;
	}
}
