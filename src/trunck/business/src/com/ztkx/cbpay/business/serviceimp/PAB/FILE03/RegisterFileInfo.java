package com.ztkx.cbpay.business.serviceimp.PAB.FILE03;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.DownUploadEnum;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.business.serviceimp.PAB.PABBusiUtil;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 登记文件信息
 * @author zhangxiaoyun
 * 2016-4-25 10:56:10
 * <p>Company:ztkx</p>
 */
public class RegisterFileInfo implements BusinessService{

	Logger logger = Logger.getLogger(RegisterFileInfo.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		String transferDate = context.getFieldStr(BusinessConstantField.PAB_TRAN_DATE, CommonContext.SCOPE_GLOBAL);
		String transferTime = context.getFieldStr(BusinessConstantField.PAB_TRAN_TIME, CommonContext.SCOPE_GLOBAL);
		String fileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		String RandomPwd = context.getFieldStr("RandomPwd", CommonContext.SCOPE_GLOBAL);
		String FileType = context.getFieldStr("FileType", CommonContext.SCOPE_GLOBAL);
		String Count = context.getFieldStr("Count", CommonContext.SCOPE_GLOBAL);
		String TradeSn = PABBusiUtil.getTradeSn();
		
		BTransferFileInfoData bTransferFileInfoData = new BTransferFileInfoData();
		BTransferFileInfo bTransferFileInfo = null;
		try {
			bTransferFileInfoData.getConnection();
			bTransferFileInfo = new BTransferFileInfo();
			bTransferFileInfo.setCount(Integer.parseInt(Count));
			bTransferFileInfo.setFilename(fileName);
			bTransferFileInfo.setFilestatus(FileStatusEnum.DOWNLOADING.getCode());
			bTransferFileInfo.setFiletype(FileType);
			bTransferFileInfo.setRandompwd(RandomPwd);
			bTransferFileInfo.setTransferDate(transferDate);
			bTransferFileInfo.setTransferType(DownUploadEnum.DOWNLOAD.getCode());
			bTransferFileInfo.setTransferTime(transferTime);
			bTransferFileInfo.setSeqbatchno(TradeSn);
			bTransferFileInfoData.insertData(bTransferFileInfo);
		} catch (HandlerException e) {
			logger.error("business  service  exe fail",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000517, context);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(bTransferFileInfoData);
		}
		
		/**
		 * 准备组包数据
		 */
		context.setFieldStr("TradeSn", TradeSn, CommonContext.SCOPE_GLOBAL);
//		context.setFieldStr("FilePath", BaseConfig.getConfig(BusinessConstantField.CHECKING_FILE_PATH), CommonContext.SCOPE_GLOBAL);
		return context;
	}
}
