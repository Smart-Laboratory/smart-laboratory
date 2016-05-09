package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.PassTraceDao;
import com.smart.model.lis.PassTrace;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.PassTraceManager;

@Service("passTraceManager")
public class PassTraceManagerImpl extends GenericManagerImpl<PassTrace, Long> implements PassTraceManager{

	private PassTraceDao passTraceDao;
	
	@Autowired
	public void setPassTraceDao(PassTraceDao passTraceDao){
		this.dao = passTraceDao;
		this.passTraceDao = passTraceDao;
	}
}
