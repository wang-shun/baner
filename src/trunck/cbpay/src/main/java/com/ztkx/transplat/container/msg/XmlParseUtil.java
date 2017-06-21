package com.ztkx.transplat.container.msg;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.ztkx.transplat.platformutil.msg.Field;
import com.ztkx.transplat.platformutil.msg.FieldFormat;
import com.ztkx.transplat.platformutil.msg.MsgConstantField;

/**
 * 解析各种拆组包配置的工具类
 * @author zhangxiaoyun
 * 2016年3月7日 上午9:20:42
 * <p>Company:ztkx</p>
 */
public class XmlParseUtil {
	
	/**
	 * 根据field标签解析获取field
	 * @param ele
	 * @return
	 * 2016年3月7日 上午9:20:06
	 * @author zhangxiaoyun
	 */
	public static Field obtainField(Element ele){
		
		Field field = new Field();
		String name = ele.attributeValue("name");
		field.setName(name);

		Element formateEle = ele.element("format");
		FieldFormat ff = new FieldFormat();
		
		String type = formateEle.attributeValue("type");
		if(StringUtils.isNotEmpty(type)){
			ff.setType(type.trim());
		}else{
			throw new NullPointerException("field "+field.getName()+" type is null");
		}
		
		/**
		 * 如果字段类型是MsgConstantField.TYPE_VIRTUAL，表示该字段的是虚拟字段不需要解析后面格式
		 */
		if(ff.getType().equals(MsgConstantField.TYPE_VIRTUAL)){
			
			String level = formateEle.attributeValue(MsgConstantField.ATTR_LEVEL);
			//解析level属性
			if(StringUtils.isNotEmpty(level)){
				int levelInt = Integer.parseInt(level);
				ff.setLevel(levelInt);
			}
		}
		
		String frommsg = formateEle.attributeValue("frommsg");
		if(StringUtils.isNotEmpty(frommsg)){
			ff.setFrommsg(Boolean.parseBoolean(frommsg.trim()));
		}
		
		String default_value = formateEle.attributeValue(MsgConstantField.ATTR_DEFAULT_VALUE);
		if(default_value != null){
			ff.setDefault_value(default_value);
		}
		
		String fillCharStr = formateEle.attributeValue("fill_char");
		if(StringUtils.isNotEmpty(fillCharStr)){
			if(fillCharStr.startsWith(MsgConstantField.Hex)){
				ff.setFill_char(Integer.parseInt(fillCharStr.trim().substring(MsgConstantField.Hex.length()), 16));
			}
		}
		
		String fill_type = formateEle.attributeValue("fill_type");
		if(StringUtils.isNotEmpty(fill_type)){
			ff.setFill_type(fill_type.trim());
		}
		
		String function = formateEle.attributeValue("function");
		if(StringUtils.isNotEmpty(function)){
			ff.setFunction(function.trim());
		}
					
		String length = formateEle.attributeValue("length");
		if(StringUtils.isNotEmpty(length)){
			ff.setLength(Integer.parseInt(length.trim()));
		}
		
		String necessary = formateEle.attributeValue("necessary");
		if(StringUtils.isNotEmpty(necessary)){
			ff.setNecessary(Boolean.parseBoolean(necessary.trim()));
		}
		
		String convert = formateEle.attributeValue("convert");
		if(StringUtils.isNotEmpty(convert)){
			ff.setConvert(convert.trim());
		}

		String superField = formateEle.attributeValue(MsgConstantField.ATTR_SUPER_FIELD);
		if(StringUtils.isNotEmpty(superField)){
			ff.setSuper_field(superField.trim());
		}

		String superLevel = formateEle.attributeValue(MsgConstantField.ATTR_SUPER_LEVEL);
		if(StringUtils.isNotEmpty(superLevel)){
			ff.setSuper_level(Integer.parseInt(superField.trim()));
		}

		field.setFieldFormat(ff);
		
		return field;
	}
}
