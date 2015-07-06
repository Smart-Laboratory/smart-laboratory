package com.smart.dao.hibernate.reagent;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.InDao;
import com.smart.model.reagent.In;

@Repository("inDao")
public class InDaoHibernate extends GenericDaoHibernate<In, Long> implements InDao {

	public InDaoHibernate() {
		super(In.class);
	}

}
