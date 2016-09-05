package com.smart.dao.hibernate.execute;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.execute.SampleNoBuilderDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.execute.SampleNoBuilder;

@Repository("sampleNoBuilderDao")
public class SampleNoBuilderDaoHibernate extends GenericDaoHibernate<SampleNoBuilder, Long> implements SampleNoBuilderDao{
	public SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	
	public SampleNoBuilderDaoHibernate(){
		super(SampleNoBuilder.class);
	}
	
	public SampleNoBuilder getByLab(String lab){
		String sql = "from SampleNoBuilder where labDepart = '"+lab+"' order by orderNo asc";
		return (SampleNoBuilder)getSession().createQuery(sql).list().get(0);
	}
	
	public SampleNoBuilder updateSampleNo(String lab,int type){
		String sampleno = "sampleno"+type;
		String sql = "update SampleNoBuilder set "+sampleno+" = "+sampleno+"+1  where KSDM='"+lab+"'";
		getSession().createQuery(sql).executeUpdate();
		return null;
	}

	public List<SampleNoBuilder> getAllByOrder() {
		return getSession().createQuery("from SampleNoBuilder order by orderNo asc").list();
	}

	public void clearNo() {
		getSession().createSQLQuery("update lab_auto_sampleno set nowno = startno").executeUpdate();
	}
}
