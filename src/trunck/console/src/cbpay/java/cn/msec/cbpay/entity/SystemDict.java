package cn.msec.cbpay.entity;

import cn.msec.ojpa.api.annotations.Tab;
import cn.msec.ojpa.msc.mysql.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Tab(name="SYSTEM_DICT")
@AllArgsConstructor
@NoArgsConstructor
public class SystemDict {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_DICT.KEY
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_DICT.NAME
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_DICT.desc
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    private String desc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_DICT.TYPE
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    private String type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_DICT.KEY
     *
     * @return the value of SYSTEM_DICT.KEY
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_DICT.KEY
     *
     * @param key the value for SYSTEM_DICT.KEY
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_DICT.NAME
     *
     * @return the value of SYSTEM_DICT.NAME
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_DICT.NAME
     *
     * @param name the value for SYSTEM_DICT.NAME
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_DICT.desc
     *
     * @return the value of SYSTEM_DICT.desc
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_DICT.desc
     *
     * @param desc the value for SYSTEM_DICT.desc
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_DICT.TYPE
     *
     * @return the value of SYSTEM_DICT.TYPE
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_DICT.TYPE
     *
     * @param type the value for SYSTEM_DICT.TYPE
     *
     * @mbggenerated Fri May 20 10:32:39 CST 2016
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_DICT
     *
     * @mbggenerated Fri May 20 10:32:40 CST 2016
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
        SystemDict other = (SystemDict) that;
        return (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_DICT
     *
     * @mbggenerated Fri May 20 10:32:40 CST 2016
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_DICT
     *
     * @mbggenerated Fri May 20 10:32:40 CST 2016
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", key=").append(key);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}