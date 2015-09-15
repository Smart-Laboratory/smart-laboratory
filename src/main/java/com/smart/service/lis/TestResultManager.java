package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.GenericManager;

public interface TestResultManager extends GenericManager<TestResult, TestResultPK> {

	String getFormulaResult(String fm);
	
	List<TestResult> getTestBySampleNo(String sampleNo);
	
	TestResult getSingleTestResult(String sampleNo, String testId);

	List<TestResult> getListByTestString(String sampleNo, String testString);
}
