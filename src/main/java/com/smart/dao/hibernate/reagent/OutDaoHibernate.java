package com.smart.dao.hibernate.reagent;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.OutDao;
import com.smart.model.reagent.Out;

@Repository("outDao")
public class OutDaoHibernate extends GenericDaoHibernate<Out, Long> implements OutDao {

	public OutDaoHibernate() {
		super(Out.class);
	}

	public void saveAll(List<Out> needSaveOut) {
		Session s = getSession();
		for(Out o : needSaveOut) {
			s.saveOrUpdate(o);
		}
		s.flush();	
	}

}
