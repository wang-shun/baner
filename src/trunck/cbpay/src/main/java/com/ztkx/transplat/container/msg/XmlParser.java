package com.ztkx.transplat.container.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.ztkx.transplat.container.preload.ModulesConfPreloader;
import com.ztkx.transplat.platformutil.msg.CompositField;
import com.ztkx.transplat.platformutil.msg.Field;
import com.ztkx.transplat.platformutil.msg.ModulesDescriber;
import com.ztkx.transplat.platformutil.msg.MsgConstantField;
import com.ztkx.transplat.platformutil.msg.MsgXmlDescriber;
import com.ztkx.transplat.platformutil.xml.Dom4jXmlUtil;

/**
 * 将拆包的xml配置解析为MsgXmlDescriber内存对象
 * @author zhangxiaoyun
 */
public class XmlParser {
	
	/**
	 * 根据xml路径，获取MsgXmlDescriber对象
	 * @param xmlPath
	 * @return
	 * @throws NullPointerException
	 */
	public static MsgXmlDescriber parse(String xmlPath) throws NullPointerException{
		
		Element root = Dom4jXmlUtil.getRootElement(xmlPath);
		if(root == null){
			throw new NullPointerException("xml:"+xmlPath+" is null");
		}
		MsgXmlDescriber describer = new MsgXmlDescriber();
		describer.setFilePaht(xmlPath);
		String id = root.attributeValue("id");
		if(StringUtils.isNotEmpty(id)){
			describer.setId(id);
		}else{
			throw new NullPointerException("xml:"+xmlPath+" [id] is null");
		}
		
		
		/**
		 * 2016年3月7日 14:56:57
		 * 新增报文头处理
		 */
		//add by zhangxiaoyun start
		List<Element> listElel = root.elements();
		for (Element element : listElel) {
			
			String eleName = element.getName();
			if(eleName.equals(MsgConstantField.LABEL_INCLUDE)){
				processIncludeEle(describer, element);
			}else if(eleName.equals(MsgConstantField.LABEL_FIELD)){
				processFieldEle(describer, element);
			}else if(eleName.equals(MsgConstantField.LABEL_COMPOSITFIELD)){
				//处理compositField标签
				processCompositField(describer, element);
			}
		}
		
		
		/*
		 * 2016年3月7日15:53:42
		 * 添加报文头功能，代码整合
		//获取include标签
		Element includeEle = root.element("include");
		String include = includeEle.getText();
		if(StringUtils.isNotEmpty(include)){
			describer.setInclude(include.trim());
		}
		
		//轮询获取field标签
		parseField(root, describer);
		
		//获取循环体
		Element compositField = root.element("compositField");
		if(compositField!=null){
			parsetCompositField(describer,root);
		}
		*/
		return describer;
		
	}

	/**
	 * 处理循环标签
	 * @param describer
	 * @param element
	 * 2016年3月7日 下午3:52:11
	 * @author zhangxiaoyun
	 */
	private static void processCompositField(MsgXmlDescriber describer,Element element) {
		CompositField compositField = new CompositField();
		
		String nameStr = element.attributeValue("name");
		if(StringUtils.isNotEmpty(nameStr)){
			compositField.setName(nameStr);
		}
		
		String sizeRefStr = element.attributeValue("sizeRef");
		if(StringUtils.isNotEmpty(sizeRefStr)){
			compositField.setSizeRef(sizeRefStr);
		}
		
		String outputStr = element.attributeValue(MsgConstantField.ATTR_OUTPUT);
		if(StringUtils.isNotEmpty(outputStr)){
			compositField.setOutPut(Boolean.parseBoolean(outputStr));
		}
		
		List<Element> eleList = element.elements("field");
		for(int i = 0;i<eleList.size();i++){
			Element eleField = eleList.get(i);
			
			Field field = XmlParseUtil.obtainField(eleField);
			compositField.setField(field);
		}
		describer.setCf(compositField);
	}

