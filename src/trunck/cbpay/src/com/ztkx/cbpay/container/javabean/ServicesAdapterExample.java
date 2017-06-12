package com.ztkx.cbpay.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapterExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ServicesAdapterExample() {
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

		public Criteria andSystemidIsNull() {
			addCriterion("SYSTEMID is null");
			return (Criteria) this;
		}

		public Criteria andSystemidIsNotNull() {
			addCriterion("SYSTEMID is not null");
			return (Criteria) this;
		}

		public Criteria andSystemidEqualTo(String value) {
			addCriterion("SYSTEMID =", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidNotEqualTo(String value) {
			addCriterion("SYSTEMID <>", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidGreaterThan(String value) {
			addCriterion("SYSTEMID >", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidGreaterThanOrEqualTo(String value) {
			addCriterion("SYSTEMID >=", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidLessThan(String value) {
			addCriterion("SYSTEMID <", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidLessThanOrEqualTo(String value) {
			addCriterion("SYSTEMID <=", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidLike(String value) {
			addCriterion("SYSTEMID like", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidNotLike(String value) {
			addCriterion("SYSTEMID not like", value, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidIn(List<String> values) {
			addCriterion("SYSTEMID in", values, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidNotIn(List<String> values) {
			addCriterion("SYSTEMID not in", values, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidBetween(String value1, String value2) {
			addCriterion("SYSTEMID between", value1, value2, "systemid");
			return (Criteria) this;
		}

		public Criteria andSystemidNotBetween(String value1, String value2) {
			addCriterion("SYSTEMID not between", value1, value2, "systemid");
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

		public Criteria andProcesslistIsNull() {
			addCriterion("PROCESSLIST is null");
			return (Criteria) this;
		}

		public Criteria andProcesslistIsNotNull() {
			addCriterion("PROCESSLIST is not null");
			return (Criteria) this;
		}

		public Criteria andProcesslistEqualTo(String value) {
			addCriterion("PROCESSLIST =", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistNotEqualTo(String value) {
			addCriterion("PROCESSLIST <>", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistGreaterThan(String value) {
			addCriterion("PROCESSLIST >", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistGreaterThanOrEqualTo(String value) {
			addCriterion("PROCESSLIST >=", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistLessThan(String value) {
			addCriterion("PROCESSLIST <", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistLessThanOrEqualTo(String value) {
			addCriterion("PROCESSLIST <=", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistLike(String value) {
			addCriterion("PROCESSLIST like", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistNotLike(String value) {
			addCriterion("PROCESSLIST not like", value, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistIn(List<String> values) {
			addCriterion("PROCESSLIST in", values, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistNotIn(List<String> values) {
			addCriterion("PROCESSLIST not in", values, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistBetween(String value1, String value2) {
			addCriterion("PROCESSLIST between", value1, value2, "processlist");
			return (Criteria) this;
		}

		public Criteria andProcesslistNotBetween(String value1, String value2) {
			addCriterion("PROCESSLIST not between", value1, value2,
					"processlist");
			return (Criteria) this;
		}

		public Criteria andOwnerIsNull() {
			addCriterion("OWNER is null");
			return (Criteria) this;
		}

		public Criteria andOwnerIsNotNull() {
			addCriterion("OWNER is not null");
			return (Criteria) this;
		}

		public Criteria andOwnerEqualTo(String value) {
			addCriterion("OWNER =", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerNotEqualTo(String value) {
			addCriterion("OWNER <>", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerGreaterThan(String value) {
			addCriterion("OWNER >", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerGreaterThanOrEqualTo(String value) {
			addCriterion("OWNER >=", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerLessThan(String value) {
			addCriterion("OWNER <", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerLessThanOrEqualTo(String value) {
			addCriterion("OWNER <=", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerLike(String value) {
			addCriterion("OWNER like", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerNotLike(String value) {
			addCriterion("OWNER not like", value, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerIn(List<String> values) {
			addCriterion("OWNER in", values, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerNotIn(List<String> values) {
			addCriterion("OWNER not in", values, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerBetween(String value1, String value2) {
			addCriterion("OWNER between", value1, value2, "owner");
			return (Criteria) this;
		}

		public Criteria andOwnerNotBetween(String value1, String value2) {
			addCriterion("OWNER not between", value1, value2, "owner");
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