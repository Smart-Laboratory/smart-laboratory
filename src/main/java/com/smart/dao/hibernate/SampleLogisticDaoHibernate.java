package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.lis.SampleLogisticDao;
import com.smart.model.lis.SampleLogistic;

@Repository("sampleLogisticDao")
public class SampleLogisticDaoHibernate extends GenericDaoHibernate<SampleLogistic, Long> implements SampleLogisticDao {

	public SampleLogisticDaoHibernate(){
		super(SampleLogistic.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<SampleLogistic> getByDoctadviseNo(Long doctadviseno){
		String hql="from SampleLogistic where doctadviseno="+doctadviseno+" order by operatetime asc";
		List<SampleLogistic> list = getSession().createQuery(hql).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<SampleLogistic> getByReceivePoint(String from, String to, String point){
		String hql = "from SampleLogistic where location='"+point+"' and operatetime between to_date('" + from + "','"
                + Constants.DATEFORMAT + "') and to_date('" + to + "','" + Constants.DATEFORMAT
                + "') order by operatetime asc";
		return getSession().createQuery(hql).list();
	}
}
