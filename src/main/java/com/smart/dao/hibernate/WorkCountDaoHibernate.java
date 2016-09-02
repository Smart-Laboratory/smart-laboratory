package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.WorkCountDao;
import com.smart.model.pb.WorkCount;

@Repository("workCountDao")
public class WorkCountDaoHibernate extends GenericDaoHibernate<WorkCount, Long> implements WorkCountDao {

	public WorkCountDaoHibernate() {
		super(WorkCount.class);
	}
	@SuppressWarnings("unchecked")
	public List<WorkCount> getBySection(String section) {
		List<WorkCount> workCounts = getSession().createQuery("from WorkCount where section = '%"+section+"'%").list();
		return workCounts;
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkCount> getMonthBySection(String section, String month){
		List<WorkCount> workCounts = getSession().createQuery("from WorkCount where section like '%"+section+"%' and workMonth = '"+month+"'").list();
		return workCounts;
	}
	
	@SuppressWarnings("unchecked")
	public WorkCount getPersonByMonth(String name,String month,String section){
		String hql = "from WorkCount where worker='"+name+"' and workMonth='"+month+"' and section like '%"+section+"%'";
		List<WorkCount> workCounts = getSession().createQuery(hql).list();
		if(workCounts!=null && workCounts.size()>0)
			return workCounts.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public double getYearCount(String year,String name){
		List<WorkCount> workCounts = getSession().createQuery("from WorkCount where workmonth like '"+year+"%' and worker = '"+name+"'").list();
		getSession().clear();
		double count = 0;
		if(workCounts.size()>0){
			for(WorkCount w: workCounts){
				count += w.getHoliday();
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkCount> getByWorker(String worker){
		String hql = "from WorkCount where worker = '"+worker+"' and defeholiday>0";
		System.out.println(hql);
		return getSession().createQuery(hql).list();
	}
}

