package com.ztkx.cbpay.business.serviceimp.PLATFORM;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BCheckErrorList;
import com.ztkx.cbpay.business.bean.BCheckStatus;
import com.ztkx.cbpay.business.bean.BPaymentFlow;
import com.ztkx.cbpay.business.bean.CheckingPayFlowBean;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.constant.BusinessMessageFormateConstant;
import com.ztkx.cbpay.business.handledata.BCheckErrorListData;
import com.ztkx.cbpay.business.handledata.BCheckStatusData;
import com.ztkx.cbpay.business.handledata.BPaymentFlowData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.business.util.GzipUtil;
import com.ztkx.cbpay.business.util.UMBTranStatusTransferUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.enanddecrypt.Base64Util;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 交易流水对账服务，通过调用宝易互通的查询接口，进行对账
 * 
 * @author tianguangzhao
 *
 */
public class CheckingPayFlowByQueryBusinessService implements BusinessService {
	Logger logger = Logger.getLogger(CheckingPayFlowByQueryBusinessService.class);


	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("CheckingPayFlowBusinessService start !");
		}
		// 取普通字段
		String msgType = context.getFieldStr(BusinessMessageFormateConstant.UMB_MSG_TYP,CommonContext.SCOPE_GLOBAL); // 报文类型
		String respCode = context.getResponseCode(); // 响应吗

    	if (logger.isDebugEnabled()) {
			logger.debug("get message success ! respCode = [" + respCode + "] , msgType = [" + msgType + "] ! ");
		}
		// 响应报文msgType为0002 如果是0001表示请求报文，首先判断如果不是响应报文则直接返回
		if (!msgType.equalsIgnoreCase(BusinessConstantField.MSG_TYPE_REP)) {
			//报文类型错误注入错误码（相应报文错误）
			logger.error(" msgType error msgType is [" + msgType + "] !");
			// update by tianguangzhao 20160524  错误码修改为 “响应报文类型错误！”
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000539,context);
			return context;
		}
		if (!respCode.equalsIgnoreCase(ErrorCodeConstant.BASE_PLA000000)) {
			// 如果响应吗为失败，则直接返回，不再进行后续操作
			logger.error(" trade failed ! responese code is ["+respCode+"] !");
			return context;
		} else {

			// 获取对账日期 ，在对账的预处理时已经判断过，该参数是否为空，如果为空则无法走到此步骤，所以此处不再做判断
			String jobdate = context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB005_JOB_DATE,CommonContext.SCOPE_GLOBAL);

			boolean checkingflag = false;

			try {
				// 进行对账操作
				checkingflag = checkPayFlow(jobdate, context);

				if (logger.isDebugEnabled()) {
					logger.debug("checking end ! checking result is [" + checkingflag + "] ！ ");
				}

			} catch (ServiceException e) {
				//打印报错信息，并注入错误码“业务服务执行异常”
				logger.error("checking payment flow error !", e);
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
				throw e;
			} finally {
				// 最后进行操作，将此次处理的结果插入对账记录表中
				try {
					// 获取此次流水号
					String jnlno = context.getFieldStr(BusinessMessageFormateConstant.CTB_FLOW_NO,CommonContext.SCOPE_GLOBAL);
					//update by tianguangzhao 20160612 新增checkType字段用于标识是定时任务发起还是console发起!,从console发起的手工对账需要将上次的日志改为“已处理”
					String jobtype =  context.getFieldStr(BusinessMessageFormateConstant.CTB_CTB005_JOB_TYPE,CommonContext.SCOPE_GLOBAL);					
					//用于保存支付流水状态，如果是console发起的对账，则支付流水的对账状态必须是失败状态
					boolean checkStatus = false;
					if (jobtype.equals("1")) {
						checkStatus = true;
					}
					endChecking(jobdate, jnlno, checkingflag, checkStatus);
				} catch (ServiceException e) {
					// 如果插入对账记录异常，注入错误码（ 插入数据异常），抛出异常，
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517,context);
					throw e;
				}
			}

			// 程序全部执行成功(判断错误码是否存在)，注入返回码
			if (checkingflag) {
				// 对账成功,
				context.setResponseCode(ErrorCodeConstant.BASE_PLA000000);
			} else {
				// 对账失败
				context.setResponseCode(BusinessErrorCodeConstant.BUSS_PLA000521);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("CheckingPayFlowBusinessService end !");
			}
			return context;
		}
	}



	/**
	 *从宝易互通返回的信息中获取对账所需的信息,待定
	 *
	 * @param context
	 * @return
	 * @throws ServiceException
	 */
	private List<CheckingPayFlowBean> getDataFromMessage(String data) throws ServiceException {
		List<CheckingPayFlowBean> list = new ArrayList<CheckingPayFlowBean>();
		// 将data还原为原始报文
        List<String> infoList= null;
         try {
        	 //先用base64解码
        	 byte[] bytes = Base64Util.decode(data.getBytes());
        	 //由于宝易互通和我们的编码一致为utf-8 所以此处不传入编码格式
			infoList=GzipUtil.uncompressToList(bytes);
		} catch (IOException e) {
			logger.error("unzip error ! ",e);
			throw new ServiceException(e);
		}
		if (infoList == null || infoList.size() == 0 || infoList.size() == 1) {
			//如果listsize = 1 则只有表头，没有任何信息
			String message = "unzip error ! result is null , please check !";
			logger.error(message);
			throw new ServiceException(message);
		} else {
			try {
				//记录读到第几条
				for(int i = 1 ;i < infoList.size() ; i++ ){
					String row = infoList.get(i); 	
					if (row == null || row.equals("")) {
						// 发生此种情况直接读取下一行
						logger.error(" data error ! line num is [" + (i + 1) + "]");
						continue;
					} else {
						String[] infoBytes = row.split(",");
						if (infoBytes == null || infoBytes.length != 14) {
							// 判断获取到的信息是否符合规则
							String message = "data info error ! can not split data ! line num is [" + (i + 1) + "] !";
							logger.error(message);
							throw new ServiceException(message);
						}else{
							CheckingPayFlowBean cpfb = new CheckingPayFlowBean();
//	0    1             2     3      4      5        6          7     8       9      10     11      12       13						
//商户号,商户平台收款账户别名,交易类型,商户订单号,银行名称,客户银行账号,客户银行账号名称,交易金额,币种 人民币,响应编码,响应内容,商户交易时间,交易完成时间,交易状态
							//支付账号
							String accountno = infoBytes[5].trim();
							cpfb.setAccountNo(accountno);
							
							//支付币种
							String currency = infoBytes[8].trim();
						    cpfb.setCurrency(currency);
						    
						    //订单号
						    String orderid = infoBytes[3].trim();
						    cpfb.setOrderid(orderid);
						    
						    //宝易互通未返回该字段，暂时不用
						    //cpfb.setPayjnlno(payjnlno);
						    
						    //支付状态，是汉字，需要转换为相应码值
						    String status = infoBytes[13].trim();
				            //由于我们的平台的编码格式就是UTF-8，所以此处不需要转换(如果发起方的编码格式不是UTF-8，则需要转码)
						    
						    //将描述转换为我们的状态码
						    status = UMBTranStatusTransferUtil.TransferToOurStatusByDES(status);
						    cpfb.setStatus(status);
						    
							//获取交易日期，从宝易互通的原始报文中截取，宝易互通报文格式（2015-01-01 23:41:11）
							String tranDate = infoBytes[11].substring(0,10).replace("-", "");
							cpfb.setTranDate(tranDate);;
							
				            //交易金额
							String txnAmt = infoBytes[7].trim();
							cpfb.setTxnAmt(new BigDecimal(txnAmt));

							if(logger.isDebugEnabled()){
								logger.debug("get bean success ! bean ="+cpfb.toString());
							}
							//将bean封装如list中
							list.add(cpfb);
						}
					}
				}
			} catch (Exception e) {
				//抛出异常
				logger.error(" get UMB data error ! ",e);
				throw new ServiceException(e);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("get data from file success ! list size = [" + list.size() + "] !");
		}

		return list;

	}

	/**
	 * 对账方法，根据商户号循环对账
	 * 
	 * @param trandate
	 * @param context
	 * @return
	 * @throws ServiceException
	 */
	private boolean checkPayFlow(String trandate,CommonContext context) throws ServiceException {
       //获取返回报文中的循环次数
		List<Map<String,String>> dataList= null;
		//记录对账结果；
		boolean checkFlag = true;
		//用于记录更新数据库的条数
		int updateCount= 0;
		//用于记录宝易互通返回数据的总条数
		int umbTotalCount = 0 ;

		try {
			//获取流水号
			String flowNo = context.getFieldStr(BusinessMessageFormateConstant.UMB_BUSS_FLOW_NO,CommonContext.SCOPE_GLOBAL); 
			
			dataList=(List<Map<String,String>>)context.getObj(BusinessMessageFormateConstant.UMB_CP0023_RSP_CYCLE_LABLE,CommonContext.SCOPE_GLOBAL);
			
			//返回数据中的循环标签，每个循环包含一个商户的交易信息
			for(Map<String,String> map : dataList){
				//获取商户号
				String merchantNo = map.get(BusinessMessageFormateConstant.UMB_CP0023_SUB_MERCHANT_NO);
				//获取宝易互通返回的单个商户的记录数
				String totalNum = map.get(BusinessMessageFormateConstant.UMB_CP0023_TOTAL_NUM);
				//获取数据摘要用于校验数据
				String hashCode = map.get(BusinessMessageFormateConstant.UMB_CP0023_HASH_CODE);
				//获取该商户的交易数据
				String data = map.get(BusinessMessageFormateConstant.UMB_CP0023_DATA);
				
				// 判断信息是否为空，如果为空则表示返回报文错误异常
				// update by tianguangzhao 20160524 是否为空的判断交给拆包服务完成 ！
				// if(totalNum ==null || totalNum.trim().equals("") ||
				// hashCode.trim().equals("") || hashCode == null || data ==null
				// || data.trim().equals("")){
				// String message = "get data info error ! please check ! " ;
				// logger.error(message);
				// ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000511);
				// throw new ServiceException(message);
				// }
				
				//将宝易互通返回的记录数转换为int。
				int umbDataCount = 0 ;
				try {
					umbDataCount = Integer.parseInt(totalNum);
					//总条数加
					umbTotalCount += umbDataCount;
				} catch (NumberFormatException e1) {
					// 如果记录数转换为int失败，则说明响应报文有误！
					String message = "can not format totalnum to int ! please check ! ";
					logger.error(message);
					//update by tianguangzhao 20160524 修改为不注入错误码，交给上游处理
					//ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000546);
					throw new ServiceException(message);
				}
				
				//首先校验数据是否完整
				boolean isComplete = false;
				
				try {
					isComplete = checkDataIntegrity(hashCode,data);
				} catch (ServiceException e) {
					logger.error("checkDataIntegrity error !",e);
				    //如果校验数据完整性时发生异常则无法再向下进行，注入错误码（）
					//update by tianguangzhao 20160525 改为不注入错误码 有上游处理！
					//ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000537);
					throw e;
				}
				//判断校验是否通过
				if(!isComplete){
					String message = "check data complete error !";
					logger.error(message);
					//如果程序运行正常而校验位通过，则说明响应报文存在问题,注入错误码(响应报文错误)
					//update by tianguangzhao 20160524 错误码改为 “响应报文校验失败”
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000537,context);
					throw new ServiceException(message);
				}
				// 从返回信息中获取需要对账的信息
				List<CheckingPayFlowBean> listFromMessage = getDataFromMessage(data);
			    //从数据库中获取数据
				List<CheckingPayFlowBean> listFromdatabase = readPayFlowFromDatabase(trandate,merchantNo);
				boolean isExist = false ;
				String errorType = "";
				//进行对账交易,首先判断宝易互通返回的数据在我方数据库中是否存在
				for(CheckingPayFlowBean umbData : listFromMessage){
				    isExist = false ; 
				    errorType = BCheckErrorList.ERROR_CHANNEL_EXTRA;
					for(CheckingPayFlowBean myData : listFromdatabase){
						if(umbData.equalsWithOutStatus(myData)){
							if(umbData.getStatus().equals(myData.getStatus())){
								isExist = true ;	
							}else{
								errorType = BCheckErrorList.ERROR_STATUS_DIFFERENT;
							}
							//如果两笔数据除了状态其他的都能对上，则可以认为两笔数据世同一笔，此时跳出循环，开始寻找下一条数据！
							break;
						}
					}
					
					//判断是否能找到这条数据
					if(!isExist){
						insertErrorRecord(umbData,flowNo,errorType);
						//如果出现一条数据对账错误，则此次对账失败
						if (checkFlag) {
							checkFlag = false;
						}
					}
				}
				//进行对账，判断我方数据在宝易互通返回的信息中是否存在
				for(CheckingPayFlowBean myData : listFromdatabase){
				    isExist = false ; 
				    errorType = BCheckErrorList.ERROR_PLA_EXTRA;
				    
					for(CheckingPayFlowBean umbData : listFromdatabase){
						if(myData.equalsWithOutStatus(umbData)){
							if(myData.getStatus().equals(umbData.getStatus())){
								isExist = true ;
							}else{
								errorType = BCheckErrorList.ERROR_STATUS_DIFFERENT;
							}
							//如果两笔数据除了状态其他的都能对上，则可以认为两笔数据世同一笔，此时跳出循环，开始寻找下一条数据
							break ;
						}
					}
					
					//判断是否能找到这条数据
					if(!isExist){
						insertErrorRecord(myData,flowNo,errorType);
						//如果出现一条数据对账错误，则此次对账失败
						if (checkFlag) {
							checkFlag = false;
						}
					}
				}
			}
			
			try {
				//对账结束后，更新数据库的内容
				if(checkFlag){
					//对账成功，更新支付流水表中的状态为对账成功
					updateCount= updatePayFlowCheckingStatusByTranDate(trandate,BPaymentFlow.CHECKING_SUCCESS);
					//判断更新条数是否正确
					if(!(updateCount == umbTotalCount)){
						//如果更新的条数和宝易互通返回的总条数不一致则，说明更新操作出现异常,注入错误码（更新数据库错误）
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
						String message = "update data error ! update count = ["+updateCount+"] , right num = ["+umbTotalCount+"] !";
						logger.error(message);
						throw new ServiceException(message);
					}
				}else{
					//如果失败，更新支付流水表中的状态为对账失败,由于对账失败，双方的记录并不一致，无法判断条数是否正确
					updatePayFlowCheckingStatusByTranDate(trandate,BPaymentFlow.CHECKING_FAILED);
				}
			} catch (ServiceException e) {
			   //此处抛出异常是因为更新数据库失败
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518,context);
				throw e;
			}
		} catch (ServiceException e) {
			//如果程序抛出异常，则表示此次对账失败，更新对账状态为失败，
			try {
				updatePayFlowCheckingStatusByTranDate(trandate,BPaymentFlow.CHECKING_FAILED);
			} catch (ServiceException e1) {
				// 更新数据库失败，注入错误码（更新数据库错误）
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw e1;
			}
		    throw e;
		}

		return checkFlag;
	}

	/**
	 * 从数据库中读取对账信息
	 *
	 * @param trandate
	 *            交易日期 ,
	 * @return
	 * @throws HandlerException
	 */
	private List<CheckingPayFlowBean> readPayFlowFromDatabase(String trandate,String merchantNo) throws ServiceException {
		List<CheckingPayFlowBean> list = new ArrayList<CheckingPayFlowBean>();
		BPaymentFlowData bpfd = new BPaymentFlowData();
		List<Map<String, String>> listAllInfo = null;
		try {
			listAllInfo = bpfd.getInfoByTrandateAndMerchantNo(trandate,merchantNo);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		}
		
		//判断是否获取到数据库中的数据。如果宝易互通返回了数据，但是我们库中不存在，则说明数据有误，对账失败
		if (listAllInfo == null || listAllInfo.size() == 0) {
			String message = "no need to check pay flow info ! list size = 0 ";
			logger.info(message);
			throw new ServiceException(message);
		} else {
			try {
				for (Map<String, String> map : listAllInfo) {
					CheckingPayFlowBean cpfb = new CheckingPayFlowBean();
					String tranDate = map.get("TRANDATE") == null ? "" : map.get("TRANDATE");
					//update by tianguangzhao 20160509 对宝易互通而言，订单号是我们的支付流水号 PAYORDERID
					//String orderid = map.get("ORDERID") == null ? "" : map.get("ORDERID");
					String orderid = map.get("PAYORDERID") == null ? "" : map.get("PAYORDERID");
					//update by tianguangzhao 宝易互通不返回 支付流水号，
					//String payjnlno = map.get("PAYJNLNO") == null ? "" : map.get("PAYJNLNO");
					String txnAmt = map.get("TXNAMT") == null ? "0" : map.get("TXNAMT");
					String currency = map.get("CURRENCY") == null ? "" : map.get("CURRENCY");
					String status = map.get("PAYSTATUS") == null ? "" : map.get("PAYSTATUS");
					String accountNo = map.get("PAYCARD") == null ? "" : map.get("PAYCARD");
					cpfb.setCurrency(currency);
					cpfb.setOrderid(orderid);
					//cpfb.setPayjnlno(payjnlno);
					cpfb.setStatus(status);
					cpfb.setTranDate(tranDate);
					cpfb.setTxnAmt(new BigDecimal(txnAmt));
					cpfb.setAccountNo(accountNo);
					
					if (logger.isDebugEnabled()) {
						logger.debug("get  database data success ! " + cpfb.toString());
					}
					list.add(cpfb);
				}
			} catch (Exception e) {
				logger.error("data error ! ",e);
				throw new ServiceException(e);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("get data from database success ! list size = [" + list.size() + "]");
			}
		}

		return list;
	}

	/**
	 * 对账结束后操作，将对账结果插入对账登记表
	 * 
	 * @throws ServiceException
	 */
	private void endChecking(String accountDate,String jnlno, boolean flag,boolean isRecheck) throws ServiceException {
		
		//将data初始放在此处，是为了方便在finally里释放数据库连接 
		BCheckStatusData bucld = new BCheckStatusData();
		try {
			bucld.getConnection();
			
			//update by tianguangzhao 20160612 首先判断是否是重新发起的对账，如果是，先将上次对账的记录较为“已处理”
			if(isRecheck){
				bucld.updateBeforeRecordIsHandled(accountDate);
			}
			
			//开始封装数据	
			BCheckStatus bucl = new BCheckStatus();
			String trandate = PlatDateParamData.getInstance().getPlatDate();
			String accountdate = accountDate;
			String checktype = BCheckStatus.CHECK_PAY_FLOW;
			String status = (flag == true ? BCheckStatus.STATUS_SUCCESS : BCheckStatus.STATUS_ERROR);
			String isHandled = (flag == true ? BCheckStatus.HANDLED_TRUE : BCheckStatus.HANDLED_FALSE);
			String trantime = TimeUtil.getCurrentTime(BusinessConstantField.PLA_TIME_FORMATE);
			bucl.setAccountdate(accountdate);
			bucl.setChecktype(checktype);
			bucl.setJnlno(jnlno);
			bucl.setStatus(status);
			bucl.setTrandate(trandate);
			bucl.setTrantime(trantime);
			bucl.setIsHandled(isHandled);
			//对账渠道设为宝易互通
			String chkchnl = bucl.CHANNEL_UMB;
			bucl.setChecknl(chkchnl);
	        //插入数据库
			bucld.insertRecord(bucl);
		} catch (HandlerException e) {
			logger.error("update checking status fail when check end !", e);
			throw new ServiceException(e);
		}finally{
			//释放资源
			DataHandlerUtil.releaseSource(bucld);
		}
	}

	/**
	 * 校验数据完整性
	 * @param umbhashCode 数据摘要
	 * @param data 原始数据
	 * @return
	 * @throws ServiceException 
	 */
	private boolean checkDataIntegrity(String umbhashCode,String data) throws ServiceException{
		boolean flag = false;

		//用于保存最终计算得到的hashcode值
		String myKeywords = "";

		try {
			//首先将data数据还原
			//base64解码
			byte[] bytes = Base64Util.decode(data.getBytes());
			//解压
    		byte[] orgBytes = GzipUtil.uncompressToBytes(bytes);
    		//计算hash值 
            ByteArrayInputStream in = new ByteArrayInputStream(orgBytes);
            myKeywords = DigestUtils.sha256Hex(in);
            
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
		//比较hash值是否相同,相同则返回true
		if (myKeywords.equals(umbhashCode)) {
			flag = true;
		}
		if(logger.isDebugEnabled()){
			logger.debug("myKeywords =["+myKeywords+"] , umbhashCode=["+umbhashCode+"] , compare result is =["+flag+"] !");
		}
		return flag;
	}
	
	/**
	 * 将对账时发现的异常数据，插入到数据库中
	 * @param checkingPayFlowBean 包含有对账信息的实体类
	 * @param flag 标识，用于标注该笔记录是我方少数据还是多数据
	 * @throws ServiceException 
	 */
	private void insertErrorRecord(CheckingPayFlowBean checkingPayFlowBean,String flowNo,String errorType) throws ServiceException{
		//开始封装记录信息
		BCheckErrorList bCheckErrorList = new BCheckErrorList();
		
		bCheckErrorList.setAccountdate(checkingPayFlowBean.getTranDate());
		bCheckErrorList.setChkchnl(BCheckErrorList.CHANNEL_UMB);
		// 处理状态设为待处理
		bCheckErrorList.setChkerrdealsts(BCheckErrorList.DEAL_WAITING);
		// 处理方式，为预留字段，暂时不插入数值
		// bCheckErrorList.setChkerrdealtyp(chkerrdealtyp);
		// 差错类型
		bCheckErrorList.setChkerrtyp(errorType);
		// 合作渠道流水号，（宝易互通返回的流水号就是我方流水号）
		bCheckErrorList.setChnljnlno(flowNo);
		// 处理日期,在手动处理异常时插入
		// bCheckErrorList.setDealdate(dealdate);
		// 取消差错原因（手动处理时插入）
		// bCheckErrorList.setErrcancelmark(errcancelmark);
		bCheckErrorList.setOrderid(checkingPayFlowBean.getOrderid());
		// 对账日期，获取当前日期
		String trandate = TimeUtil.getCurrentTime(BusinessConstantField.PLA_DATE_FORMATE);
		bCheckErrorList.setTrandate(trandate);
			
		//将信息插入数据库中
		BCheckErrorListData bCheckErrorListData = new BCheckErrorListData();
		try {
			bCheckErrorListData.getConnection();
			bCheckErrorListData.insertRecord(bCheckErrorList);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		} finally {
			// 释放资源
			DataHandlerUtil.releaseSource(bCheckErrorListData);
		}
		
	}
	
	
	/**
	 * 根据交易日期更新支付流水表中的对账状态信息
	 * 
	 * @param tranDate
	 * @param checkingStatus
	 * @throws ServiceException 
	 */
	private int updatePayFlowCheckingStatusByTranDate(String trandate,String checkingStatus) throws ServiceException{
		int updateCount = 0;
		BPaymentFlowData bPaymentFlowData = new BPaymentFlowData();
		try {
			bPaymentFlowData.getConnection();
			//调用data类的方法更新数据内容
			updateCount = bPaymentFlowData.updateStatusByTrandate(trandate,checkingStatus);
		} catch (HandlerException e) {
			throw new ServiceException(e);
		} finally {
			//释放资源
			DataHandlerUtil.releaseSource(bPaymentFlowData);
		}

		return updateCount;
	}
}
