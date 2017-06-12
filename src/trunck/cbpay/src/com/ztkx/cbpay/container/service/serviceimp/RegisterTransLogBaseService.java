package com.ztkx.cbpay.container.service.serviceimp;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.constant.ContainerConstantField;
import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.initdata.PlatDateParamData;
import com.ztkx.cbpay.container.initdata.ServerInfoData;
import com.ztkx.cbpay.container.initdata.TransLogData;
import com.ztkx.cbpay.container.javabean.ServerInfo;
import com.ztkx.cbpay.container.javabean.TransLog;
import com.ztkx.cbpay.container.service.ServiceException;
import com.ztkx.cbpay.container.service.intface.BaseService;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 此基础服务用于记录交易的日志信息， author tianguangzhao 20160530
 */
public class RegisterTransLogBaseService implements BaseService {
	static Logger logger = Logger.getLogger(RegisterTransLogBaseService.class);

	@Override
	public CommonContext service(CommonContext context) {

		if(logger.isDebugEnabled()){
			logger.debug("Register transLog start ...");
		}
		// 判断是否需要记录信息到数据库中
		String transLogSwich = BaseConfig.getConfig(ConstantConfigField.TRANS_INFO_SWITCH);

		if (logger.isDebugEnabled()) {
			logger.debug(ConstantConfigField.TRANS_INFO_SWITCH + " = "+ transLogSwich);
		}

		try {
			// 如果标志值是on，则需要将原始报文插入数据库中
			if (transLogSwich == null || "on".equals(transLogSwich)) {
				insertTransLog(context);
			}

		} catch (Exception e) {
			logger.error("Register message error !", e);
			//注入错误码"登记交易日志失败"
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000013, context);
			return context;
		} 
		
		if(logger.isDebugEnabled()){
			logger.debug(" Register transLog success ...");
		}
		
		return context;
	}

	/**
	 * 将交易信息保存到数据库中
	 * 
	 * @param context
	 * @throws SerialException 
	 */
	private void insertTransLog(CommonContext context) throws ServiceException {
		
		TransLogData transLogData = new TransLogData();
		TransLog transLog = new TransLog();
		//从context中取出信息，进行封装
		String flowNo = context.getSDO().flowNo;
		transLog.setFlowNo(flowNo);
		
		//获取平台日期和时间
		transLog.setTranDate(PlatDateParamData.getInstance().getPlatDate());
		transLog.setTranTime(TimeUtil.getCurrentTime(ContainerConstantField.PLA_TIME_FORMATE));
		
		//封装请求方信息
		String tranCode = context.getSDO().TRANCODE;
		transLog.setTranCode(tranCode);
		String tranFrom = context.getSDO().tranFrom;
		transLog.setTranFrom(tranFrom);
		
		//封装服务方信息
		String serverCode = context.getSDO().serverCode;
		transLog.setServerCode(serverCode);
		String serverId = context.getSDO().serverId;
		transLog.setServerId(serverId);
		//封装服务方响应码和响应信息 
		// 先从serverinfo表中获取该服务方的信息,然后取出响应码和响应描述字段的名称
		ServerInfo serverInfo = ServerInfoData.getInstance().getServerInfo(serverId);
		String serverRespcodeName = serverInfo.getRes_code();
		String serverRespDesName = serverInfo.getRes_msg();
		// 从context中获取服务方的响应码
		String serviceResponseCode = context.getFieldStr(serverRespcodeName,CommonContext.SCOPE_GLOBAL);
		String serviceResponseDesc = context.getFieldStr(serverRespDesName,CommonContext.SCOPE_GLOBAL);
		transLog.setServiceResponseCode(serviceResponseCode);
		transLog.setServiceResponseDesc(serviceResponseDesc);
		
		//封装平台响应码
		String platResponseCode = context.getResponseCode() == null ? "" :  context.getResponseCode();
		transLog.setPlatResponseCode(platResponseCode);

		//将信息插入数据库中
		try {
			transLogData.insertLog(transLog);
		} catch (HandlerException e) {
		    throw new ServiceException(e);
		}
	}
}
