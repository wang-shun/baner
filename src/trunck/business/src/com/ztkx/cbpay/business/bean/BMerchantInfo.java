package com.ztkx.cbpay.business.bean;

public class BMerchantInfo {
	private String merchantid;
	private String merchantname;
	private String merchantdesc;
	private String isencrypt;
	private String encrypt_algorithm;
	private String issignature;
	private String signature_algorithm;
	private String public_key_file;
	private String key_file_type;
	private String encrypt_type;
	private String signature_type;
	private String key_store_file;
	private String key_store_alias;
	private String key_store_password;
	private String country_code;
	private String currency_type;
	private String merplatacctalias;
	private String protocolno;
	private String valid;
	private String poundageflag;
	private String poundagerate;
	private String contract_no;//合同号
	private String merchant_principal;//商户联系人
	private String principal_tel;//商户联系人电话
	private String bank_card_csttype;//银行客户类型
    private String bank_card_type;//银行账户类型
    private String local_currency_account_no;//商户在宝易互通开具的本币虚账户
    private String foreign_currency_account_no;//商户在宝易互通开具的外币虚账户
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BMerchantInfo [merchantid=");
		builder.append(merchantid);
		builder.append(", merchantname=");
		builder.append(merchantname);
		builder.append(", merchantdesc=");
		builder.append(merchantdesc);
		builder.append(", isencrypt=");
		builder.append(isencrypt);
		builder.append(", encrypt_algorithm=");
		builder.append(encrypt_algorithm);
		builder.append(", issignature=");
		builder.append(issignature);
		builder.append(", signature_algorithm=");
		builder.append(signature_algorithm);
		builder.append(", public_key_file=");
		builder.append(public_key_file);
		builder.append(", key_file_type=");
		builder.append(key_file_type);
		builder.append(", encrypt_type=");
		builder.append(encrypt_type);
		builder.append(", signature_type=");
		builder.append(signature_type);
		builder.append(", key_store_file=");
		builder.append(key_store_file);
		builder.append(", key_store_alias=");
		builder.append(key_store_alias);
		builder.append(", key_store_password=");
		builder.append(key_store_password);
		builder.append(", country_code=");
		builder.append(country_code);
		builder.append(", currency_type=");
		builder.append(currency_type);
		builder.append(", merplatacctalias=");
		builder.append(merplatacctalias);
		builder.append(", protocolno=");
		builder.append(protocolno);
		builder.append(", valid=");
		builder.append(valid);
		builder.append(", poundageflag=");
		builder.append(poundageflag);
		builder.append(", poundagerate=");
		builder.append(poundagerate);
		builder.append(", contract_no=");
		builder.append(contract_no);
		builder.append(", merchant_principal=");
		builder.append(merchant_principal);
		builder.append(", principal_tel=");
		builder.append(principal_tel);
		builder.append(", bank_card_csttype=");
		builder.append(bank_card_csttype);
		builder.append(", bank_card_type=");
		builder.append(bank_card_type);
		builder.append(", local_currency_account_no=");
		builder.append(local_currency_account_no);
		builder.append(", foreign_currency_account_no=");
		builder.append(foreign_currency_account_no);
		builder.append("]");
		return builder.toString();
	}

	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getMerchant_principal() {
		return merchant_principal;
	}
	public void setMerchant_principal(String merchant_principal) {
		this.merchant_principal = merchant_principal;
	}
	public String getPrincipal_tel() {
		return principal_tel;
	}
	public void setPrincipal_tel(String principal_tel) {
		this.principal_tel = principal_tel;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getMerchantdesc() {
		return merchantdesc;
	}

	public void setMerchantdesc(String merchantdesc) {
		this.merchantdesc = merchantdesc;
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

	public String getPublic_key_file() {
		return public_key_file;
	}

	public void setPublic_key_file(String public_key_file) {
		this.public_key_file = public_key_file;
	}

	public String getKey_file_type() {
		return key_file_type;
	}

	public void setKey_file_type(String key_file_type) {
		this.key_file_type = key_file_type;
	}

	public String getEncrypt_type() {
		return encrypt_type;
	}

	public void setEncrypt_type(String encrypt_type) {
		this.encrypt_type = encrypt_type;
	}

	public String getSignature_type() {
		return signature_type;
	}

	public void setSignature_type(String signature_type) {
		this.signature_type = signature_type;
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

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCurrency_type() {
		return currency_type;
	}

	public void setCurrency_type(String currency_type) {
		this.currency_type = currency_type;
	}

	public String getMerplatacctalias() {
		return merplatacctalias;
	}

	public void setMerplatacctalias(String merplatacctalias) {
		this.merplatacctalias = merplatacctalias;
	}

	public String getProtocolno() {
		return protocolno;
	}

	public void setProtocolno(String protocolno) {
		this.protocolno = protocolno;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getPoundageflag() {
		return poundageflag;
	}

	public void setPoundageflag(String poundageflag) {
		this.poundageflag = poundageflag;
	}

	public String getPoundagerate() {
		return poundagerate;
	}

	public void setPoundagerate(String poundagerate) {
		this.poundagerate = poundagerate;
	}

	public String getBank_card_csttype() {
		return bank_card_csttype;
	}

	public void setBank_card_csttype(String bank_card_csttype) {
		this.bank_card_csttype = bank_card_csttype;
	}

	public String getBank_card_type() {
		return bank_card_type;
	}

	public void setBank_card_type(String bank_card_type) {
		this.bank_card_type = bank_card_type;
	}

	public String getLocal_currency_account_no() {
		return local_currency_account_no;
	}

	public void setLocal_currency_account_no(String local_currency_account_no) {
		this.local_currency_account_no = local_currency_account_no;
	}

	public String getForeign_currency_account_no() {
		return foreign_currency_account_no;
	}

	public void setForeign_currency_account_no(String foreign_currency_account_no) {
		this.foreign_currency_account_no = foreign_currency_account_no;
	}
	
}