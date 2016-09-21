package com.smart.dao.hibernate.execute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import org.hibernate.Query;
import org.hibernate.Session;
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
	public List<LabOrder> getByPatientId(String patientId, String fromDate, String toDate){
		String hql = "from LabOrder where patientid = '"+patientId+"'";
		if (!fromDate.equals("")) {
			fromDate += " 00:00:00";
			hql += "and requesttime>=to_date('" + fromDate + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if (!toDate.equals("")) {
			toDate += " 23:59:59";
			hql += "and requesttime<=to_date('" + toDate + "','yyyy-MM-dd hh24:mi:ss')";
		}
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<LabOrder> getByRequestIds(String requestIds) {
		String hql = "from LabOrder where laborderorg in ("+ requestIds+")";
		System.out.println(hql);
		return getSession().createQuery(hql).list();
	}

	/**
	 * 获取住院病人所有采集记录
	 * @param ward			病区
	 * @param bedNo			床位号
	 * @param patientId		病人ID
	 * @return
	 */
	public List<Object[]> getByRequestIds(String ward,String bedNo,String patientId,List requestIds){
		String sql ="select l,s,p from LabOrder l, Sample s,Process p where l.barcode = s.barcode and s.id = p.sampleid";

		if(ward !=null && !ward.isEmpty()){
			sql += " and l.wardId=:ward";
		}
		if(bedNo !=null && !bedNo.isEmpty()){
			sql += " and l.bed=:bedNo";
		}
		if(patientId !=null && !patientId.isEmpty()){
			sql += " and  l.patientid=:patientId";
		}
		if(requestIds !=null && requestIds.size()>0){
			sql += " and  l.requestId in (:requestIds)";
		}

		Query query = getSession().createQuery(sql);
		if(ward !=null && !ward.isEmpty()){
			query.setString("ward",ward);
		}
		if(bedNo !=null && !bedNo.isEmpty()){
			query.setString("bedNo",bedNo);
		}
		if(patientId !=null && !patientId.isEmpty()){
			query.setString("patientId",patientId);
		}
		if(requestIds !=null && requestIds.size()>0){
			query.setParameterList("requestIds",requestIds);
		}

		return query.list();
	}



	public List<LabOrder> saveAll(List<LabOrder> list) {
		Session s = getSessionFactory().openSession();
		List<LabOrder> returnList = new ArrayList<LabOrder>();
		for(LabOrder labOrder : list) {
			returnList.add((LabOrder) s.merge(labOrder));
		}
		s.flush();
		s.close();
        return returnList;
    }

	public void removeAll(List<LabOrder> list) {
		Session s = getSessionFactory().openSession();
		for(LabOrder labOrder : list) {
			s.delete(labOrder);
		}
		s.flush();
		s.close();
	}


}
