package com.ztkx.cbpay.business.bean;

import java.io.Serializable;

/**
 * 商户费率的具体收费区间信息。
 * 
 * @author tianguangzhao
 *
 */
public class BMerchantRateSectionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2970541278485220127L;
	private String ruleId; // 规则ID
	private String beginInput; // 开始笔数或金额
	private String endInput; // 结束笔数或金额
	private String secChargeMode; // 区间收费模式
	private String secChargeAmt; // 区间收费金额
	private String secChargeRatio; // 区间收费比率

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getBeginInput() {
		return beginInput;
	}

	public void setBeginInput(String beginInput) {
		this.beginInput = beginInput;
	}

	public String getEndInput() {
		return endInput;
	}

	public void setEndInput(String endInput) {
		this.endInput = endInput;
	}

	public String getSecChargeMode() {
		return secChargeMode;
	}

	public void setSecChargeMode(String secChargeMode) {
		this.secChargeMode = secChargeMode;
	}

	public String getSecChargeAmt() {
		return secChargeAmt;
	}

	public void setSecChargeAmt(String secChargeAmt) {
		this.secChargeAmt = secChargeAmt;
	}

	public String getSecChargeRatio() {
		return secChargeRatio;
	}

	public void setSecChargeRatio(String secChargeRatio) {
		this.secChargeRatio = secChargeRatio;
	}

	@Override
	public String toString() {
		return "MerchantRateSectionInfo [ruleId=" + ruleId + ", beginInput="
				+ beginInput + ", endInput=" + endInput + ", secChargeMode="
				+ secChargeMode + ", secChargeAmt=" + secChargeAmt
				+ ", secChargeRatio=" + secChargeRatio + "]";
	}

}
