package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.CriticalRecordDao;
import com.smart.model.lis.CriticalRecord;

@Repository("criticalRecordDao")
public class CriticalRecordDaoHibernate extends GenericDaoHibernate<CriticalRecord, Long> implements CriticalRecordDao {
	
	public CriticalRecordDaoHibernate(){
		super(CriticalRecord.class);
	}

}