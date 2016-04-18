package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.DiagnosisDao;
import com.smart.model.lis.Diagnosis;

@Repository("diagnosisDao")
public class DiagnosisDaoHibernate extends GenericDaoHibernate<Diagnosis, Long> implements DiagnosisDao{

	public DiagnosisDaoHibernate(){
		super(Diagnosis.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Diagnosis> getByDid(String dId){
		String hql = "from Diagnosis where descriptionId = "+dId;
		return getSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	public Diagnosis getByDiagnosisName(String dName){
		String hql = "from Diagnosis where diagnosisName = '"+dName+"'";
		List<Diagnosis> diagnosis = getSession().createQuery(hql).list();
		if(diagnosis == null || diagnosis.size()<=0)
			return null;
		return diagnosis.get(0);
	}
}
