package org.inn.baner.serviceimp.UMBpay;

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
 * 查询账户划转结果服务，用于查询账户划转状态交易，宝易互通返回信息后，进行判断并更新数据库中的数据，暂时未用到
 * 
 * @author tianguangzhao
 *
 */
public class QueryTransferAccountBusinessService implements BusinessService {
	private Logger logger = Logger.getLogger(QueryTransferAccountBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("QueryTransferAccountBusinessService start ! ");
		}

		// 取需要的字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String state = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0040_TRAN_STATE, CommonContext.SCOPE_GLOBAL); // 返回信息
		String transferBatchNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_CP0040_ORGTRANFLOW,CommonContext.SCOPE_GLOBAL); // 原交易流水号
		String respCode = context.getResponseCode(); // 响应吗

		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则注入错误码（响应报文错误）直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			logger.error(" msgType error msgType is [" + msgType + "]");
			//update by tianguangzhao 20160524 错误码修改为“响应报文类型错误”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		// PLA000000表示交易成功，其他码交易失败，如果响应吗为失败，则直接返回，不再进行后续操作
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			logger.error(" trade failed ! responese code is ["+respCode+"] !");
			return context;
		}

		String  transType = (String) context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB004_TRANS_TYPE,CommonContext.SCOPE_GLOBAL);//划转类型
		// update by tianguangzhao 是否为空的判断交由拆包服务完成
		// if(transType ==null || transType.equals("")){
		// //如果获取划转类型失败，注入错误码（响应报文错误），然后返回！
		// logger.error("can not get transType ! lable=["+BusinessMessageFormateConstant.CTB_CTB004_TRANS_TYPE+"] !");
		// context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
		// return context;
		// }
		
		// 划转流水状态，由于 宝易互通和我们的状态不一致，所以需要将宝易互通的状态码转换为我们的状态码
		String flowStatus = "";
        String transResult =null;
		try {
			//update by tianguangzhao 2016/4/28 除了交易成功之外，宝易互通返回的其他状态均为失败
			if (state.equals(BusinessConstantField.UMB_TRADING_INIT)) {
				//交易初始化
				//flowStatus = BusinessConstantField.TRADING_INIT;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);
			} else if (state.equals(BusinessConstantField.UMB_TRADING_SUCCESS)) {
				//交易完成
				flowStatus = BusinessConstantField.TRADING_SUCCESS;
				transResult = getTransStatus(transType,true);
			} else if (state.equals(BusinessConstantField.UMB_TRADING_PROCESSING)) {
				//交易处理中
				//flowStatus = BusinessConstantField.TRADING_PROCESSING;
				flowStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);
			} else if (state.equals(BusinessConstantField.UMB_TRADING_FAILED)) {
				//交易处理失败
				flowStatus = BusinessConstantField.TRADING_FAILED;
				transResult = getTransStatus(transType,false);
			} else {
				//如果交易状态不在上述四个状态中，则表示宝易互通的响应报文存在问题,注入错误码（响应报文错误）
				//update by tianguangzhao 20160524 错误码修改为“业务服务执行异常”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				logger.error("state error ! state = [" + state + "] !");
			    return context;
			}
		} catch (ServiceException e) {
            //交易码转换失败，则说明划转类型有误,及请求报文中存在错误,注入错误码（ 请求报文异常）
			//update by tianguangzhao 20160525 错误码改为“业务服务执行异常”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw e;
		}
		//将转换后的状态码，注入context，返回给调用方
		context.setFieldStr(BusinessMessageFormateConstant.CTB_CTB004_TRAN_STATE, flowStatus,CommonContext.SCOPE_GLOBAL);
		// 检查宝易互通返回的状态是否正确
		try {
			int flag = checkTransferAccountFlowStatus(transferBatchNo, flowStatus,context);
			if (logger.isDebugEnabled()) {
				logger.debug("get flag success , flag = [" + flag + "] !");
			}
			if (flag == -1) {
				//如果本次状态码小于上次的状态码,则宝易互通返回的报文有误，注入错误码（响应报文错误），然后返回
				//update by tianguangzhao 20160524 错误码修改为“响应报文中交易状态错误”
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000533,context);
				return context;
			} else if (flag == 1) {
				//如果本次的状态码大于上次的状态码,此时更新数据库的信息
				BAccountTransferFlowData bAcountTransferFlowData = new BAccountTransferFlowData();
				BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
				//开启事务，设置自动提交为false
				Connection conn = bAcountTransferFlowData.getConnection();
				bMerchantOrderData.setConnection(conn);
				DataHandlerUtil.setAutoCommit(conn, false);
				
				String paydate=context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_DATE,CommonContext.SCOPE_GLOBAL);
				String paytime=context.getFieldStr(BusinessMessageFormateConstant.UMB_TRAN_TIME,CommonContext.SCOPE_GLOBAL);
				
				try {
					// 更新账户划转流水表中的“划转状态”
					updateTransferAccountFlowInfo(bAcountTransferFlowData,transferBatchNo, flowStatus, paydate,paytime);
					//判断是否是终态,如果是则更新商户订单表中“账户划转状态”字段
					if(transResult != null){
						updateMerchantOrderTransStatus(bMerchantOrderData,transferBatchNo,transResult);
					}
					//最后手动提交更新内容
					DataHandlerUtil.commitConn(conn);
				} catch (ServiceException e) {
					//如果更新过程中抛出异常，则先回滚事务，然后交给上游处理,如果发生异常会直接抛出ServiceException，被最外层try catch抓住!
					DataHandlerUtil.rollbakConn(conn);
					//注入错误码（更新数据库错误）
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
					throw e ;
				}finally{
					//调用工具类释放资源,如果发生异常会直接抛出ServiceException，被最外层try catch抓住!
					DataHandlerUtil.releaseSource(bMerchantOrderData);
					DataHandlerUtil.releaseSource(bAcountTransferFlowData);
				}
				
			} else {
                //此时flag的值为 0 ；表示此次得到的状态码和上次得到的状态码一致，则不需要更新数据库中的内容
				logger.info(" no need to update database nowstatus = laststatus !");
				return context;
			}
		} catch (Exception e) {
			// 如果更新数据过程中抛出异常，则注入错误码（更新数据库错误），考虑是否以后细分错误
			logger.error("QueryTransferAccountBusinessService error !", e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			throw new ServiceException(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("QueryTransferAccountBusinessService success ! ");
		}
		return context;
	}

	/**
	 * 更新数据库中的账户划转状态
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void updateTransferAccountFlowInfo(BAccountTransferFlowData bAcountTransferFlowData,String jnlNo, String status,String paydate,String paytime) throws ServiceException {
	    //update by tianguangzhao 2016/4/27 去掉宝易互通流水号，宝易互通无法返回,考虑到后期对账，划转完成日期和时间以宝易互通返回值为准，
		int count = 0 ;
		try {
			count=bAcountTransferFlowData.updateTransInfo(jnlNo, status, paydate, paytime);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
	    //无法判断一次划转的笔数是多少，所以无法比较条数，暂时不比较，以后确定
		if(logger.isDebugEnabled()){
			logger.debug("update trans flow info success ! update count = ["+count+"] !");
		}
	}

	/**
	 * 根据流水号。查询划转状态
	 * 
	 * @param transferBatchNo
	 * @return
	 * @throws ServiceException
	 */
	private int checkTransferAccountFlowStatus(String transferBatchNo, String status,CommonContext context) throws ServiceException {
		int flag = 1;

		BAccountTransferFlowData atf = new BAccountTransferFlowData();
		List<Map<String, String>> list = null;
		
		try {
			//根据划转批次号查询该批次所有划转的状态
			list = atf.getAcountTransferFlowInfoByTransferBatchNo(transferBatchNo);
		} catch (HandlerException e) {
			//查询数据库失败注入错误码（查询数据异常）
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			throw new ServiceException(e);
		}
		if (list == null || list.size() == 0) {
			//如果获取不到数据注入错误码（表中无有效数据）
			//update by tianguangzhao 20160524 修改错误码为”账户划转信息不存在!“
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531,context);
			String message = "can not get  transfer flow info !";
			logger.error(message);
			throw new ServiceException(message);
		} else if (list.size() == 1){
             //根据数据库中查询的信息和此次宝易互通返回的状态，判断状态是否正常 ！
			 Map<String, String> map1 =list.get(0);
				String databaseStatus = map1.get("ACTTRAFSTATUS");

				if (logger.isDebugEnabled()) {
					logger.debug("get status success lastStatus =[" + ( databaseStatus == null ? "null" : databaseStatus ) + "] , nowStatus =[" + status + "]");
				}

				try {
					int lastStatus = Integer.parseInt(databaseStatus);
					int nowStatus = Integer.parseInt(status);
					if (lastStatus > nowStatus) {
						flag = -1;
					} else if (lastStatus == nowStatus) {
						flag = 0;
					}
				} catch (NumberFormatException e) {
					//我们保存到数据的状态码都是数字，如果转换失败说明数据存在问题，注入错误码（数据异常）
					//update by tianguangzhao 20160525 改为不注入错误码有上游处理
					//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
					logger.error("data error ! ",e);
					throw new ServiceException(e);
				}
		}else{
			//如果获取到的划转状态不唯一，则数据存在错误，注入错误码(数据异常),
			String message = "get transfer status error , please check ! transferBatchNo =["+transferBatchNo+"] !";
			logger.error(message);
			//update by tiangunagzhao 20160525 改为不注入错误码由上游处理！
			//context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000531);
			throw new ServiceException(message);
		}

		return flag;
	}
	
	/**
	 * 更新订单表中的账户划转状态为最新状态
	 * @param context
	 * @throws ServiceException 
	 */
	private void updateMerchantOrderTransStatus(BMerchantOrderData bMerchantOrderData,String mountChangeNo,String transResult) throws ServiceException{
		int count = 0 ;
		try {
			count = bMerchantOrderData.updateMountChangeStatus(mountChangeNo,transResult);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		if (count != 1) {
			String message = "update merchanrOder trans status error ! update count =[" + count + "] ! ";
			logger.error(message);
			throw new ServiceException(message);
		}
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
			String message = "transType error ! transType=[" + transType + "] !";
			logger.error(message);
			throw new ServiceException(message);
		}
		return status;
	}
}
