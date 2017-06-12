package com.ztkx.cbpay.business.system.handler;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.ztkx.cbpay.business.constant.BusinessConstantField;
import com.ztkx.cbpay.business.constant.BusinessErrorCodeConstant;
import com.ztkx.cbpay.business.initdata.BServerParamData;
import com.ztkx.cbpay.container.HandlerException;
import com.ztkx.cbpay.container.system.SystemHandler;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.time.TimeUtil;

/**
 * 平安银行作为客户端的系统特殊处理
 * @author zhangxiaoyun
 * 2016年3月28日 下午4:48:23
 * <p>Company:ztkx</p>
 */
public class PABCLISpecialHandler implements SystemHandler {
	private Logger logger = Logger.getLogger(PABCLISpecialHandler.class);

	@Override
	public CommonContext beforeHandler(CommonContext context) throws HandlerException{
		logger.info("start exec PABCLISpecialHandler beforeHandler");
		byte[] msg = context.getByteArray(ConstantConfigField.ORIGINAL_MSG_BYTE_ARRAY, CommonContext.SCOPE_GLOBAL);
		try{
			byte[] msgHead = Arrays.copyOfRange(msg, 0, 222);
			String tranDate = new String(Arrays.copyOfRange(msgHead, 53, 61));
			String tranTime = new String(Arrays.copyOfRange(msgHead, 61, 67));
			String flowno = new String(Arrays.copyOfRange(msgHead, 67, 87));
			logger.info("msg head tranDate ["+tranDate+"] tranTime ["+tranTime+"]");
			context.setFieldStr(BusinessConstantField.PAB_TRAN_DATE, tranDate, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessConstantField.PAB_TRAN_TIME, tranTime, CommonContext.SCOPE_GLOBAL);
			context.setByteArray(BusinessConstantField.PAB_MSG_HEAD, msgHead, CommonContext.SCOPE_GLOBAL);
			context.setFieldStr(BusinessConstantField.PAB_MSGHEAD_FLOWNO, flowno, CommonContext.SCOPE_GLOBAL);
			byte[] msgBody =Arrays.copyOfRange(msg, 222, msg.length);
			String msgBodyStr = new String(msgBody,context.getSDO().enCodeing);
			logger.info("message body is ["+msgBodyStr+"]");
			context.setOrginalField(msgBodyStr);		
		}catch(Exception e){
			logger.error("PABCLISpecialHandler exec exception",e);
			context.setErrorCode(BusinessErrorCodeConstant.BUSS_PLA000520);
			throw new HandlerException(e);
		}
		logger.info("PABCLISpecialHandler beforeHandler exec succ");
		return context;
	}

	/**
	 * 将服务方响应报文的前222位截掉
	 */
	@Override
	public CommonContext afterHandler(CommonContext context) throws HandlerException{
		logger.info("start exec PABCLISpecialHandler afterHandler");
		
		StringBuilder sb = new StringBuilder();
		String msg = context.getOrginalField();
		
		String msgVersion = "A001";	//报文版本
		sb.append(msgVersion);
		String targetSys = "01";	//目标系统
		sb.append(targetSys);
		String msgCode = "01";		//编码格式
		sb.append(msgCode);
		String protocolType = "01";		//协议类型
		sb.append(protocolType);
		
		//因为pab做渠道的时候后台服务可能是不适pab，所以这个服务id必须写死
		String custCode = BServerParamData.getInstance().getParamsValue("PAB_SVR", BusinessConstantField.PAB_OUTSIDE_CUST_NO).getParavalue();
		sb.append(custCode);
		
		int msgLen = msg.getBytes().length;	//计算报文长度
		sb.append(String.format("%010d", msgLen));
		sb.append(String.format("%1$-6s", context.getSDO().TRANCODE));		//渠道交易码
		sb.append(String.format("%1$-5s", " "));	//操作员
		String servertype = "02";							//服务类型    应答
		sb.append(servertype);
		String date = TimeUtil.getCurrentTime("yyyyMMdd");
		sb.append(date);		//交易日期
		String time = TimeUtil.getCurrentTime("hhmmss");
		sb.append(time);		//交易时间
		//获取报文头流水号
		String flowno = context.getFieldStr(BusinessConstantField.PAB_MSGHEAD_FLOWNO, CommonContext.SCOPE_GLOBAL);
		sb.append(flowno);
		sb.append("000000");		//响应码服务方要求请求必填000000
		sb.append(String.format("%1$-100s", " "));		//响应消息
		sb.append("0");									//后续报标志
		sb.append("000");								//请求次数
		sb.append("0");									//签名标志
		sb.append("1");									//签名数据包格式
		sb.append(String.format("%1$-12s", "RSA-SHA1"));									//签名算法
		sb.append(String.format("%010d", 0));			//签名数据长度
		sb.append("0");									//是否有附件，先默认填0以后有必要再改		
//		if("0".equals(context.getFieldStr(BusinessConstantField.ISTRANSFERFILE, CommonContext.SCOPE_GLOBAL))){
//			sb.append("0");
//		}else if("1".equals(context.getFieldStr(BusinessConstantField.ISTRANSFERFILE, CommonContext.SCOPE_GLOBAL))){
//			sb.append("1");
//		}
		
		String msgHeader = sb.toString();
		logger.info("PAB msg header is :"+sb.toString()+" msg head length is "+msgHeader.getBytes().length);
		context.setOrginalField(msgHeader+msg);
		
		logger.info("PABCLISpecialHandler afterHandler exec succ");
		return context;
	}
	
	public static void main(String[] args) {         
	   
		
		int youNumber = 10;         
	    // 0 代表前面补充0         
	    // 4 代表长度为4         
	    // d 代表参数为正数型         
//	    String str = String.format("%080d", youNumber);     
	    String msg = "1234567890";
//	    System.out.println(msg.substring(3));
	    System.out.println(String.format("%1$-12s", "RSA-SHA1"));      
	    System.out.println(String.format("%1$-100s", " "));      
	  }         

}
