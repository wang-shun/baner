package com.ztkx.cbpay.platformutil.test;


public class TEst {
	public static void main(String[] args) {
		String s = "0x30";
//		int j = IntegerUtils.parseInt(s, 16);
//		int i = Integer.parseInt("30",16);
//		DataSourceUtil.getInstance();
//		int i = 0x30;
//		int j = Integer.parseInt("20", 16);
//		System.out.println("i:["+(char)i+"]");
//		System.out.println("j:["+(char)j+"]");
	}
	
	public static final String bytesToHexString(byte[] bArray) {   
	    StringBuffer sb = new StringBuffer(bArray.length);   
	    String sTemp;   
	    for (int i = 0; i < bArray.length; i++) {   
	     sTemp = Integer.toHexString(0xFF & bArray[i]);   
	     if (sTemp.length() < 2)   
	      sb.append(0);   
	     sb.append(sTemp.toUpperCase());   
	    }   
	    return sb.toString();   
	}  
	
	
	public static byte[] hexStringToByte(String hex) {   
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	}  
	
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	}  
}
