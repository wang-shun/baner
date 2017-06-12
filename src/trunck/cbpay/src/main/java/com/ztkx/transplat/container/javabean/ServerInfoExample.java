package com.ztkx.transplat.container.javabean;

import java.util.ArrayList;
import java.util.List;

public class ServerInfoExample {
    protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ServerInfoExample() {
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

		public Criteria andServerKeyIsNull() {
			addCriterion("SERVER_KEY is null");
			return (Criteria) this;
		}

		public Criteria andServerKeyIsNotNull() {
			addCriterion("SERVER_KEY is not null");
			return (Criteria) this;
		}

		public Criteria andServerKeyEqualTo(String value) {
			addCriterion("SERVER_KEY =", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyNotEqualTo(String value) {
			addCriterion("SERVER_KEY <>", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyGreaterThan(String value) {
			addCriterion("SERVER_KEY >", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyGreaterThanOrEqualTo(String value) {
			addCriterion("SERVER_KEY >=", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyLessThan(String value) {
			addCriterion("SERVER_KEY <", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyLessThanOrEqualTo(String value) {
			addCriterion("SERVER_KEY <=", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyLike(String value) {
			addCriterion("SERVER_KEY like", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyNotLike(String value) {
			addCriterion("SERVER_KEY not like", value, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyIn(List<String> values) {
			addCriterion("SERVER_KEY in", values, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyNotIn(List<String> values) {
			addCriterion("SERVER_KEY not in", values, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyBetween(String value1, String value2) {
			addCriterion("SERVER_KEY between", value1, value2, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerKeyNotBetween(String value1, String value2) {
			addCriterion("SERVER_KEY not between", value1, value2, "serverKey");
			return (Criteria) this;
		}

		public Criteria andServerdescIsNull() {
			addCriterion("SERVERDESC is null");
			return (Criteria) this;
		}

		public Criteria andServerdescIsNotNull() {
			addCriterion("SERVERDESC is not null");
			return (Criteria) this;
		}

		public Criteria andServerdescEqualTo(String value) {
			addCriterion("SERVERDESC =", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescNotEqualTo(String value) {
			addCriterion("SERVERDESC <>", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescGreaterThan(String value) {
			addCriterion("SERVERDESC >", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescGreaterThanOrEqualTo(String value) {
			addCriterion("SERVERDESC >=", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescLessThan(String value) {
			addCriterion("SERVERDESC <", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescLessThanOrEqualTo(String value) {
			addCriterion("SERVERDESC <=", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescLike(String value) {
			addCriterion("SERVERDESC like", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescNotLike(String value) {
			addCriterion("SERVERDESC not like", value, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescIn(List<String> values) {
			addCriterion("SERVERDESC in", values, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescNotIn(List<String> values) {
			addCriterion("SERVERDESC not in", values, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescBetween(String value1, String value2) {
			addCriterion("SERVERDESC between", value1, value2, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andServerdescNotBetween(String value1, String value2) {
			addCriterion("SERVERDESC not between", value1, value2, "serverdesc");
			return (Criteria) this;
		}

		public Criteria andIpIsNull() {
			addCriterion("IP is null");
			return (Criteria) this;
		}

		public Criteria andIpIsNotNull() {
			addCriterion("IP is not null");
			return (Criteria) this;
		}

		public Criteria andIpEqualTo(String value) {
			addCriterion("IP =", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpNotEqualTo(String value) {
			addCriterion("IP <>", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpGreaterThan(String value) {
			addCriterion("IP >", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpGreaterThanOrEqualTo(String value) {
			addCriterion("IP >=", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpLessThan(String value) {
			addCriterion("IP <", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpLessThanOrEqualTo(String value) {
			addCriterion("IP <=", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpLike(String value) {
			addCriterion("IP like", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpNotLike(String value) {
			addCriterion("IP not like", value, "ip");
			return (Criteria) this;
		}

		public Criteria andIpIn(List<String> values) {
			addCriterion("IP in", values, "ip");
			return (Criteria) this;
		}

		public Criteria andIpNotIn(List<String> values) {
			addCriterion("IP not in", values, "ip");
			return (Criteria) this;
		}

		public Criteria andIpBetween(String value1, String value2) {
			addCriterion("IP between", value1, value2, "ip");
			return (Criteria) this;
		}

		public Criteria andIpNotBetween(String value1, String value2) {
			addCriterion("IP not between", value1, value2, "ip");
			return (Criteria) this;
		}

		public Criteria andProtIsNull() {
			addCriterion("PROT is null");
			return (Criteria) this;
		}

		public Criteria andProtIsNotNull() {
			addCriterion("PROT is not null");
			return (Criteria) this;
		}

		public Criteria andProtEqualTo(String value) {
			addCriterion("PROT =", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtNotEqualTo(String value) {
			addCriterion("PROT <>", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtGreaterThan(String value) {
			addCriterion("PROT >", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtGreaterThanOrEqualTo(String value) {
			addCriterion("PROT >=", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtLessThan(String value) {
			addCriterion("PROT <", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtLessThanOrEqualTo(String value) {
			addCriterion("PROT <=", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtLike(String value) {
			addCriterion("PROT like", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtNotLike(String value) {
			addCriterion("PROT not like", value, "prot");
			return (Criteria) this;
		}

		public Criteria andProtIn(List<String> values) {
			addCriterion("PROT in", values, "prot");
			return (Criteria) this;
		}

		public Criteria andProtNotIn(List<String> values) {
			addCriterion("PROT not in", values, "prot");
			return (Criteria) this;
		}

		public Criteria andProtBetween(String value1, String value2) {
			addCriterion("PROT between", value1, value2, "prot");
			return (Criteria) this;
		}

		public Criteria andProtNotBetween(String value1, String value2) {
			addCriterion("PROT not between", value1, value2, "prot");
			return (Criteria) this;
		}

		public Criteria andIsencryptIsNull() {
			addCriterion("ISENCRYPT is null");
			return (Criteria) this;
		}

		public Criteria andIsencryptIsNotNull() {
			addCriterion("ISENCRYPT is not null");
			return (Criteria) this;
		}

		public Criteria andIsencryptEqualTo(String value) {
			addCriterion("ISENCRYPT =", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptNotEqualTo(String value) {
			addCriterion("ISENCRYPT <>", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptGreaterThan(String value) {
			addCriterion("ISENCRYPT >", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptGreaterThanOrEqualTo(String value) {
			addCriterion("ISENCRYPT >=", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptLessThan(String value) {
			addCriterion("ISENCRYPT <", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptLessThanOrEqualTo(String value) {
			addCriterion("ISENCRYPT <=", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptLike(String value) {
			addCriterion("ISENCRYPT like", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptNotLike(String value) {
			addCriterion("ISENCRYPT not like", value, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptIn(List<String> values) {
			addCriterion("ISENCRYPT in", values, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptNotIn(List<String> values) {
			addCriterion("ISENCRYPT not in", values, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptBetween(String value1, String value2) {
			addCriterion("ISENCRYPT between", value1, value2, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andIsencryptNotBetween(String value1, String value2) {
			addCriterion("ISENCRYPT not between", value1, value2, "isencrypt");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmIsNull() {
			addCriterion("ENCRYPT_ALGORITHM is null");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmIsNotNull() {
			addCriterion("ENCRYPT_ALGORITHM is not null");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmEqualTo(String value) {
			addCriterion("ENCRYPT_ALGORITHM =", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmNotEqualTo(String value) {
			addCriterion("ENCRYPT_ALGORITHM <>", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmGreaterThan(String value) {
			addCriterion("ENCRYPT_ALGORITHM >", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmGreaterThanOrEqualTo(String value) {
			addCriterion("ENCRYPT_ALGORITHM >=", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmLessThan(String value) {
			addCriterion("ENCRYPT_ALGORITHM <", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmLessThanOrEqualTo(String value) {
			addCriterion("ENCRYPT_ALGORITHM <=", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmLike(String value) {
			addCriterion("ENCRYPT_ALGORITHM like", value, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmNotLike(String value) {
			addCriterion("ENCRYPT_ALGORITHM not like", value,
					"encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmIn(List<String> values) {
			addCriterion("ENCRYPT_ALGORITHM in", values, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmNotIn(List<String> values) {
			addCriterion("ENCRYPT_ALGORITHM not in", values, "encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmBetween(String value1, String value2) {
			addCriterion("ENCRYPT_ALGORITHM between", value1, value2,
					"encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andEncryptAlgorithmNotBetween(String value1,
				String value2) {
			addCriterion("ENCRYPT_ALGORITHM not between", value1, value2,
					"encryptAlgorithm");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileIsNull() {
			addCriterion("PUBLIC_KEY_FILE is null");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileIsNotNull() {
			addCriterion("PUBLIC_KEY_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileEqualTo(String value) {
			addCriterion("PUBLIC_KEY_FILE =", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileNotEqualTo(String value) {
			addCriterion("PUBLIC_KEY_FILE <>", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileGreaterThan(String value) {
			addCriterion("PUBLIC_KEY_FILE >", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileGreaterThanOrEqualTo(String value) {
			addCriterion("PUBLIC_KEY_FILE >=", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileLessThan(String value) {
			addCriterion("PUBLIC_KEY_FILE <", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileLessThanOrEqualTo(String value) {
			addCriterion("PUBLIC_KEY_FILE <=", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileLike(String value) {
			addCriterion("PUBLIC_KEY_FILE like", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileNotLike(String value) {
			addCriterion("PUBLIC_KEY_FILE not like", value, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileIn(List<String> values) {
			addCriterion("PUBLIC_KEY_FILE in", values, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileNotIn(List<String> values) {
			addCriterion("PUBLIC_KEY_FILE not in", values, "publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileBetween(String value1, String value2) {
			addCriterion("PUBLIC_KEY_FILE between", value1, value2,
					"publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andPublicKeyFileNotBetween(String value1, String value2) {
			addCriterion("PUBLIC_KEY_FILE not between", value1, value2,
					"publicKeyFile");
			return (Criteria) this;
		}

		public Criteria andIssignatureIsNull() {
			addCriterion("ISSIGNATURE is null");
			return (Criteria) this;
		}

		public Criteria andIssignatureIsNotNull() {
			addCriterion("ISSIGNATURE is not null");
			return (Criteria) this;
		}

		public Criteria andIssignatureEqualTo(String value) {
			addCriterion("ISSIGNATURE =", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureNotEqualTo(String value) {
			addCriterion("ISSIGNATURE <>", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureGreaterThan(String value) {
			addCriterion("ISSIGNATURE >", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureGreaterThanOrEqualTo(String value) {
			addCriterion("ISSIGNATURE >=", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureLessThan(String value) {
			addCriterion("ISSIGNATURE <", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureLessThanOrEqualTo(String value) {
			addCriterion("ISSIGNATURE <=", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureLike(String value) {
			addCriterion("ISSIGNATURE like", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureNotLike(String value) {
			addCriterion("ISSIGNATURE not like", value, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureIn(List<String> values) {
			addCriterion("ISSIGNATURE in", values, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureNotIn(List<String> values) {
			addCriterion("ISSIGNATURE not in", values, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureBetween(String value1, String value2) {
			addCriterion("ISSIGNATURE between", value1, value2, "issignature");
			return (Criteria) this;
		}

		public Criteria andIssignatureNotBetween(String value1, String value2) {
			addCriterion("ISSIGNATURE not between", value1, value2,
					"issignature");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmIsNull() {
			addCriterion("SIGNATURE_ALGORITHM is null");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmIsNotNull() {
			addCriterion("SIGNATURE_ALGORITHM is not null");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmEqualTo(String value) {
			addCriterion("SIGNATURE_ALGORITHM =", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmNotEqualTo(String value) {
			addCriterion("SIGNATURE_ALGORITHM <>", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmGreaterThan(String value) {
			addCriterion("SIGNATURE_ALGORITHM >", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmGreaterThanOrEqualTo(String value) {
			addCriterion("SIGNATURE_ALGORITHM >=", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmLessThan(String value) {
			addCriterion("SIGNATURE_ALGORITHM <", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmLessThanOrEqualTo(String value) {
			addCriterion("SIGNATURE_ALGORITHM <=", value, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmLike(String value) {
			addCriterion("SIGNATURE_ALGORITHM like", value,
					"signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmNotLike(String value) {
			addCriterion("SIGNATURE_ALGORITHM not like", value,
					"signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmIn(List<String> values) {
			addCriterion("SIGNATURE_ALGORITHM in", values, "signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmNotIn(List<String> values) {
			addCriterion("SIGNATURE_ALGORITHM not in", values,
					"signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmBetween(String value1,
				String value2) {
			addCriterion("SIGNATURE_ALGORITHM between", value1, value2,
					"signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andSignatureAlgorithmNotBetween(String value1,
				String value2) {
			addCriterion("SIGNATURE_ALGORITHM not between", value1, value2,
					"signatureAlgorithm");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeIsNull() {
			addCriterion("REMITTANCE_FEE is null");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeIsNotNull() {
			addCriterion("REMITTANCE_FEE is not null");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeEqualTo(String value) {
			addCriterion("REMITTANCE_FEE =", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeNotEqualTo(String value) {
			addCriterion("REMITTANCE_FEE <>", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeGreaterThan(String value) {
			addCriterion("REMITTANCE_FEE >", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeGreaterThanOrEqualTo(String value) {
			addCriterion("REMITTANCE_FEE >=", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeLessThan(String value) {
			addCriterion("REMITTANCE_FEE <", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeLessThanOrEqualTo(String value) {
			addCriterion("REMITTANCE_FEE <=", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeLike(String value) {
			addCriterion("REMITTANCE_FEE like", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeNotLike(String value) {
			addCriterion("REMITTANCE_FEE not like", value, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeIn(List<String> values) {
			addCriterion("REMITTANCE_FEE in", values, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeNotIn(List<String> values) {
			addCriterion("REMITTANCE_FEE not in", values, "remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeBetween(String value1, String value2) {
			addCriterion("REMITTANCE_FEE between", value1, value2,
					"remittanceFee");
			return (Criteria) this;
		}

		public Criteria andRemittanceFeeNotBetween(String value1, String value2) {
			addCriterion("REMITTANCE_FEE not between", value1, value2,
					"remittanceFee");
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