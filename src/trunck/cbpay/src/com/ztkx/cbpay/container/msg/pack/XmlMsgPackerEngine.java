package com.ztkx.cbpay.container.msg.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztkx.cbpay.container.constant.ErrorCodeConstant;
import com.ztkx.cbpay.container.util.ContextUtil;
import com.ztkx.cbpay.platformutil.context.CommonContext;
import com.ztkx.cbpay.platformutil.enanddecrypt.Base64Util;
import com.ztkx.cbpay.platformutil.msg.CompositField;
import com.ztkx.cbpay.platformutil.msg.Field;
import com.ztkx.cbpay.platformutil.msg.FieldFormat;
import com.ztkx.cbpay.platformutil.msg.MsgConstantField;
import com.ztkx.cbpay.platformutil.msg.MsgXmlDescriber;
import com.ztkx.cbpay.platformutil.xml.StreamXmlUtil;

public class XmlMsgPackerEngine {

	private static Logger logger = Logger.getLogger(XmlMsgPackerEngine.class);
	
	/**
	 * xml组包引擎
	 * 1.先组include标签中包含的字段
	 * 2.按照格式填充普通xml标签(包含xml对象的普通标签)
	 * 3.按照格式填充循环体标签
	 * @param context
	 * @param msgXmlDescriber
	 * @throws XMLStreamException 
	 */
	public static void pack(CommonContext context,MsgXmlDescriber msgXmlDescriber) throws XMLStreamException {
		//穿件XmlStreamWriter对象，
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLStreamWriter writer = StreamXmlUtil.getXmlStreamWriter(bos,context.getSDO().enCodeing);
		Deque<Field> stack = new ArrayDeque<Field>();
		Deque<Field> compositStack = new ArrayDeque<Field>();
		try {
			//组include
			packIncludeField(msgXmlDescriber, writer, context);
			//组普通字段
			packGeneralField(msgXmlDescriber, writer, context,stack);
			
			//组循环体报文
			packCompositField(msgXmlDescriber, writer, context,compositStack);
			
			/**
			 * 处理整个报文最后需要关闭的标签
			 */
			packVirtualField(writer, null,stack);
			//关闭document
			writer.writeEndDocument();
			writer.flush();
			try {
				String msg = bos.toString(context.getSDO().enCodeing);
				logger.info("pack msg is ["+msg+"]");
				context.setOrginalField(msg);
			} catch (UnsupportedEncodingException e) {
				logger.error("msg convert encoding error",e);
				ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
				throw new XMLStreamException(e);
			}
			
		}
		finally{
			//释放资源
			stack = null;
			compositStack = null;
			
			closeBOS(bos);
			StreamXmlUtil.closeWriter(writer);
		}
		
	}
	
