package com.ztkx.transplat.container.javabean;

public class ForeignCurrencyRate {
    private String from_id;//来源id
    private String e3rdPayNo;//第三方支付号
	private String currency_code;//币种代码
	private String recv_date;//接收日期
	private String recv_time;//接收时间
	private String transtime;//银行发起时间
	private String cashbuyprice;//钞买价
	private String exbuyprice;//汇买价
	private String cashsellprice;//钞卖价
	private String exsellprice;//汇卖价	
	private String exQuoteDate;//牌价日期
	private String exQuoteTime;//牌价时间
	
	public String getE3rdPayNo() {
		return e3rdPayNo;
	}
	public void setE3rdPayNo(String e3rdPayNo) {
		this.e3rdPayNo = e3rdPayNo;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public String getExQuoteDate() {
		return exQuoteDate;
	}
	public void setExQuoteDate(String exQuoteDate) {
		this.exQuoteDate = exQuoteDate;
	}
	public String getExQuoteTime() {
		return exQuoteTime;
	}
	public void setExQuoteTime(String exQuoteTime) {
		this.exQuoteTime = exQuoteTime;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getRecv_date() {
		return recv_date;
	}
	public void setRecv_date(String recv_date) {
		this.recv_date = recv_date;
	}
	public String getRecv_time() {
		return recv_time;
	}
	public void setRecv_time(String recv_time) {
		this.recv_time = recv_time;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getCashbuyprice() {
		return cashbuyprice;
	}
	public void setCashbuyprice(String cashbuyprice) {
		this.cashbuyprice = cashbuyprice;
	}
	public String getExbuyprice() {
		return exbuyprice;
	}
	public void setExbuyprice(String exbuyprice) {
		this.exbuyprice = exbuyprice;
	}
	public String getCashsellprice() {
		return cashsellprice;
	}
	public void setCashsellprice(String cashsellprice) {
		this.cashsellprice = cashsellprice;
	}
	public String getExsellprice() {
		return exsellprice;
	}
	public void setExsellprice(String exsellprice) {
		this.exsellprice = exsellprice;
	}
	
}
