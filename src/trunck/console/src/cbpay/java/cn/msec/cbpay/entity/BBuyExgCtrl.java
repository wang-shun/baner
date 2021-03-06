package cn.msec.cbpay.entity;



import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import cn.msec.ojpa.api.annotations.Tab;

@Tab(name="B_BUY_EXG_CTRL")
@AllArgsConstructor
@NoArgsConstructor
public class BBuyExgCtrl extends BBuyExgCtrlKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUYTIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buytime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.QUOTECHNL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String quotechnl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BECIF
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String becif;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.TOT_NUM
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private Short totNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.SALE_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String saleCcy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUY_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buyCcy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUY_SELL_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buySellFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.TOTAL_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal totalAmt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.SPOT_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String spotFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.REGISTER_DATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String registerDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.PRICE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.CLIENT_EXCHANGE_RATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal clientExchangeRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.DISCOUNT_TYPE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String discountType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.DIS_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal disAmt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal amt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.TRAN_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal tranAmt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.SELL_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String sellAcctNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUY_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buyAcctNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.SALE_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal saleAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUY_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private BigDecimal buyAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.RATE_TIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String rateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.EXCEED_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String exceedFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.PROCESS_STATUS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String processStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.PROCESS_MSG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String processMsg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.TXN_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String txnSts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.CHK_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String chkSts;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUYTIME
     *
     * @return the value of B_BUY_EXG_CTRL.BUYTIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuytime() {
        return buytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUYTIME
     *
     * @param buytime the value for B_BUY_EXG_CTRL.BUYTIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuytime(String buytime) {
        this.buytime = buytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.QUOTECHNL
     *
     * @return the value of B_BUY_EXG_CTRL.QUOTECHNL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getQuotechnl() {
        return quotechnl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.QUOTECHNL
     *
     * @param quotechnl the value for B_BUY_EXG_CTRL.QUOTECHNL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setQuotechnl(String quotechnl) {
        this.quotechnl = quotechnl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BECIF
     *
     * @return the value of B_BUY_EXG_CTRL.BECIF
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBecif() {
        return becif;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BECIF
     *
     * @param becif the value for B_BUY_EXG_CTRL.BECIF
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBecif(String becif) {
        this.becif = becif;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.TOT_NUM
     *
     * @return the value of B_BUY_EXG_CTRL.TOT_NUM
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public Short getTotNum() {
        return totNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.TOT_NUM
     *
     * @param totNum the value for B_BUY_EXG_CTRL.TOT_NUM
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setTotNum(Short totNum) {
        this.totNum = totNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.SALE_CCY
     *
     * @return the value of B_BUY_EXG_CTRL.SALE_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getSaleCcy() {
        return saleCcy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.SALE_CCY
     *
     * @param saleCcy the value for B_BUY_EXG_CTRL.SALE_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setSaleCcy(String saleCcy) {
        this.saleCcy = saleCcy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUY_CCY
     *
     * @return the value of B_BUY_EXG_CTRL.BUY_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuyCcy() {
        return buyCcy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUY_CCY
     *
     * @param buyCcy the value for B_BUY_EXG_CTRL.BUY_CCY
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuyCcy(String buyCcy) {
        this.buyCcy = buyCcy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUY_SELL_FLAG
     *
     * @return the value of B_BUY_EXG_CTRL.BUY_SELL_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuySellFlag() {
        return buySellFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUY_SELL_FLAG
     *
     * @param buySellFlag the value for B_BUY_EXG_CTRL.BUY_SELL_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuySellFlag(String buySellFlag) {
        this.buySellFlag = buySellFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.TOTAL_AMT
     *
     * @return the value of B_BUY_EXG_CTRL.TOTAL_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.TOTAL_AMT
     *
     * @param totalAmt the value for B_BUY_EXG_CTRL.TOTAL_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.SPOT_FLAG
     *
     * @return the value of B_BUY_EXG_CTRL.SPOT_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getSpotFlag() {
        return spotFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.SPOT_FLAG
     *
     * @param spotFlag the value for B_BUY_EXG_CTRL.SPOT_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setSpotFlag(String spotFlag) {
        this.spotFlag = spotFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.REGISTER_DATE
     *
     * @return the value of B_BUY_EXG_CTRL.REGISTER_DATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.REGISTER_DATE
     *
     * @param registerDate the value for B_BUY_EXG_CTRL.REGISTER_DATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.PRICE
     *
     * @return the value of B_BUY_EXG_CTRL.PRICE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.PRICE
     *
     * @param price the value for B_BUY_EXG_CTRL.PRICE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.CLIENT_EXCHANGE_RATE
     *
     * @return the value of B_BUY_EXG_CTRL.CLIENT_EXCHANGE_RATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getClientExchangeRate() {
        return clientExchangeRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.CLIENT_EXCHANGE_RATE
     *
     * @param clientExchangeRate the value for B_BUY_EXG_CTRL.CLIENT_EXCHANGE_RATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setClientExchangeRate(BigDecimal clientExchangeRate) {
        this.clientExchangeRate = clientExchangeRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.DISCOUNT_TYPE
     *
     * @return the value of B_BUY_EXG_CTRL.DISCOUNT_TYPE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getDiscountType() {
        return discountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.DISCOUNT_TYPE
     *
     * @param discountType the value for B_BUY_EXG_CTRL.DISCOUNT_TYPE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.DIS_AMT
     *
     * @return the value of B_BUY_EXG_CTRL.DIS_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getDisAmt() {
        return disAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.DIS_AMT
     *
     * @param disAmt the value for B_BUY_EXG_CTRL.DIS_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setDisAmt(BigDecimal disAmt) {
        this.disAmt = disAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.AMT
     *
     * @return the value of B_BUY_EXG_CTRL.AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.AMT
     *
     * @param amt the value for B_BUY_EXG_CTRL.AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.TRAN_AMT
     *
     * @return the value of B_BUY_EXG_CTRL.TRAN_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.TRAN_AMT
     *
     * @param tranAmt the value for B_BUY_EXG_CTRL.TRAN_AMT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.SELL_ACCT_NO
     *
     * @return the value of B_BUY_EXG_CTRL.SELL_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getSellAcctNo() {
        return sellAcctNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.SELL_ACCT_NO
     *
     * @param sellAcctNo the value for B_BUY_EXG_CTRL.SELL_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setSellAcctNo(String sellAcctNo) {
        this.sellAcctNo = sellAcctNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUY_ACCT_NO
     *
     * @return the value of B_BUY_EXG_CTRL.BUY_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuyAcctNo() {
        return buyAcctNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUY_ACCT_NO
     *
     * @param buyAcctNo the value for B_BUY_EXG_CTRL.BUY_ACCT_NO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuyAcctNo(String buyAcctNo) {
        this.buyAcctNo = buyAcctNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.SALE_AMOUNT
     *
     * @return the value of B_BUY_EXG_CTRL.SALE_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.SALE_AMOUNT
     *
     * @param saleAmount the value for B_BUY_EXG_CTRL.SALE_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUY_AMOUNT
     *
     * @return the value of B_BUY_EXG_CTRL.BUY_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUY_AMOUNT
     *
     * @param buyAmount the value for B_BUY_EXG_CTRL.BUY_AMOUNT
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.RATE_TIME
     *
     * @return the value of B_BUY_EXG_CTRL.RATE_TIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getRateTime() {
        return rateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.RATE_TIME
     *
     * @param rateTime the value for B_BUY_EXG_CTRL.RATE_TIME
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setRateTime(String rateTime) {
        this.rateTime = rateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.EXCEED_FLAG
     *
     * @return the value of B_BUY_EXG_CTRL.EXCEED_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getExceedFlag() {
        return exceedFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.EXCEED_FLAG
     *
     * @param exceedFlag the value for B_BUY_EXG_CTRL.EXCEED_FLAG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setExceedFlag(String exceedFlag) {
        this.exceedFlag = exceedFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.PROCESS_STATUS
     *
     * @return the value of B_BUY_EXG_CTRL.PROCESS_STATUS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getProcessStatus() {
        return processStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.PROCESS_STATUS
     *
     * @param processStatus the value for B_BUY_EXG_CTRL.PROCESS_STATUS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.PROCESS_MSG
     *
     * @return the value of B_BUY_EXG_CTRL.PROCESS_MSG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getProcessMsg() {
        return processMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.PROCESS_MSG
     *
     * @param processMsg the value for B_BUY_EXG_CTRL.PROCESS_MSG
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.TXN_STS
     *
     * @return the value of B_BUY_EXG_CTRL.TXN_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getTxnSts() {
        return txnSts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.TXN_STS
     *
     * @param txnSts the value for B_BUY_EXG_CTRL.TXN_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setTxnSts(String txnSts) {
        this.txnSts = txnSts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.CHK_STS
     *
     * @return the value of B_BUY_EXG_CTRL.CHK_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getChkSts() {
        return chkSts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.CHK_STS
     *
     * @param chkSts the value for B_BUY_EXG_CTRL.CHK_STS
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setChkSts(String chkSts) {
        this.chkSts = chkSts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_BUY_EXG_CTRL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BBuyExgCtrl other = (BBuyExgCtrl) that;
        return (this.getBuybatno() == null ? other.getBuybatno() == null : this.getBuybatno().equals(other.getBuybatno()))
            && (this.getBuydate() == null ? other.getBuydate() == null : this.getBuydate().equals(other.getBuydate()))
            && (this.getBuytime() == null ? other.getBuytime() == null : this.getBuytime().equals(other.getBuytime()))
            && (this.getQuotechnl() == null ? other.getQuotechnl() == null : this.getQuotechnl().equals(other.getQuotechnl()))
            && (this.getBecif() == null ? other.getBecif() == null : this.getBecif().equals(other.getBecif()))
            && (this.getTotNum() == null ? other.getTotNum() == null : this.getTotNum().equals(other.getTotNum()))
            && (this.getSaleCcy() == null ? other.getSaleCcy() == null : this.getSaleCcy().equals(other.getSaleCcy()))
            && (this.getBuyCcy() == null ? other.getBuyCcy() == null : this.getBuyCcy().equals(other.getBuyCcy()))
            && (this.getBuySellFlag() == null ? other.getBuySellFlag() == null : this.getBuySellFlag().equals(other.getBuySellFlag()))
            && (this.getTotalAmt() == null ? other.getTotalAmt() == null : this.getTotalAmt().equals(other.getTotalAmt()))
            && (this.getSpotFlag() == null ? other.getSpotFlag() == null : this.getSpotFlag().equals(other.getSpotFlag()))
            && (this.getRegisterDate() == null ? other.getRegisterDate() == null : this.getRegisterDate().equals(other.getRegisterDate()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getClientExchangeRate() == null ? other.getClientExchangeRate() == null : this.getClientExchangeRate().equals(other.getClientExchangeRate()))
            && (this.getDiscountType() == null ? other.getDiscountType() == null : this.getDiscountType().equals(other.getDiscountType()))
            && (this.getDisAmt() == null ? other.getDisAmt() == null : this.getDisAmt().equals(other.getDisAmt()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getTranAmt() == null ? other.getTranAmt() == null : this.getTranAmt().equals(other.getTranAmt()))
            && (this.getSellAcctNo() == null ? other.getSellAcctNo() == null : this.getSellAcctNo().equals(other.getSellAcctNo()))
            && (this.getBuyAcctNo() == null ? other.getBuyAcctNo() == null : this.getBuyAcctNo().equals(other.getBuyAcctNo()))
            && (this.getSaleAmount() == null ? other.getSaleAmount() == null : this.getSaleAmount().equals(other.getSaleAmount()))
            && (this.getBuyAmount() == null ? other.getBuyAmount() == null : this.getBuyAmount().equals(other.getBuyAmount()))
            && (this.getRateTime() == null ? other.getRateTime() == null : this.getRateTime().equals(other.getRateTime()))
            && (this.getExceedFlag() == null ? other.getExceedFlag() == null : this.getExceedFlag().equals(other.getExceedFlag()))
            && (this.getProcessStatus() == null ? other.getProcessStatus() == null : this.getProcessStatus().equals(other.getProcessStatus()))
            && (this.getProcessMsg() == null ? other.getProcessMsg() == null : this.getProcessMsg().equals(other.getProcessMsg()))
            && (this.getTxnSts() == null ? other.getTxnSts() == null : this.getTxnSts().equals(other.getTxnSts()))
            && (this.getChkSts() == null ? other.getChkSts() == null : this.getChkSts().equals(other.getChkSts()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_BUY_EXG_CTRL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBuybatno() == null) ? 0 : getBuybatno().hashCode());
        result = prime * result + ((getBuydate() == null) ? 0 : getBuydate().hashCode());
        result = prime * result + ((getBuytime() == null) ? 0 : getBuytime().hashCode());
        result = prime * result + ((getQuotechnl() == null) ? 0 : getQuotechnl().hashCode());
        result = prime * result + ((getBecif() == null) ? 0 : getBecif().hashCode());
        result = prime * result + ((getTotNum() == null) ? 0 : getTotNum().hashCode());
        result = prime * result + ((getSaleCcy() == null) ? 0 : getSaleCcy().hashCode());
        result = prime * result + ((getBuyCcy() == null) ? 0 : getBuyCcy().hashCode());
        result = prime * result + ((getBuySellFlag() == null) ? 0 : getBuySellFlag().hashCode());
        result = prime * result + ((getTotalAmt() == null) ? 0 : getTotalAmt().hashCode());
        result = prime * result + ((getSpotFlag() == null) ? 0 : getSpotFlag().hashCode());
        result = prime * result + ((getRegisterDate() == null) ? 0 : getRegisterDate().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getClientExchangeRate() == null) ? 0 : getClientExchangeRate().hashCode());
        result = prime * result + ((getDiscountType() == null) ? 0 : getDiscountType().hashCode());
        result = prime * result + ((getDisAmt() == null) ? 0 : getDisAmt().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getTranAmt() == null) ? 0 : getTranAmt().hashCode());
        result = prime * result + ((getSellAcctNo() == null) ? 0 : getSellAcctNo().hashCode());
        result = prime * result + ((getBuyAcctNo() == null) ? 0 : getBuyAcctNo().hashCode());
        result = prime * result + ((getSaleAmount() == null) ? 0 : getSaleAmount().hashCode());
        result = prime * result + ((getBuyAmount() == null) ? 0 : getBuyAmount().hashCode());
        result = prime * result + ((getRateTime() == null) ? 0 : getRateTime().hashCode());
        result = prime * result + ((getExceedFlag() == null) ? 0 : getExceedFlag().hashCode());
        result = prime * result + ((getProcessStatus() == null) ? 0 : getProcessStatus().hashCode());
        result = prime * result + ((getProcessMsg() == null) ? 0 : getProcessMsg().hashCode());
        result = prime * result + ((getTxnSts() == null) ? 0 : getTxnSts().hashCode());
        result = prime * result + ((getChkSts() == null) ? 0 : getChkSts().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_BUY_EXG_CTRL
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", buytime=").append(buytime);
        sb.append(", quotechnl=").append(quotechnl);
        sb.append(", becif=").append(becif);
        sb.append(", totNum=").append(totNum);
        sb.append(", saleCcy=").append(saleCcy);
        sb.append(", buyCcy=").append(buyCcy);
        sb.append(", buySellFlag=").append(buySellFlag);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", spotFlag=").append(spotFlag);
        sb.append(", registerDate=").append(registerDate);
        sb.append(", price=").append(price);
        sb.append(", clientExchangeRate=").append(clientExchangeRate);
        sb.append(", discountType=").append(discountType);
        sb.append(", disAmt=").append(disAmt);
        sb.append(", amt=").append(amt);
        sb.append(", tranAmt=").append(tranAmt);
        sb.append(", sellAcctNo=").append(sellAcctNo);
        sb.append(", buyAcctNo=").append(buyAcctNo);
        sb.append(", saleAmount=").append(saleAmount);
        sb.append(", buyAmount=").append(buyAmount);
        sb.append(", rateTime=").append(rateTime);
        sb.append(", exceedFlag=").append(exceedFlag);
        sb.append(", processStatus=").append(processStatus);
        sb.append(", processMsg=").append(processMsg);
        sb.append(", txnSts=").append(txnSts);
        sb.append(", chkSts=").append(chkSts);
        sb.append("]");
        return sb.toString();
    }
}