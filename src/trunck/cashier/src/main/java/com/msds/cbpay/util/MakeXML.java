package com.msds.cbpay.util;

import java.io.UnsupportedEncodingException;

import com.msds.cbpay.constant.ConstantField;

public class MakeXML {
	
	public static String makeSecretTrancodeXml(String param,String trancode) throws UnsupportedEncodingException{
		param = ConstantField.XML_HEAD+ConstantField.TAG_MSG_BEGIN
				+ConstantField.TAG_TRANCODE_BEGIN
				+trancode+ConstantField.TAG_TRANCODE_END
				+ConstantField.TAG_SECRET_BEGIN+param+ConstantField.TAG_SECRET_END
				+ConstantField.TAG_MSG_END;
		return param;
	}
	
}
