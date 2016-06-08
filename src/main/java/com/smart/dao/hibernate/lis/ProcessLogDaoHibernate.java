package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProcessLogDao;
import com.smart.model.lis.ProcessLog;

@Repository("processLogDao")
public class ProcessLogDaoHibernate extends GenericDaoHibernate<ProcessLog, Long> implements ProcessLogDao {

	public ProcessLogDaoHibernate() {
		super(ProcessLog.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProcessLog> getBySampleId(Long sid) {
		return getSession().createQuery("from ProcessLog where sampleid=" + sid + " order by logtime desc").list();
	}

	public ProcessLog getBySampleLogId(Long logid) {
		return (ProcessLog) getSession().createQuery("from ProcessLog where sampleLogId=" + logid).list().get(0);
	}

}
