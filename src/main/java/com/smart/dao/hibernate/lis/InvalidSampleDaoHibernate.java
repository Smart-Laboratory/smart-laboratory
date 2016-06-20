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
	public InvalidSample getByEzh(Long id){
		if (id==null) {
			return null;
		}
		List<InvalidSample> samples = getSession().createQuery("from InvalidSample where sampleId='"+id+"' ").list();
		if(samples==null || samples.size()==0)
			return null;
		return samples.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public InvalidSample getByPatientId(String patientId){
		String hql = "from InvalidSample where patientid = '"+patientId+"'";
		List<InvalidSample> list = getSession().createQuery(hql).list();
		if(list==null || list.size()==0)
			return null;
		return list.get(0);
	}
}
