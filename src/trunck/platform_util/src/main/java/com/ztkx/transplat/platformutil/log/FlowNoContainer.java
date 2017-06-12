package com.ztkx.transplat.platformutil.log;



public class FlowNoContainer {

	static ThreadLocal<String> t = new ThreadLocal<String>();

	public static String getFlowNo() {
		return t.get();
	}

	public static void setFlowNo(String flowNo) {
		t.set(flowNo);
	}
}
