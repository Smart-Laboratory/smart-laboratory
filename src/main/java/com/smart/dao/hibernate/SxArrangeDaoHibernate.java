package com.smart.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.Order;

import org.springframework.stereotype.Repository;

import com.smart.dao.SxArrangeDao;
import com.smart.model.pb.SxArrange;

@Repository("sxArrangeDao")
public class SxArrangeDaoHibernate extends GenericDaoHibernate<SxArrange, Long> implements SxArrangeDao {
	
	public SxArrangeDaoHibernate(){
		super(SxArrange.class);
	}
	
	public void saveAll(List<SxArrange> list){
		for(SxArrange s: list)
			getSession().saveOrUpdate(s);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SxArrange> getByMonth(String month){
		String hql = "from SxArrange where month like '%"+month+"%'";
		List<SxArrange> s = getSession().createQuery(hql).list();
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public List<SxArrange> getByName(String name){
		String hql = "from SxArrange where worker = '"+name+"' and section is not null order by SUBSTR(month,1,4)";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SxArrange> getByWeek(int year, int startWeek, int maxWeek){
		String hql = "from SxArrange where week >="+startWeek+" and week<="+(startWeek+maxWeek-1)+" and month like '%"+year+"%'";
		return getSession().createQuery(hql).list();
	}
}
