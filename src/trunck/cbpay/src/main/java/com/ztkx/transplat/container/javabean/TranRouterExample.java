package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class TranRouterExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public TranRouterExample() {
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

		public Criteria andTypeIsNull() {
			addCriterion("TYPE is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(String value) {
			addCriterion("TYPE =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(String value) {
			addCriterion("TYPE <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(String value) {
			addCriterion("TYPE >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(String value) {
			addCriterion("TYPE >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(String value) {
			addCriterion("TYPE <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(String value) {
			addCriterion("TYPE <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLike(String value) {
			addCriterion("TYPE like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotLike(String value) {
			addCriterion("TYPE not like", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List<String> values) {
			addCriterion("TYPE in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List<String> values) {
			addCriterion("TYPE not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(String value1, String value2) {
			addCriterion("TYPE between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(String value1, String value2) {
			addCriterion("TYPE not between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andMaxAmtIsNull() {
			addCriterion("MAX_AMT is null");
			return (Criteria) this;
		}

		public Criteria andMaxAmtIsNotNull() {
			addCriterion("MAX_AMT is not null");
			return (Criteria) this;
		}

		public Criteria andMaxAmtEqualTo(String value) {
			addCriterion("MAX_AMT =", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtNotEqualTo(String value) {
			addCriterion("MAX_AMT <>", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtGreaterThan(String value) {
			addCriterion("MAX_AMT >", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtGreaterThanOrEqualTo(String value) {
			addCriterion("MAX_AMT >=", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtLessThan(String value) {
			addCriterion("MAX_AMT <", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtLessThanOrEqualTo(String value) {
			addCriterion("MAX_AMT <=", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtLike(String value) {
			addCriterion("MAX_AMT like", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtNotLike(String value) {
			addCriterion("MAX_AMT not like", value, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtIn(List<String> values) {
			addCriterion("MAX_AMT in", values, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtNotIn(List<String> values) {
			addCriterion("MAX_AMT not in", values, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtBetween(String value1, String value2) {
			addCriterion("MAX_AMT between", value1, value2, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMaxAmtNotBetween(String value1, String value2) {
			addCriterion("MAX_AMT not between", value1, value2, "maxAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtIsNull() {
			addCriterion("MIN_AMT is null");
			return (Criteria) this;
		}

		public Criteria andMinAmtIsNotNull() {
			addCriterion("MIN_AMT is not null");
			return (Criteria) this;
		}

		public Criteria andMinAmtEqualTo(String value) {
			addCriterion("MIN_AMT =", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtNotEqualTo(String value) {
			addCriterion("MIN_AMT <>", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtGreaterThan(String value) {
			addCriterion("MIN_AMT >", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtGreaterThanOrEqualTo(String value) {
			addCriterion("MIN_AMT >=", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtLessThan(String value) {
			addCriterion("MIN_AMT <", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtLessThanOrEqualTo(String value) {
			addCriterion("MIN_AMT <=", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtLike(String value) {
			addCriterion("MIN_AMT like", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtNotLike(String value) {
			addCriterion("MIN_AMT not like", value, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtIn(List<String> values) {
			addCriterion("MIN_AMT in", values, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtNotIn(List<String> values) {
			addCriterion("MIN_AMT not in", values, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtBetween(String value1, String value2) {
			addCriterion("MIN_AMT between", value1, value2, "minAmt");
			return (Criteria) this;
		}

		public Criteria andMinAmtNotBetween(String value1, String value2) {
			addCriterion("MIN_AMT not between", value1, value2, "minAmt");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeIsNull() {
			addCriterion("CURRENCY_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeIsNotNull() {
			addCriterion("CURRENCY_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeEqualTo(String value) {
			addCriterion("CURRENCY_TYPE =", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeNotEqualTo(String value) {
			addCriterion("CURRENCY_TYPE <>", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeGreaterThan(String value) {
			addCriterion("CURRENCY_TYPE >", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeGreaterThanOrEqualTo(String value) {
			addCriterion("CURRENCY_TYPE >=", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeLessThan(String value) {
			addCriterion("CURRENCY_TYPE <", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeLessThanOrEqualTo(String value) {
			addCriterion("CURRENCY_TYPE <=", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeLike(String value) {
			addCriterion("CURRENCY_TYPE like", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeNotLike(String value) {
			addCriterion("CURRENCY_TYPE not like", value, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeIn(List<String> values) {
			addCriterion("CURRENCY_TYPE in", values, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeNotIn(List<String> values) {
			addCriterion("CURRENCY_TYPE not in", values, "currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeBetween(String value1, String value2) {
			addCriterion("CURRENCY_TYPE between", value1, value2,
					"currencyType");
			return (Criteria) this;
		}

		public Criteria andCurrencyTypeNotBetween(String value1, String value2) {
			addCriterion("CURRENCY_TYPE not between", value1, value2,
					"currencyType");
			return (Criteria) this;
		}

		public Criteria andRatePolicyIsNull() {
			addCriterion("RATE_POLICY is null");
			return (Criteria) this;
		}

		public Criteria andRatePolicyIsNotNull() {
			addCriterion("RATE_POLICY is not null");
			return (Criteria) this;
		}

		public Criteria andRatePolicyEqualTo(String value) {
			addCriterion("RATE_POLICY =", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyNotEqualTo(String value) {
			addCriterion("RATE_POLICY <>", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyGreaterThan(String value) {
			addCriterion("RATE_POLICY >", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyGreaterThanOrEqualTo(String value) {
			addCriterion("RATE_POLICY >=", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyLessThan(String value) {
			addCriterion("RATE_POLICY <", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyLessThanOrEqualTo(String value) {
			addCriterion("RATE_POLICY <=", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyLike(String value) {
			addCriterion("RATE_POLICY like", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyNotLike(String value) {
			addCriterion("RATE_POLICY not like", value, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyIn(List<String> values) {
			addCriterion("RATE_POLICY in", values, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyNotIn(List<String> values) {
			addCriterion("RATE_POLICY not in", values, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyBetween(String value1, String value2) {
			addCriterion("RATE_POLICY between", value1, value2, "ratePolicy");
			return (Criteria) this;
		}

		public Criteria andRatePolicyNotBetween(String value1, String value2) {
			addCriterion("RATE_POLICY not between", value1, value2,
					"ratePolicy");
			return (Criteria) this;
		}

		public Criteria andTrancodeIsNull() {
			addCriterion("TRANCODE is null");
			return (Criteria) this;
		}

		public Criteria andTrancodeIsNotNull() {
			addCriterion("TRANCODE is not null");
			return (Criteria) this;
		}

		public Criteria andTrancodeEqualTo(String value) {
			addCriterion("TRANCODE =", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeNotEqualTo(String value) {
			addCriterion("TRANCODE <>", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeGreaterThan(String value) {
			addCriterion("TRANCODE >", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeGreaterThanOrEqualTo(String value) {
			addCriterion("TRANCODE >=", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeLessThan(String value) {
			addCriterion("TRANCODE <", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeLessThanOrEqualTo(String value) {
			addCriterion("TRANCODE <=", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeLike(String value) {
			addCriterion("TRANCODE like", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeNotLike(String value) {
			addCriterion("TRANCODE not like", value, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeIn(List<String> values) {
			addCriterion("TRANCODE in", values, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeNotIn(List<String> values) {
			addCriterion("TRANCODE not in", values, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeBetween(String value1, String value2) {
			addCriterion("TRANCODE between", value1, value2, "trancode");
			return (Criteria) this;
		}

		public Criteria andTrancodeNotBetween(String value1, String value2) {
			addCriterion("TRANCODE not between", value1, value2, "trancode");
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