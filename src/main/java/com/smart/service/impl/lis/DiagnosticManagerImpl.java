package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.DiagnosticDao;
import com.smart.model.lis.Diagnostic;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.DiagnosticManager;

@Service("diagnosticManager")
public class DiagnosticManagerImpl extends GenericManagerImpl<Diagnostic, Long> implements DiagnosticManager {

	@SuppressWarnings("unused")
	private DiagnosticDao diagnosticDao;

	@Autowired
	public void setDiagnosticDao(DiagnosticDao diagnosticDao) {
		this.dao = diagnosticDao;
		this.diagnosticDao = diagnosticDao;
	}
	
	
}
