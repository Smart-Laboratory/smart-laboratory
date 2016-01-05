package com.smart.dao.hibernate.reagent;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.InDao;
import com.smart.model.reagent.In;

@Repository("inDao")
public class InDaoHibernate extends GenericDaoHibernate<In, Long> implements InDao {

	public InDaoHibernate() {
		super(In.class);
	}

	public void saveAll(List<In> needSaveIn) {
		Session s = getSession();
		for(In i : needSaveIn) {
			s.saveOrUpdate(i);
		}
		s.flush();		
	}

	@SuppressWarnings("unchecked")
	public List<In> getByInDate(Date indate) {
		return getSession().createCriteria(In.class).add(Restrictions.eq("indate", indate)).list();
	}

	@SuppressWarnings("unchecked")
	public List<In> getByLab(String lab) {
		return getSession().createCriteria(In.class).add(Restrictions.eq("lab", lab)).list();
	}

}
