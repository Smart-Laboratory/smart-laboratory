package com.smart.service.lis;

import java.util.List;
import java.util.Map;

import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.GenericManager;

public interface TestResultManager extends GenericManager<TestResult, TestResultPK> {

	String getFormulaResult(String fm);
	
	List<TestResult> getTestBySampleNo(String sampleNo);
	
	List<TestResult> getTestBySampleNos(String sampleNos);
	
	/**
	 * key:old value:new
	 * @param trMap
	 * @return
	 */
	int updateAll(Map <String,String>trMap);
	
	TestResult getSingleTestResult(String sampleNo, String testId);

	List<TestResult> getListByTestString(String sampleNo, String testString);
	
	TestResult getListByTestId(String sNo,String tid);
	
	//testresult右键表图
	List<TestResult> getSingleHistory(String testid, String patientName);

	List<TestResult> getRelative(String patientId, String blh, String history);

	List<TestResult> getPrintTestBySampleNo(String sampleno);

	void saveAll(List<TestResult> list);
	void deleteAll (List<TestResult> list);

	List<TestResult> getHisTestResult(String substring);

	List<TestResult> getSampleByCode(String string);
}
