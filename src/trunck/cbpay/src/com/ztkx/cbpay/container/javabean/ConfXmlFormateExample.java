package com.ztkx.cbpay.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class ConfXmlFormateExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ConfXmlFormateExample() {
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

		public Criteria andTranfromIsNull() {
			addCriterion("TRANFROM is null");
			return (Criteria) this;
		}

		public Criteria andTranfromIsNotNull() {
			addCriterion("TRANFROM is not null");
			return (Criteria) this;
		}

		public Criteria andTranfromEqualTo(String value) {
			addCriterion("TRANFROM =", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromNotEqualTo(String value) {
			addCriterion("TRANFROM <>", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromGreaterThan(String value) {
			addCriterion("TRANFROM >", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromGreaterThanOrEqualTo(String value) {
			addCriterion("TRANFROM >=", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromLessThan(String value) {
			addCriterion("TRANFROM <", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromLessThanOrEqualTo(String value) {
			addCriterion("TRANFROM <=", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromLike(String value) {
			addCriterion("TRANFROM like", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromNotLike(String value) {
			addCriterion("TRANFROM not like", value, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromIn(List<String> values) {
			addCriterion("TRANFROM in", values, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromNotIn(List<String> values) {
			addCriterion("TRANFROM not in", values, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromBetween(String value1, String value2) {
			addCriterion("TRANFROM between", value1, value2, "tranfrom");
			return (Criteria) this;
		}

		public Criteria andTranfromNotBetween(String value1, String value2) {
			addCriterion("TRANFROM not between", value1, value2, "tranfrom");
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

		public Criteria andSystemcodeIsNull() {
			addCriterion("SYSTEMCODE is null");
			return (Criteria) this;
		}

		public Criteria andSystemcodeIsNotNull() {
			addCriterion("SYSTEMCODE is not null");
			return (Criteria) this;
		}

		public Criteria andSystemcodeEqualTo(String value) {
			addCriterion("SYSTEMCODE =", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeNotEqualTo(String value) {
			addCriterion("SYSTEMCODE <>", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeGreaterThan(String value) {
			addCriterion("SYSTEMCODE >", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeGreaterThanOrEqualTo(String value) {
			addCriterion("SYSTEMCODE >=", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeLessThan(String value) {
			addCriterion("SYSTEMCODE <", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeLessThanOrEqualTo(String value) {
			addCriterion("SYSTEMCODE <=", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeLike(String value) {
			addCriterion("SYSTEMCODE like", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeNotLike(String value) {
			addCriterion("SYSTEMCODE not like", value, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeIn(List<String> values) {
			addCriterion("SYSTEMCODE in", values, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeNotIn(List<String> values) {
			addCriterion("SYSTEMCODE not in", values, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeBetween(String value1, String value2) {
			addCriterion("SYSTEMCODE between", value1, value2, "systemcode");
			return (Criteria) this;
		}

		public Criteria andSystemcodeNotBetween(String value1, String value2) {
			addCriterion("SYSTEMCODE not between", value1, value2, "systemcode");
			return (Criteria) this;
		}

		public Criteria andPathIsNull() {
			addCriterion("PATH is null");
			return (Criteria) this;
		}

		public Criteria andPathIsNotNull() {
			addCriterion("PATH is not null");
			return (Criteria) this;
		}

		public Criteria andPathEqualTo(String value) {
			addCriterion("PATH =", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotEqualTo(String value) {
			addCriterion("PATH <>", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathGreaterThan(String value) {
			addCriterion("PATH >", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathGreaterThanOrEqualTo(String value) {
			addCriterion("PATH >=", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLessThan(String value) {
			addCriterion("PATH <", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLessThanOrEqualTo(String value) {
			addCriterion("PATH <=", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathLike(String value) {
			addCriterion("PATH like", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotLike(String value) {
			addCriterion("PATH not like", value, "path");
			return (Criteria) this;
		}

		public Criteria andPathIn(List<String> values) {
			addCriterion("PATH in", values, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotIn(List<String> values) {
			addCriterion("PATH not in", values, "path");
			return (Criteria) this;
		}

		public Criteria andPathBetween(String value1, String value2) {
			addCriterion("PATH between", value1, value2, "path");
			return (Criteria) this;
		}

		public Criteria andPathNotBetween(String value1, String value2) {
			addCriterion("PATH not between", value1, value2, "path");
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