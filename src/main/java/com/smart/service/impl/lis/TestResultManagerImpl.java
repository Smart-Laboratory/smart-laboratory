package com.smart.service.impl.lis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.TestResultDao;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.TestResultManager;

@Service("testResultManager")
public class TestResultManagerImpl extends GenericManagerImpl<TestResult, TestResultPK> implements TestResultManager {

	private TestResultDao testResultDao;

	@Autowired
	public void setTestResultDao(TestResultDao testResultDao) {
		this.dao = testResultDao;
		this.testResultDao = testResultDao;
	}

	public String getFormulaResult(String fm) {
		return testResultDao.getFormulaResult(fm);
	}
	
	public List<TestResult> getTestBySampleNo(String sampleNos){
		return testResultDao.getTestBySampleNo(sampleNos);
	}
	
	public List<TestResult> getTestBySampleNos(String sampleNos){
		return testResultDao.getTestBySampleNos(sampleNos);
	}
	/**
	 * key:old value:new
	 */
	public int updateAll(Map <String,String>trMap){
		return testResultDao.updateAll(trMap);
	}
	public TestResult getSingleTestResult(String sampleNo, String testId){
		return testResultDao.getSingleTestResult(sampleNo,testId);
	}
	
	public List<TestResult> getListByTestString (String sampleNo, String testString){
		return testResultDao.getListByTestString(sampleNo,testString);
	}
	
	public TestResult getListByTestId(String sNo,String tid){
		return testResultDao.getListByTestId(sNo,tid);
	}
	
	public List<TestResult> getSingleHistory(String testid, String patientid) {
		return testResultDao.getSingleHistory(testid, patientid);
	}

	public List<TestResult> getRelative(String patientId, String blh, String history) {
		return testResultDao.getRelative(patientId, blh, history);
	}

	public List<TestResult> getPrintTestBySampleNo(String sampleno) {
		return testResultDao.getPrintTestBySampleNo(sampleno);
	}

	public void saveAll(List<TestResult> list) {
		testResultDao.saveAll(list);
	}
	public void deleteAll(List<TestResult> list) {
		testResultDao.deleteAll(list);
	}

	public List<TestResult> getHisTestResult(String samplenos) {
		return testResultDao.getHisTestResult(samplenos);
	}

	public List<TestResult> getSampleByCode(String code) {
		return testResultDao.getSampleByCode(code);
	}
}
