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
	List<TestResult> getSingleHistory(String testid, String patientName);

	@Transactional
	List<TestResult> getRelative(String patientId, String blh, String history);

	@Transactional
	List<TestResult> getPrintTestBySampleNo(String sampleno);

	@Transactional
	void saveAll(List<TestResult> list);

	@Transactional
	List<TestResult> getHisTestResult(String samplenos);

	List<TestResult> getSampleByCode(String code);
}
