package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SampleLogDao;
import com.smart.model.lis.SampleLog;

@Repository("sampleLogDao")
public class SampleLogDaoHibernate extends GenericDaoHibernate<SampleLog, Long> implements SampleLogDao {

	public SampleLogDaoHibernate() {
		super(SampleLog.class);
	}

	@SuppressWarnings("unchecked")
	public List<SampleLog> getBySampleId(Long sid) {
		return getSession().createQuery("from SampleLog where sampleId=" + sid + " order by logtime desc").list();
	}

}
