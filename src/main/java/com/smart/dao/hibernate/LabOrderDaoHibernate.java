package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.execute.LabOrderDao;
import com.smart.model.execute.LabOrder;

@Repository("labOrderDao")
public class LabOrderDaoHibernate extends GenericDaoHibernate<LabOrder, Long> implements LabOrderDao {

	public LabOrderDaoHibernate(){
		super(LabOrder.class);
	}
	
	public boolean existSampleId(String sampleid){
		String hql = "select count(*) from LabOrder where laborder = '"+sampleid+"'";
		
		int count = ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
		if(count>0)
			return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabOrder> getByIds(String ids){
		String hqlString = "from LabOrder where laborder in ("+ids+")";
		System.out.println(hqlString);
		return getSession().createQuery(hqlString).list();
	}
}
