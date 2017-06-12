package com.ztkx.cbpay.business.serviceimp.PAB.FBS002;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 为fbs002准备数据
 * @author zhangxiaoyun
 * 2016年3月28日 下午4:13:25
 * <p>Company:ztkx</p>
 */
public class PreFileUploadNotice implements BusinessService{

	Logger logger = Logger.getLogger(PreFileUploadNotice.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		String TradeSn = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
		String FileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		String transer_date = context.getFieldStr(BusinessConstantField.PAB_TRAN_DATE,CommonContext.SCOPE_GLOBAL);
		
		logger.info("TradeSn ["+TradeSn+"]");
		logger.info("FileName ["+FileName+"]");
		logger.info("transer_date ["+transer_date+"]");
		
		BTransferFileInfoData bTransferFileInfoData = new BTransferFileInfoData();
		BTransferFileInfo bTransferFileInfo = null;
		try{
			bTransferFileInfo = bTransferFileInfoData.getTransferFile(transer_date, FileName, TradeSn);
			//准备组包数据
			context.setFieldStr("FileType", bTransferFileInfo.getFiletype(),CommonContext.SCOPE_GLOBAL);
			context.setFieldStr("Count", bTransferFileInfo.getCount().toString(),CommonContext.SCOPE_GLOBAL);
			
		} catch (HandlerException e) {
			logger.error("update data fail",e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516);
			throw new ServiceException(e);
		}finally{
			bTransferFileInfoData.relaceResource();
		}
		
		return context;
	}

}
