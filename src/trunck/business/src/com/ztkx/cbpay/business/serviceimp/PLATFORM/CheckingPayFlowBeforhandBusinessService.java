package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BPaymentFlow;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BPaymentFlowData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 交易流水对账的预处理类，用于准备宝易互通接口需要的报文
 * 
 * @author tianguangzhao
 *
 */
public class CheckingPayFlowBeforhandBusinessService implements BusinessService {
	Logger logger = Logger.getLogger(CheckingPayFlowBeforhandBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("prepare checking paypament flow info start !");
		}
		//获取对账日期
		String checkingDate = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB005_JOB_DATE,CommonContext.SCOPE_GLOBAL);
		
		//update by tianguangzhao 20160606 新增checkType字段用于标识是定时任务发起还是console发起!
		String jobtype =  context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB005_JOB_TYPE,CommonContext.SCOPE_GLOBAL);
		
		//用于保存支付流水状态，如果是console发起的对账，则支付流水的对账状态必须是失败状态
		String checkStatus = "";
		if (jobtype.equals("0")) {
			checkStatus = BPaymentFlow.CHECKING_INIT;
		} else if (jobtype.equals("1")) {
			checkStatus = BPaymentFlow.CHECKING_FAILED;
		} else {
			String message = " jobtype error ! jobtype = [" + jobtype + "] ! ";
			logger.error(message);
			throw new ServiceException(message);
		}

		try {
			//首先查询数据库获取所需信息
			try {
				List<Map<String,String>> list = findInfos(checkingDate,checkStatus);
				//如果没有处于待対帐状态下的订单则抛出异常
				if (list == null || list.size() == 0){
					String message = "there is no pay flow waiting to be checked !";
					logger.info(message);
					//注入错误码“支付流水不存在”
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000530,context);
					throw new ServiceException(message);
				}
				//将信息注入context中
				context.setObj(BusinessMessageFormateConstant.UMB_CP0023_REQ_CYCLE_LABLE, list,CommonContext.SCOPE_GLOBAL);
				context.setFieldStr(BusinessMessageFormateConstant.UMB_CP0023_REQ_ORDER_SIZE, list.size()+"",CommonContext.SCOPE_GLOBAL);
			} catch (ServiceException e) {
			    logger.error("find payment flow infos error ! ",e);
			    //注入错误码，（查询数据异常）,将异常抛给上游处理
			    ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515,context);
			    throw e;
			}

			//更新支付流水表中的对账状态为，“开始对账”
			try {
				updateCheckingStatus(checkingDate);
			} catch (ServiceException e) {
			   logger.error("update payment flow data checking status error ! ",e);
			   //注入错误码,(更新数据库错误)，将异常抛给上游处理
			   ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
			   throw e ;
			}
			
		} catch (Exception e) {
			//注入错误码“业务服务执行异常”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
            //异常抛给上游处理
			throw new ServiceException(e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("prepare checking paypament flow info  end !");
		}
		return context;
	}
	
	/**
	 * 根据对账日期获取，带待对账的相关数据
	 * @param checkingDate
	 * @return
	 */
	private List<Map<String,String>> findInfos(String checkingDate,String status) throws ServiceException{
		List<Map<String,String>> infoList = new ArrayList<Map<String,String>>();
		
		BPaymentFlowData bPaymentFlowData = new BPaymentFlowData();
		List<Map<String,String>> merchantNos = null;
		try {
			merchantNos = bPaymentFlowData.getMerchantNosByTrandateAndStatus(checkingDate,status);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		
		if(merchantNos==null|| merchantNos.size()==0){
			//如果查询不到支付流水信息，直接返回null
			return null;
		}
		
		//循环添加数据，在map中添加其他所需的信心，并将map添加到infoList中
		for(Map<String,String> map : merchantNos){
			// 开始日期和结束日期都设定为 对账日期 ，（按照目前的规则，每天对账，对账，只对前一天的）
			map.put(BusinessMessageFormateConstant.UMB_CP0023_START_DATE,checkingDate);
			map.put(BusinessMessageFormateConstant.UMB_CP0023_END_DATE,checkingDate);
			// 将商户号修改标签
			String merchantNo = map.get("MERCHANTID");
			map.remove("MERCHANTID");
			map.put(BusinessMessageFormateConstant.UMB_CP0023_SUB_MERCHANT_NO,merchantNo);

			//将支付流水信息封装到list中
			infoList.add(map);
		}
		
		return infoList;
	}
	
	/**
	 * 更新支付流水表中的
	 * @param checkingDate 对账日期
	 */
	private void updateCheckingStatus(String checkingDate) throws ServiceException {
		BPaymentFlowData bPaymentFlowData = new BPaymentFlowData();
		try {
			bPaymentFlowData.getConnection();
			// 更新支付流水表中的状态为开始对账
			bPaymentFlowData.updateStatusByTrandate(checkingDate,BPaymentFlow.CHECKING_PROCESSING);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		} finally {
			// 释放资源
			DataHandlerUtil.releaseSource(bPaymentFlowData);
		}	
	}
}
