package com.ztkx.cbpay.container.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.ztkx.cbpay.platformutil.msg.Field;
import com.ztkx.cbpay.platformutil.msg.ModulesDescriber;
import com.ztkx.cbpay.platformutil.msg.MsgConstantField;
import com.ztkx.cbpay.platformutil.xml.Dom4jXmlUtil;

/**
 * 解析modules.xml的工具类
 * @author zhangxiaoyun
 * 2016年3月6日 下午3:20:56
 * <p>Company:ztkx</p>
 */
public class ModulesXmlParser {

	
	/**
	 * 将moudles.xml文件解析为map对象
	 * @param xmlPath
	 * @return
	 * 2016年3月6日 下午3:22:02
	 * @author zhangxiaoyun
	 */
	public static Map<String, ModulesDescriber> parse(String xmlPath) {
		
		Map<String, ModulesDescriber> map = new HashMap<String, ModulesDescriber>();

		Element root = Dom4jXmlUtil.getRootElement(xmlPath);
		if (root == null) {
			throw new NullPointerException("xml:" + xmlPath + " is null");
		}
		List<Element> modulesList = root.elements(MsgConstantField.LABEL_MODULE);
		for (int i = 0; i < modulesList.size(); i++) {
			Element moduleEle = modulesList.get(i);
			String id = moduleEle.attributeValue(MsgConstantField.ATTR_ID);
			//处理field列表
			List<Element> fieldsListEle = moduleEle.elements(MsgConstantField.LABEL_FIELD);
			List<Field> fields = new ArrayList<Field>();
			
			for (Element element : fieldsListEle) {
				Field field = XmlParseUtil.obtainField(element);
				fields.add(field);
			}
			
			ModulesDescriber describer = new ModulesDescriber(id,fields);
			
			map.put(id, describer);
		}
		return map;
	}	
}
