package cn.msec.msc.mysqlgens.admin.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SysBusinessKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SYS_BUSINESS.BUSINESS_ID
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    private String businessId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SYS_BUSINESS.BUSINESS_ID
     *
     * @return the value of T_SYS_BUSINESS.BUSINESS_ID
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SYS_BUSINESS.BUSINESS_ID
     *
     * @param businessId the value for T_SYS_BUSINESS.BUSINESS_ID
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_BUSINESS
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
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
        SysBusinessKey other = (SysBusinessKey) that;
        return (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_BUSINESS
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SYS_BUSINESS
     *
     * @mbggenerated Thu Dec 31 10:48:44 CST 2015
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", businessId=").append(businessId);
        sb.append("]");
        return sb.toString();
    }
}