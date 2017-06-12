package com.ztkx.cbpay.container.javabean;

public class TranFromInfo {

	private String from_type;
	private String from_id;
	private String from_desc;
	private boolean isEncrypt;			//是否加密
	private String encrypt_type;
	private String encrypt_algorithm;
	private boolean isSignature;		//是否验签
	private String signature_algorithm;
	private String public_key_file;
	private String signature_type;
	private String chanel_code;
	private String chanel_des;
	private String key_store_file;
	private String key_store_alias;
	private String key_store_password;

	
	
	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public boolean isSignature() {
		return isSignature;
	}

	public void setSignature(boolean isSignature) {
		this.isSignature = isSignature;
	}

	public String getKey_store_file() {
		return key_store_file;
	}

	public void setKey_store_file(String key_store_file) {
		this.key_store_file = key_store_file;
	}

	public String getKey_store_alias() {
		return key_store_alias;
	}

	public void setKey_store_alias(String key_store_alias) {
		this.key_store_alias = key_store_alias;
	}

	public String getKey_store_password() {
		return key_store_password;
	}

	public void setKey_store_password(String key_store_password) {
		this.key_store_password = key_store_password;
	}

	public String getFrom_type() {
		return from_type;
	}

	public void setFrom_type(String from_type) {
		this.from_type = from_type;
	}

	public String getFrom_id() {
		return from_id;
	}

	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

	public String getFrom_desc() {
		return from_desc;
	}

	public void setFrom_desc(String from_desc) {
		this.from_desc = from_desc;
	}

	

	public String getEncrypt_type() {
		return encrypt_type;
	}

	public void setEncrypt_type(String encrypt_type) {
		this.encrypt_type = encrypt_type;
	}

	public String getEncrypt_algorithm() {
		return encrypt_algorithm;
	}

	public void setEncrypt_algorithm(String encrypt_algorithm) {
		this.encrypt_algorithm = encrypt_algorithm;
	}

	

	public String getSignature_algorithm() {
		return signature_algorithm;
	}

	public void setSignature_algorithm(String signature_algorithm) {
		this.signature_algorithm = signature_algorithm;
	}

	public String getPublic_key_file() {
		return public_key_file;
	}

	public void setPublic_key_file(String public_key_file) {
		this.public_key_file = public_key_file;
	}

	public String getSignature_type() {
		return signature_type;
	}

	public void setSignature_type(String signature_type) {
		this.signature_type = signature_type;
	}

	public String getChanel_code() {
		return chanel_code;
	}

	public void setChanel_code(String chanel_code) {
		this.chanel_code = chanel_code;
	}

	public String getChanel_des() {
		return chanel_des;
	}

	public void setChanel_des(String chanel_des) {
		this.chanel_des = chanel_des;
	}
	
	@Override
	public String toString() {
		return "TranFromInfo [from_type=" + from_type + ", from_id=" + from_id
				+ ", from_desc=" + from_desc + ", isEncrypt=" + isEncrypt
				+ ", encrypt_type=" + encrypt_type + ", encrypt_algorithm="
				+ encrypt_algorithm + ", isSignature=" + isSignature
				+ ", signature_algorithm=" + signature_algorithm
				+ ", public_key_file=" + public_key_file + ", signature_type="
				+ signature_type + ", chanel_code=" + chanel_code
				+ ", chanel_des=" + chanel_des + ", key_store_file="
				+ key_store_file + ", key_store_alias=" + key_store_alias
				+ ", key_store_password=" + key_store_password + "]";
	}

}
