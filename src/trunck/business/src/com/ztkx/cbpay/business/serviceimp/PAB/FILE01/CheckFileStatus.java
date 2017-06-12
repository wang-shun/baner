package com.ztkx.cbpay.business.serviceimp.PAB.FILE01;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 判断文件是否存在
 * @author zhangxiaoyun
 * 2016年3月24日 下午2:29:25
 * <p>Company:ztkx</p>
 */
public class CheckFileStatus implements BusinessService{

	Logger logger = Logger.getLogger(CheckFileStatus.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		//文件日期
		String transferDate = context.getFieldStr("FileDate", CommonContext.SCOPE_GLOBAL);
		//文件名称
		String fileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		//购汇批次号
		String TradeSn = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
		BTransferFileInfoData dataHandler = new BTransferFileInfoData();
		try {
			dataHandler.getConnection();
			BTransferFileInfo bTransferFileInfo =  dataHandler.getTransferFile(transferDate, fileName, TradeSn);
			//如果文件不存在，或者状态不正确
			if(bTransferFileInfo ==null || !bTransferFileInfo.getFilestatus().equals(FileStatusEnum.CREATED.getCode())){
				logger.error("file info error or file not exist");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000527, context);
				throw new ServiceException("file info error or file not exist");
			}
		} catch (Exception e) {
			logger.error("business service exception",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(dataHandler);
		}
		//准备组包数据
//		context.setFieldStr("FilePath", BaseConfig.getConfig(BusinessConstantField.CHECKING_FILE_PATH), CommonContext.SCOPE_GLOBAL);
		return context;
	}

}
