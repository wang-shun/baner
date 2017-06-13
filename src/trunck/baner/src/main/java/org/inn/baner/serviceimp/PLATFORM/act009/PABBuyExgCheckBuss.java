package org.inn.baner.serviceimp.PLATFORM.act009;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BBuyExgCtrl;
import com.ztkx.cbpay.business.bean.BCheckErrorList;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.BuyExgChannelEnum;
import com.ztkx.cbpay.business.enums.CheckErrTypeEnum;
import com.ztkx.cbpay.business.handledata.BBuyExgCtrlData;
import com.ztkx.cbpay.business.handledata.BCheckErrorListData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.ftp.FtpClientUtil;

/**
 * 对账主业务
 * 登记购汇对账状态
 *以文件为主键，检索数据库中的内容
 *数据库为主键检索文件中的内容
 * @author zhagnxiaoyun
 * 2016年4月25日14:27:04
 *
 */
public class PABBuyExgCheckBuss implements BusinessService {

	Logger logger = Logger.getLogger(PABBuyExgCheckBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		String FileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		String filePath = BaseConfig.getConfig(ConstantConfigField.LOCAL_FILE_PATH);
		logger.info("FileName ["+FileName+"]");
		BCheckErrorListData bCheckErrorListData = null;
		BBuyExgCtrlData bBuyExgCtrlHandler = null;
		try {
			//ftp从远端地址下载文件到本地目录
			FtpClientUtil.downloadFile(FileName);
			
			//读取文件内容
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath + File.separator + FileName)));
			Map<String, FileInfo> fileMap = new HashMap<String, FileInfo>();
			List<Method> methodList = new ArrayList<Method>();
			Field[] fields = fields = FileInfo.class.getDeclaredFields(); // 获取文件信息的所有字段

			// 获取所有字段的set方法
			for (Field field : fields) {
				String methodName = "set" + field.getName();
				Method method = FileInfo.class.getDeclaredMethod(methodName,String.class);
				methodList.add(method);
			}

			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split("\\|\\$\\|");
				FileInfo fileInfo = new FileInfo();
				for (int i = 0; i < lineArray.length; i++) {
					Method method = methodList.get(i);
					method.invoke(fileInfo, lineArray[i]);
				}
				fileMap.put(fileInfo.getEX_SEQNO(), fileInfo);
			}
			
			//读取数据库表中上一天的所有交易记录
			String lastDate = PlatDateParamData.getInstance().getLastPlatDate();
			String sqlStatement = "select * from b_buy_exg_ctrl where buydate='"+lastDate+"'";
			bBuyExgCtrlHandler = new BBuyExgCtrlData();
			
			List<BBuyExgCtrl> list = bBuyExgCtrlHandler.executeQueryResBean(sqlStatement, null);
			Map<String,BBuyExgCtrl> buyExgCtrlMap = new HashMap<String, BBuyExgCtrl>();
			//将list转化为map
			for (BBuyExgCtrl bBuyExgCtrl : list) {
				buyExgCtrlMap.put(bBuyExgCtrl.getBuybatno(), bBuyExgCtrl);
			}
			
			List<BCheckErrorList> errList = new ArrayList<BCheckErrorList>();
			
			bCheckErrorListData = new BCheckErrorListData();
			bCheckErrorListData.getConnection();
			
			//开始以银行为准轮询跨境支付平台数据
			for (Map.Entry<String, FileInfo> entry : fileMap.entrySet()) {
				String seqno = entry.getKey();
				FileInfo fileInfo = entry.getValue();
				BBuyExgCtrl bBuyExgCtrl = buyExgCtrlMap.get(seqno);
				if(bBuyExgCtrl==null){
					//记差错表 渠道多账
					BCheckErrorList bCheckErrorList = initCheckErrorBean(lastDate, fileInfo, bBuyExgCtrl,CheckErrTypeEnum.CHANNEL_MUCH.getCode());
					errList.add(bCheckErrorList);
					bCheckErrorListData.insertRecord(bCheckErrorList);
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000523,context);
				}
			}
			
			//以平台数据为准轮询平安银行的对账数据
			for (Map.Entry<String, BBuyExgCtrl> entry : buyExgCtrlMap.entrySet()) {
				String seqno = entry.getKey();
				BBuyExgCtrl bBuyExgCtrl = entry.getValue();
				FileInfo fileInfo = fileMap.get(seqno);
				if(fileInfo==null){
					//记差错表    平台多账				
					BCheckErrorList bCheckErrorList = initCheckErrorBean(lastDate, fileInfo, bBuyExgCtrl,CheckErrTypeEnum.PLAT_MUCH.getCode());
					errList.add(bCheckErrorList);
					bCheckErrorListData.insertRecord(bCheckErrorList);
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000522,context);
				}else if(!bBuyExgCtrl.getProcessStatus().equals(fileInfo.getRET_CODE()) || 		//判断状态
						!bBuyExgCtrl.getClientExchangeRate().toString().equals(fileInfo.getCUST_RATE()) || 	//判断成交汇率
						!bBuyExgCtrl.getBuyCcy().equals(fileInfo.getBUY_CURR())	||	//买入币种
						!bBuyExgCtrl.getSaleAmount().toString().equals(fileInfo.getSELL_AMT()) ||	//卖出金额
						!bBuyExgCtrl.getBuyAmount().toString().equals(fileInfo.getBUY_AMT()))		//买入金额
				{
					//状态错误
					BCheckErrorList bCheckErrorList = initCheckErrorBean(lastDate, fileInfo, bBuyExgCtrl,CheckErrTypeEnum.STATUS_ERR.getCode());
					errList.add(bCheckErrorList);
					bCheckErrorListData.insertRecord(bCheckErrorList);
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000524,context);
				}
			}
			
			if(errList.size()!=0)
			{
				//如果错误列表中有数据
				logger.info("error data size is ["+errList.size()+"]");
				for (int i = 0; i < errList.size(); i++) {
					BCheckErrorList errorInfo = errList.get(i);
					String errorType = errorInfo.getChkerrtyp();
					String statusdesc = errorType.equals(CheckErrTypeEnum.CHANNEL_MUCH.getCode())?CheckErrTypeEnum.CHANNEL_MUCH.getDesc():
										errorType.equals(CheckErrTypeEnum.PLAT_MUCH.getCode())?CheckErrTypeEnum.PLAT_MUCH.getDesc():
											errorType.equals(CheckErrTypeEnum.STATUS_ERR.getCode())?CheckErrTypeEnum.STATUS_ERR.getDesc():"";
					logger.info("buybatno ["+errorInfo.getOrderid()+"] channel sequence ["+errorInfo.getChnljnlno()+"] status is ["+statusdesc+"]");
				}
			}
			
			//准备后面业务服务需要的数据
			context.setObj(BusinessConstantField.MERCHANT_ORDER_OBJ, list);
			
		}catch(Exception e){
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("PABBuyExgCheckBuss is error",e);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bCheckErrorListData);
			DataHandlerUtil.releaseSource(bBuyExgCtrlHandler);
		}
		
		
		logger.info("CheckMerchantPermissionBusinessService is succ");
		return context;
	}
	
	private BCheckErrorList initCheckErrorBean(String lastDate,FileInfo fileInfo, BBuyExgCtrl bBuyExgCtrl,String errorCode) {
		BCheckErrorList bCheckErrorList = new BCheckErrorList();
		bCheckErrorList.setAccountdate(lastDate);
		bCheckErrorList.setChkchnl(BuyExgChannelEnum.PAB.getCode());
		if(bBuyExgCtrl!=null){
			bCheckErrorList.setOrderid(bBuyExgCtrl.getBuybatno());
		}
		if(fileInfo!=null){
			bCheckErrorList.setChnljnlno(fileInfo.getEX_SEQNO());
		}
		bCheckErrorList.setChkerrtyp(errorCode);
		bCheckErrorList.setTrandate(PlatDateParamData.getInstance().getPlatDate());
		return bCheckErrorList;
	}
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		BigDecimal b1 = new BigDecimal("6.2105");
		String b2 = "6.2105";
		System.out.println(b1.toString().equals(b2));
//		Class clazz =  FileInfo.class;
//		Method[]  methods = clazz.getDeclaredMethods();
//		Field[] fields = clazz.getDeclaredFields();
//		for (int i = 0; i < fields.length; i++) {
//			String fieldName = fields[i].getName();
//			String methodName = "set"+fieldName;
//			Method method = clazz.getDeclaredMethod(methodName, String.class);
//			System.out.println(i+method.getName());
//		}
	}
}
