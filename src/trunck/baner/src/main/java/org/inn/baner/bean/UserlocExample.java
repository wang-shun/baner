package org.inn.baner.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserlocExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table userloc
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table userloc
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table userloc
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public UserlocExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table userloc
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPlatdateIsNull() {
            addCriterion("platdate is null");
            return (Criteria) this;
        }

        public Criteria andPlatdateIsNotNull() {
            addCriterion("platdate is not null");
            return (Criteria) this;
        }

        public Criteria andPlatdateEqualTo(String value) {
            addCriterion("platdate =", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateNotEqualTo(String value) {
            addCriterion("platdate <>", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateGreaterThan(String value) {
            addCriterion("platdate >", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateGreaterThanOrEqualTo(String value) {
            addCriterion("platdate >=", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateLessThan(String value) {
            addCriterion("platdate <", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateLessThanOrEqualTo(String value) {
            addCriterion("platdate <=", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateLike(String value) {
            addCriterion("platdate like", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateNotLike(String value) {
            addCriterion("platdate not like", value, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateIn(List<String> values) {
            addCriterion("platdate in", values, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateNotIn(List<String> values) {
            addCriterion("platdate not in", values, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateBetween(String value1, String value2) {
            addCriterion("platdate between", value1, value2, "platdate");
            return (Criteria) this;
        }

        public Criteria andPlatdateNotBetween(String value1, String value2) {
            addCriterion("platdate not between", value1, value2, "platdate");
            return (Criteria) this;
        }

        public Criteria andMobilenoIsNull() {
            addCriterion("mobileno is null");
            return (Criteria) this;
        }

        public Criteria andMobilenoIsNotNull() {
            addCriterion("mobileno is not null");
            return (Criteria) this;
        }

        public Criteria andMobilenoEqualTo(String value) {
            addCriterion("mobileno =", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoNotEqualTo(String value) {
            addCriterion("mobileno <>", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoGreaterThan(String value) {
            addCriterion("mobileno >", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoGreaterThanOrEqualTo(String value) {
            addCriterion("mobileno >=", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoLessThan(String value) {
            addCriterion("mobileno <", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoLessThanOrEqualTo(String value) {
            addCriterion("mobileno <=", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoLike(String value) {
            addCriterion("mobileno like", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoNotLike(String value) {
            addCriterion("mobileno not like", value, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoIn(List<String> values) {
            addCriterion("mobileno in", values, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoNotIn(List<String> values) {
            addCriterion("mobileno not in", values, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoBetween(String value1, String value2) {
            addCriterion("mobileno between", value1, value2, "mobileno");
            return (Criteria) this;
        }

        public Criteria andMobilenoNotBetween(String value1, String value2) {
            addCriterion("mobileno not between", value1, value2, "mobileno");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNull() {
            addCriterion("areacode is null");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNotNull() {
            addCriterion("areacode is not null");
            return (Criteria) this;
        }

        public Criteria andAreacodeEqualTo(String value) {
            addCriterion("areacode =", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotEqualTo(String value) {
            addCriterion("areacode <>", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThan(String value) {
            addCriterion("areacode >", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThanOrEqualTo(String value) {
            addCriterion("areacode >=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThan(String value) {
            addCriterion("areacode <", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThanOrEqualTo(String value) {
            addCriterion("areacode <=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLike(String value) {
            addCriterion("areacode like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotLike(String value) {
            addCriterion("areacode not like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeIn(List<String> values) {
            addCriterion("areacode in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotIn(List<String> values) {
            addCriterion("areacode not in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeBetween(String value1, String value2) {
            addCriterion("areacode between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotBetween(String value1, String value2) {
            addCriterion("areacode not between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andLocationdescIsNull() {
            addCriterion("locationdesc is null");
            return (Criteria) this;
        }

        public Criteria andLocationdescIsNotNull() {
            addCriterion("locationdesc is not null");
            return (Criteria) this;
        }

        public Criteria andLocationdescEqualTo(String value) {
            addCriterion("locationdesc =", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescNotEqualTo(String value) {
            addCriterion("locationdesc <>", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescGreaterThan(String value) {
            addCriterion("locationdesc >", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescGreaterThanOrEqualTo(String value) {
            addCriterion("locationdesc >=", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescLessThan(String value) {
            addCriterion("locationdesc <", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescLessThanOrEqualTo(String value) {
            addCriterion("locationdesc <=", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescLike(String value) {
            addCriterion("locationdesc like", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescNotLike(String value) {
            addCriterion("locationdesc not like", value, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescIn(List<String> values) {
            addCriterion("locationdesc in", values, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescNotIn(List<String> values) {
            addCriterion("locationdesc not in", values, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescBetween(String value1, String value2) {
            addCriterion("locationdesc between", value1, value2, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLocationdescNotBetween(String value1, String value2) {
            addCriterion("locationdesc not between", value1, value2, "locationdesc");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(String value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(String value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(String value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(String value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(String value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLike(String value) {
            addCriterion("latitude like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotLike(String value) {
            addCriterion("latitude not like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<String> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<String> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(String value1, String value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(String value1, String value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(String value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(String value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(String value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(String value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(String value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLike(String value) {
            addCriterion("longitude like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotLike(String value) {
            addCriterion("longitude not like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<String> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<String> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(String value1, String value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(String value1, String value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table userloc
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table userloc
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}