package com.smart.dao.hibernate.execute;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.execute.LabOrderDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.execute.LabOrder;

@Repository("labOrderDao")
public class LabOrderDaoHibernate extends GenericDaoHibernate<LabOrder, Long> implements LabOrderDao {

	public LabOrderDaoHibernate(){
		super(LabOrder.class);
	}
	
	public boolean existSampleId(String sampleid){
		String hql = "select count(*) from LabOrder where laborder = '"+sampleid+"'";
		
		int count = ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
        return count > 0;
    }
	
	@SuppressWarnings("unchecked")
	public List<LabOrder> getByIds(String ids){
		String hqlString = "from LabOrder where laborder in ("+ids+")";
		System.out.println(hqlString);
		return getSession().createQuery(hqlString).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LabOrder> getByPatientId(String patientId){
		String hql = "from LabOrder where patientid = '"+patientId+"'";
		return getSession().createQuery(hql).list();
	}
}
