package com.smart.dao.lis;

import java.util.List;
import java.util.Map;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestResultPK;
import com.smart.model.lis.TestResult;

public interface TestResultDao extends GenericDao<TestResult, TestResultPK> {

	String getFormulaResult(String fm);

	List<TestResult> getTestBySampleNo(String sampleNo);
	
	int updateAll(Map <String,String>trMap);
	
	List<TestResult> getTestBySampleNos(String sampleNos);
	
	TestResult getSingleTestResult(String sampleNo, String testId);
	
	List<TestResult> getListByTestString (String sampleNo, String testString);
	
	TestResult getListByTestId(String sNo,String tid);
	
	List<TestResult> getSingleHistory(String testid, String patientName);

	List<TestResult> getRelative(String patientId, String blh, String history);

	List<TestResult> getPrintTestBySampleNo(String sampleno);

	void saveAll(List<TestResult> list);
	
	void deleteAll(List<TestResult> list);
	
	List<TestResult> getHisTestResult(String samplenos);

	List<TestResult> getSampleByCode(String code);

    List<TestResult> getNoInfoSampleNo(String text, String code);
}
