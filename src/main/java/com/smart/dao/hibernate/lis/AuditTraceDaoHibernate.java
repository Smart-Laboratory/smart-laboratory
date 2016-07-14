package com.smart.dao.hibernate.lis;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.dao.lis.AuditTraceDao;
import com.smart.model.lis.AuditTrace;
import com.smart.dao.hibernate.GenericDaoHibernate;

@Repository("auditTraceDao")
public class AuditTraceDaoHibernate extends GenericDaoHibernate<AuditTrace, Long> implements AuditTraceDao {

	public AuditTraceDaoHibernate() {
		super(AuditTrace.class);
	}

	@SuppressWarnings("unchecked")
	public List<AuditTrace> getBySampleNo(String sampleNo) {
		Query q = getSession().createQuery("from AuditTrace where sampleno='" + sampleNo + "' order by checktime desc");
		return q.list();
	}

	public void saveAll(List<AuditTrace> updateAuditTrace) {
		Session s = getSessionFactory().openSession();
		for(AuditTrace at : updateAuditTrace) {
			s.saveOrUpdate(at);
		}
		s.flush();
		s.close();
	}


}
