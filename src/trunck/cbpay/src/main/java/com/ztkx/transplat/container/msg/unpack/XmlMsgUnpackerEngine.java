package com.ztkx.transplat.container.msg.unpack;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.transplat.container.HandlerException;
import com.ztkx.transplat.container.constant.ContainerConstantField;
import com.ztkx.transplat.container.constant.ErrorCodeConstant;
import com.ztkx.transplat.container.util.ContextUtil;
import com.ztkx.transplat.platformutil.context.CommonContext;
import com.ztkx.transplat.platformutil.enanddecrypt.Base64Util;
import com.ztkx.transplat.platformutil.msg.CompositField;
import com.ztkx.transplat.platformutil.msg.Field;
import com.ztkx.transplat.platformutil.msg.FieldFormat;
import com.ztkx.transplat.platformutil.msg.FieldFormatCheckUtil;
import com.ztkx.transplat.platformutil.msg.MsgConstantField;
import com.ztkx.transplat.platformutil.msg.MsgXmlDescriber;
import com.ztkx.transplat.platformutil.xml.StreamXmlUtil;

public class XmlMsgUnpackerEngine {
	
	static Logger logger = Logger.getLogger(XmlMsgUnpackerEngine.class);
	/**
	 * 完成xml拆包
	 * 1.解析include标签
	 * 2.解析MsgXmlDescriber中的list
	 * 3.解析MsgXmlDescriber中的CompositField字段(解析循环体)
	 * @param context
	 * @param msgXmlDescriber
	 * @throws XMLStreamException 
	 */
	public static void unpack(CommonContext context,MsgXmlDescriber msgXmlDescriber) throws HandlerException{
		
		String msg = context.getOrginalField();
		//获取xmlReader对象
		XMLStreamReader reader = null;
		
		try {
			reader = StreamXmlUtil.getXmlStreamReader(msg.getBytes(context.getSDO().enCodeing));
			//拆include
			unpackIncludeField(msgXmlDescriber, reader, context);
			//拆普通字段
			unpackGeneralField(msgXmlDescriber, reader, context);
			
			//拆循环体报文
			unpackCompositField(msgXmlDescriber, reader, context);
		}catch(Exception e){
			logger.error("unpack Engine exec exception",e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000008, context);
			throw new HandlerException("unpack Engine exec exception", e);
		}
		finally{
			//释放资源
			StreamXmlUtil.closeReader(reader);
		}
	}
	
	/**
	 * 拆循环体字段
	 * @param msgXmlDescriber
	 * @param reader
	 * @param context
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
	private static void unpackCompositField(MsgXmlDescriber msgXmlDescriber,XMLStreamReader reader, CommonContext context) throws Exception {
		CompositField cf = msgXmlDescriber.getCf();
		if(cf == null){
			//没有循环体拆包结束
			if(logger.isDebugEnabled()){
				logger.debug("compositField is null");
			}
			return;
		}
		String compsitFieldName = cf.getName();
		//获取循环次数标签
		String sizeStr = cf.getSizeRef();
		int size = 0;
		String fieldName = null;
//		try {
			/**
			 * 将循环次数标签转为int，如果能成功转换为int则循环次数用xml中配置的数值。
			 * 如果转换出现异常，默认为当前循环次数为某个字段的值，从context中获取该字段的值
			 */
			try{
				size = Integer.parseInt(sizeStr);
			}catch(NumberFormatException e){
				String sizeArray = context.getFieldStr(sizeStr,CommonContext.SCOPE_GLOBAL);
				if(null == sizeArray){
					logger.error("get cycle times fail");
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000004, context);
					throw new  NumberFormatException("get cycle times fail");
				}
				
