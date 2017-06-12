package com.ztkx.cbpay.business.bean;

/**
 * 用户信息表的实体类
 * 
 * @author tianguangzhao
 *
 */
public class BUserInfo {

	//将状态码置为常量，
	//用户可用标识对应usrSts字段
	public static final String USER_STATUS_NORMAL="1";
	public static final String USER_STATUS_DISABLE="0";
	//实名认证状态对应realNmFlg
	public static final String VERIFY_INIT="0";
	public static final String VERIFY_PASS="1";
	public static final String VERIFY_FAILED="2";
	
	
	
	private String merchantid; // 商户号
	private String purchaserid; // 购买者标识
	private String nickName; // 用户昵称
	private String usrSts; // 用户状态
	private String realNmFlg; // 实名标志
	private String realName; // 姓名
	private String idTyp; // 证件类型
	private String idNo; // 证件号码
	private String regDate; // 注册日期
	private String regTime; // 注册时间
	private String regChnl; // 注册渠道
	private String email; // 注册邮箱
	private String tmSmp; // 时间戳
	private String telnum; // 电话号

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getPurchaserid() {
		return purchaserid;
	}

	public void setPurchaserid(String purchaserid) {
		this.purchaserid = purchaserid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUsrSts() {
		return usrSts;
	}

	public void setUsrSts(String usrSts) {
		this.usrSts = usrSts;
	}

	public String getRealNmFlg() {
		return realNmFlg;
	}

	public void setRealNmFlg(String realNmFlg) {
		this.realNmFlg = realNmFlg;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdTyp() {
		return idTyp;
	}

	public void setIdTyp(String idTyp) {
		this.idTyp = idTyp;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getRegChnl() {
		return regChnl;
	}

	public void setRegChnl(String regChnl) {
		this.regChnl = regChnl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTmSmp() {
		return tmSmp;
	}

	public void setTmSmp(String tmSmp) {
		this.tmSmp = tmSmp;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	@Override
	public String toString() {
		return "UserInfo [merchantid=" + merchantid + ", purchaserid="
				+ purchaserid + ", nickName=" + nickName + ", usrSts=" + usrSts
				+ ", realNmFlg=" + realNmFlg + ", realName=" + realName
				+ ", idTyp=" + idTyp + ", idNo=" + idNo + ", regDate="
				+ regDate + ", regTime=" + regTime + ", regChnl=" + regChnl
				+ ", email=" + email + ", tmSmp=" + tmSmp + ", telnum="
				+ telnum + "]";
	}

}
