package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class ConvertResCodeExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ConvertResCodeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andResponsecodeIsNull() {
			addCriterion("RESPONSECODE is null");
			return (Criteria) this;
		}

		public Criteria andResponsecodeIsNotNull() {
			addCriterion("RESPONSECODE is not null");
			return (Criteria) this;
		}

		public Criteria andResponsecodeEqualTo(String value) {
			addCriterion("RESPONSECODE =", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeNotEqualTo(String value) {
			addCriterion("RESPONSECODE <>", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeGreaterThan(String value) {
			addCriterion("RESPONSECODE >", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeGreaterThanOrEqualTo(String value) {
			addCriterion("RESPONSECODE >=", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeLessThan(String value) {
			addCriterion("RESPONSECODE <", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeLessThanOrEqualTo(String value) {
			addCriterion("RESPONSECODE <=", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeLike(String value) {
			addCriterion("RESPONSECODE like", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeNotLike(String value) {
			addCriterion("RESPONSECODE not like", value, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeIn(List<String> values) {
			addCriterion("RESPONSECODE in", values, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeNotIn(List<String> values) {
			addCriterion("RESPONSECODE not in", values, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeBetween(String value1, String value2) {
			addCriterion("RESPONSECODE between", value1, value2, "responsecode");
			return (Criteria) this;
		}

		public Criteria andResponsecodeNotBetween(String value1, String value2) {
			addCriterion("RESPONSECODE not between", value1, value2,
					"responsecode");
			return (Criteria) this;
		}

		public Criteria andServeridIsNull() {
			addCriterion("SERVERID is null");
			return (Criteria) this;
		}

		public Criteria andServeridIsNotNull() {
			addCriterion("SERVERID is not null");
			return (Criteria) this;
		}

		public Criteria andServeridEqualTo(String value) {
			addCriterion("SERVERID =", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridNotEqualTo(String value) {
			addCriterion("SERVERID <>", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridGreaterThan(String value) {
			addCriterion("SERVERID >", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridGreaterThanOrEqualTo(String value) {
			addCriterion("SERVERID >=", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridLessThan(String value) {
			addCriterion("SERVERID <", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridLessThanOrEqualTo(String value) {
			addCriterion("SERVERID <=", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridLike(String value) {
			addCriterion("SERVERID like", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridNotLike(String value) {
			addCriterion("SERVERID not like", value, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridIn(List<String> values) {
			addCriterion("SERVERID in", values, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridNotIn(List<String> values) {
			addCriterion("SERVERID not in", values, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridBetween(String value1, String value2) {
			addCriterion("SERVERID between", value1, value2, "serverid");
			return (Criteria) this;
		}

		public Criteria andServeridNotBetween(String value1, String value2) {
			addCriterion("SERVERID not between", value1, value2, "serverid");
			return (Criteria) this;
		}

		public Criteria andMsgIsNull() {
			addCriterion("MSG is null");
			return (Criteria) this;
		}

		public Criteria andMsgIsNotNull() {
			addCriterion("MSG is not null");
			return (Criteria) this;
		}

		public Criteria andMsgEqualTo(String value) {
			addCriterion("MSG =", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgNotEqualTo(String value) {
			addCriterion("MSG <>", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgGreaterThan(String value) {
			addCriterion("MSG >", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgGreaterThanOrEqualTo(String value) {
			addCriterion("MSG >=", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgLessThan(String value) {
			addCriterion("MSG <", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgLessThanOrEqualTo(String value) {
			addCriterion("MSG <=", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgLike(String value) {
			addCriterion("MSG like", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgNotLike(String value) {
			addCriterion("MSG not like", value, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgIn(List<String> values) {
			addCriterion("MSG in", values, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgNotIn(List<String> values) {
			addCriterion("MSG not in", values, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgBetween(String value1, String value2) {
			addCriterion("MSG between", value1, value2, "msg");
			return (Criteria) this;
		}

		public Criteria andMsgNotBetween(String value1, String value2) {
			addCriterion("MSG not between", value1, value2, "msg");
			return (Criteria) this;
		}

		public Criteria andSysRescodeIsNull() {
			addCriterion("SYS_RESCODE is null");
			return (Criteria) this;
		}

		public Criteria andSysRescodeIsNotNull() {
			addCriterion("SYS_RESCODE is not null");
			return (Criteria) this;
		}

		public Criteria andSysRescodeEqualTo(String value) {
			addCriterion("SYS_RESCODE =", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeNotEqualTo(String value) {
			addCriterion("SYS_RESCODE <>", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeGreaterThan(String value) {
			addCriterion("SYS_RESCODE >", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeGreaterThanOrEqualTo(String value) {
			addCriterion("SYS_RESCODE >=", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeLessThan(String value) {
			addCriterion("SYS_RESCODE <", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeLessThanOrEqualTo(String value) {
			addCriterion("SYS_RESCODE <=", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeLike(String value) {
			addCriterion("SYS_RESCODE like", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeNotLike(String value) {
			addCriterion("SYS_RESCODE not like", value, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeIn(List<String> values) {
			addCriterion("SYS_RESCODE in", values, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeNotIn(List<String> values) {
			addCriterion("SYS_RESCODE not in", values, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeBetween(String value1, String value2) {
			addCriterion("SYS_RESCODE between", value1, value2, "sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysRescodeNotBetween(String value1, String value2) {
			addCriterion("SYS_RESCODE not between", value1, value2,
					"sysRescode");
			return (Criteria) this;
		}

		public Criteria andSysDescIsNull() {
			addCriterion("SYS_DESC is null");
			return (Criteria) this;
		}

		public Criteria andSysDescIsNotNull() {
			addCriterion("SYS_DESC is not null");
			return (Criteria) this;
		}

		public Criteria andSysDescEqualTo(String value) {
			addCriterion("SYS_DESC =", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescNotEqualTo(String value) {
			addCriterion("SYS_DESC <>", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescGreaterThan(String value) {
			addCriterion("SYS_DESC >", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescGreaterThanOrEqualTo(String value) {
			addCriterion("SYS_DESC >=", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescLessThan(String value) {
			addCriterion("SYS_DESC <", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescLessThanOrEqualTo(String value) {
			addCriterion("SYS_DESC <=", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescLike(String value) {
			addCriterion("SYS_DESC like", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescNotLike(String value) {
			addCriterion("SYS_DESC not like", value, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescIn(List<String> values) {
			addCriterion("SYS_DESC in", values, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescNotIn(List<String> values) {
			addCriterion("SYS_DESC not in", values, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescBetween(String value1, String value2) {
			addCriterion("SYS_DESC between", value1, value2, "sysDesc");
			return (Criteria) this;
		}

		public Criteria andSysDescNotBetween(String value1, String value2) {
			addCriterion("SYS_DESC not between", value1, value2, "sysDesc");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {
		protected Criteria() {
			super();
		}
	}

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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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