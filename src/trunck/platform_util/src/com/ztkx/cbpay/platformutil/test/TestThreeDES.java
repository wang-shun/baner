package com.ztkx.cbpay.platformutil.test;

import com.ztkx.cbpay.platformutil.enanddecrypt.Base64Util;
import com.ztkx.cbpay.platformutil.enanddecrypt.RandomUtil;
import com.ztkx.cbpay.platformutil.enanddecrypt.ThreeDES;

public class TestThreeDES {

	public static void main(String[] args) throws Exception {

		byte[] key = Base64Util.encode(RandomUtil.genRandomNum(112).getBytes());// 密鈅
		byte[] keyiv = "aaaaaaaa".getBytes();// 自定義，（初始向量可以为全0或约定的值）8 //
												// 字节长度的十六进制数
		byte[] data = "中国ABCabc123".getBytes("UTF-8");// 要進行加解密的字符串
		System.out.println("CBC加密解密");
		System.out.println(new String(key));
		byte[] str5 = ThreeDES.des3EncodeCBC(key, keyiv, data);// 加密
		byte[] str6 = ThreeDES.des3DecodeCBC(key, keyiv, str5);// 解密
		System.out.println("加密后：" + new String(Base64Util.encode(str5)));
		System.out.println("解密后：" + new String(str6));

	}

}
