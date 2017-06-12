package com.ztkx.transplat.container.protocol.parser;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztkx.transplat.container.protocol.ProtocolConstantField;
import com.ztkx.transplat.container.protocol.config.CommonConfig;
import com.ztkx.transplat.container.protocol.config.ProtocolConfig;
import com.ztkx.transplat.container.protocol.config.ProtocolConfigImp;
import com.ztkx.transplat.container.protocol.config.RequestConfig;
import com.ztkx.transplat.container.protocol.config.ResponseConfig;
import com.ztkx.transplat.platformutil.xml.Dom4jXmlUtil;

public class ProtocolParserImp implements ProtocolConfParser{
	ProtocolConfig pci = null;
	Logger logger = Logger.getLogger(ProtocolParserImp.class);
	@Override
	public ProtocolConfig parse(String xml) throws Exception{
		pci = new ProtocolConfigImp();
		
		Element root = Dom4jXmlUtil.getRootElementByStr(xml);
		
		Element eleCommon = root.element(ProtocolConstantField.LABEL_COMMON);
		String id = eleCommon.attributeValue(ProtocolConstantField.ATTR_ID);
		String type = eleCommon.attributeValue(ProtocolConstantField.ATTR_TYPE);
		String inout = eleCommon.attributeValue(ProtocolConstantField.ATTR_INOUT);
		String model = eleCommon.attributeValue(ProtocolConstantField.ATTR_MODEL);
		String encoding = eleCommon.attributeValue(ProtocolConstantField.ATTR_ENCODING);
		String sessionCount = eleCommon.attributeValue(ProtocolConstantField.ATTR_SESSIONCOUNT);
		String isTowWayverify = eleCommon.attributeValue(ProtocolConstantField.ATTR_ISTOWWAYVERIFY);
		String flag = eleCommon.attributeValue(ProtocolConstantField.ATTR_FLAG);
		if(StringUtils.isBlank(sessionCount)){
			sessionCount="1";
		}
		
		String factory = eleCommon.attributeValue(ProtocolConstantField.ATTR_FACTORY);
		String url = eleCommon.attributeValue(ProtocolConstantField.ATTR_URL);
		String queueName = eleCommon.attributeValue(ProtocolConstantField.ATTR_QUEUENAME);
		String overtime = eleCommon.attributeValue(ProtocolConstantField.ATTR_OVERTIME);
		String isTransactionStr = eleCommon.attributeValue(ProtocolConstantField.ATTR_ISTRANSACTION);
		String autoAcknowledgeStr = eleCommon.attributeValue(ProtocolConstantField.ATTR_AUTOACKNOWLEDGE);
		String msgListener = eleCommon.attributeValue(ProtocolConstantField.ATTR_MSGLISTENER);
		if(StringUtils.isBlank(isTransactionStr)){
			isTransactionStr="false";
		}
		if(StringUtils.isBlank(autoAcknowledgeStr)){
			autoAcknowledgeStr="false";
		}
		
		if(StringUtils.isBlank(overtime)){
			overtime="0";
		}
		
		CommonConfig cc = new CommonConfig(id,type,encoding,inout,model,Integer.parseInt(sessionCount),Boolean.parseBoolean(isTowWayverify),flag,factory,url,queueName,Integer.parseInt(overtime),Boolean.parseBoolean(isTransactionStr),Boolean.parseBoolean(autoAcknowledgeStr),msgListener);
		pci.setCommonConfig(cc);
		
		Element eleRequest = root.element(ProtocolConstantField.LABEL_REQUEST);
		if(eleRequest == null){
			return pci;
		}
		String host = eleRequest.attributeValue(ProtocolConstantField.ATTR_HOST);
		String portStr = eleRequest.attributeValue(ProtocolConstantField.ATTR_PORT);
		int port = 0;
		
		if(StringUtils.isNotEmpty(portStr)){
			if(StringUtils.isNumeric(portStr)){
				port = Integer.parseInt(portStr);
			}else{
				logger.error("protocol ["+id+"] port is error");
				throw new NumberFormatException();
			}
		}
		
		String policy = eleRequest.attributeValue(ProtocolConstantField.ATTR_POLICY);
		String file = eleRequest.attributeValue(ProtocolConstantField.ATTR_FILE);
		String encryption = eleRequest.attributeValue(ProtocolConstantField.ATTR_ENCRYPTION);
		String connectTimeout = eleRequest.attributeValue(ProtocolConstantField.ATTR_CONNECTTIMEOUT);
		String method = eleRequest.attributeValue(ProtocolConstantField.ATTR_METHOD);
		String dataParams = eleRequest.attributeValue(ProtocolConstantField.ATTR_DATAPARAMS);
		
		String password = eleRequest.attributeValue(ProtocolConstantField.ATTR_PASSWORD);
		String keystoreType = eleRequest.attributeValue(ProtocolConstantField.ATTR_KEYSTORETYPE);
		String certificatePath = eleRequest.attributeValue(ProtocolConstantField.ATTR_CERTIFICATEPATH);
		String algorithm = eleRequest.attributeValue(ProtocolConstantField.ATTR_ALGORITHM);
		String headLen = eleRequest.attributeValue(ProtocolConstantField.ATTR_HEADLEN);
		if(StringUtils.isBlank(connectTimeout)){
			connectTimeout="60000";
		}
		if(StringUtils.isBlank(headLen)){
			headLen="-1";
		}
		
		RequestConfig rc = new RequestConfig(host,port,encryption,Integer.parseInt(connectTimeout),file,method,dataParams,password,keystoreType,certificatePath,algorithm,Integer.parseInt(headLen));
		
		try {
			if(StringUtils.isNotEmpty(policy)){
				rc.setPolicy(policy);
				parseLen(policy,rc);
			}
		} catch (Exception e) {
			logger.error("parse len attribute fail",e);
			throw e;
		}
		
		pci.setRequestConfig(rc);
		
		Element eleResponse = root.element(ProtocolConstantField.LABEL_RESPONSE);
		if(eleResponse == null){
			return pci;
		}
		policy =  eleResponse.attributeValue(ProtocolConstantField.ATTR_POLICY);
		String readTimeout = eleResponse.attributeValue(ProtocolConstantField.ATTR_READTIMEOUT);
		dataParams = eleResponse.attributeValue(ProtocolConstantField.ATTR_DATAPARAMS);
		encryption = eleResponse.attributeValue(ProtocolConstantField.ATTR_ENCRYPTION);
		password = eleResponse.attributeValue(ProtocolConstantField.ATTR_PASSWORD);
		keystoreType = eleResponse.attributeValue(ProtocolConstantField.ATTR_KEYSTORETYPE);
		certificatePath = eleResponse.attributeValue(ProtocolConstantField.ATTR_CERTIFICATEPATH);
		algorithm = eleResponse.attributeValue(ProtocolConstantField.ATTR_ALGORITHM);
		headLen = eleResponse.attributeValue(ProtocolConstantField.ATTR_HEADLEN);
		if(StringUtils.isBlank(readTimeout)){
			readTimeout="60000";
		}
		if(StringUtils.isBlank(headLen)){
			headLen="-1";
		}
		ResponseConfig resConf = new ResponseConfig(Integer.parseInt(readTimeout),dataParams,encryption,password,keystoreType,certificatePath,algorithm,Integer.parseInt(headLen));
		
		
		try {
			if(StringUtils.isNotEmpty(policy)){
				resConf.setPolicy(policy);
				parseLen(policy,resConf);
			}
			
		} catch (Exception e) {
			logger.error("parse len attribute fail protocol id is["+id+"]",e);
		}
		pci.setResponseConfig(resConf);
		
		return pci;
	}

