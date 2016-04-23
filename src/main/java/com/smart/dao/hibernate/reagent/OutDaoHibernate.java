package com.smart.dao.hibernate.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
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
	public List<Out> getLastHMs(String rgId, String measuretime) {
		List<Out> list = getSession().createQuery("from Out where rgId in (" + rgId + ") and outdate<'"+measuretime+"' order by outdate desc").list();
	
		Set<Long> ids = new HashSet<Long>();
		List<Out> out = new ArrayList<Out>();
		for(Out o : list) {
			if(!ids.contains(o.getRgId())) {
				ids.add(o.getRgId());
				out.add(o);
			}
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public List<Out> getByLab(String lab) {
		return getSession().createQuery("from Out where lab='" + lab + "'").list();
	}

	@SuppressWarnings("unchecked")
	public void updateTestnum(String lab, String testid, Long rgid, Date now) {
		Session s = getSession();
		
		String sql = "from Out where rgId=" + rgid + " and outdate<'" + Constants.SDF.format(now) + "' and lab='" + lab + "' order by outdate desc";
		List<Out> list = s.createQuery(sql).list();
		if (list != null && list.size() > 0) {
			Out o = list.get(0);
			String sql2 = "select count(*) from l_testresult t, l_sample p where p.sampleno=t.sampleno and t.testid in (" + testid + ") and t.measuretime>'" + Constants.SDF.format(o.getOutdate()) +"' and t.measuretime<'" + Constants.SDF.format(now) +"'";
			int count = (Integer)s.createSQLQuery(sql2).uniqueResult();
			o.setTestnum(count);
			s.save(o);
			s.flush();
		}
	}
}
