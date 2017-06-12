package com.ztkx.cbpay.business.serviceimp.PLATFORM.act009;

/**
 * 购汇对账文件信息
 * @author zhangxiaoyun
 *
 */
public class FileInfo {
	
	private String BECIF_NO;
	private String EX_SEQNO;
	private String REG_DT;
	private String MARKET_RATE;
	private String CUST_RATE;
	private String CUST_FAVFLAG;
	private String CUST_FAVVAL;
	private String MARKET_AMT;
	private String CUST_AMT;
	private String SELL_CURR;
	private String BUY_CURR;
	private String SELL_ACCT;
	private String BUY_ACCT;
	private String SELL_AMT;
	private String BUY_AMT;
	private String RATE_DATE;
	private String EXCH_LINE;
	private String RET_CODE;
	private String RET_MSG;
	
	public String getBECIF_NO() {
		return BECIF_NO;
	}
	public void setBECIF_NO(String bECIF_NO) {
		BECIF_NO = bECIF_NO;
	}
	public String getEX_SEQNO() {
		return EX_SEQNO;
	}
	public void setEX_SEQNO(String eX_SEQNO) {
		EX_SEQNO = eX_SEQNO;
	}
	public String getREG_DT() {
		return REG_DT;
	}
	public void setREG_DT(String rEG_DT) {
		REG_DT = rEG_DT;
	}
	public String getMARKET_RATE() {
		return MARKET_RATE;
	}
	public void setMARKET_RATE(String mARKET_RATE) {
		MARKET_RATE = mARKET_RATE;
	}
	public String getCUST_RATE() {
		return CUST_RATE;
	}
	public void setCUST_RATE(String cUST_RATE) {
		CUST_RATE = cUST_RATE;
	}
	public String getCUST_FAVFLAG() {
		return CUST_FAVFLAG;
	}
	public void setCUST_FAVFLAG(String cUST_FAVFLAG) {
		CUST_FAVFLAG = cUST_FAVFLAG;
	}
	public String getCUST_FAVVAL() {
		return CUST_FAVVAL;
	}
	public void setCUST_FAVVAL(String cUST_FAVVAL) {
		CUST_FAVVAL = cUST_FAVVAL;
	}
	public String getMARKET_AMT() {
		return MARKET_AMT;
	}
	public void setMARKET_AMT(String mARKET_AMT) {
		MARKET_AMT = mARKET_AMT;
	}
	public String getCUST_AMT() {
		return CUST_AMT;
	}
	public void setCUST_AMT(String cUST_AMT) {
		CUST_AMT = cUST_AMT;
	}
	public String getSELL_CURR() {
		return SELL_CURR;
	}
	public void setSELL_CURR(String sELL_CURR) {
		SELL_CURR = sELL_CURR;
	}
	public String getBUY_CURR() {
		return BUY_CURR;
	}
	public void setBUY_CURR(String bUY_CURR) {
		BUY_CURR = bUY_CURR;
	}
	public String getSELL_ACCT() {
		return SELL_ACCT;
	}
	public void setSELL_ACCT(String sELL_ACCT) {
		SELL_ACCT = sELL_ACCT;
	}
	public String getBUY_ACCT() {
		return BUY_ACCT;
	}
	public void setBUY_ACCT(String bUY_ACCT) {
		BUY_ACCT = bUY_ACCT;
	}
	public String getSELL_AMT() {
		return SELL_AMT;
	}
	public void setSELL_AMT(String sELL_AMT) {
		SELL_AMT = sELL_AMT;
	}
	public String getBUY_AMT() {
		return BUY_AMT;
	}
	public void setBUY_AMT(String bUY_AMT) {
		BUY_AMT = bUY_AMT;
	}
	public String getRATE_DATE() {
		return RATE_DATE;
	}
	public void setRATE_DATE(String rATE_DATE) {
		RATE_DATE = rATE_DATE;
	}
	public String getEXCH_LINE() {
		return EXCH_LINE;
	}
	public void setEXCH_LINE(String eXCH_LINE) {
		EXCH_LINE = eXCH_LINE;
	}
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}
	public String getRET_MSG() {
		return RET_MSG;
	}
	public void setRET_MSG(String rET_MSG) {
		RET_MSG = rET_MSG;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [BECIF_NO=");
		builder.append(BECIF_NO);
		builder.append(", EX_SEQNO=");
		builder.append(EX_SEQNO);
		builder.append(", REG_DT=");
		builder.append(REG_DT);
		builder.append(", MARKET_RATE=");
		builder.append(MARKET_RATE);
		builder.append(", CUST_RATE=");
		builder.append(CUST_RATE);
		builder.append(", CUST_FAVFLAG=");
		builder.append(CUST_FAVFLAG);
		builder.append(", CUST_FAVVAL=");
		builder.append(CUST_FAVVAL);
		builder.append(", MARKET_AMT=");
		builder.append(MARKET_AMT);
		builder.append(", CUST_AMT=");
		builder.append(CUST_AMT);
		builder.append(", SELL_CURR=");
		builder.append(SELL_CURR);
		builder.append(", BUY_CURR=");
		builder.append(BUY_CURR);
		builder.append(", SELL_ACCT=");
		builder.append(SELL_ACCT);
		builder.append(", BUY_ACCT=");
		builder.append(BUY_ACCT);
		builder.append(", SELL_AMT=");
		builder.append(SELL_AMT);
		builder.append(", BUY_AMT=");
		builder.append(BUY_AMT);
		builder.append(", RATE_DATE=");
		builder.append(RATE_DATE);
		builder.append(", EXCH_LINE=");
		builder.append(EXCH_LINE);
		builder.append(", RET_CODE=");
		builder.append(RET_CODE);
		builder.append(", RET_MSG=");
		builder.append(RET_MSG);
		builder.append("]");
		return builder.toString();
	}
}
