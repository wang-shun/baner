package com.ztkx.cbpay.business.serviceimp.PAB.FILE01;

import java.sql.Connection;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.enums.FileStatusEnum;
import com.ztkx.cbpay.business.handledata.BTransferFileInfoData;
import com.ztkx.cbpay.business.handledata.DataHandlerUtil;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BusinessService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.db.DBUtil;

/**
 * 将文件状态修改为开始上传
 * @author zhangxiaoyun
 * 2016年3月24日 下午2:29:25
 * <p>Company:ztkx</p>
 */
public class UpdateFileStatus implements BusinessService{

	Logger logger = Logger.getLogger(UpdateFileStatus.class);
	@Override
	public CommonContext service(CommonContext context) throws ServiceException {
		
		//文件日期
		String transferDate = context.getFieldStr("FileDate", CommonContext.SCOPE_GLOBAL);
		//文件名称
		String fileName = context.getFieldStr("FileName", CommonContext.SCOPE_GLOBAL);
		//购汇批次号
		String TradeSn = context.getFieldStr("TradeSn", CommonContext.SCOPE_GLOBAL);
		
		
		BTransferFileInfoData dataHandler = new BTransferFileInfoData();
		Connection conn = null;
		try {
			conn = dataHandler.getConnection();
			conn.setAutoCommit(false);
			dataHandler.setRelaseConn(false);
		
			//如果文件状态为以创建,将状态修改为上传
			
			String sqlStatement = "update b_transfer_file_info set  filestatus = ? where transfer_date = ? and filename = ? and seqbatchno = ? and filestatus=?";
			LinkedHashMap<String, String> paraMap = new LinkedHashMap<String, String>();
			paraMap.put("filestatus", FileStatusEnum.UPLOADING.getCode());
			paraMap.put("transfer_date", transferDate);
			paraMap.put("filename", fileName);
			paraMap.put("seqbatchno", TradeSn);
			paraMap.put("filestatus_where", FileStatusEnum.CREATED.getCode());
			int res = dataHandler.executeUpdate(sqlStatement, paraMap);
			if(res == 1){
				logger.info("file status update success");
			}else{
				logger.error("update record row error res is ["+res+"]");
				ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000518, context);
				throw new ServiceException();
			}
			DBUtil.commit(dataHandler.connection);
		} catch (Exception e) {
			logger.error("update data fail",e);
			ContextUtil.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000516, context);
			DataHandlerUtil.rollbakConn(conn);
			throw new ServiceException(e);
		}finally{
			DataHandlerUtil.releaseSource(dataHandler);
		}
		return context;
	}

}
