package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class TransInfoExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public TransInfoExample() {
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

		public Criteria andTrandescIsNull() {
			addCriterion("TRANDESC is null");
			return (Criteria) this;
		}

		public Criteria andTrandescIsNotNull() {
			addCriterion("TRANDESC is not null");
			return (Criteria) this;
		}

		public Criteria andTrandescEqualTo(String value) {
			addCriterion("TRANDESC =", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescNotEqualTo(String value) {
			addCriterion("TRANDESC <>", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescGreaterThan(String value) {
			addCriterion("TRANDESC >", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescGreaterThanOrEqualTo(String value) {
			addCriterion("TRANDESC >=", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescLessThan(String value) {
			addCriterion("TRANDESC <", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescLessThanOrEqualTo(String value) {
			addCriterion("TRANDESC <=", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescLike(String value) {
			addCriterion("TRANDESC like", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescNotLike(String value) {
			addCriterion("TRANDESC not like", value, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescIn(List<String> values) {
			addCriterion("TRANDESC in", values, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescNotIn(List<String> values) {
			addCriterion("TRANDESC not in", values, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescBetween(String value1, String value2) {
			addCriterion("TRANDESC between", value1, value2, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTrandescNotBetween(String value1, String value2) {
			addCriterion("TRANDESC not between", value1, value2, "trandesc");
			return (Criteria) this;
		}

		public Criteria andTranOprIsNull() {
			addCriterion("TRAN_OPR is null");
			return (Criteria) this;
		}

		public Criteria andTranOprIsNotNull() {
			addCriterion("TRAN_OPR is not null");
			return (Criteria) this;
		}

		public Criteria andTranOprEqualTo(String value) {
			addCriterion("TRAN_OPR =", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprNotEqualTo(String value) {
			addCriterion("TRAN_OPR <>", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprGreaterThan(String value) {
			addCriterion("TRAN_OPR >", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprGreaterThanOrEqualTo(String value) {
			addCriterion("TRAN_OPR >=", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprLessThan(String value) {
			addCriterion("TRAN_OPR <", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprLessThanOrEqualTo(String value) {
			addCriterion("TRAN_OPR <=", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprLike(String value) {
			addCriterion("TRAN_OPR like", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprNotLike(String value) {
			addCriterion("TRAN_OPR not like", value, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprIn(List<String> values) {
			addCriterion("TRAN_OPR in", values, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprNotIn(List<String> values) {
			addCriterion("TRAN_OPR not in", values, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprBetween(String value1, String value2) {
			addCriterion("TRAN_OPR between", value1, value2, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andTranOprNotBetween(String value1, String value2) {
			addCriterion("TRAN_OPR not between", value1, value2, "tranOpr");
			return (Criteria) this;
		}

		public Criteria andChannelDiyIsNull() {
			addCriterion("CHANNEL_DIY is null");
			return (Criteria) this;
		}

		public Criteria andChannelDiyIsNotNull() {
			addCriterion("CHANNEL_DIY is not null");
			return (Criteria) this;
		}

		public Criteria andChannelDiyEqualTo(String value) {
			addCriterion("CHANNEL_DIY =", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyNotEqualTo(String value) {
			addCriterion("CHANNEL_DIY <>", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyGreaterThan(String value) {
			addCriterion("CHANNEL_DIY >", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyGreaterThanOrEqualTo(String value) {
			addCriterion("CHANNEL_DIY >=", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyLessThan(String value) {
			addCriterion("CHANNEL_DIY <", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyLessThanOrEqualTo(String value) {
			addCriterion("CHANNEL_DIY <=", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyLike(String value) {
			addCriterion("CHANNEL_DIY like", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyNotLike(String value) {
			addCriterion("CHANNEL_DIY not like", value, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyIn(List<String> values) {
			addCriterion("CHANNEL_DIY in", values, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyNotIn(List<String> values) {
			addCriterion("CHANNEL_DIY not in", values, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyBetween(String value1, String value2) {
			addCriterion("CHANNEL_DIY between", value1, value2, "channelDiy");
			return (Criteria) this;
		}

		public Criteria andChannelDiyNotBetween(String value1, String value2) {
			addCriterion("CHANNEL_DIY not between", value1, value2,
					"channelDiy");
			return (Criteria) this;
		}

		public Criteria andOvertimeIsNull() {
			addCriterion("OVERTIME is null");
			return (Criteria) this;
		}

		public Criteria andOvertimeIsNotNull() {
			addCriterion("OVERTIME is not null");
			return (Criteria) this;
		}

		public Criteria andOvertimeEqualTo(String value) {
			addCriterion("OVERTIME =", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeNotEqualTo(String value) {
			addCriterion("OVERTIME <>", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeGreaterThan(String value) {
			addCriterion("OVERTIME >", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeGreaterThanOrEqualTo(String value) {
			addCriterion("OVERTIME >=", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeLessThan(String value) {
			addCriterion("OVERTIME <", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeLessThanOrEqualTo(String value) {
			addCriterion("OVERTIME <=", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeLike(String value) {
			addCriterion("OVERTIME like", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeNotLike(String value) {
			addCriterion("OVERTIME not like", value, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeIn(List<String> values) {
			addCriterion("OVERTIME in", values, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeNotIn(List<String> values) {
			addCriterion("OVERTIME not in", values, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeBetween(String value1, String value2) {
			addCriterion("OVERTIME between", value1, value2, "overtime");
			return (Criteria) this;
		}

		public Criteria andOvertimeNotBetween(String value1, String value2) {
			addCriterion("OVERTIME not between", value1, value2, "overtime");
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