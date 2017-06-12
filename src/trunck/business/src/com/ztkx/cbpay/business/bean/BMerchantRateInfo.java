package com.ztkx.cbpay.business.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 从计费模块查出的商户费率信息
 * 
 * @author tianguangzhao
 *
 */
public class BMerchantRateInfo implements Serializable{
	
	private static final long serialVersionUID = -9110814876628032398L;
	// feeChargeRules ; 符合条件的规则列表名称，
	private String id; // 收费规则ID
	private String merchantNo; // 商户号
	private String roundingMode; // 舍入规则
	private String chargeMode; // 收费模式
	private String overStrategy; // 超出阶梯后收费办法
	private String chargeCycle; // 收费周期
	private String beginAmt; // 保底金额
	private String endAmt; // 封顶金额
	private String tranType; // 交易类型
	private String tranCode; // 交易码
	private String payChannelCode; // 支付渠道编号
	private String bankCardCstType; // 银行客户类型
	private String bankCardType; // 银行账户类型
	private String bankCode; // 银行编码
	private String curType; // 币种
	private String chargeStateStr; // 收费状态
	private String state; // 规则状态
	private String weight; // 权重
	private String availBeginTime; // 生效开始时间
	private String availEndTime; // 生效结束时间
	private String secChargeType; // 区间收费类型
	private ArrayList<BMerchantRateSectionInfo> Feechargerulesections; // 区间模式

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getRoundingMode() {
		return roundingMode;
	}

	public void setRoundingMode(String roundingMode) {
		this.roundingMode = roundingMode;
	}

	public String getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}

	public String getOverStrategy() {
		return overStrategy;
	}

	public void setOverStrategy(String overStrategy) {
		this.overStrategy = overStrategy;
	}

	public String getChargeCycle() {
		return chargeCycle;
	}

	public void setChargeCycle(String chargeCycle) {
		this.chargeCycle = chargeCycle;
	}

	public String getBeginAmt() {
		return beginAmt;
	}

	public void setBeginAmt(String beginAmt) {
		this.beginAmt = beginAmt;
	}

	public String getEndAmt() {
		return endAmt;
	}

	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getPayChannelCode() {
		return payChannelCode;
	}

	public void setPayChannelCode(String payChannelCode) {
		this.payChannelCode = payChannelCode;
	}

	public String getBankCardCstType() {
		return bankCardCstType;
	}

	public void setBankCardCstType(String bankCardCstType) {
		this.bankCardCstType = bankCardCstType;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getChargeStateStr() {
		return chargeStateStr;
	}

	public void setChargeStateStr(String chargeStateStr) {
		this.chargeStateStr = chargeStateStr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAvailBeginTime() {
		return availBeginTime;
	}

	public void setAvailBeginTime(String availBeginTime) {
		this.availBeginTime = availBeginTime;
	}

	public String getAvailEndTime() {
		return availEndTime;
	}

	public void setAvailEndTime(String availEndTime) {
		this.availEndTime = availEndTime;
	}

	public String getSecChargeType() {
		return secChargeType;
	}

	public void setSecChargeType(String secChargeType) {
		this.secChargeType = secChargeType;
	}

	public ArrayList<BMerchantRateSectionInfo> getFeechargerulesections() {
		return Feechargerulesections;
	}

	public void setFeechargerulesections(
			ArrayList<BMerchantRateSectionInfo> feechargerulesections) {
		Feechargerulesections = feechargerulesections;
	}

	@Override
	public String toString() {
		return "MerchantRateInfo [id=" + id + ", merchantNo=" + merchantNo
				+ ", roundingMode=" + roundingMode + ", chargeMode="
				+ chargeMode + ", overStrategy=" + overStrategy
				+ ", chargeCycle=" + chargeCycle + ", beginAmt=" + beginAmt
				+ ", endAmt=" + endAmt + ", tranType=" + tranType
				+ ", tranCode=" + tranCode + ", payChannelCode="
				+ payChannelCode + ", bankCardCstType=" + bankCardCstType
				+ ", bankCardType=" + bankCardType + ", bankCode=" + bankCode
				+ ", curType=" + curType + ", chargeStateStr=" + chargeStateStr
				+ ", state=" + state + ", weight=" + weight
				+ ", availBeginTime=" + availBeginTime + ", availEndTime="
				+ availEndTime + ", secChargeType=" + secChargeType
				+ ", Feechargerulesections=" + Feechargerulesections + "]";
	}

}
