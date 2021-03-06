package cn.msec.cbpay.entity;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BBuyExgCtrlKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUYBATNO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buybatno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column B_BUY_EXG_CTRL.BUYDATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    private String buydate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUYBATNO
     *
     * @return the value of B_BUY_EXG_CTRL.BUYBATNO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuybatno() {
        return buybatno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUYBATNO
     *
     * @param buybatno the value for B_BUY_EXG_CTRL.BUYBATNO
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuybatno(String buybatno) {
        this.buybatno = buybatno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column B_BUY_EXG_CTRL.BUYDATE
     *
     * @return the value of B_BUY_EXG_CTRL.BUYDATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public String getBuydate() {
        return buydate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column B_BUY_EXG_CTRL.BUYDATE
     *
     * @param buydate the value for B_BUY_EXG_CTRL.BUYDATE
     *
     * @mbggenerated Wed May 04 15:01:39 CST 2016
     */
    public void setBuydate(String buydate) {
        this.buydate = buydate;
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
        BBuyExgCtrlKey other = (BBuyExgCtrlKey) that;
        return (this.getBuybatno() == null ? other.getBuybatno() == null : this.getBuybatno().equals(other.getBuybatno()))
            && (this.getBuydate() == null ? other.getBuydate() == null : this.getBuydate().equals(other.getBuydate()));
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
        sb.append(", buybatno=").append(buybatno);
        sb.append(", buydate=").append(buydate);
        sb.append("]");
        return sb.toString();
    }
}