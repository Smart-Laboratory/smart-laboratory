package com.smart.dao.hibernate;

import java.util.List;

import javax.persistence.Table;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.smart.model.pb.Arrange;
import com.smart.model.user.User;
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
	public List<Arrange> getArrangerd(String names, String month, int state) {
		return getSession().createQuery("from Arrange where worker in ("+ names +") and date like '"+ month +"%' and type=0 and state>="+state+"  order by worker asc, date asc").list();
	}

	@SuppressWarnings("unchecked")
	public Arrange getByUser(String name, String day){
		
		long start = System.currentTimeMillis();
		List<Arrange> list = getSession().createQuery("from Arrange where worker = '"+ name +"' and date = '"+ day +"' and type=0 order by worker asc, date asc").list();
		System.out.println((System.currentTimeMillis()-start)/1000);
		if(list !=null && list.size()>0)
			return (Arrange)list.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Arrange> getPersonalArrange(String name, String day) {
		return getSession().createQuery("from Arrange where worker='"+ name +"' and date like'"+ day +"%' order by date asc").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Arrange> getMonthArrangeByName(String name, String month){
		return getSession().createQuery("from Arrange where worker='"+ name +"' and date like'"+ month +"%' order by date asc").list();
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
	
	public List<String> getGXcount(String month){
		JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		
		List<String> gxList = jdbcTemplate.queryForList("select distinct(riqi) from workarrange where riqi like '"+month+"%' and shift like '%公休%'", String.class);
        
        return gxList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Arrange> getArrangeByType(String type, String month){
		String hql = "from Arrange where date like '"+month+"%' and shift like '"+type+"%' and state>=5";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Arrange> getPublish(String section,String month,int state){
		String hql = "from Arrange where date like '"+month+"%' and section like '%"+section+"%' and state = "+state;
		return getSession().createQuery(hql).list();
	}
}
