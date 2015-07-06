package com.smart.dao.hibernate.reagent;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.ComboDao;
import com.smart.model.reagent.Combo;

@Repository("comboDao")
public class ComboDaoHibernate extends GenericDaoHibernate<Combo, Long> implements ComboDao {

	public ComboDaoHibernate() {
		super(Combo.class);
	}

}
