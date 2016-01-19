package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.pb.Arrange;
import com.smart.dao.ArrangeDao;

@Repository("arrangeDao")
public class ArrangeDaoHibernate extends GenericDaoHibernate<Arrange, Long> implements ArrangeDao {

	public ArrangeDaoHibernate() {
		super(Arrange.class);
	}

	public void saveAll(List<Arrange> list) {
		for(Arrange arrange : list)
			getSession().saveOrUpdate(arrange); 
	}

	@SuppressWarnings("unchecked")
	public List<Arrange> getArrangerd(String names, String month) {
		return getSession().createQuery("from Arrange where worker in ("+ names +") and date like '"+ month +"%' and type=0 order by worker asc, date asc").list();
	}

	@SuppressWarnings("unchecked")
	public List<Arrange> getPersonalArrange(String name, String day) {
		return getSession().createQuery("from Arrange where worker='"+ name +"' and date>='"+ day +"' order by worker asc, date asc").list();
	}

	@SuppressWarnings("unchecked")
	public List<Arrange> getSxjxArrangerd(String yearAndMonth) {
		return getSession().createQuery("from Arrange where date like'"+ yearAndMonth +"%' and type=1 order by worker asc, date asc").list();
	}

	@SuppressWarnings("unchecked")
	public List<Arrange> getHistorySxjx(String names, String tomonth) {
		return getSession().createQuery("from Arrange where worker in ("+ names +") and date<'"+ tomonth +"' and type=1 order by worker asc, date asc").list();
	}

	@SuppressWarnings("unchecked")
	public void removeAll(String name, String date) {
		List<Arrange> list = getSession().createQuery("from Arrange where worker='"+ name +"' and date like '"+ date +"%'").list();
		getSession().delete(list);
	}
}
