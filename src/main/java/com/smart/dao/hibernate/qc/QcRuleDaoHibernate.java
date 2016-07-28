package com.smart.dao.hibernate.qc;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcRuleDao;
import com.smart.model.qc.QcRule;

@Repository("qcRuleDao")
public class QcRuleDaoHibernate extends GenericDaoHibernate<QcRule, Long> implements QcRuleDao {

	public QcRuleDaoHibernate() {
		super(QcRule.class);
	}

}
