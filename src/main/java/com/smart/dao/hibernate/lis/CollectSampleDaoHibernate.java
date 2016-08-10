package com.smart.dao.hibernate.lis;

import java.util.List;


import org.hibernate.Query;
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
        return list.size() > 0;
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
	
	@SuppressWarnings("unchecked")
	public List<String> getAllCollectType() {
        String sql = "select distinct type from l_collectsample";
        return getSession().createSQLQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	public List<CollectSample> getCollectSampleByType(String type) {
		Query q = getSession().createQuery("from CollectSample where type in ("+type+") order by sampleno desc");
		return q.list();
	}
}
 