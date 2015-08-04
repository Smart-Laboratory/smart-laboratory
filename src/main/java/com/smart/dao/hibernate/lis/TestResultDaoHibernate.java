package com.smart.dao.hibernate.lis;

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
		if(formula.contains("decode(1,2,0.742,1)")) {
			formula = formula.replace("decode(1,2,0.742,1)", "1");
		}
		if(formula.contains("decode(2,2,0.742,1)")) {
			formula = formula.replace("decode(2,2,0.742,1)", "0.742");
		}
		String sql = "update l_testresult set testresult=to_char(round(" + formula + ", 2),'fm99990.00') where testid='" + t.getTestId()
				+ "' and sampleno='" + t.getSampleNo() + "'";
		getSession().createQuery(sql).executeUpdate();
	}
	
}