	/**
	 * 处理field标签
	 * @param describer
	 * @param element
	 * 2016年3月7日 下午3:48:09
	 * @author zhangxiaoyun
	 */
	private static void processFieldEle(MsgXmlDescriber describer,Element element) {
		//处理field标签
		Field field = XmlParseUtil.obtainField(element);
		if(StringUtils.isEmpty(field.getFieldFormat().getType())){
			throw new NullPointerException("field "+field.getName()+" type is null");
		}
		//如果字段类型为xml，表示当前字段的数据为一个xml实体。需要继续解析该字段内部的xml字段
		if(field.getFieldFormat().getType().equals(MsgConstantField.TYPE_XML)){
			parseSimpleField(element,field);
		}
		describer.addField(field);
	}

	/**
	 * 处理include标签
	 * @param describer
	 * @param element
	 * 2016年3月7日 下午3:20:13
	 * @author zhangxiaoyun
	 */
	private static void processIncludeEle(MsgXmlDescriber describer,Element element) {
		String includeId = element.getText();
		
		if(StringUtils.isNotEmpty(includeId)){
			describer.setInclude(includeId.trim());
			ModulesDescriber moduleDescriber = ModulesConfPreloader.getModuleById(includeId);
			describer.setList(moduleDescriber.getModulesField());
		}
	}

//	private static void parseField(Element root, MsgXmlDescriber describer) throws NullPointerException {
//		List<Element> eleList = root.elements("field");
//		List<Field> list = new ArrayList<Field>();
//		for (int i = 0; i < eleList.size(); i++) {
//			Element ele = eleList.get(i);
//			//获取field的name属性
//			/**
//			 * 2016-3-7 09:29:13
//			 * 新增module模块的时候代码整合
//			 */
//			Field field = XmlParseUtil.obtainField(ele);
//			
//			if(StringUtils.isEmpty(field.getFieldFormat().getType())){
//				throw new NullPointerException("field "+field.getName()+" type is null");
//			}
//			//如果字段类型为xml，表示当前字段的数据为一个xml实体。需要继续解析该字段内部的xml字段
//			if(field.getFieldFormat().getType().equals(MsgConstantField.TYPE_XML)){
//				parseSimpleField(ele,field);
//			}
//			list.add(field);
//		}
//		describer.setList(list);
//	}
	
	/**
	 * 解析简单field标签
	 * @param ele
	 * @param field
	 */
	private static void parseSimpleField(Element ele, Field field) {
		List<Element> list = ele.elements("field");
		List<Field> fieldList = new ArrayList<Field>();
		if(list != null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				Element simpleFieldEle = list.get(i);
				Field simpleField = XmlParseUtil.obtainField(simpleFieldEle);
				
				/**
				 * 2016-3-7 09:29:13
				 * 新增module模块的时候代码整合
				 */
				/*
				Field simpleField = new Field();
				String name = simpleFieldEle.attributeValue("name");
				simpleField.setName(name);
				//获取fiedl的formate子标签
				parseFormate(simpleFieldEle, simpleField);
				*/
				fieldList.add(simpleField);
			}
			field.setListField(fieldList);
		}
	}

	
	/**
	 * 获取循环体标签
	 * @param describer
	 * @param ele
	 */
//	private static void parsetCompositField(MsgXmlDescriber describer,Element ele){
//		CompositField compositField = new CompositField();
//		
//		Element compositEle = ele.element("compositField");
//		String nameStr = compositEle.attributeValue("name");
//		if(StringUtils.isNotEmpty(nameStr)){
//			compositField.setName(nameStr);
//		}
//		
//		String sizeRefStr = compositEle.attributeValue("sizeRef");
//		if(StringUtils.isNotEmpty(sizeRefStr)){
//			compositField.setSizeRef(sizeRefStr);
//		}
//		
//		List<Element> eleList = compositEle.elements("field");
//		for(int i = 0;i<eleList.size();i++){
//			Element eleField = eleList.get(i);
//			
//			Field field = XmlParseUtil.obtainField(eleField);
//			/**
//			 * 2016-3-7 09:29:13
//			 * 新增module模块的时候代码整合
//			 */
//			/*
//			Field field = new Field();
//			String name = eleField.attributeValue("name");
//			if(StringUtils.isNotEmpty(name)){
//				field.setName(name);
//			}
//			//解析循环体里面的field的format
//			parseFormate(eleField,field);
//			*/
//			compositField.setField(field);
//		}
//		describer.setCf(compositField);
//	}
}
