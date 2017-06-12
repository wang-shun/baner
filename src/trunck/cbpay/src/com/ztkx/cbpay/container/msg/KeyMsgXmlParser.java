package com.ztkx.cbpay.container.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.ztkx.cbpay.platformutil.msg.Field;
import com.ztkx.cbpay.platformutil.msg.FieldFormat;
import com.ztkx.cbpay.platformutil.msg.KeyField;
import com.ztkx.cbpay.platformutil.msg.KeyMsgDescriber;
import com.ztkx.cbpay.platformutil.msg.MsgConstantField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * @author zhangxiaoyun
 */
public class KeyMsgXmlParser {

	/**
	 * 根据xml路径，获取KeyMsgDescriber对象
	 * @param xmlPath
	 * @return
	 * @throws NullPointerException
	 */
	public static Map<String, KeyMsgDescriber> parse(String xmlPath) {
		Map<String, KeyMsgDescriber> map = new HashMap<String, KeyMsgDescriber>();

		Element root = Dom4jXmlUtil.getRootElement(xmlPath);
		if (root == null) {
			throw new NullPointerException("xml:" + xmlPath + " is null");
		}
		List<Element> sysEle = root.elements(MsgConstantField.LABEL_SYSTEM);
		for (int i = 0; i < sysEle.size(); i++) {
			Element ele = sysEle.get(i);
			String id = ele.attributeValue(MsgConstantField.ATTR_ID);
			String from = ele.attributeValue(MsgConstantField.ATTR_FROM);
			String type = ele.attributeValue(MsgConstantField.ATTR_TYPE);
			String format = ele.attributeValue(MsgConstantField.ATTR_FORMAT);
			String encoding = ele.attributeValue(MsgConstantField.ATTR_ENCODING);
			Element identifyEle = ele.element(MsgConstantField.LABEL_IDENTIFY);
			String identify = null;
			if(identifyEle != null){
				identify= identifyEle.getText();
			}
			
			KeyMsgDescriber describer = new KeyMsgDescriber(id, from, type,format, encoding,identify);
			// 获取key field
			Element keyfieldEle = ele.element(MsgConstantField.LABEL_KEYFIELD);
			List<Field> fieldList = new ArrayList<Field>();

			List<Element> simpleFieldEleList = keyfieldEle.elements(MsgConstantField.LABEL_FIELD);

			for (int j = 0; j < simpleFieldEleList.size(); j++) {
				Field simpleField = new Field();
				Element simpleFieldEle = simpleFieldEleList.get(j);
				String name = simpleFieldEle.attributeValue(MsgConstantField.ATTR_NAME);
				simpleField.setName(name);
				parseFormate(simpleFieldEle, simpleField);
				fieldList.add(simpleField);
			}
			KeyField keyField = new KeyField(fieldList);
			describer.setKeyField(keyField);
			map.put(id, describer);
		}
		return map;
	}

	private static void parseFormate(Element ele, Field field) {
		Element formateEle = ele.element(MsgConstantField.LABEL_FORMAT);
		FieldFormat ff = new FieldFormat();

		String type = formateEle.attributeValue(MsgConstantField.ATTR_TYPE);
		if (StringUtils.isNotEmpty(type)) {
			ff.setType(type.trim());
		} else {
			throw new NullPointerException("field " + field.getName()
					+ " type is null");
		}

		/**
		 * 如果字段类型是MsgConstantField.TYPE_VIRTUAL，表示该字段的是虚拟字段不需要解析后面格式
		 */
		if (ff.getType().equals(MsgConstantField.TYPE_VIRTUAL)) {
			field.setFieldFormat(ff);
			return;
		}

		String frommsg = formateEle.attributeValue(MsgConstantField.ATTR_FROMMSG);
		if (StringUtils.isNotEmpty(frommsg)) {
			ff.setFrommsg(Boolean.parseBoolean(frommsg.trim()));
		}

		String default_value = formateEle.attributeValue(MsgConstantField.ATTR_DEFAULT_VALUE);
		if (default_value != null) {
			ff.setDefault_value(default_value);
		}

		String fillCharStr = formateEle.attributeValue("fill_char");
		if (StringUtils.isNotEmpty(fillCharStr)) {
			if (fillCharStr.startsWith(MsgConstantField.Hex)) {
				ff.setFill_char(Integer.parseInt(
						fillCharStr.trim().substring(
								MsgConstantField.Hex.length()), 16));
			}
		}

		String fill_type = formateEle.attributeValue(MsgConstantField.ATTR_FILL_TYPE);
		if (StringUtils.isNotEmpty(fill_type)) {
			ff.setFill_type(fill_type.trim());
		}

		String function = formateEle.attributeValue(MsgConstantField.ATTR_FUNCTION);
		if (StringUtils.isNotEmpty(function)) {
			ff.setFunction(function.trim());
		}

		String length = formateEle.attributeValue(MsgConstantField.ATTR_LENGTH);
		if (StringUtils.isNotEmpty(length)) {
			ff.setLength(Integer.parseInt(length.trim()));
		}

		String necessary = formateEle.attributeValue(MsgConstantField.ATTR_NECESSARY);
		if (StringUtils.isNotEmpty(necessary)) {
			ff.setNecessary(Boolean.parseBoolean(necessary.trim()));
		}

		String convert = formateEle.attributeValue(MsgConstantField.ATTR_CONVERT);
		if (StringUtils.isNotEmpty(convert)) {
			ff.setConvert(convert.trim());
		}

		field.setFieldFormat(ff);
	}
}
