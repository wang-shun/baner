package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class MerchantCurrencyMapExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public MerchantCurrencyMapExample() {
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

		public Criteria andCurrencyCodeIsNull() {
			addCriterion("CURRENCY_CODE is null");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeIsNotNull() {
			addCriterion("CURRENCY_CODE is not null");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeEqualTo(String value) {
			addCriterion("CURRENCY_CODE =", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeNotEqualTo(String value) {
			addCriterion("CURRENCY_CODE <>", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeGreaterThan(String value) {
			addCriterion("CURRENCY_CODE >", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeGreaterThanOrEqualTo(String value) {
			addCriterion("CURRENCY_CODE >=", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeLessThan(String value) {
			addCriterion("CURRENCY_CODE <", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeLessThanOrEqualTo(String value) {
			addCriterion("CURRENCY_CODE <=", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeLike(String value) {
			addCriterion("CURRENCY_CODE like", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeNotLike(String value) {
			addCriterion("CURRENCY_CODE not like", value, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeIn(List<String> values) {
			addCriterion("CURRENCY_CODE in", values, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeNotIn(List<String> values) {
			addCriterion("CURRENCY_CODE not in", values, "currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeBetween(String value1, String value2) {
			addCriterion("CURRENCY_CODE between", value1, value2,
					"currencyCode");
			return (Criteria) this;
		}

		public Criteria andCurrencyCodeNotBetween(String value1, String value2) {
			addCriterion("CURRENCY_CODE not between", value1, value2,
					"currencyCode");
			return (Criteria) this;
		}

		public Criteria andMerchantidIsNull() {
			addCriterion("MERCHANTID is null");
			return (Criteria) this;
		}

		public Criteria andMerchantidIsNotNull() {
			addCriterion("MERCHANTID is not null");
			return (Criteria) this;
		}

		public Criteria andMerchantidEqualTo(String value) {
			addCriterion("MERCHANTID =", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidNotEqualTo(String value) {
			addCriterion("MERCHANTID <>", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidGreaterThan(String value) {
			addCriterion("MERCHANTID >", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidGreaterThanOrEqualTo(String value) {
			addCriterion("MERCHANTID >=", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidLessThan(String value) {
			addCriterion("MERCHANTID <", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidLessThanOrEqualTo(String value) {
			addCriterion("MERCHANTID <=", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidLike(String value) {
			addCriterion("MERCHANTID like", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidNotLike(String value) {
			addCriterion("MERCHANTID not like", value, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidIn(List<String> values) {
			addCriterion("MERCHANTID in", values, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidNotIn(List<String> values) {
			addCriterion("MERCHANTID not in", values, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidBetween(String value1, String value2) {
			addCriterion("MERCHANTID between", value1, value2, "merchantid");
			return (Criteria) this;
		}

		public Criteria andMerchantidNotBetween(String value1, String value2) {
			addCriterion("MERCHANTID not between", value1, value2, "merchantid");
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