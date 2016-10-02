package com.smart.service.lis;

import java.util.List;
import java.util.Map;

import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

public interface TestResultManager extends GenericManager<TestResult, TestResultPK> {

	@Transactional
	String getFormulaResult(String fm);

	@Transactional
	List<TestResult> getTestBySampleNo(String sampleNo);

	@Transactional
	List<TestResult> getTestBySampleNos(String sampleNos);
	
	/**
	 * key:old value:new
	 * @param trMap
	 * @return
	 */
	int updateAll(Map <String,String>trMap);

	@Transactional
	TestResult getSingleTestResult(String sampleNo, String testId);

	@Transactional
	List<TestResult> getListByTestString(String sampleNo, String testString);

	@Transactional
	TestResult getListByTestId(String sNo,String tid);
	
	//testresult右键表图
	@Transactional
	List<TestResult> getSingleHistory(String testid, String patientName);

	@Transactional
	List<TestResult> getRelative(String patientId, String blh, String history);

	@Transactional
	List<TestResult> getPrintTestBySampleNo(String sampleno);

	void saveAll(List<TestResult> list);

	void deleteAll (List<TestResult> list);

	@Transactional
	List<TestResult> getHisTestResult(String substring);

	@Transactional
	List<TestResult> getSampleByCode(String string);

	/**
	 * key:old value:new
	 * @param text 日期：20161002
	 * @param  code   检验段：CBC，CBA，CBD
	 * @return
	 */
	@Transactional
    List<TestResult> getNoInfoSampleNo(String text,String code);
}
