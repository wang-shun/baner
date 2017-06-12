package com.ztkx.cbpay.business.serviceimp.PAB.FBS005;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BMerchantOrder;
import com.ztkx.cbpay.business.bean.BSellExgDet;
import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.DownUploadEnum;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.enums.FileTypeEnum;
import com.ztkx.cbpay.business.handledata.BMerchantInfoData;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.business.handledata.BUserInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.business.serviceimp.PAB.PABBusiUtil;
import com.ztkx.cbpay.business.util.FileUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.flowno.FlowNoPoolManager;
import com.ztkx.cbpay.platformutil.ftp.FtpClientUtil;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 3.生成付汇请求文件
 * 4.登记文件表
 * 文件名:PAB_FH_yyyyMMdd+六位顺序号.txt
 * 文件字段分隔符：|$|
 * @author zhangxiaoyun
 * 2016-3-24 15:16:09
 * <p>Company:ztkx</p>
 */
public class GeneratePaymentFile implements BusinessService{

	Logger logger = Logger.getLogger(GeneratePaymentFile.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		List<BMerchantOrder> listMapData = (List<BMerchantOrder>)context.getObj(BusinessConstantField.MERCHANT_ORDER_OBJ);
		
		if(listMapData == null || listMapData.size()<=0){
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			logger.error("business service exec exception ");
			throw new ServiceException("business service exec exception");
		}
		
		/**
		 * 准备组包数据
		 */
		String paymentdate = TimeUtil.getCurrentTime("yyyyMMdd");	//付汇日期
		String paymenttime = TimeUtil.getCurrentTime("HHmmss");		//付汇时间
		String fileName ="MS_PAB_FH_"+paymentdate+FlowNoPoolManager.getInstance().getSequence()+".txt";
		//付汇批次号
		String TradeSn = PABBusiUtil.getTradeSn();
		String EX_SEQNOPRE = TimeUtil.getCurrentTime(null);
		String seqPre = "ms"+EX_SEQNOPRE;
		//商户号
		String merchantid = context.getFieldStr("merchantId", CommonContext.SCOPE_GLOBAL);
		logger.info("fileName ["+fileName+"]");
		//becif号
		String custCode = BServerParamData.getInstance().getParamsValue(context.getSDO().serverId, BusinessConstantField.PAB_BECIF).getParavalue();
		BUserInfoData  bUserInfoData = new BUserInfoData();
		bUserInfoData.setRelaseConn(false);		//不释放链接
		Map<String,String> payUserInfo = null;
		//用户信息的缓存map
		Map<String,Map<String,String>> userInfoMap = new HashMap<String, Map<String,String>>();
		
		//商户信息
		Map<String,String> merchantInfo = queryMerchantInfo(context, merchantid);
		List<String> fileContent = new ArrayList<String>();
		
		//付汇明细表信息
		List<BSellExgDet> sellExgDetList = new ArrayList<BSellExgDet>();
		//汇款金额
		BigDecimal totalAmt = new BigDecimal(0);
		BTransferFileInfoData dataHandler = null;
		try{
			for (BMerchantOrder order : listMapData) {
				StringBuilder sb = new StringBuilder();	//写文件内容
				/**
				 * 客户号 指令唯一识别码  子客户号   汇款人类型   付款人证件号   账号  付款人名称  收款方商户号名称 汇款币种 汇款金额 
				 * 指令唯一识别码? 订单号? 收款人常驻国家 付款类型 交易编码 交易附言   是否保税货物项下付款     合同号 发票号 申请人 申请人电话 
				 */
				sb.append(custCode).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append(TradeSn).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append(merchantid).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append("D").append(BusinessConstantField.PAB_FILE_SEPERATOR);
				String PURCHASERID = order.getPurchaserid();//购买者标示
				
				if(userInfoMap.get(PURCHASERID) == null){
					payUserInfo = queryPayUserInfo(context, merchantid, bUserInfoData, PURCHASERID);
					userInfoMap.put(PURCHASERID, payUserInfo);
				}else{
					payUserInfo = userInfoMap.get(PURCHASERID);
				}
				String idNo = payUserInfo.get("IDNO");
				sb.append(idNo).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append(order.getPaycard()).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				//付款人名
				String payerName = payUserInfo.get("REALNAME");
				sb.append(payerName).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				//收款方商户号名称     汉字字段需要转码 
				String payeeName = merchantInfo.get("MERCHANTNAME");
				sb.append(payeeName).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				//币种
				sb.append(order.getCurrency()).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				//金额
				sb.append(order.getPurchaseamount().toString()).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				//计算付汇总金额
				totalAmt = totalAmt.add(order.getPurchaseamount());
				String PAY_SEQNO = seqPre+FlowNoPoolManager.getInstance().getSequence();
				sb.append(PAY_SEQNO).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append(order.getOrderid()).append(BusinessConstantField.PAB_FILE_SEPERATOR);
				sb.append(merchantInfo.get("COUNTRY_CODE")).append(BusinessConstantField.PAB_FILE_SEPERATOR);//币种
				sb.append("A").append(BusinessConstantField.PAB_FILE_SEPERATOR);//预付货款
				sb.append(order.getTradeCode()).append(BusinessConstantField.PAB_FILE_SEPERATOR);//一般贸易
				sb.append(order.getProductDesc()).append(BusinessConstantField.PAB_FILE_SEPERATOR);//一般贸易
				sb.append(order.getIsRef()).append(BusinessConstantField.PAB_FILE_SEPERATOR);//是否在保税区项下付款
				sb.append(merchantInfo.get("CONTRACT_NO")).append(BusinessConstantField.PAB_FILE_SEPERATOR);//合同号
				sb.append(order.getOrderid()).append(BusinessConstantField.PAB_FILE_SEPERATOR);//发票号   暂时用订单号代替
				sb.append(merchantInfo.get("MERCHANT_PRINCIPAL")).append(BusinessConstantField.PAB_FILE_SEPERATOR);//商户联系人
				sb.append(merchantInfo.get("PRINCIPAL_TEL"));//商户联系人电话联系人电话	最后一个字段不能有分隔符
				String fileRow = new String(sb.toString());
				sb = null;
				fileContent.add(fileRow);
				logger.debug("file row ["+fileRow+"]");
				
				
				BSellExgDet sellExgDet = new BSellExgDet();
				sellExgDet.setPaybatno(TradeSn);
				sellExgDet.setPaydate(paymentdate);
				sellExgDet.setPaySeqno(PAY_SEQNO);
				sellExgDet.setMerchantid(merchantid);
				sellExgDet.setPayerid(idNo);
				sellExgDet.setPayeraccount(order.getPaycard());
				sellExgDet.setPayername(payerName);
				sellExgDet.setRemitCcy(merchantInfo.get("CURRENCY_TYPE"));
				sellExgDet.setRemitAmt(order.getPurchaseamount());
				sellExgDet.setOrderid(order.getOrderid());
				sellExgDet.setPayeename(payeeName);
				sellExgDet.setPayeecountrycode(merchantInfo.get("COUNTRY_CODE"));
				sellExgDet.setPayType("A");
				sellExgDet.setTranCode(order.getTradeCode());
				sellExgDet.setTranDesc(order.getProductDesc());
				sellExgDet.setIsRef(order.getIsRef());
				sellExgDet.setContractNo(merchantInfo.get("CONTRACT_NO"));
				sellExgDet.setInvoiceNo("");//发票号
				sellExgDet.setApplicant(merchantInfo.get("MERCHANT_PRINCIPAL"));
				sellExgDet.setApplicantTel(merchantInfo.get("PRINCIPAL_TEL"));
				sellExgDetList.add(sellExgDet);
			}
			logger.info("start write payment file");
			FileUtil.writeFile(BaseConfig.getConfig(ConstantConfigField.LOCAL_FILE_PATH)+File.separator+fileName, fileContent,context.getSDO().enCodeing);
			FtpClientUtil.uploadFile(fileName);
			//清理内存
			fileContent.clear();
			fileContent = null;
			
			dataHandler = new BTransferFileInfoData();
			//登记文件表
			BTransferFileInfo bTransferFileInfo = new BTransferFileInfo();
			bTransferFileInfo.setTransferDate(paymentdate);
			bTransferFileInfo.setTransferTime(paymenttime);
			bTransferFileInfo.setSeqbatchno(TradeSn);
			bTransferFileInfo.setFilename(fileName);
			bTransferFileInfo.setCount(listMapData.size());
			bTransferFileInfo.setFiletype(FileTypeEnum.FHFILE.getCode());
			bTransferFileInfo.setTransferType(DownUploadEnum.UPLOAD.getCode());//上传
			bTransferFileInfo.setFilestatus(FileStatusEnum.CREATED.getCode());
			dataHandler.setConnection(bUserInfoData.getConnection());
			logger.info("start register B_TRANSFER_FILE_INFO table ["+bTransferFileInfo+"]");
			dataHandler.insertData(bTransferFileInfo);	
			
			
			/**
			 * 开始准备响应组包数据
			 */
			context.setObj("totalAmt", totalAmt, CommonContext.SCOPE_GLOBAL);//总金额
			context.setFieldStr("TradeSn", TradeSn, CommonContext.SCOPE_GLOBAL);//付汇批次号
			context.setFieldStr("paymentdate", paymentdate, CommonContext.SCOPE_GLOBAL);//付汇日期
			context.setFieldStr("paymenttime", paymenttime, CommonContext.SCOPE_GLOBAL);//付汇批批次号
			context.setFieldStr("FileName", fileName, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("FileDate", paymentdate, CommonContext.SCOPE_GLOBAL);
			context.setObj("merchantInfo", merchantInfo);//商户信息
			context.setObj("sellExgDetList", sellExgDetList);//付汇明细信息
		}catch(Exception e){
			logger.error("business service exe  fail",e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bUserInfoData);
			DataHandlerUtil.releaseSource(dataHandler);
		}
		
		
		
		return context;
	}
	
	/**
	 * 查询用户信息
	 * @param context
	 * @param merchantid
	 * @param bUserInfoData
	 * @param PURCHASERID
	 * @return
	 * @throws ServiceException
	 * 2016年3月24日 下午5:07:49
	 * @author zhangxiaoyun
	 */
	private Map<String, String> queryPayUserInfo(CommonContext context,
			String merchantid, BUserInfoData bUserInfoData, String PURCHASERID)
			throws ServiceException {
		//查询付款用户信息
		try {
			List<Map<String,String>> userInfoResList = bUserInfoData.getUserInfo(merchantid, PURCHASERID);
			if(userInfoResList==null || userInfoResList.size()==0){
				throw new HandlerException("query user info fail");
			}
			return userInfoResList.get(0);
			//付款人证件号
			
		} catch (HandlerException e) {
			logger.error("query user info fail",e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 查询商户信息
	 * @param context
	 * @param merchantid
	 * @return
	 * @throws ServiceException
	 * 2016年3月24日 下午5:07:59
	 * @author zhangxiaoyun
	 */
	private Map<String, String> queryMerchantInfo(CommonContext context,String merchantid)
			throws ServiceException {
		//查询商户信息
		BMerchantInfoData bMerchantInfoData = new BMerchantInfoData();
		bMerchantInfoData.setRelaseConn(true);
		try {
			List<Map<String,String>> merchantResList = bMerchantInfoData.getBMerchantInfo(merchantid);
			if(merchantResList==null || merchantResList.size()==0){
				throw new HandlerException("query user info fail");
			}
			return merchantResList.get(0);
			
		} catch (HandlerException e) {
			logger.error("query merchant info fail",e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000515);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bMerchantInfoData);
		}
	}

}
