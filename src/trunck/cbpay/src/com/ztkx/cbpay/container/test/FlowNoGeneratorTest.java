package com.ztkx.cbpay.container.test;

import org.junit.Test;

import com.ztkx.cbpay.container.frame.FlowNoGenerator;

public class FlowNoGeneratorTest {

	@Test
	public void test() {
		FlowNoGenerator.instance.getFlowNo();
	}

}
