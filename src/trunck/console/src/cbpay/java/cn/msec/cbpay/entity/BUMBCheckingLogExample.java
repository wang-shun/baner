package cn.msec.cbpay.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BUMBCheckingLogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    protected List<Criteria> oredCriteria;

    protected int offset;

    protected int limit;

    protected String sumCol;

    protected String groupSelClause;

    protected String groupByClause;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public BUMBCheckingLogExample() {
        oredCriteria = new ArrayList<Criteria>();
        offset = 0;
        limit = Integer.MAX_VALUE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
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
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        this.offset= 0;
        this.limit= Integer.MAX_VALUE;
        this.sumCol=null;
        this.groupSelClause=null;
        this.groupByClause=null;
    }

    public void setOffset(int offset) {
         this.offset = offset;
    }

    public int getOffset() {
          return offset;
    }

    public void setLimit(int limit) {
         this.limit = limit;
    }

    public int getLimit() {
          return limit;
    }

    public void setSumCol(String sumCol) {
         this.sumCol = sumCol;
    }

    public String getSumCol() {
          return sumCol;
    }

    public void setGroupSelClause(String groupSelClause) {
         this.groupSelClause = groupSelClause;
    }

    public String getGroupSelClause() {
          return groupSelClause;
    }

    public void setGroupByClause(String groupByClause) {
         this.groupByClause = groupByClause;
    }

    public String getGroupByClause() {
          return groupByClause;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    @Data
    public abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        public GeneratedCriteria() {
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

        public void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        public void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        public void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andJnlnoIsNull() {
            addCriterion("JNLNO is null");
            return (Criteria) this;
        }

        public Criteria andJnlnoIsNotNull() {
            addCriterion("JNLNO is not null");
            return (Criteria) this;
        }

        public Criteria andJnlnoEqualTo(String value) {
            addCriterion("JNLNO =", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoNotEqualTo(String value) {
            addCriterion("JNLNO <>", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoGreaterThan(String value) {
            addCriterion("JNLNO >", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoGreaterThanOrEqualTo(String value) {
            addCriterion("JNLNO >=", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoLessThan(String value) {
            addCriterion("JNLNO <", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoLessThanOrEqualTo(String value) {
            addCriterion("JNLNO <=", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoLike(String value) {
            addCriterion("JNLNO like", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoNotLike(String value) {
            addCriterion("JNLNO not like", value, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoIn(List<String> values) {
            addCriterion("JNLNO in", values, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoNotIn(List<String> values) {
            addCriterion("JNLNO not in", values, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoBetween(String value1, String value2) {
            addCriterion("JNLNO between", value1, value2, "jnlno");
            return (Criteria) this;
        }

        public Criteria andJnlnoNotBetween(String value1, String value2) {
            addCriterion("JNLNO not between", value1, value2, "jnlno");
            return (Criteria) this;
        }

        public Criteria andTrandateIsNull() {
            addCriterion("TRANDATE is null");
            return (Criteria) this;
        }

        public Criteria andTrandateIsNotNull() {
            addCriterion("TRANDATE is not null");
            return (Criteria) this;
        }

        public Criteria andTrandateEqualTo(String value) {
            addCriterion("TRANDATE =", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateNotEqualTo(String value) {
            addCriterion("TRANDATE <>", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateGreaterThan(String value) {
            addCriterion("TRANDATE >", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateGreaterThanOrEqualTo(String value) {
            addCriterion("TRANDATE >=", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateLessThan(String value) {
            addCriterion("TRANDATE <", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateLessThanOrEqualTo(String value) {
            addCriterion("TRANDATE <=", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateLike(String value) {
            addCriterion("TRANDATE like", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateNotLike(String value) {
            addCriterion("TRANDATE not like", value, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateIn(List<String> values) {
            addCriterion("TRANDATE in", values, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateNotIn(List<String> values) {
            addCriterion("TRANDATE not in", values, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateBetween(String value1, String value2) {
            addCriterion("TRANDATE between", value1, value2, "trandate");
            return (Criteria) this;
        }

        public Criteria andTrandateNotBetween(String value1, String value2) {
            addCriterion("TRANDATE not between", value1, value2, "trandate");
            return (Criteria) this;
        }

        public Criteria andAccountdateIsNull() {
            addCriterion("ACCOUNTDATE is null");
            return (Criteria) this;
        }

        public Criteria andAccountdateIsNotNull() {
            addCriterion("ACCOUNTDATE is not null");
            return (Criteria) this;
        }

        public Criteria andAccountdateEqualTo(String value) {
            addCriterion("ACCOUNTDATE =", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateNotEqualTo(String value) {
            addCriterion("ACCOUNTDATE <>", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateGreaterThan(String value) {
            addCriterion("ACCOUNTDATE >", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNTDATE >=", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateLessThan(String value) {
            addCriterion("ACCOUNTDATE <", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNTDATE <=", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateLike(String value) {
            addCriterion("ACCOUNTDATE like", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateNotLike(String value) {
            addCriterion("ACCOUNTDATE not like", value, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateIn(List<String> values) {
            addCriterion("ACCOUNTDATE in", values, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateNotIn(List<String> values) {
            addCriterion("ACCOUNTDATE not in", values, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateBetween(String value1, String value2) {
            addCriterion("ACCOUNTDATE between", value1, value2, "accountdate");
            return (Criteria) this;
        }

        public Criteria andAccountdateNotBetween(String value1, String value2) {
            addCriterion("ACCOUNTDATE not between", value1, value2, "accountdate");
            return (Criteria) this;
        }

        public Criteria andChecktypeIsNull() {
            addCriterion("CHECKTYPE is null");
            return (Criteria) this;
        }

        public Criteria andChecktypeIsNotNull() {
            addCriterion("CHECKTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andChecktypeEqualTo(String value) {
            addCriterion("CHECKTYPE =", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeNotEqualTo(String value) {
            addCriterion("CHECKTYPE <>", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeGreaterThan(String value) {
            addCriterion("CHECKTYPE >", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeGreaterThanOrEqualTo(String value) {
            addCriterion("CHECKTYPE >=", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeLessThan(String value) {
            addCriterion("CHECKTYPE <", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeLessThanOrEqualTo(String value) {
            addCriterion("CHECKTYPE <=", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeLike(String value) {
            addCriterion("CHECKTYPE like", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeNotLike(String value) {
            addCriterion("CHECKTYPE not like", value, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeIn(List<String> values) {
            addCriterion("CHECKTYPE in", values, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeNotIn(List<String> values) {
            addCriterion("CHECKTYPE not in", values, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeBetween(String value1, String value2) {
            addCriterion("CHECKTYPE between", value1, value2, "checktype");
            return (Criteria) this;
        }

        public Criteria andChecktypeNotBetween(String value1, String value2) {
            addCriterion("CHECKTYPE not between", value1, value2, "checktype");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDonetimeIsNull() {
            addCriterion("DONETIME is null");
            return (Criteria) this;
        }

        public Criteria andDonetimeIsNotNull() {
            addCriterion("DONETIME is not null");
            return (Criteria) this;
        }

        public Criteria andDonetimeEqualTo(String value) {
            addCriterion("DONETIME =", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeNotEqualTo(String value) {
            addCriterion("DONETIME <>", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeGreaterThan(String value) {
            addCriterion("DONETIME >", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeGreaterThanOrEqualTo(String value) {
            addCriterion("DONETIME >=", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeLessThan(String value) {
            addCriterion("DONETIME <", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeLessThanOrEqualTo(String value) {
            addCriterion("DONETIME <=", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeLike(String value) {
            addCriterion("DONETIME like", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeNotLike(String value) {
            addCriterion("DONETIME not like", value, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeIn(List<String> values) {
            addCriterion("DONETIME in", values, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeNotIn(List<String> values) {
            addCriterion("DONETIME not in", values, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeBetween(String value1, String value2) {
            addCriterion("DONETIME between", value1, value2, "donetime");
            return (Criteria) this;
        }

        public Criteria andDonetimeNotBetween(String value1, String value2) {
            addCriterion("DONETIME not between", value1, value2, "donetime");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FILENAME =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FILENAME <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FILENAME >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FILENAME >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FILENAME <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FILENAME <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FILENAME like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FILENAME not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FILENAME in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FILENAME not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FILENAME between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FILENAME not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andJnlnoLikeInsensitive(String value) {
            addCriterion("upper(JNLNO) like", value.toUpperCase(), "jnlno");
            return (Criteria) this;
        }

        public Criteria andTrandateLikeInsensitive(String value) {
            addCriterion("upper(TRANDATE) like", value.toUpperCase(), "trandate");
            return (Criteria) this;
        }

        public Criteria andAccountdateLikeInsensitive(String value) {
            addCriterion("upper(ACCOUNTDATE) like", value.toUpperCase(), "accountdate");
            return (Criteria) this;
        }

        public Criteria andChecktypeLikeInsensitive(String value) {
            addCriterion("upper(CHECKTYPE) like", value.toUpperCase(), "checktype");
            return (Criteria) this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            addCriterion("upper(STATUS) like", value.toUpperCase(), "status");
            return (Criteria) this;
        }

        public Criteria andDonetimeLikeInsensitive(String value) {
            addCriterion("upper(DONETIME) like", value.toUpperCase(), "donetime");
            return (Criteria) this;
        }

        public Criteria andFilenameLikeInsensitive(String value) {
            addCriterion("upper(FILENAME) like", value.toUpperCase(), "filename");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 29 14:37:11 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        public Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table B_UMB_CHECKING_LOG
     *
     * @mbggenerated Tue Mar 29 14:37:11 CST 2016
     */
    @Data
    @NoArgsConstructor
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

        public Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        public Criterion(String condition, Object value, String typeHandler) {
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

        public Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        public Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}