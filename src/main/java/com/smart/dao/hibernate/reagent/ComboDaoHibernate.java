package com.smart.dao.hibernate.reagent;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.ComboDao;
import com.smart.model.reagent.Combo;

@Repository("comboDao")
public class ComboDaoHibernate extends GenericDaoHibernate<Combo, Long> implements ComboDao {

	public ComboDaoHibernate() {
		super(Combo.class);
	}

	@SuppressWarnings("unchecked")
	public List<Combo> getCombos(String name) {
		return getSession().createQuery("from Combo c where name like '" + name + "%' order by upper(c.id)").list();
	}

	@SuppressWarnings("unchecked")
	public List<Combo> getByLab(String lab) {
		return getSession().createQuery("from Combo where lab='" + lab + "' or lab='21' order by upper(id)").list();
	}

}
