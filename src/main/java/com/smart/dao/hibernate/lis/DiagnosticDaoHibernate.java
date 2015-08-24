package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.DiagnosticDao;
import com.smart.model.lis.Diagnostic;

@Repository("diagnosticDao")
public class DiagnosticDaoHibernate extends GenericDaoHibernate<Diagnostic, Long> implements DiagnosticDao {

	public DiagnosticDaoHibernate() {
		super(Diagnostic.class);
	}

}
