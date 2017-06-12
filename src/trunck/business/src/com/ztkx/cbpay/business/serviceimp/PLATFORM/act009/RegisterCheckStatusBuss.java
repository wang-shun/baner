package com.ztkx.cbpay.business.serviceimp.PLATFORM.act009;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BCheckStatus;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BCheckStatusData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.log.FlowNoContainer;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 查询是否存在对账记录
 * 
 * 如果不存在对账记录 ，登记购汇对账状态
 * 如果已经存在对账成功状态，返回错误
 * 如果已经存在对账失败状态，将状态修改为初始状态
 *以文件为主键，检索数据库中的内容
 *数据库为主键检索文件中的内容
 * @author zhagnxiaoyun
 * 2016年4月25日14:27:04
 *
 */
public class RegisterCheckStatusBuss implements BusinessService {

	Logger logger = Logger.getLogger(RegisterCheckStatusBuss.class);

	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		String TradeSn = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
		String FileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		logger.info("TradeSn ["+TradeSn+"] FileName ["+FileName+"]");
		BCheckStatusData bCheckStatusData = null;
		try {
			bCheckStatusData = new BCheckStatusData();
			bCheckStatusData.getConnection();
			//检查是否已经对账
			BCheckStatus checkstatus =  bCheckStatusData.getRecord(PlatDateParamData.getInstance().getLastPlatDate(),BCheckStatus.CHECK_GH,BCheckStatus.CHANNEL_PAB);
			if(checkstatus!=null){
				logger.info("check record alread exist ");
				String status = checkstatus.getStatus();
				if(status.equals(BCheckStatus.STATUS_SUCCESS)){
					//如果对账记录存在且状态为成功，置错误码已经完成对账
					ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000525, context);
					logger.error("check already succ");
					throw new ServiceException("check already succ");
				}else if(status.equals(BCheckStatus.STATUS_ERROR)){
					//如果对账记录存在且状态为失败，将对账记录修改为初始状态
					int res = bCheckStatusData.updateRecord(PlatDateParamData.getInstance().getLastPlatDate(),BCheckStatus.CHANNEL_PAB,BCheckStatus.STATUS_INIT,BCheckStatus.CHECK_GH);
					if(res !=1){
						ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
						logger.error("update B_CHECK_STATUS table fial");
						throw new ServiceException("update B_CHECK_STATUS table fial");
					}
					logger.info("update check record succ");
				}
			}else{
				checkstatus = new BCheckStatus();
				checkstatus.setAccountdate(PlatDateParamData.getInstance().getLastPlatDate());
				checkstatus.setTrandate(TimeUtil.getCurrentTime(null));
				checkstatus.setChecktype(BCheckStatus.CHECK_GH);
				checkstatus.setFilename(FileName);
				checkstatus.setJnlno(FlowNoContainer.getFlowNo());
				checkstatus.setStatus(BCheckStatus.STATUS_INIT);
				checkstatus.setTrantime(TimeUtil.getCurrentTime("HHmmss"));
				checkstatus.setChecknl(BCheckStatus.CHANNEL_PAB);
				checkstatus.setChecktype(BCheckStatus.CHECK_GH);
				bCheckStatusData.insertRecord(checkstatus);
			}
			
		}catch(Exception e){
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			logger.error("getBMerchantInfo is error");
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bCheckStatusData);
		}
		return context;
	}
}
