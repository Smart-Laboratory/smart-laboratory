package com.smart.dao.hibernate.reagent;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
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
		Session s = getSessionFactory().openSession();
		for(In i : needSaveIn) {
			s.saveOrUpdate(i);
		}
		s.flush();
		s.close();
	}

	@SuppressWarnings("unchecked")
	public List<In> getByInDate(String indate) {
		return getSession().createQuery("from In where indate='" + indate + "'").list();
	}

	@SuppressWarnings("unchecked")
	public List<In> getByLab(String lab) {
		return getSession().createQuery("from In where lab='" + lab + "' or lab='21' order by indate desc").list();
	}


}
