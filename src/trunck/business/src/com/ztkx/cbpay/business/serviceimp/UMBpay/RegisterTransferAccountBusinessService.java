package com.ztkx.cbpay.business.serviceimp.UMBpay;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BAccountTransferFlow;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BAccountTransferFlowData;
import com.ztkx.cbpay.business.handledata.BMerchantOrderData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 账户划转交易，在调用宝易互通服务进行账户划转之前首先进行登记账户划转信息.数据库中的交易状态置为初始状态
 * 此服务只管登记，组织所需字段在预处理类完成 !
 * @author tianguangzhao
 *
 */
public class RegisterTransferAccountBusinessService implements BusinessService {
	private Logger logger = Logger.getLogger(RegisterTransferAccountBusinessService.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("RegisterTransferAccountBusinessService start ! ");
		}

		//首先启用事务管理
		BAccountTransferFlowData bAccountTransferFlowData = new BAccountTransferFlowData();
		BMerchantOrderData bMerchantOrderData = new BMerchantOrderData();
		Connection connection = bAccountTransferFlowData.getConnection();
		bMerchantOrderData.setConnection(connection);
		
		try {
	
			//关闭自动提交
			DataHandlerUtil.setAutoCommit(connection, false);
			
			// 将context中的信息取出保存到数据库中
			addTransferAccountFlow(bAccountTransferFlowData,context);
			//将账户划转流水号更新到订单表中
			updateMerchantOrderMountChangeNo(bMerchantOrderData,context);
			
			//手动提交信息
			DataHandlerUtil.commitConn(connection);
		} catch (Exception e) {
			//如果发生异常，则回退事务， 并抛出异常
			DataHandlerUtil.rollbakConn(connection);
			
			logger.error("register transfer flow error !", e);
			// update by tianguangzhao 20160525 修改错误码为“业务服务执行失败”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw new ServiceException(e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bMerchantOrderData);
			DataHandlerUtil.releaseSource(bAccountTransferFlowData);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("RegisterTransferAccountBusinessService end ! ");
		}
		return context;
	}

	
	/**
	 * 向表中新增记录
	 * 
	 * @param context
	 * @throws ServiceException
	 */
	private void addTransferAccountFlow(BAccountTransferFlowData bAccountTransferFlowData,CommonContext context) throws ServiceException {
		
		List<Map<String,String>> list = null;
		try {
			list = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL,CommonContext.SCOPE_GLOBAL);
		} catch (Exception e) {
			  //如果获取请求报文标签失败，则注入错误码（请求报文异常）
			  logger.error("get cycle list error ! lable = ["+BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL+"] ! ");
			  //update by tianguangzhao 20160525 响应报文数据类型错误
		      ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			 throw new ServiceException(e);
		}
		// update by tianguangzhao 判断是否为空的操作由拆包服务完成
		// if (list == null || list.size() == 0) {
		// String message =
		// "get list error ; list size = 0 ; list name is ["+BusinessMessageFormateConstant.UMB_CP0039_CYCLE_LABEL+"]";
		// logger.error(message);
		// //获取到的请求报文内容为空，则注入错误码（请求报文异常）
		// context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000529);
		// throw new ServiceException(message);
		// }
		String bussFlowNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL);
		// 获取当前时间
		String tranTime = TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE);
		String trandate = TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE);
		String transType = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB003_TRANS_TYPE,CommonContext.SCOPE_GLOBAL);
		List<BAccountTransferFlow> acountTransferFlowList = new ArrayList<BAccountTransferFlow>();
		for (Map<String,String> map : list) {
			BAccountTransferFlow atf = new BAccountTransferFlow();
			atf.setTransferBatchNo(bussFlowNo);
			atf.setTxnDate(trandate);
			atf.setTxnTime(tranTime);
			atf.setTransType(transType);
			//update by tianguangzhao 2016/4/14 新增账户划转流水号，每笔划转明细对应一笔
			atf.setTransferFlowNo(map.get(BusinessMessageFormateConstant.UMB_CP0039_TRANS_FLOW_NO));
			atf.setCurrency(map.get(BusinessMessageFormateConstant.UMB_CP0039_CURRENCY));
			atf.setTxnAmt(map.get(BusinessMessageFormateConstant.UMB_CP0039_AMOUNT));
		     //划转标识已经不用，数据库中暂时保留
			atf.setTransferFlg("");
			//将账户划转状态和队长状态设为初始状态
			atf.setActTrafStatus(BAccountTransferFlow.TRANS_STATUS_INIT);
			atf.setCheckingStatus(BAccountTransferFlow.CHECKING_INIT);
			//收付款账号和户名 ,update by tianguangzhao 2016/4/27 去掉收付款人姓名，
			atf.setPayAccountNo(map.get(BusinessMessageFormateConstant.UMB_CP0039_PAY_ACCT));
			//atf.setPayName(map.get(BusinessMessageFormateConstant.UMB_CP0039_PAY_NAME));
			atf.setRecAccountNo(map.get(BusinessMessageFormateConstant.UMB_CP0039_REC_ACCT));
			//atf.setRecName(map.get(BusinessMessageFormateConstant.UMB_CP0039_REC_NAME));
			//保存orderid
			atf.setOrderid(map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO));
			atf.setBak(map.get(BusinessMessageFormateConstant.UMB_CP0039_BAK));
			atf.setExrate(map.get(BusinessMessageFormateConstant.UMB_CP0039_EXRATE));
			acountTransferFlowList.add(atf);
		}
		try {
			bAccountTransferFlowData.insertRecord(acountTransferFlowList);
		} catch (HandlerException e) {
			//入库失败，注入错误码（插入数据异常）
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517,context);
		    throw new ServiceException(e);
		}
	}
	/**
	 * 更新订单表中的账户划转状态为初始状态,在插入订单信息时完成此操作，（此方法暂时废弃）
	 * @param context
	 * @throws ServiceException 
 */
	private void updateMerchantOrderMountChangeNo(BMerchantOrderData bMerchantOrderData,CommonContext context) throws ServiceException{
		//首先获取划转流水号
		String  mountChangeNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL);
		List<Map<String,String>> list = (List<Map<String,String>>) context.getObj(BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE,CommonContext.SCOPE_GLOBAL);
	
		if (list == null || list.size() == 0) {
			logger.error("get list error list size is 0 !");
			throw new ServiceException("get list error ! list name is ["+BusinessMessageFormateConstant.CTB_CTB003_CYCLE_LABLE+"]");
		}else{
			//根据解析list中的循环更新订单表的账户划转状态
			for(Map<String,String> map : list){
				String merchantNo = map.get(BusinessMessageFormateConstant.CTB_CTB003_MERCHANT_NO);
				String orderId = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_NO);
				String orderDate = map.get(BusinessMessageFormateConstant.CTB_CTB003_ORDER_DATE);
			    //根据商户号，订单日期，订单时间唯一确定一笔订单
				
				int count = 0 ;
				try {
					count = bMerchantOrderData.updateMountChangeNo(orderId, merchantNo, orderDate, mountChangeNo);
					if(logger.isDebugEnabled()){
						logger.debug("update merchant order updateMountChangeNo success ! update count =["+count+"] !");
					}
				} catch (HandlerException e) {
					//如果更新失败注入错误吗，更新数据库失败
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
				    throw new ServiceException(e);
				}
				if(count != 1 ){
					String message ="update merchant order updateMountChangeNo error ! update count =["+count+"] !";
					logger.error(message);
					throw new ServiceException(message);
				}
			}
			
		}
	}
		
}
