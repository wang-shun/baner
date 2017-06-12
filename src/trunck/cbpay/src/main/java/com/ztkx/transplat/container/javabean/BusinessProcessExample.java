package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class BusinessProcessExample {
	
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public BusinessProcessExample() {
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

		public Criteria andSubOprcodeIsNull() {
			addCriterion("SUB_OPRCODE is null");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeIsNotNull() {
			addCriterion("SUB_OPRCODE is not null");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeEqualTo(String value) {
			addCriterion("SUB_OPRCODE =", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeNotEqualTo(String value) {
			addCriterion("SUB_OPRCODE <>", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeGreaterThan(String value) {
			addCriterion("SUB_OPRCODE >", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeGreaterThanOrEqualTo(String value) {
			addCriterion("SUB_OPRCODE >=", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeLessThan(String value) {
			addCriterion("SUB_OPRCODE <", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeLessThanOrEqualTo(String value) {
			addCriterion("SUB_OPRCODE <=", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeLike(String value) {
			addCriterion("SUB_OPRCODE like", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeNotLike(String value) {
			addCriterion("SUB_OPRCODE not like", value, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeIn(List<String> values) {
			addCriterion("SUB_OPRCODE in", values, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeNotIn(List<String> values) {
			addCriterion("SUB_OPRCODE not in", values, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeBetween(String value1, String value2) {
			addCriterion("SUB_OPRCODE between", value1, value2, "subOprcode");
			return (Criteria) this;
		}

		public Criteria andSubOprcodeNotBetween(String value1, String value2) {
			addCriterion("SUB_OPRCODE not between", value1, value2,
					"subOprcode");
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

		public Criteria andOrdernumIsNull() {
			addCriterion("ORDERNUM is null");
			return (Criteria) this;
		}

		public Criteria andOrdernumIsNotNull() {
			addCriterion("ORDERNUM is not null");
			return (Criteria) this;
		}

		public Criteria andOrdernumEqualTo(String value) {
			addCriterion("ORDERNUM =", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumNotEqualTo(String value) {
			addCriterion("ORDERNUM <>", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumGreaterThan(String value) {
			addCriterion("ORDERNUM >", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumGreaterThanOrEqualTo(String value) {
			addCriterion("ORDERNUM >=", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumLessThan(String value) {
			addCriterion("ORDERNUM <", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumLessThanOrEqualTo(String value) {
			addCriterion("ORDERNUM <=", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumLike(String value) {
			addCriterion("ORDERNUM like", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumNotLike(String value) {
			addCriterion("ORDERNUM not like", value, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumIn(List<String> values) {
			addCriterion("ORDERNUM in", values, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumNotIn(List<String> values) {
			addCriterion("ORDERNUM not in", values, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumBetween(String value1, String value2) {
			addCriterion("ORDERNUM between", value1, value2, "ordernum");
			return (Criteria) this;
		}

		public Criteria andOrdernumNotBetween(String value1, String value2) {
			addCriterion("ORDERNUM not between", value1, value2, "ordernum");
			return (Criteria) this;
		}

		public Criteria andNextOrderIsNull() {
			addCriterion("NEXT_ORDER is null");
			return (Criteria) this;
		}

		public Criteria andNextOrderIsNotNull() {
			addCriterion("NEXT_ORDER is not null");
			return (Criteria) this;
		}

		public Criteria andNextOrderEqualTo(String value) {
			addCriterion("NEXT_ORDER =", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderNotEqualTo(String value) {
			addCriterion("NEXT_ORDER <>", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderGreaterThan(String value) {
			addCriterion("NEXT_ORDER >", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderGreaterThanOrEqualTo(String value) {
			addCriterion("NEXT_ORDER >=", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderLessThan(String value) {
			addCriterion("NEXT_ORDER <", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderLessThanOrEqualTo(String value) {
			addCriterion("NEXT_ORDER <=", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderLike(String value) {
			addCriterion("NEXT_ORDER like", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderNotLike(String value) {
			addCriterion("NEXT_ORDER not like", value, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderIn(List<String> values) {
			addCriterion("NEXT_ORDER in", values, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderNotIn(List<String> values) {
			addCriterion("NEXT_ORDER not in", values, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderBetween(String value1, String value2) {
			addCriterion("NEXT_ORDER between", value1, value2, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andNextOrderNotBetween(String value1, String value2) {
			addCriterion("NEXT_ORDER not between", value1, value2, "nextOrder");
			return (Criteria) this;
		}

		public Criteria andExpressionIsNull() {
			addCriterion("EXPRESSION is null");
			return (Criteria) this;
		}

		public Criteria andExpressionIsNotNull() {
			addCriterion("EXPRESSION is not null");
			return (Criteria) this;
		}

		public Criteria andExpressionEqualTo(String value) {
			addCriterion("EXPRESSION =", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionNotEqualTo(String value) {
			addCriterion("EXPRESSION <>", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionGreaterThan(String value) {
			addCriterion("EXPRESSION >", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionGreaterThanOrEqualTo(String value) {
			addCriterion("EXPRESSION >=", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionLessThan(String value) {
			addCriterion("EXPRESSION <", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionLessThanOrEqualTo(String value) {
			addCriterion("EXPRESSION <=", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionLike(String value) {
			addCriterion("EXPRESSION like", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionNotLike(String value) {
			addCriterion("EXPRESSION not like", value, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionIn(List<String> values) {
			addCriterion("EXPRESSION in", values, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionNotIn(List<String> values) {
			addCriterion("EXPRESSION not in", values, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionBetween(String value1, String value2) {
			addCriterion("EXPRESSION between", value1, value2, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionNotBetween(String value1, String value2) {
			addCriterion("EXPRESSION not between", value1, value2, "expression");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextIsNull() {
			addCriterion("EXPRESSION_SUCC_NEXT is null");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextIsNotNull() {
			addCriterion("EXPRESSION_SUCC_NEXT is not null");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextEqualTo(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT =", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextNotEqualTo(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT <>", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextGreaterThan(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT >", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextGreaterThanOrEqualTo(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT >=", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextLessThan(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT <", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextLessThanOrEqualTo(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT <=", value, "expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextLike(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT like", value,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextNotLike(String value) {
			addCriterion("EXPRESSION_SUCC_NEXT not like", value,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextIn(List<String> values) {
			addCriterion("EXPRESSION_SUCC_NEXT in", values,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextNotIn(List<String> values) {
			addCriterion("EXPRESSION_SUCC_NEXT not in", values,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextBetween(String value1,
				String value2) {
			addCriterion("EXPRESSION_SUCC_NEXT between", value1, value2,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionSuccNextNotBetween(String value1,
				String value2) {
			addCriterion("EXPRESSION_SUCC_NEXT not between", value1, value2,
					"expressionSuccNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextIsNull() {
			addCriterion("EXPRESSION_FAIL_NEXT is null");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextIsNotNull() {
			addCriterion("EXPRESSION_FAIL_NEXT is not null");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextEqualTo(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT =", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextNotEqualTo(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT <>", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextGreaterThan(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT >", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextGreaterThanOrEqualTo(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT >=", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextLessThan(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT <", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextLessThanOrEqualTo(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT <=", value, "expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextLike(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT like", value,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextNotLike(String value) {
			addCriterion("EXPRESSION_FAIL_NEXT not like", value,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextIn(List<String> values) {
			addCriterion("EXPRESSION_FAIL_NEXT in", values,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextNotIn(List<String> values) {
			addCriterion("EXPRESSION_FAIL_NEXT not in", values,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextBetween(String value1,
				String value2) {
			addCriterion("EXPRESSION_FAIL_NEXT between", value1, value2,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andExpressionFailNextNotBetween(String value1,
				String value2) {
			addCriterion("EXPRESSION_FAIL_NEXT not between", value1, value2,
					"expressionFailNext");
			return (Criteria) this;
		}

		public Criteria andReversalFalgIsNull() {
			addCriterion("REVERSAL_FALG is null");
			return (Criteria) this;
		}

		public Criteria andReversalFalgIsNotNull() {
			addCriterion("REVERSAL_FALG is not null");
			return (Criteria) this;
		}

		public Criteria andReversalFalgEqualTo(String value) {
			addCriterion("REVERSAL_FALG =", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgNotEqualTo(String value) {
			addCriterion("REVERSAL_FALG <>", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgGreaterThan(String value) {
			addCriterion("REVERSAL_FALG >", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgGreaterThanOrEqualTo(String value) {
			addCriterion("REVERSAL_FALG >=", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgLessThan(String value) {
			addCriterion("REVERSAL_FALG <", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgLessThanOrEqualTo(String value) {
			addCriterion("REVERSAL_FALG <=", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgLike(String value) {
			addCriterion("REVERSAL_FALG like", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgNotLike(String value) {
			addCriterion("REVERSAL_FALG not like", value, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgIn(List<String> values) {
			addCriterion("REVERSAL_FALG in", values, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgNotIn(List<String> values) {
			addCriterion("REVERSAL_FALG not in", values, "reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgBetween(String value1, String value2) {
			addCriterion("REVERSAL_FALG between", value1, value2,
					"reversalFalg");
			return (Criteria) this;
		}

		public Criteria andReversalFalgNotBetween(String value1, String value2) {
			addCriterion("REVERSAL_FALG not between", value1, value2,
					"reversalFalg");
			return (Criteria) this;
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