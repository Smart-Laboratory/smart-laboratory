package com.smart.dao.hibernate.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public List<Out> getLastHMs(String testid, Date measuretime) {
		String hql = "select o from Out o where o.reagent.testname like '%" + testid +"%' and o.outdate<'"+measuretime+"' order by o.outdate desc";
		List<Out> list = getSession().createQuery(hql).list();
		Set<String> name = new HashSet<String>();
		List<Out> out = new ArrayList<Out>();
		for(Out hmo : list) {
			if(!name.contains(hmo.getReagent().getName())) {
				name.add(hmo.getReagent().getName());
				out.add(hmo);
			}
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public List<Out> getByLab(String lab) {
		return getSession().createCriteria(Out.class).add(Restrictions.eq("lab", lab)).list();
	}
}
