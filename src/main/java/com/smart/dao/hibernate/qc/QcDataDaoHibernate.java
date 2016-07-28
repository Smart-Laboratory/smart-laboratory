package com.smart.dao.hibernate.qc;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcDataDao;
import com.smart.model.qc.QcData;

@Repository("qcDataDao")
public class QcDataDaoHibernate extends GenericDaoHibernate<QcData, Long> implements QcDataDao {

	public QcDataDaoHibernate() {
		super(QcData.class);
	}

}
