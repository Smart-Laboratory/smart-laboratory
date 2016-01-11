package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.user.Evaluate;
import com.smart.dao.EvaluateDao;

@Repository("evaluateDao")
public class EvaluateDaoHibernate extends GenericDaoHibernate<Evaluate, Long> implements EvaluateDao {

	public EvaluateDaoHibernate() {
		super(Evaluate.class);
	}

	@SuppressWarnings("unchecked")
	public List<Evaluate> getByBA(String sampleno, String collector) {
		String hql = "from Evaluate where sampleno='" + sampleno
				+"' and collector='" + collector + "' order by evaluatetime desc";
		return getSession().createQuery(hql).list();
	}

}
