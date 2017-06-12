package com.ztkx.cbpay.business.bean;

/**
 * 短信验证码表实体类
 * 
 * @author tianguangzhao
 *
 */
public class BVerificationCode {
	//设置状态常量,
	//正常状态
	public static final String CODE_SATUS_NOMARL ="0";
	//不可用状态
	public static final String CODE_SATUS_DISABLE ="1";
	
	//验证类型。
	//短信验证
	public static final String VALIDATE_SMS="1";
	//语音验证
	public static final String VALIDATE_SOUND="2";
	// 验证业务流水号
	private String verJnlNo;
	// 用户标识号
	private String mblNo;
	// 短信验证渠道
	private String verChnl;
	// 验证业务类型
	private String verBizTyp;
	// 验证码
	private String verCode;
	// 验证码生成时间
	private String prdDate;
	// 验证码生成时间
	private String prdTime;
	// 生效标志
	private String status;
	// 时间戳
	private String tmSmp;

	public String getVerJnlNo() {
		return verJnlNo;
	}

	public void setVerJnlNo(String verJnlNo) {
		this.verJnlNo = verJnlNo;
	}

	public String getMblNo() {
		return mblNo;
	}

	public void setMblNo(String mblNo) {
		this.mblNo = mblNo;
	}

	public String getVerChnl() {
		return verChnl;
	}

	public void setVerChnl(String verChnl) {
		this.verChnl = verChnl;
	}

	public String getVerBizTyp() {
		return verBizTyp;
	}

	public void setVerBizTyp(String verBizTyp) {
		this.verBizTyp = verBizTyp;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public String getPrdTime() {
		return prdTime;
	}

	public void setPrdTime(String prdTime) {
		this.prdTime = prdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTmSmp() {
		return tmSmp;
	}

	public void setTmSmp(String tmSmp) {
		this.tmSmp = tmSmp;
	}

	public String getPrdDate() {
		return prdDate;
	}

	public void setPrdDate(String prdDate) {
		this.prdDate = prdDate;
	}

	@Override
	public String toString() {
		return "VerificationCode [verJnlNo=" + verJnlNo + ", mblNo=" + mblNo
				+ ", verChnl=" + verChnl + ", verBizTyp=" + verBizTyp
				+ ", verCode=" + verCode + ", prdDate=" + prdDate
				+ ", prdTime=" + prdTime + ", status=" + status + ", tmSmp="
				+ tmSmp + "]";
	}

}
