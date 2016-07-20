package com.smart.dao.hibernate.lis;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.CriticalRecordDao;
import com.smart.model.lis.CriticalRecord;

@Repository("criticalRecordDao")
public class CriticalRecordDaoHibernate extends GenericDaoHibernate<CriticalRecord, Long> implements CriticalRecordDao {
	
	public CriticalRecordDaoHibernate(){
		super(CriticalRecord.class);
	}

	public void saveAll(List<CriticalRecord> updateCriticalRecord) {
		Session s = getSessionFactory().openSession();
		for(CriticalRecord cr : updateCriticalRecord) {
			s.saveOrUpdate(cr);
		}
		s.flush();
		s.close();
	}

	@SuppressWarnings("unchecked")
	public CriticalRecord getBySampleId(Long sampleid) {
		List<CriticalRecord> list = getSession().createQuery("from CriticalRecord where sampleid=" + sampleid).list();
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CriticalRecord> getBySampleIds(String ids) {
		return getSession().createQuery("from CriticalRecord where sampleid in (" + ids + ")").list();
	}

}