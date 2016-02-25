package com.smart.dao.hibernate;

import java.util.List;

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
	

	public List<SxArrange> getByMonth(String month){
		String hql = "from SxArrange where month like '%"+month+"%'";
		List<SxArrange> s = getSession().createQuery(hql).list();
		return s;
	}

}
