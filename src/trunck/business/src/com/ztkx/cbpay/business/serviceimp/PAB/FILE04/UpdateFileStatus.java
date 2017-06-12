package com.ztkx.cbpay.business.serviceimp.PAB.FILE04;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;

/**
 * 记录文件的随机密码、文件签名、文件hash值、
 * 修改文件的状态为上传成功
 * @author zhangxiaoyun
 * 2016年3月28日 下午4:13:25
 * <p>Company:ztkx</p>
 */
public class UpdateFileStatus implements BusinessService{

	Logger logger = Logger.getLogger(UpdateFileStatus.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		BTransferFileInfoData bTransferFileInfoData = null;
		try {
			String action = context.getFieldStr("Action", CommonContext.SCOPE_GLOBAL);
			String TradeSn = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
			String FileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
			String RandomPwd = context.getFieldStr("RandomPwd", CommonContext.SCOPE_GLOBAL);
			String SignData = context.getFieldStr("SignData", CommonContext.SCOPE_GLOBAL);
			String HashData = context.getFieldStr("HashData", CommonContext.SCOPE_GLOBAL);
			
			logger.info("action ["+action+"]");
			logger.info("TradeSn ["+TradeSn+"]");
			logger.info("FileName ["+FileName+"]");
			logger.info("RandomPwd ["+RandomPwd+"]");
			logger.info("SignData ["+SignData+"]");
			logger.info("HashData ["+HashData+"]");
			
			bTransferFileInfoData = new BTransferFileInfoData();
			String sqlstatement = "update b_transfer_file_info  set  randompwd = ?, hashvalue = ?,  "
					+ "signvalue = ?, filestatus = ? where transfer_date = ? and filename = ? and "
					+ "seqbatchno=?";
			
			LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("randompwd", RandomPwd);
			paramMap.put("hashvalue", HashData);
			paramMap.put("signvalue", SignData);
			byte[] msgHead = context.getByteArray(BusinessConstantField.PAB_MSG_HEAD,CommonContext.SCOPE_GLOBAL);
			String fileStatus = new String(Arrays.copyOfRange(msgHead, 87, 93));//文件上传下载状态
			logger.info("file status is ["+fileStatus+"]");
			if(fileStatus.trim().equals("F0") ){
				if(action.trim().equals("1")){
					//文件上传成功
					paramMap.put("filestatus", FileStatusEnum.UPLOAD_COMPLETE.getCode());
				}else if(action.trim().equals("2")){
					//文件下载成功
					paramMap.put("filestatus", FileStatusEnum.DOWNLOAD_COMPLETE.getCode());
				}
			}else{
				logger.error("file status error");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000527, context);
				throw new ServiceException("business service exec file,file status error");
			}
			paramMap.put("transfer_date", context.getFieldStr(BusinessConstantField.PAB_TRAN_DATE, CommonContext.SCOPE_GLOBAL));
			paramMap.put("filename", FileName);
			paramMap.put("seqbatchno", TradeSn);
			
			bTransferFileInfoData.getConnection();
			bTransferFileInfoData.executeUpdate(sqlstatement, paramMap);
			DataHandlerUtil.commitConn(bTransferFileInfoData.connection);
			
		} catch (Exception e) {
			logger.error("business service exception ",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516,context);
			throw new ServiceException(e);
		}finally{
			bTransferFileInfoData.getConnection();
		}
		return context;
	}

}
