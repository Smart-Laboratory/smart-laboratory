package com.smart.drools;

public class SI extends I {

	public SI(String testId, String type, String unit, String value) {
		super(testId, type, unit, value);
	}

	public SI(String testId, String result) {
		super(testId, "血液", "", "");
		this.setSv(result);
	}

	private String sv;

	public String getSv() {
		return sv;
	}

	public void setSv(String v) {
		this.sv = v;
	}
}
