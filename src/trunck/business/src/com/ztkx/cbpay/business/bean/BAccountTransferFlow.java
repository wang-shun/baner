package com.ztkx.cbpay.business.bean;


/**
 * 账户划转登记表实体类
 * 
 * @author tianguangzhao
 *
 */
public class BAccountTransferFlow {
	
	//定义状态
	//账户划转状态
	public static final String TRANS_STATUS_INIT="00"; //账户划转初始状态
	public static final String TRANS_STATUS_PROCESSING="01"; //账户划转进行中
	public static final String TRANS_STATUS_SUCCESS="02"; //划转成功
	public static final String TRANS_STATUS_FALED="03"; // 划转失败
	//对账状态
	public static final String CHECKING_INIT = "00"; // 对账初始化
	public static final String CHECKING_PROCESSING = "01"; // 对账进中
	public static final String CHECKING_SUCCESS = "02"; // 对账成功
	public static final String CHECKING_FAILED = "03"; // 对账失败
	
	//划转类型
	public static final String TRANS_TYPE_BEFORE_FEP = "00";//购汇前  FEP即：Foreign Exchange Purchase
	public static final String TRANS_TYPE_AFTER_FEP = "01";//购汇后
	public static final String TRANS_TYPE_BEFORE_PFE = "02";//付汇前    PFE 即 ：Payment Foreign Exchang
	
	private String transferBatchNo; // '划转批次号，每次划转产生一笔';
	private String txnDate; // '平台日期';
	private String txnTime; // '平台时间';
	private String currency; // '币种';
	private String txnAmt; // '金额';
	private String payAccountNo; // '付款人账户号' ;
	//private String payName;// 付款方户名, //收款方户名  , update by tianguangzhao 2016/4/27 暂时未用到，去掉该属性
	private String transferFlg; // '转入转出标志' ;
	private String actTrafStatus; // '账户划转状态' ;
	private String payTxnDate; // '宝易互通交易日期' ;
	private String paytxnTime; // '宝易互通交易时间' ;
	private String buyBatNo; // '购汇批次号' ;
	private String payBatNo; // '付汇批次号' ;
	private String checkingStatus;// 对账状态
	private String tmSmp; // '时间戳' ;
	private String orderid; // 订单号
	private String exrate; // 汇率
	private String bak; // 备注
	private String recAccountNo; //收款方账号
	//private String recName;//收款方户名  , update by tianguangzhao 2016/4/27 暂时未用到，去掉该属性
 	private String transType;//类型代码；01，购汇前划转；02购汇后划转;03 付汇前划转
	private String transferFlowNo; //划转流水号,同一个划转批次中每笔划转对应一笔流水号，
	//由于对账时的需要，增加收付款客户号
	private String payCustomerNo;//收款客户号
	private String recCustomerNo;//付款客户号
	
	public String getTransferBatchNo() {
		return transferBatchNo;
	}
	public void setTransferBatchNo(String transferBatchNo) {
		this.transferBatchNo = transferBatchNo;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getPayAccountNo() {
		return payAccountNo;
	}
	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}
	public String getTransferFlg() {
		return transferFlg;
	}
	public void setTransferFlg(String transferFlg) {
		this.transferFlg = transferFlg;
	}
	public String getActTrafStatus() {
		return actTrafStatus;
	}
	public void setActTrafStatus(String actTrafStatus) {
		this.actTrafStatus = actTrafStatus;
	}
	public String getPayTxnDate() {
		return payTxnDate;
	}
	public void setPayTxnDate(String payTxnDate) {
		this.payTxnDate = payTxnDate;
	}
	public String getPaytxnTime() {
		return paytxnTime;
	}
	public void setPaytxnTime(String paytxnTime) {
		this.paytxnTime = paytxnTime;
	}
	public String getBuyBatNo() {
		return buyBatNo;
	}
	public void setBuyBatNo(String buyBatNo) {
		this.buyBatNo = buyBatNo;
	}
	public String getPayBatNo() {
		return payBatNo;
	}
	public void setPayBatNo(String payBatNo) {
		this.payBatNo = payBatNo;
	}
	public String getCheckingStatus() {
		return checkingStatus;
	}
	public void setCheckingStatus(String checkingStatus) {
		this.checkingStatus = checkingStatus;
	}
	public String getTmSmp() {
		return tmSmp;
	}
	public void setTmSmp(String tmSmp) {
		this.tmSmp = tmSmp;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getExrate() {
		return exrate;
	}
	public void setExrate(String exrate) {
		this.exrate = exrate;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public String getRecAccountNo() {
		return recAccountNo;
	}
	public void setRecAccountNo(String recAccountNo) {
		this.recAccountNo = recAccountNo;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransferFlowNo() {
		return transferFlowNo;
	}
	public void setTransferFlowNo(String transferFlowNo) {
		this.transferFlowNo = transferFlowNo;
	}
	
	public String getPayCustomerNo() {
		return payCustomerNo;
	}
	public void setPayCustomerNo(String payCustomerNo) {
		this.payCustomerNo = payCustomerNo;
	}
	public String getRecCustomerNo() {
		return recCustomerNo;
	}
	public void setRecCustomerNo(String recCustomerNo) {
		this.recCustomerNo = recCustomerNo;
	}
	@Override
	public String toString() {
		return "BAccountTransferFlow [transferBatchNo=" + transferBatchNo
				+ ", txnDate=" + txnDate + ", txnTime=" + txnTime
				+ ", currency=" + currency + ", txnAmt=" + txnAmt
				+ ", payAccountNo=" + payAccountNo + ", transferFlg="
				+ transferFlg + ", actTrafStatus=" + actTrafStatus
				+ ", payTxnDate=" + payTxnDate + ", paytxnTime=" + paytxnTime
				+ ", buyBatNo=" + buyBatNo + ", payBatNo=" + payBatNo
				+ ", checkingStatus=" + checkingStatus + ", tmSmp=" + tmSmp
				+ ", orderid=" + orderid + ", exrate=" + exrate + ", bak="
				+ bak + ", recAccountNo=" + recAccountNo + ", transType="
				+ transType + ", transferFlowNo=" + transferFlowNo
				+ ", payCustomerNo=" + payCustomerNo + ", recCustomerNo="
				+ recCustomerNo + "]";
	}
}
