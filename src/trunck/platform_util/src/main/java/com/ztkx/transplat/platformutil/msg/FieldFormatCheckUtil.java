package com.ztkx.transplat.platformutil.msg;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FieldFormatCheckUtil {
	private static Logger logger = Logger.getLogger(FieldFormatCheckUtil.class);
		
	/**
	 * 判断是否需要补位
	 * @param fieldName
	 * @param value
	 * @param ff
	 * @return
	 */
	public static boolean isFillChar(String fieldName,String value,FieldFormat ff){
		if(StringUtils.isNotBlank(ff.getFill_type()) && ff.getFill_char()!=-1){
			if(logger.isDebugEnabled()){
				logger.debug(fieldName + " request fill char the char is ["+(char)ff.getFill_char()+"]");
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 完成字段填充
	 * @param fieldName
	 * @param value
	 * @param ff
	 */
	public static String fillChar(String fieldName,String value,FieldFormat ff){
		if(ff.getFill_type().equals(MsgConstantField.ATTR_FILL_TYPE_LEFT)){
			//从左边填充
		}else if(ff.getFill_type().equals(MsgConstantField.ATTR_FILL_TYPE_RIGHT)){
			//从右边填充
		}
		return null;
	}
	
	
	
	/**
	 * 必输字段检测
	 * @return
	 */
	public static boolean necessaryCheck(String fieldName,String value,FieldFormat ff){
		//校验是否必输
		if(ff.getNecessary()){
			if(StringUtils.isEmpty(value) || "".equals(value)){
				logger.error(fieldName + " field is necessary current value is ["+value+"]");
				return false;
			}
		}
		return true;
	}
	
	public static boolean isLengthOverFlow(String fieldName,String value,FieldFormat ff){
		//校验长度是否超过最大长度
		if (value.length() > ff.getLength()) {
			logger.error(fieldName + " length is ["+ value.length()+ "] exceed max length ["+ ff.getLength() + "] current value is ["+value+"]");
			return true;
		}
		return false;
	}
}
