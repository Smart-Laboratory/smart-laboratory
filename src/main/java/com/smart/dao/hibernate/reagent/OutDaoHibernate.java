package com.smart.dao.hibernate.reagent;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

	@SuppressWarnings("unchecked")
	public List<Out> getLastHMs(Long rgId, Date measuretime) {
		return getSession().createQuery("select o from Out o where o.rgId=" + rgId + " and o.outdate<'"+measuretime+"' order by o.outdate desc").list();
	}

	@SuppressWarnings("unchecked")
	public List<Out> getByLab(String lab) {
		return getSession().createCriteria(Out.class).add(Restrictions.eq("lab", lab)).list();
	}
}