				try{
					size = Integer.parseInt(sizeArray);
				}catch(NumberFormatException ex){
					logger.error("get cycle times fail");
					ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000004, context);
					throw new  NumberFormatException("get cycle times fail");
				}
			}
			if(logger.isDebugEnabled()){
				logger.debug("cyclic times value ["+size+"]");
				logger.debug("start parse cyclic msg ");
			}
			//根据循环次数拆循环体
			
			List<Map<String, String>> cyclicList = new ArrayList<Map<String,String>>(size);
			List<Field> fieldList = cf.getList();
			for (int i = 0; i < size; i++) {
				if(logger.isDebugEnabled()){
					logger.debug("start unpack "+ i +" times cyclic ");
				}
				Map<String,String> map = new HashMap<String, String>(1);
				for (int j = 0; j < fieldList.size(); j++) {
					
					Field f = fieldList.get(j);
					fieldName = f.getName();
					FieldFormat ff = f.getFieldFormat();

					//如果这个字段不需要从msg中拆
					if(!isParseFromMsg(fieldName,ff)){
						if(StringUtils.isNotBlank(ff.getDefault_value())){
							map.put(fieldName, ff.getDefault_value());
							if(logger.isDebugEnabled()){
								logger.debug("the field ["+fieldName+"] type is ["+ff.getType()+"] the default is ["+ff.getDefault_value()+"]");
							}
						}
						continue;
					}
					String value = getFieldValue(reader,fieldName);
					if(logger.isDebugEnabled()){
						logger.debug("the field ["+fieldName +"] type is ["+ff.getType()+"] value is ["+value+"]");
					}
					if(!FieldFormatCheckUtil.necessaryCheck(fieldName, value, ff)){
						//如果必输字段检测失败，直接抛出exception
						context.setErrorCode(ErrorCodeConstant.BASE_PLA000003);
						throw new NumberFormatException(fieldName + " field is necessary current value is ["+value+"]");
					}
					//如果超过长度
					if(FieldFormatCheckUtil.isLengthOverFlow(fieldName, value, ff)){
						value = value.substring(0, ff.getLength());
					}else if(FieldFormatCheckUtil.isFillChar(fieldName, value, ff)){
						value = FieldFormatCheckUtil.fillChar(fieldName, value, ff);
					}
					
					map.put(fieldName,value);
					execFunction(map, ff, value);
					if(logger.isDebugEnabled()){
						logger.debug("the field "+fieldName+" type is ["+ff.getType()+"] ultimate value is ["+value+"]");
					}
				}
				cyclicList.add(map);
			}
			context.setObj(compsitFieldName, cyclicList, CommonContext.SCOPE_GLOBAL);
