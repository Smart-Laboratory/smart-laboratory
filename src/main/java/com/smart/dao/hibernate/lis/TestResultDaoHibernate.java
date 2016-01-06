package com.smart.dao.hibernate.lis;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestResultDao;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;

@Repository("testResultDao")
public class TestResultDaoHibernate extends GenericDaoHibernate<TestResult, TestResultPK> implements TestResultDao {
	
	public TestResultDaoHibernate(){
		super(TestResult.class);
	}

	public void updateFormula(TestResult t, String formula) {
		if(formula.contains("decode(1,2")) {
			formula = formula.substring(formula.indexOf("),1,(")+5, formula.indexOf("))"));
		}
		if(formula.contains("decode(2,2")) {
			formula = formula.substring(12, formula.indexOf("),1,("));
		}
		String sql = "update l_testresult set testresult=concat(round(" + formula + ", 2),'') where testid='" + t.getTestId()
				+ "' and sampleno='" + t.getSampleNo() + "'";
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public String getFormulaResult(String formula) {
		if(formula.contains("decode(1,2")) {
			formula = formula.substring(formula.indexOf("),1,(")+5, formula.indexOf("))"));
		}
		if(formula.contains("decode(2,2")) {
			formula = formula.substring(12, formula.indexOf("),1,("));
		}
		String sql = "select concat(round(" + formula + ", 2),'')";
		return (String) getSession().createSQLQuery(sql).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TestResult> getTestBySampleNo(String sampleNo){
//		String sql = "select t.* from l_testResult t where  t.sampleno='"+ sampleNo + "' order by t.testid ";
		
		return getSession().createQuery("select t from TestResult as t, Index as i where t.testId=i.indexId and sampleNo='"+sampleNo+"' order by i.printord").list();
		
		
	}
	
	public TestResult getSingleTestResult(String sampleNo, String testId){
		return (TestResult)getSession().createQuery("from TestResult where sampleNo='"+sampleNo+"' and testId='"+testId+"'").list().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TestResult> getListByTestString (String sampleNo, String testString){
		return getSession().createQuery("from TestResult where sampleNo='" + sampleNo
				+ "' and testId in ('"+ testString.replace(",", "','") +"')").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TestResult> getSingleHistory(String testid, String patientName,
			String patientid) {
		String hql = "select t from Sample s, TestResult t where t.testId in (" + testid
				+ ")  and s.patient.blh='" + patientid + "' and s.sampleNo=t.sampleNo order by t.measureTime desc";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getRelative(String patientId, String blh, String history) {
		String hql = "select t from Sample s, TestResult t where (s.patientId='" + patientId
				+ "' or s.patientId='" + blh + "') and t.sampleNo=s.sampleNo and t.testId in " + history + " and s.process.receivetime is not null order by s.process.receivetime desc,t.testId asc";
		Query q = getSession().createQuery(hql);
		return q.list();
	}
	
}
