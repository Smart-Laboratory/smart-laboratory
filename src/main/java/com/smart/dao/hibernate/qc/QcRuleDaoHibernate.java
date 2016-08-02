package com.smart.dao.hibernate.qc;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcRuleDao;
import com.smart.model.qc.QcRule;

@Repository("qcRuleDao")
public class QcRuleDaoHibernate extends GenericDaoHibernate<QcRule, Long> implements QcRuleDao {

	public QcRuleDaoHibernate() {
		super(QcRule.class);
	}

	@SuppressWarnings("unchecked")
	public List<QcRule> getRuleList(String query, int start, int end, String sidx, String sord) {
		String sql = "from QcRule q where 1=1 ";
		if(query != null && !query.equals(""))
			sql += " and q.name like '%" +query +"%'";
		sidx = sidx.equals("")?"name":sidx;
		sql +=" order by  " +sidx + " " + sord;
		Query q =  getSession().createQuery(sql);
		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}

}
