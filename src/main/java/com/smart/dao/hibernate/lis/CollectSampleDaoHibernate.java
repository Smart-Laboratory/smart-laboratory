package com.smart.dao.hibernate.lis;

import java.util.List;


import org.hibernate.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.CollectSampleDao;
import com.smart.model.lis.CollectSample;

@Repository("collectSampleDao")
public class CollectSampleDaoHibernate extends GenericDaoHibernate<CollectSample, Long> implements CollectSampleDao {

	public CollectSampleDaoHibernate() {
		super(CollectSample.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CollectSample> getCollectSample(String username) {
		Query q = getSession().createQuery("from CollectSample c where username  = '" + username +"' order by sampleno desc");
		
		return q.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean isSampleCollected(String username, String sampleno) {
		Query q = getSession().createQuery("from CollectSample where username='" + username +"' and sampleno='" + sampleno +"'");
		
		List<CollectSample> list = q.list();
		if(list.size()>0) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<CollectSample> getAllCollectSample() {
		Query q = getSession().createQuery("from CollectSample order by sampleno desc");
		
		return q.list();
		
	}
	
	public void removeCollectSample(String collector, String sampleno) {
		String hql = "delete from CollectSample where username='" + collector +"' and sampleno='" + sampleno +"'";
		Query q = getSession().createQuery(hql);
		q.executeUpdate();
	}	
	
	@SuppressWarnings("unchecked")
	public List<CollectSample> getCollectSampleByName(String name) {
	    Query q = getSession().createQuery("from CollectSample where bamc like '%"+name+"%' order by sampleno desc");
	    return q.list();
	}
	
	public List<String> getAllCollectType() {
		JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        String sql = "select distinct type from collect";
        return jdbcTemplate.queryForList(sql, String.class);
	}

	@SuppressWarnings("unchecked")
	public List<CollectSample> getCollectSampleByType(String type) {
		Query q = getSession().createQuery("from CollectSample where type in ("+type+") order by sampleno desc");
		return q.list();
	}
}
 