	/**
	 * 组循环体报文
	 * @param msgXmlDescriber
	 * @param writer
	 * @param context
	 * @throws XMLStreamException 
	 */
	private static void packCompositField(MsgXmlDescriber msgXmlDescriber,XMLStreamWriter writer, CommonContext context,Deque<Field> compositStack) throws XMLStreamException  {
		String enCode = context.getSDO().enCodeing;
		CompositField cf = msgXmlDescriber.getCf();
		if(cf == null){
			//没有循环体拆包结束
			if(logger.isDebugEnabled()){
				logger.debug("compositField is null");
			}
			return;
		}
		
		String compsitFieldName = cf.getName();
		List<Field> cycleList  = cf.getList();
		String fieldName = null;
		try {
			/**
			 * 1.如果配置的sizeRef能转化为int就直接用xml里面配置的
			 * 2.如果配置的sizeRef不能转化为int，就直接获取context的循环内容，
			 */
			List<Map<String,String>> compsitList = (List<Map<String,String>>)context.getObj(compsitFieldName,CommonContext.SCOPE_GLOBAL);
			if(null == compsitList){
				logger.error("get cycle data fail");
				context.setErrorCode(ErrorCodeConstant.BASE_PLA000004);
				throw new  NumberFormatException("get cycle data fail");
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("cyclic times value ["+compsitList.size()+"]");
				logger.debug("start package cyclic msg ");
			}
			//根据循环次数拆循环体
			
			//如果当前循环体的父标签需要输出到xml报文中
			if(cf.isOutPut()){
				writeStartElement(writer, compsitFieldName);
			}
			
			List<Field> fieldList = cf.getList();
			for (int i = 0; i < compsitList.size(); i++) {
				if(logger.isDebugEnabled()){
					logger.debug("start pack "+ i +" times cyclic ");
				}
				
				Map<String,String> map = compsitList.get(i);
				
				for (int j = 0; j < cycleList.size(); j++) {
					Field f = fieldList.get(j);
					fieldName = f.getName();
					FieldFormat ff = f.getFieldFormat();				
					String type = ff.getType();
					switch (type) {
					case MsgConstantField.ATTR_TYPE_VIRTUAL:
						if(logger.isDebugEnabled()){
							logger.debug("the field <"+fieldName+"> type is < virtual > ");
						}
						packVirtualField(writer, f,compositStack);
						break;
					case MsgConstantField.ATTR_TYPE_N:
					case MsgConstantField.ATTR_TYPE_S:
						//给字段赋值
						String value = execFieldFunction(f, map, ff);
						/**
						 * 判断字段值是否超过最大长度
						 * 如果超过最大长度则截掉超过以后的内容
						 */
						if(isOverLenth(fieldName,value,ff)){
							value = value.substring(0, ff.getLength());
						}
						
						if(logger.isDebugEnabled()){
							logger.debug("the field <"+fieldName+"> type is <"+ff.getType()+"> ultimate value is <"+value+">");
						}
						writeElement(writer,fieldName,value);
						break;
					default:
						//TODO
						break;
					}
				}
			}
			/**
			 * 处理最后的需要闭合的字段
			 */
			packVirtualField(writer, null,compositStack);
			
			//如果当前循环体的父标签需要输出到xml报文中
			if(cf.isOutPut()){
				writeEndElement(writer, compsitFieldName);
			}
		} catch (NumberFormatException e) {
			logger.error("pack Composit field exception the fieldName is <"+fieldName+">",e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
			throw e;
		} catch (XMLStreamException e) {
			logger.error("pack Composit field exception the fieldName is <"+fieldName+">",e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
			throw e;
		}
	}
	
	/**
	 * 处理普通字段的虚拟标签虚拟字段
	 * @param writer
	 * @param fieldName
	 * @param stack
	 * @throws XMLStreamException
	 * 2016年3月9日 上午10:51:06
	 * @author zhangxiaoyun
	 */
	private static void packVirtualField(XMLStreamWriter writer,
			Field field, Deque<Field> stack) throws XMLStreamException {
		
		//处理最后的需要闭合的标签
		if(field==null){
			while(stack.size()!=0){
				Field fieldtmp = stack.pop();
				writeEndElement(writer, fieldtmp.getName());
			}
			return ;
		}
		
		//入队列
		if(stack.size()==0){
			//如果该栈是空，直接入栈，然后写开始标签
			stack.push(field);
			writeStartElement(writer, field.getName());
		}else{
			/**
			 * 1.如果栈中以前有元素，
			 * 2.将以前元素出栈
			 * 3.拿到出栈标签，根据前一个虚拟字段级别判断是否要写结束标签
			 */
			Field old_field= stack.pop();
			if(old_field.getFieldFormat().getLevel()==field.getFieldFormat().getLevel()){
				/**
				 * 1.如果当前字段级别和之前的虚拟字段级别相等，
				 * 2.写上一个字段的结束标签
				 * 3.写当前字段的开始标签，
				 * 4.将当前字段入栈
				 */
				writeEndElement(writer, old_field.getName());
				stack.push(field);
				writeStartElement(writer, field.getName());
			}else{
				/**
				 * 1.如果当前字段级别和之前的虚拟字段级别不相等，
				 * 2.将之前的虚拟字段入栈
				 * 3.写当前字段的开始标签，
				 * 4.将当前字段入栈
				 */
				stack.push(old_field);
				writeStartElement(writer, field.getName());
				stack.push(field);
			}
			
		}
	}

	
	/**
	 * 组xml普通字段报文
	 * @param msgXmlDescriber
	 * @param writer
	 * @param context
	 * @throws XMLStreamException 
	 * @throws IOException 
	 */
	private static void packGeneralField(MsgXmlDescriber msgXmlDescriber,XMLStreamWriter writer, CommonContext context,Deque<Field> stack) throws XMLStreamException {
		
		String enCode = context.getSDO().enCodeing;
		List<Field> fieldList = msgXmlDescriber.getList();
		String fieldName = null;
		try {
			//拆普通字段
			for (int i = 0; i < fieldList.size(); i++) {
				Field f = fieldList.get(i);
				fieldName = f.getName();
				FieldFormat ff = f.getFieldFormat();				
				String type = ff.getType();
				switch (type) {
				case MsgConstantField.ATTR_TYPE_VIRTUAL:
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+fieldName+"> type is < virtual > ");
					}
					packVirtualField(writer, f, stack);
					break;
				case MsgConstantField.ATTR_TYPE_XML:
					
					processXmlLebel(writer, context, enCode,f);
					break;
				case MsgConstantField.ATTR_TYPE_N:
				case MsgConstantField.ATTR_TYPE_S:
					//给字段赋值
					String value = execFieldFunction(f, context, ff);					
					
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+fieldName+"> type is <"+ff.getType()+"> value is <"+value+">");
					}
					/**
					 * 判断字段值是否超过最大长度
					 * 如果超过最大长度则截掉超过以后的内容
					 */
					if(isOverLenth(fieldName,value,ff)){
						value = value.substring(0, ff.getLength());
					}
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+fieldName+"> type is <"+ff.getType()+"> ultimate value is <"+value+">");
					}
					writeElement(writer,fieldName,value);
					break;
				default:
					//TODO
					break;
				}
			}
		} catch (XMLStreamException e) {
			logger.error("unpack general field exception the fieldName is ["+fieldName+"]",e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
			throw e;
		} 
	}

	/**
	 * 处理xml标签
	 * 如果是xml对象，
	 * 1.获取xmlStreamWriter对象
	 * 1.先获取内存中和当前字段对应的map
	 * 2.获取当前xml字段的sub字段
	 * 3.轮询组每个字段的值
	 */
	private static void processXmlLebel(XMLStreamWriter writer,CommonContext context, String enCode,Field f) throws XMLStreamException {
		
		String fieldName = f.getName();
		String subfieldName = null;
		ByteArrayOutputStream subBos = new ByteArrayOutputStream();
		XMLStreamWriter subwriter = StreamXmlUtil.getXmlStreamWriter(subBos,context.getSDO().enCodeing);
		try {
			subwriter.writeStartDocument(enCode, "1.0");
			Map<String,String> map = null;
			map = execXmlFunction(context, f);
			List<Field> subList = f.getListField();
			Deque<String> substack = new ArrayDeque<String>();
			
			for (int j = 0; j < subList.size(); j++) {
				Field subf = subList.get(j);
				subfieldName = subf.getName();
				FieldFormat subff = subf.getFieldFormat();				
				String subtype = subff.getType();
				switch (subtype) {
				case MsgConstantField.ATTR_TYPE_VIRTUAL:
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+subfieldName+"> type is [ virtual ] ");
					}
					//入队列
					substack.push(subfieldName);
					//只写开始标签
					subwriter.writeStartElement(subfieldName);
					break;
				case MsgConstantField.ATTR_TYPE_N:
				case MsgConstantField.ATTR_TYPE_S:
					//给字段赋值
					String subvalue = execFieldFunction(subf, map, subff);
					subvalue = subvalue==null?"":subvalue;
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+fieldName+"> type is <"+subff.getType()+"> value is <"+subvalue+">");
					}
					/**
					 * 判断字段值是否超过最大长度
					 * 如果超过最大长度则截掉超过以后的内容
					 */
					if(isOverLenth(subfieldName,subvalue,subff)){
						subvalue = subvalue.substring(0, subff.getLength());
					}
					if(logger.isDebugEnabled()){
						logger.debug("the field <"+fieldName+"> type is <"+subff.getType()+"> ultimate value is <"+subvalue+">");
					}
					writeElement(subwriter,subfieldName,subvalue);
					break;
				default:
					//TODO
					break;
				}
			}
			/*
			 这一段根据以后测试情况在看要不要加上
			String subEndFieldName = null;
			while((subEndFieldName = substack.poll())!=null){
				subwriter.writeEndElement();
			}
			*/
			//关闭document
			subwriter.writeEndDocument();
			//释放资源
			byte[] xmlValue = Base64Util.encode(subBos.toByteArray());
			writeElement(writer,fieldName,new String(xmlValue));
		} finally{
			closeBOS(subBos);
			StreamXmlUtil.closeWriter(subwriter);
		}
	}

	/**
	 * 执行普通字段的函数
	 * @param subField
	 * @param map
	 * @param subff
	 * @return
	 * 2016年3月31日 下午3:08:23
	 * @author zhangxiaoyun
	 */
	private static String execFieldFunction(Field subField,Map<String, String> map, FieldFormat subff) {
		String value = null;
		if(subff.getDefault_value()!=null){
			value = subff.getDefault_value();
		}else if(subff.getConvert()!=null){
			value = map.get(subff.getConvert());
		}else{
			value = map.get(subField.getName());
		}
		value = value==null?"":value;
		
		return value ;
	}
	
	private static String execFieldFunction(Field f, CommonContext context,FieldFormat ff) {
		String value = null;
		if(ff.getDefault_value()!=null){
			value =  ff.getDefault_value();
		}else if(ff.getConvert()!=null){
			value = context.getFieldStr(ff.getConvert(), CommonContext.SCOPE_GLOBAL);
		}else{
			value = context.getFieldStr(f.getName(), CommonContext.SCOPE_GLOBAL);
		}
		value = value==null?"":value;
		
		return value;
	}

	/**
	 * 执行xml对象的函数
	 * @param context
	 * @param f
	 * @return
	 * 2016年3月31日 下午3:01:39
	 * @author zhangxiaoyun
	 */
	private static Map<String, String> execXmlFunction(CommonContext context,Field f) {
		Map<String, String> map = null;
		String fieldName = f.getName();
		if(StringUtils.isNotBlank(f.getFieldFormat().getConvert())){
			//检查convert
			if(logger.isDebugEnabled()){
				logger.debug("field ["+fieldName+"] convert is ["+f.getFieldFormat().getConvert()+"] get value from context");
			}
			map= (Map<String, String>) context.getObj(f.getFieldFormat().getConvert(), CommonContext.SCOPE_GLOBAL);
		}else{
			map= (Map<String, String>) context.getObj(fieldName, CommonContext.SCOPE_GLOBAL);
		}
		return map;
	}

	public static void closeBOS(ByteArrayOutputStream bos){
		if(bos != null){
			try {
				bos.close();
			} catch (IOException e) {
				logger.error("close ByteArrayOutputStream exception",e);
			}
		}
	}

	/**
	 * 组include标签
	 * @param msgXmlDescriber
	 * @param reader
	 * @param context
	 * @throws XMLStreamException 
	 */
	private static void packIncludeField(MsgXmlDescriber msgXmlDescriber,XMLStreamWriter writer,CommonContext context) throws XMLStreamException {
		//获取include标签
		String encode = context.getSDO().enCodeing;
		//这一句话不能删，有用！！！
		try {
			writer.writeStartDocument(encode, "1.0");
		} catch (XMLStreamException e) {
			logger.error("pack writeStartDocument exception",e);
			ContextUtil.setErrorCode(ErrorCodeConstant.BASE_PLA000009, context);
			throw e;
		}
	}
	
	
	/**
	 * 字段是否超过最大长度
	 * @param fieldName
	 * @param value
	 * @param ff
	 * @return
	 */
	private static boolean isOverLenth(String fieldName,String value,FieldFormat ff){
		int maxLen = ff.getLength();
		if(value.length()>maxLen){
			if(logger.isDebugEnabled()){
				logger.error("the field <"+fieldName+"> length exceed max Length! current length is :"+value.length());
			}
			return true;
		}
		return false;
	}
	
	public static void writeElement(XMLStreamWriter writer,String fieldName,String value) throws XMLStreamException{
		writer.writeStartElement(fieldName);
		writer.writeCharacters(value);
		writer.writeEndElement();
		writer.flush();
	}
	/**
	 * 写开始标签
	 * @param writer
	 * @param fieldName
	 * @throws XMLStreamException
	 */
	private static void writeStartElement(XMLStreamWriter writer,String fieldName) throws XMLStreamException{
		writer.writeStartElement(fieldName);
		writer.flush();
	}
	/**
	 * 写结束标签
	 * @param writer
	 * @param fieldName
	 * @throws XMLStreamException
	 */
	private static void writeEndElement(XMLStreamWriter writer,String fieldName) throws XMLStreamException{
		writer.writeEndElement();
		writer.flush();
	}
	
}
