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
		Session s = getSession();
		for(CriticalRecord cr : updateCriticalRecord) {
			s.saveOrUpdate(cr);
		}
		s.flush();
	}

}