	/**
	 * 解析协议中的len属性
	 * @param len
	 * @param rc
	 * @throws Exception
	 */
	private void parseLen(String len, Object rc) throws Exception {
		if(StringUtils.isEmpty(len) || rc == null){
			logger.error("policy params error!");
			return ;
		}
		
		RequestConfig reqConf = null;
		ResponseConfig resConf = null;
		if (rc instanceof RequestConfig) {
			reqConf = (RequestConfig) rc;
			
			if(len.startsWith(ProtocolConstantField.POLICY_FIX)){
				//len=fix:200
				reqConf.setMaxLen(Integer.parseInt(len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR,2)[1]));
			}else if(len.startsWith(ProtocolConstantField.POLICY_DYNAMIC_LEN)){
				//len=dynamic_len:start:0,end:5
				String tmp = len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR, 2)[1];
				String[] tmparray = tmp.split(",",-1);
				String startStr=tmparray[0];
				String endStr=tmparray[1];
				int start=Integer.parseInt(startStr.split(ProtocolConstantField.ATTR_LEN_SEPARATOR)[1]);
				int end=Integer.parseInt(endStr.split(ProtocolConstantField.ATTR_LEN_SEPARATOR)[1]);
				reqConf.setStart(start);
				reqConf.setEnd(end);
				reqConf.setMsgHeadLen((end-start)+1);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < reqConf.getMsgHeadLen(); i++) {
					sb.append("9");
				}
				reqConf.setMaxLen(Long.parseLong(sb.toString()));
				sb = new StringBuffer();
				for(int i = 0;i <= reqConf.getEnd();i++){
					sb.append("0");
				}
				DecimalFormat df = new DecimalFormat(sb.toString());
				reqConf.setDf(df);
			}else if(len.startsWith(ProtocolConstantField.POLICY_SPECIALC)){
				//len=specialc:@@@@
				reqConf.setOver_flag(len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR,2)[1]);
				reqConf.setMaxLen(-1);
			}else if(len.startsWith(ProtocolConstantField.LEN_STREAM_OVER_FLAG)){
				//len=stream_over_flag
				reqConf.setMaxLen(-1);
			}else if(len.startsWith(ProtocolConstantField.POLICY_DATAPARAM)){
				//len=stream_over_flag
				reqConf.setMaxLen(-1);
			}else{
				logger.error("msg len config error");
				throw new Exception("msg len config error");
			}
		}else if(rc instanceof ResponseConfig){
			resConf = (ResponseConfig) rc;
			if(len.startsWith(ProtocolConstantField.POLICY_FIX)){
				//len=fix:200
				resConf.setMaxLen(Integer.parseInt(len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR,2)[1]));
			}else if(len.startsWith(ProtocolConstantField.POLICY_DYNAMIC_LEN)){
				//len=dynamic_len:start:0,end:5
				String tmp = len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR, 2)[1];
				String[] tmparray = tmp.split(",",-1);
				String startStr=tmparray[0];
				String endStr=tmparray[1];
				int start=Integer.parseInt(startStr.split(ProtocolConstantField.ATTR_LEN_SEPARATOR)[1]);
				int end=Integer.parseInt(endStr.split(ProtocolConstantField.ATTR_LEN_SEPARATOR)[1]);
				
				resConf.setStart(start);
				resConf.setEnd(end);
				resConf.setMsgHeadLen((end-start)+1);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < resConf.getMsgHeadLen(); i++) {
					sb.append("9");
				}
				resConf.setMaxLen(Long.parseLong(sb.toString()));
				sb = new StringBuffer();
				for(int i = 0;i <= resConf.getEnd();i++){
					sb.append("0");
				}
				DecimalFormat df = new DecimalFormat(sb.toString());
				resConf.setDf(df);
			}else if(len.startsWith(ProtocolConstantField.POLICY_SPECIALC)){
				//len=specialc:@@@@
				resConf.setOver_flag(len.split(ProtocolConstantField.ATTR_LEN_SEPARATOR,2)[1]);
				resConf.setMaxLen(-1);
			}else if(len.startsWith(ProtocolConstantField.LEN_STREAM_OVER_FLAG)){
				//len=stream_over_flag
				resConf.setMaxLen(-1);
			}else if(len.startsWith(ProtocolConstantField.POLICY_DATAPARAM)){
				//len=stream_over_flag
				resConf.setMaxLen(-1);
			}else{
				logger.error("msg len config error");
				throw new Exception("msg len config error");
			}
		}
	}
}
