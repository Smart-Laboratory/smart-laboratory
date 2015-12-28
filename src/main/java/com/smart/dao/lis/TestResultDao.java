package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestResultPK;
import com.smart.model.lis.TestResult;

public interface TestResultDao extends GenericDao<TestResult, TestResultPK> {

	@Transactional
	String getFormulaResult(String fm);

	@Transactional
	List<TestResult> getTestBySampleNo(String sampleNo);
	
	@Transactional
	TestResult getSingleTestResult(String sampleNo, String testId);
	
	@Transactional
	List<TestResult> getListByTestString (String sampleNo, String testString);
	
	@Transactional
	List<TestResult> getSingleHistory(String testid, String patientName,
			String birthday);

	@Transactional
	List<TestResult> getRelative(String patientId, String history);
}
