package com.smart.dao.hibernate.reagent;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.ReagentDao;
import com.smart.model.reagent.Reagent;

@Repository("reagentDao")
public class ReagentDaoHibernate extends GenericDaoHibernate<Reagent, Long> implements ReagentDao {

	public ReagentDaoHibernate() {
		super(Reagent.class);
	}

}