//		} catch (NumberFormatException | XMLStreamException e) {
//			logger.error("unpack Composit field exception the fieldName is ["+fieldName+"]",e);
//			throw e;
//		}
	}

	private static void unpackIncludeField(MsgXmlDescriber msgXmlDescriber,XMLStreamReader reader,CommonContext context) throws XMLStreamException{
		//获取include标签
		//跳过所有空格	
		try {
			//这一句话不能删，有用！！！
			reader.nextTag();
		} catch (XMLStreamException e) {
			logger.error("reader.nextTag exception",e);
			throw e;
		}
	}
	
	/**
	 * 拆普通字段
	 * @param msgXmlDescriber
	 * @param reader
	 * @param context
	 * @param enCode
	 * @throws Exception 
	 * @throws XMLStreamException 
	 * @throws UnsupportedEncodingException 
	 */
	private static void unpackGeneralField(MsgXmlDescriber msgXmlDescriber,XMLStreamReader reader,CommonContext context) throws Exception {
		
		String enCode = context.getSDO().enCodeing;
		
		List<Field> fieldList = msgXmlDescriber.getList();
		String fieldName = null;
			//拆普通字段
//		try{
			for (int i = 0; i < fieldList.size(); i++) {
				Field f = fieldList.get(i);
				fieldName = f.getName();
				FieldFormat ff = f.getFieldFormat();

				//如果这个字段不需要从msg中拆
				if(!isParseFromMsg(fieldName,ff)){
					if(StringUtils.isNotBlank(ff.getDefault_value())){
						context.setFieldStr(fieldName, ff.getDefault_value(),CommonContext.SCOPE_GLOBAL);
						if(logger.isDebugEnabled()){
							logger.debug("the field "+fieldName+" type is ["+ff.getType()+"] the default is ["+ff.getDefault_value()+"]");
						}
					}
					continue;
				}
				/**
				 * 1.获取当前字段的值
				 * 2.判断字段格式
				 */
				String value = getFieldValue(reader,fieldName);
				
				if(logger.isDebugEnabled()){
					logger.debug("the field ["+fieldName +"] type is ["+ff.getType()+"] value is ["+value+"]");
				}
				
				if(null == value){
					//如果value为空表示xml标签不存在，不执行下面逻辑
					continue;
				}
				//如果当前字段是xml对象的
				if (ff.getType().equals(MsgConstantField.ATTR_TYPE_XML)) {
					if(logger.isDebugEnabled()){
						logger.debug("the field ["+fieldName+"] is "+MsgConstantField.ATTR_TYPE_XML+" field start parse xml document");
					}
					/**
					 * 1.将得到的数据先处理得到元数据
					 * 2.用元数据获取xmlReader对象
					 * 3.按照格式解析xmlReader对象
					 */
					byte[] xmlContentArray = Base64Util.decode(value.getBytes(enCode));
					XMLStreamReader subReader =  StreamXmlUtil.getXmlStreamReader(xmlContentArray);
					
					Map<String, String> subMap;
					try {
						subReader.nextTag();
						List<Field> subFieldList = f.getListField();
						subMap = new HashMap<String, String>(subFieldList.size());
						for (int j = 0; j < subFieldList.size(); j++) {
							Field subf = subFieldList.get(j);
							String subFieldName = subf.getName();
							FieldFormat subff = subf.getFieldFormat();
							//如果这个字段不需要从msg中拆
							if(!isParseFromMsg(subFieldName,subff)){
								if(StringUtils.isNotBlank(subff.getDefault_value())){
									subMap.put(subFieldName, subff.getDefault_value());
									if(logger.isDebugEnabled()){
										logger.debug("the field ["+subFieldName+"] type is ["+subff.getType()+"] the default is ["+subff.getDefault_value()+"]");
									}
								}
								continue;
							}
							String subValue = getFieldValue(subReader,subFieldName);
							
							if(subValue == null){
								continue;
							}
							
							if(!FieldFormatCheckUtil.necessaryCheck(subFieldName, subValue, subff)){
								//如果必输字段检测失败，直接抛出exception
								context.setFieldStr(ContainerConstantField.CONTAINER_IN_ERROR_CODE, ErrorCodeConstant.BASE_PLA000003, CommonContext.SCOPE_GLOBAL);
								throw new NumberFormatException(subFieldName + " field is necessary current value is ["+subValue+"]");
							}
							//如果超过长度
							if(FieldFormatCheckUtil.isLengthOverFlow(subFieldName, subValue, subff)){
								subValue = subValue.substring(0, subff.getLength());
							}else if(FieldFormatCheckUtil.isFillChar(subFieldName, subValue, subff)){
								subValue = FieldFormatCheckUtil.fillChar(subFieldName, subValue, subff);
							}
							
							//检查通过
							subMap.put(subFieldName, subValue);
							execFunction(subMap, subff, subValue);
							if(logger.isDebugEnabled()){
								logger.debug("the field ["+subFieldName+"] type is ["+subff.getType()+"] value is ["+subValue+"]");
							}
						}
					} finally{
						//释放资源
						StreamXmlUtil.closeReader(subReader);
					}
					context.setObj(fieldName, subMap, CommonContext.SCOPE_GLOBAL);
					if(logger.isDebugEnabled()){
						logger.debug("the field ["+fieldName+"] type is ["+ff.getType()+"] value is ["+subMap+"]");
					}
					execFunction(context, ff, subMap);
				}
				//如果该字段是普通字段
				else if(ff.getType().equals(MsgConstantField.ATTR_TYPE_N) ||ff.getType().equals(MsgConstantField.ATTR_TYPE_S)){
					if(!FieldFormatCheckUtil.necessaryCheck(fieldName, value, ff)){
						//如果必输字段检测失败，直接抛出exception
						context.setErrorCode(ErrorCodeConstant.BASE_PLA000003);
						throw new NumberFormatException(fieldName + " field is necessary current value is ["+value+"]");
					}
					//如果超过长度
					if(FieldFormatCheckUtil.isLengthOverFlow(fieldName, value, ff)){
						value = value.substring(0, ff.getLength());
					}else if(FieldFormatCheckUtil.isFillChar(fieldName, value, ff)){
						value = FieldFormatCheckUtil.fillChar(fieldName, value, ff);
					}
					//检查通过
					context.setFieldStr(fieldName, value, CommonContext.SCOPE_GLOBAL);
					execFunction(context, ff, value);
					if(logger.isDebugEnabled()){
						logger.debug("the field ["+fieldName+"] type is ["+ff.getType()+"] ultimate value is ["+value+"]");
					}
				}
			}
//		} catch (UnsupportedEncodingException|XMLStreamException e) {
//			logger.error("unpack general field exception the fieldName is ["+fieldName+"]",e);
//			throw e;
//		}
	}

	/**
	 * 执行xml对象的convert字段
	 * @param subMap
	 * @param subff
	 * @param subValue
	 * 2016年3月31日 下午2:44:23
	 * @author zhangxiaoyun
	 */
	private static void execFunction(Map<String, String> subMap,FieldFormat subff, String subValue) {
		if(StringUtils.isNotEmpty(subff.getConvert())){
			subMap.put(subff.getConvert(), subValue);
			if(logger.isDebugEnabled()){
				logger.debug("exec convert function the file ["+subff.getConvert()+"] value is ["+subValue+"]");
			}
		}
	}

	/**
	 * 执行拆组包函数
	 * @param context
	 * @param ff
	 * @param value
	 * 2016年3月31日 下午2:37:14
	 * @author zhangxiaoyun
	 */
	private static void execFunction(CommonContext context, FieldFormat ff,Object value) {
		if(StringUtils.isNotEmpty(ff.getConvert())){
			context.setObj(ff.getConvert(), value, CommonContext.SCOPE_GLOBAL);
			if(logger.isDebugEnabled()){
				logger.debug("exec convert function the file ["+ff.getConvert()+"] value is ["+value+"]");
			}
		}
	}

	/**
	 * 从xmlReader中获取字段的值
	 * @param reader
	 * @param fieldName
	 * @return
	 * @throws XMLStreamException
	 * @throws UnsupportedEncodingException
	 */
	private static String getFieldValue(XMLStreamReader reader, String fieldName) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamReader.START_ELEMENT) {
				if (reader.getLocalName().equals(fieldName)) {
					return reader.getElementText();
				}
			}
		}
		return null;
	}
	
	/**
	 * 检查当前字段是否需要从reader中解析出来
	 * @param fieldName
	 * @param ff
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static boolean isParseFromMsg(String fieldName,FieldFormat ff) {
		// 如果字段是virtual，表示当前字段只是xml中规定格式的字段不需要从从报文中拆
		if (ff.getType().equals(MsgConstantField.ATTR_TYPE_VIRTUAL)) {
			return false;
		}
		
		//如果当前字段不需要从报文中拆，直接取default字段里面的值
		if (!ff.isFrommsg()) {
			return false;
		}
		//如果当前字段配置了default，直接取default字段里面的值
		if (StringUtils.isNotEmpty(ff.getDefault_value())) {
			return false;
		}
		return true;
	}
	
}