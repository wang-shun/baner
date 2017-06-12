package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class ConfOprationExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ConfOprationExample() {
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

		public Criteria andOprCodeIsNull() {
			addCriterion("OPR_CODE is null");
			return (Criteria) this;
		}

		public Criteria andOprCodeIsNotNull() {
			addCriterion("OPR_CODE is not null");
			return (Criteria) this;
		}

		public Criteria andOprCodeEqualTo(String value) {
			addCriterion("OPR_CODE =", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeNotEqualTo(String value) {
			addCriterion("OPR_CODE <>", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeGreaterThan(String value) {
			addCriterion("OPR_CODE >", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeGreaterThanOrEqualTo(String value) {
			addCriterion("OPR_CODE >=", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeLessThan(String value) {
			addCriterion("OPR_CODE <", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeLessThanOrEqualTo(String value) {
			addCriterion("OPR_CODE <=", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeLike(String value) {
			addCriterion("OPR_CODE like", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeNotLike(String value) {
			addCriterion("OPR_CODE not like", value, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeIn(List<String> values) {
			addCriterion("OPR_CODE in", values, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeNotIn(List<String> values) {
			addCriterion("OPR_CODE not in", values, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeBetween(String value1, String value2) {
			addCriterion("OPR_CODE between", value1, value2, "oprCode");
			return (Criteria) this;
		}

		public Criteria andOprCodeNotBetween(String value1, String value2) {
			addCriterion("OPR_CODE not between", value1, value2, "oprCode");
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

		public Criteria andServercodeIsNull() {
			addCriterion("SERVERCODE is null");
			return (Criteria) this;
		}

		public Criteria andServercodeIsNotNull() {
			addCriterion("SERVERCODE is not null");
			return (Criteria) this;
		}

		public Criteria andServercodeEqualTo(String value) {
			addCriterion("SERVERCODE =", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeNotEqualTo(String value) {
			addCriterion("SERVERCODE <>", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeGreaterThan(String value) {
			addCriterion("SERVERCODE >", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeGreaterThanOrEqualTo(String value) {
			addCriterion("SERVERCODE >=", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeLessThan(String value) {
			addCriterion("SERVERCODE <", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeLessThanOrEqualTo(String value) {
			addCriterion("SERVERCODE <=", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeLike(String value) {
			addCriterion("SERVERCODE like", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeNotLike(String value) {
			addCriterion("SERVERCODE not like", value, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeIn(List<String> values) {
			addCriterion("SERVERCODE in", values, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeNotIn(List<String> values) {
			addCriterion("SERVERCODE not in", values, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeBetween(String value1, String value2) {
			addCriterion("SERVERCODE between", value1, value2, "servercode");
			return (Criteria) this;
		}

		public Criteria andServercodeNotBetween(String value1, String value2) {
			addCriterion("SERVERCODE not between", value1, value2, "servercode");
			return (Criteria) this;
		}

		public Criteria andServerDiyIsNull() {
			addCriterion("SERVER_DIY is null");
			return (Criteria) this;
		}

		public Criteria andServerDiyIsNotNull() {
			addCriterion("SERVER_DIY is not null");
			return (Criteria) this;
		}

		public Criteria andServerDiyEqualTo(String value) {
			addCriterion("SERVER_DIY =", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyNotEqualTo(String value) {
			addCriterion("SERVER_DIY <>", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyGreaterThan(String value) {
			addCriterion("SERVER_DIY >", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyGreaterThanOrEqualTo(String value) {
			addCriterion("SERVER_DIY >=", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyLessThan(String value) {
			addCriterion("SERVER_DIY <", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyLessThanOrEqualTo(String value) {
			addCriterion("SERVER_DIY <=", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyLike(String value) {
			addCriterion("SERVER_DIY like", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyNotLike(String value) {
			addCriterion("SERVER_DIY not like", value, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyIn(List<String> values) {
			addCriterion("SERVER_DIY in", values, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyNotIn(List<String> values) {
			addCriterion("SERVER_DIY not in", values, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyBetween(String value1, String value2) {
			addCriterion("SERVER_DIY between", value1, value2, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerDiyNotBetween(String value1, String value2) {
			addCriterion("SERVER_DIY not between", value1, value2, "serverDiy");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeIsNull() {
			addCriterion("SERVER_OVERTIME is null");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeIsNotNull() {
			addCriterion("SERVER_OVERTIME is not null");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeEqualTo(String value) {
			addCriterion("SERVER_OVERTIME =", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeNotEqualTo(String value) {
			addCriterion("SERVER_OVERTIME <>", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeGreaterThan(String value) {
			addCriterion("SERVER_OVERTIME >", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeGreaterThanOrEqualTo(String value) {
			addCriterion("SERVER_OVERTIME >=", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeLessThan(String value) {
			addCriterion("SERVER_OVERTIME <", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeLessThanOrEqualTo(String value) {
			addCriterion("SERVER_OVERTIME <=", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeLike(String value) {
			addCriterion("SERVER_OVERTIME like", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeNotLike(String value) {
			addCriterion("SERVER_OVERTIME not like", value, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeIn(List<String> values) {
			addCriterion("SERVER_OVERTIME in", values, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeNotIn(List<String> values) {
			addCriterion("SERVER_OVERTIME not in", values, "serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeBetween(String value1, String value2) {
			addCriterion("SERVER_OVERTIME between", value1, value2,
					"serverOvertime");
			return (Criteria) this;
		}

		public Criteria andServerOvertimeNotBetween(String value1, String value2) {
			addCriterion("SERVER_OVERTIME not between", value1, value2,
					"serverOvertime");
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