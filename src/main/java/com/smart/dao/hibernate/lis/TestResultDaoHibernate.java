package com.smart.dao.hibernate.lis;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TestResultDao;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultPK;

@Repository("testResultDao")
public class TestResultDaoHibernate extends
		GenericDaoHibernate<TestResult, TestResultPK> implements TestResultDao {

	public TestResultDaoHibernate() {
		super(TestResult.class);
	}

	public void updateFormula(TestResult t, String formula) {
		if (formula.contains("decode(1,2")) {
			formula = formula.substring(formula.indexOf("),1,(") + 5,
					formula.indexOf("))"));
		}
		if (formula.contains("decode(2,2")) {
			formula = formula.substring(12, formula.indexOf("),1,("));
		}
		String sql = "update l_testresult set testresult=concat(round("
				+ formula + ", 2),'') where testid='" + t.getTestId()
				+ "' and sampleno='" + t.getSampleNo() + "'";
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public String getFormulaResult(String formula) {
		if (formula.contains("decode(1,2")) {
			formula = formula.substring(formula.indexOf("),1,(") + 5,
					formula.indexOf("))"));
		}
		if (formula.contains("decode(2,2")) {
			formula = formula.substring(12, formula.indexOf("),1,("));
		}
		String sql = "select to_char(round(" + formula
				+ ", 2),'fm99990.000') from dual";
		// String sql = "select concat(round(" + formula + ", 2),'')";
		return (String) getSession().createSQLQuery(sql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getTestBySampleNo(String sampleNo) {
		// String sql = "select t.* from l_testResult t where  t.sampleno='"+
		// sampleNo + "' order by t.testid ";

		return getSession()
				.createQuery(
						"select t from TestResult t, Index i where t.testId=i.indexId and t.sampleNo='"
								+ sampleNo + "' order by i.printord").list();

	}

	public TestResult getSingleTestResult(String sampleNo, String testId) {
		return (TestResult) getSession()
				.createQuery(
						"from TestResult where sampleNo='" + sampleNo
								+ "' and testId='" + testId + "'").list()
				.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getTestBySampleNos(String ids) {
		try {
			String sql = "from TestResult where sampleNo in (" + ids + ")";
			return getSession().createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updateAll(Map <String,String>trMap){
		try {
			Transaction tx = getSession().beginTransaction();
			for (String key : trMap.keySet()) {  
				String hqlUpdate = "update TestResult set sampleNo = :newsampleNo where sampleNo=:sampleNo";
				getSession().createQuery(hqlUpdate)
						.setString("newsampleNo",trMap.get(key))
						.setString("sampleNo",key).executeUpdate();
			}
			tx.commit();
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getListByTestString(String sampleNo,
			String testString) {
		return getSession().createQuery(
				"from TestResult where sampleNo='" + sampleNo
						+ "' and testId in ('" + testString.replace(",", "','")
						+ "')").list();
	}
	
	@SuppressWarnings("unchecked")
	public TestResult getListByTestId(String sampleNo,
			String testId) {
		 List<TestResult>  tList = getSession().createQuery(
				"from TestResult where sampleNo='" + sampleNo
						+ "' and testId  ='" + testId+ "'").list();
		 if(null!=tList&&tList.size()>0){
			 return tList.get(0);
		 }else{
			 return null;
		 }
		 
	}
	
	

	@SuppressWarnings("unchecked")
	public List<TestResult> getSingleHistory(String testid, String patientid) {
		String hql = "select t from Sample s, TestResult t where t.testId in ("
				+ testid + ")  and s.patientblh='" + patientid
				+ "' and s.sampleNo=t.sampleNo order by t.measureTime desc";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getRelative(String patientId, String blh,
			String history) {
		String hql = "select t from Sample s, TestResult t,Process p where (s.patientId='"
				+ patientId
				+ "' or s.patientId='"
				+ blh
				+ "') and t.sampleNo=s.sampleNo and s.id=p.sampleid and t.testId in "
				+ history
				+ " and p.receivetime is not null order by p.receivetime desc,t.testId asc";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getPrintTestBySampleNo(String sampleno) {
		return getSession()
				.createQuery(
						"select t from TestResult t, Index i where t.testId=i.indexId and t.sampleNo='"
								+ sampleno
								+ "' and i.isprint=1 order by i.printord")
				.list();
	}

	public void saveAll(List<TestResult> list) {
		Session s = getSessionFactory().openSession();
		for (TestResult tr : list) {
			s.merge(tr);
		}
		s.flush();
		s.close();
	}
	
	public void deleteAll(List<TestResult> list){
		Session s = getSessionFactory().openSession();
		for (TestResult tr : list) {
			s.delete(tr);
		}
		s.flush();
		s.close();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getHisTestResult(String samplenos) {
		return getSession().createQuery(
				"select t from TestResult as t, Index as i where t.testId=i.indexId and t.sampleNo in (" + samplenos
						+ ") order by i.printord")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getSampleByCode(String code) {
		return getSession().createQuery(
				"from TestResult where sampleNo like '" + code + "%'").list();
	}

	@SuppressWarnings("unchecked")
	public List<TestResult> getNoInfoSampleNo(String text, String code) {
		String sql = "select t from TestResult t, Index i where t.testId=i.indexId";
		if(!code.isEmpty()) {
			sql +=" and (";
			for(int i = 0; i < code.split(",").length; i++) {
				if(i == 0) {
					sql += "t.sampleNo like '" + text + code.split(",")[0] + "%'";
				} else {
					sql += "or t.sampleNo like '" + text + code.split(",")[0] + "%'";
				}
			}
			sql +=")";
		}
		sql += " and t.testStatus=-1 order by t.sampleNo, i.printord";
		return getSession().createQuery(sql).list();
	}

	public void updateSampleNo(String oldSampleNo, String samplenNo) {
		getSession().createSQLQuery("update l_testresult set sampleno='"+ samplenNo + "' where sampleno='" + oldSampleNo + "'").executeUpdate();
	}

}
