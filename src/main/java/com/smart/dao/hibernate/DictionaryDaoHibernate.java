package com.smart.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.Dictionary;
import com.smart.dao.DictionaryDao;

@Repository("dictionaryDao")
public class DictionaryDaoHibernate extends GenericDaoHibernate<Dictionary, Long>  implements DictionaryDao{
	
	public DictionaryDaoHibernate(){
		super(Dictionary.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getByType(int type){
		String hql="from dictionary where type =:type";
		Query q = getSession().createQuery(hql).setLong("bagId", type);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getPatientInfo(String name) {
		String hql = "from Dictionary where type=2 and value like '%" + name + "%'";
		Query q = getSession().createQuery(hql);
		return q.list(); 
	}

}
