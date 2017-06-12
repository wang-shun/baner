package com.ztkx.cbpay.business.serviceimp.PAB.FILE04;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.bean.BTransferFileInfo;
import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.DownUploadEnum;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.enums.FileTypeEnum;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 根据文件类型和文件上传下载状态路由出后端服务
 * @author zhangxiaoyun
 * 2016-3-24 15:16:09
 * <p>Company:ztkx</p>
 */
public class RouteByFileType implements BusinessService{

	Logger logger = Logger.getLogger(RouteByFileType.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		String transferDate = context.getFieldStr(BusinessConstantField.PAB_TRAN_DATE, CommonContext.SCOPE_GLOBAL);
		String fileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		String seqbatchno = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
		
		BTransferFileInfoData bTransferFileInfoData = new BTransferFileInfoData();
		BTransferFileInfo bTransferFileInfo = null;
		try {
			bTransferFileInfo = bTransferFileInfoData.getTransferFile(transferDate, fileName, seqbatchno);
			if(bTransferFileInfo == null){
				logger.error("query transferfile info faile bTransferFileInfo is null");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000540, context);
				throw new ServiceException();
			}
			
			
			String fileType = bTransferFileInfo.getFiletype();
			if(fileType.equals(FileTypeEnum.FHFILE.getCode()) 
					&& bTransferFileInfo.getTransferType().equals(DownUploadEnum.UPLOAD.getCode())
					&& bTransferFileInfo.getFilestatus().equals(FileStatusEnum.UPLOAD_COMPLETE.getCode())){
				//如果是付汇文件且状态为上传成功，将服务交易码置为FBS002
				context.getSDO().serverCode="FBS002";
				context.getSDO().serverId="PAB_SVR";
			}
			else if(fileType.equals(FileTypeEnum.GHFILE.getCode()) 
					&& bTransferFileInfo.getTransferType().equals(DownUploadEnum.DOWNLOAD.getCode())
					&& bTransferFileInfo.getFilestatus().equals(FileStatusEnum.DOWNLOAD_COMPLETE.getCode())){
				//如果是购汇明细文件且下载成功，将服务交易码置为
				context.getSDO().serverCode="act009";
				context.getSDO().serverId="PLATFORM_SVR";
			}
			else{
				logger.error("file type is error fileType ["+fileType+"]");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000538, context);
				throw new ServiceException();
			}
			logger.info("serverId [ "+context.getSDO().serverId+" ]  serverCode [ "+context.getSDO().serverCode+" ]");
			
		} catch (HandlerException e) {
			logger.error("business service exe  fail",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw new ServiceException(e);
		} 
		return context;
	}
}
