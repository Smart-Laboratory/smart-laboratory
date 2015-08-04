package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.InvalidSampleDao;
import com.smart.model.lis.InvalidSample;

@Repository("invalidSampleDao")
public class InvalidSampleDaoHibernate extends GenericDaoHibernate<InvalidSample, Long> implements InvalidSampleDao{

	public InvalidSampleDaoHibernate(){
		super(InvalidSample.class);
	}
	@SuppressWarnings("unchecked")
	public List<InvalidSample> getByEzh(Long id){
		if (id==null) {
			return null;
		}
		
		return getSession().createQuery("from InvalidSample where (sample.id='" + id + "') or (sampleId='"+id+"') ").list();
	}
}
