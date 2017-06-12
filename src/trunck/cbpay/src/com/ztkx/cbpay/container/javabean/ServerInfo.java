package com.ztkx.cbpay.container.javabean;

public class ServerInfo {
	private String serverid;

	private String server_key;

	private String serverdesc;

	private String ip;

	private String prot;

	private String isencrypt;

	private String encrypt_algorithm;

	private String public_key_file;

	private String issignature;

	private String signature_algorithm;

	private String remittance_fee;

	private String res_code;
	
	private String res_msg;

	private String key_store_file;

	private String key_store_alias;

	private String key_store_password;

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

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getServer_key() {
		return server_key;
	}

	public void setServer_key(String server_key) {
		this.server_key = server_key;
	}

	public String getServerdesc() {
		return serverdesc;
	}

	public void setServerdesc(String serverdesc) {
		this.serverdesc = serverdesc;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProt() {
		return prot;
	}

	public void setProt(String prot) {
		this.prot = prot;
	}

	public String getIsencrypt() {
		return isencrypt;
	}

	public void setIsencrypt(String isencrypt) {
		this.isencrypt = isencrypt;
	}

	public String getEncrypt_algorithm() {
		return encrypt_algorithm;
	}

	public void setEncrypt_algorithm(String encrypt_algorithm) {
		this.encrypt_algorithm = encrypt_algorithm;
	}

	public String getPublic_key_file() {
		return public_key_file;
	}

	public void setPublic_key_file(String public_key_file) {
		this.public_key_file = public_key_file;
	}

	public String getIssignature() {
		return issignature;
	}

	public void setIssignature(String issignature) {
		this.issignature = issignature;
	}

	public String getSignature_algorithm() {
		return signature_algorithm;
	}

	public void setSignature_algorithm(String signature_algorithm) {
		this.signature_algorithm = signature_algorithm;
	}

	public String getRemittance_fee() {
		return remittance_fee;
	}

	public void setRemittance_fee(String remittance_fee) {
		this.remittance_fee = remittance_fee;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_msg() {
		return res_msg;
	}

	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}

	@Override
	public String toString() {
		return "ServerInfo [serverid=" + serverid + ", server_key="
				+ server_key + ", serverdesc=" + serverdesc + ", ip=" + ip
				+ ", prot=" + prot + ", isencrypt=" + isencrypt
				+ ", encrypt_algorithm=" + encrypt_algorithm
				+ ", public_key_file=" + public_key_file + ", issignature="
				+ issignature + ", signature_algorithm=" + signature_algorithm
				+ ", remittance_fee=" + remittance_fee + ", res_code="
				+ res_code + ", res_msg=" + res_msg + ", key_store_file="
				+ key_store_file + ", key_store_alias=" + key_store_alias
				+ ", key_store_password=" + key_store_password + "]";
	}



//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ServerInfo other = (ServerInfo) obj;
//
//		if (encrypt_algorithm == null) {
//			if (other.encrypt_algorithm != null)
//				return false;
//		} else if (!encrypt_algorithm.equals(other.encrypt_algorithm))
//			return false;
//		if (ip == null) {
//			if (other.ip != null)
//				return false;
//		} else if (!ip.equals(other.ip))
//			return false;
//		if (isencrypt == null) {
//			if (other.isencrypt != null)
//				return false;
//		} else if (!isencrypt.equals(other.isencrypt))
//			return false;
//		if (issignature == null) {
//			if (other.issignature != null)
//				return false;
//		} else if (!issignature.equals(other.issignature))
//			return false;
//		if (key_store_alias == null) {
//			if (other.key_store_alias != null)
//				return false;
//		} else if (!key_store_alias.equals(other.key_store_alias))
//			return false;
//		if (key_store_file == null) {
//			if (other.key_store_file != null)
//				return false;
//		} else if (!key_store_file.equals(other.key_store_file))
//			return false;
//		if (key_store_password == null) {
//			if (other.key_store_password != null)
//				return false;
//		} else if (!key_store_password.equals(other.key_store_password))
//			return false;
//		if (prot == null) {
//			if (other.prot != null)
//				return false;
//		} else if (!prot.equals(other.prot))
//			return false;
//		if (public_key_file == null) {
//			if (other.public_key_file != null)
//				return false;
//		} else if (!public_key_file.equals(other.public_key_file))
//			return false;
//		if (remittance_fee == null) {
//			if (other.remittance_fee != null)
//				return false;
//		} else if (!remittance_fee.equals(other.remittance_fee))
//			return false;
//		if (res_code == null) {
//			if (other.res_code != null)
//				return false;
//		} else if (!res_code.equals(other.res_code))
//			return false;
//		if (server_key == null) {
//			if (other.server_key != null)
//				return false;
//		} else if (!server_key.equals(other.server_key))
//			return false;
//		if (serverdesc == null) {
//			if (other.serverdesc != null)
//				return false;
//		} else if (!serverdesc.equals(other.serverdesc))
//			return false;
//		if (serverid == null) {
//			if (other.serverid != null)
//				return false;
//		} else if (!serverid.equals(other.serverid))
//			return false;
//		if (signature_algorithm == null) {
//			if (other.signature_algorithm != null)
//				return false;
//		} else if (!signature_algorithm.equals(other.signature_algorithm))
//			return false;
//		return true;
//	}

	

}