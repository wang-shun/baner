package com.ztkx.transplat.container.service.serviceimp;

import java.io.UnsupportedEncodingException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.apache.log4j.Logger;

import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.initdata.PlatDateParamData;
import com.ztkx.transplat.container.initdata.TransMgsLogStampData;
import com.ztkx.transplat.container.initdata.TransMsgLogData;
import com.ztkx.transplat.container.service.intface.BaseService;
import com.ztkx.transplat.platformutil.baseconfig.BaseConfig;
import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * 此服务将收到的报文保存到数据库中
 * 
 * @author tianguangzhao
 *
 */
public class RegisterMsgBaseService implements BaseService {
	static Logger logger = Logger.getLogger(RegisterMsgBaseService.class);

	@Override
	public CommonContext service(CommonContext context) {
		if (logger.isDebugEnabled()) {
           logger.debug("RegisterMsgBaseService start ! ");
		}
		// 判断是否需要将原始报文插入数据库中
		String transLogSwich = BaseConfig.getConfig(ConstantConfigField.TRANS_LOG_SWITCH);

		// 判断是否需要更新交易进度
		String transTimestampSwich = BaseConfig.getConfig(ConstantConfigField.TRANS_TIMESTAMP_SWITCH);

		if (logger.isDebugEnabled()) {
			logger.debug(ConstantConfigField.TRANS_LOG_SWITCH + " ["+ transLogSwich+"] "+ ConstantConfigField.TRANS_TIMESTAMP_SWITCH + " ["+ transTimestampSwich+"]");
		}

		try {
			// 如果标志值是on，则需要将原始报文插入数据库中
			if ("on".equals(transLogSwich)) {
				insertTransLog(context);
			}

			// 如果标志值是on，则需要将原始报文插入数据库中
			if ("on".equals(transTimestampSwich)) {
				updateTransSchedule(context);
			}
		} catch (Throwable e) {
			logger.error("Register message error !", e);
			return context;
		} finally {
			// 提交消息确认到ActiveMQ
			try {
				commitMessage(context);
			} catch (Exception e) {
				logger.error("commit BytesMessage error !", e);
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000012);
			}
		}
		if (logger.isDebugEnabled()) {
	           logger.debug("RegisterMsgBaseService success ! ");
			}
		return context;
	}

	/**
	 * 将交易信息插入数据库表中，表名：TransMsgLogData
	 * 
	 * @param context
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private void insertTransLog(CommonContext context) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("start insertTransLog ! ");
		}
		
		// 根据字段名称获取context中的相关报文
		String flowNo = context.getSDO().flowNo;
		// String tranDate = context.getSDO().flatDate;
		String tranDate = PlatDateParamData.getInstance().getPlatDate();
		String msgOrder = context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);
		String message = context.getOrginalField();

		TransMsgLogData dataHandler = new TransMsgLogData();
		try {
			dataHandler.insertLog(flowNo, tranDate, msgOrder,message.getBytes(context.getSDO().enCodeing));
		} catch (Exception e) {
			logger.error("insert trans logs error !", e);
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000012);
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("insertTransLog success ! ");
		}
	}

	/**
	 * 更新交易进度到数据库中
	 * 
	 * @param context
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private void updateTransSchedule(CommonContext context) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("updateTransSchedule start ! ");
		}
		// 根据字段名称获取context中的相关报文
		String flowNo = context.getSDO().flowNo;
		String msgOrder = context.getFieldStr(ContainerConstantField.MSG_ORDER,CommonContext.SCOPE_GLOBAL);

		// 业务流水号待定
		String trandate = PlatDateParamData.getInstance().getPlatDate();

		TransMgsLogStampData dataHandler = new TransMgsLogStampData();
		dataHandler.getConnection();
		try {
			if (msgOrder.equals(ContainerConstantField.MSG_ORDER_FIRST)) {
				dataHandler.insert(flowNo, trandate);
			} else {
				dataHandler.update(flowNo, trandate, msgOrder);
			}
		} catch (Exception e) {
			logger.error("update trans timestamp error !", e);
			context.setErrorCode(ErrorCodeConstant.BASE_PLA000012);
			throw e;
		}finally{
			if(dataHandler!=null)
				dataHandler.relaceResource();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("updateTransSchedule success ! ");
		}
	}

	/**
	 * 提交消息确认到ActiveMQ
	 * 
	 * @param context
	 * @throws JMSException
	 */
	private void commitMessage(CommonContext context) throws JMSException {

		boolean flag = false;

		// 首先判断是否需要提交确认消息
		String MsgOrder = context.getFieldStr(ContainerConstantField.MSG_ORDER,
				CommonContext.SCOPE_GLOBAL);
		String constantName = BaseConfig
				.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME);
		if (constantName.equals(ConstantConfigField.CONTAINER_NAME_IN)
				&& MsgOrder.equals(ContainerConstantField.MSG_ORDER_FIRST)) {
			flag = true;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("commit message  flag =[" + flag + "]");
			logger.debug(" MsgOrder =[" + MsgOrder + "]");
			logger.debug(" constantName =[" + constantName + "]");
		}

		if (flag) {
			BytesMessage bm = (BytesMessage) context.getObj(ContainerConstantField.JMS_MSG_OBJ);
			bm.acknowledge();

			if (logger.isDebugEnabled()) {
				logger.debug("commit message success !");
			}
		}
	}
}
