package com.smart.dao.hibernate.qc;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcOutControlDao;
import com.smart.model.qc.QcOutControl;

@Repository("qcOutControlDao")
public class QcOutControlDaoHibernate extends GenericDaoHibernate<QcOutControl, Long> implements QcOutControlDao {

	public QcOutControlDaoHibernate() {
		super(QcOutControl.class);
	}

}
