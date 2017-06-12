package com.ztkx.transplat.container.javabean;

public class BusinessProcess extends BusinessProcessKey {
    private String ordernum;

	private String next_order;

	private String expression;

	private String expression_succ_next;

	private String expression_fail_next;

	private String rversal_falg;

	private String responsecode;

	private String owner;

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}


	public String getNext_order() {
		return next_order;
	}

	public void setNext_order(String next_order) {
		this.next_order = next_order;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression_succ_next() {
		return expression_succ_next;
	}

	public void setExpression_succ_next(String expression_succ_next) {
		this.expression_succ_next = expression_succ_next;
	}

	public String getExpression_fail_next() {
		return expression_fail_next;
	}

	public void setExpression_fail_next(String expression_fail_next) {
		this.expression_fail_next = expression_fail_next;
	}

	public String getRversal_falg() {
		return rversal_falg;
	}

	public void setRversal_falg(String rversal_falg) {
		this.rversal_falg = rversal_falg;
	}

	public String getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}	
}