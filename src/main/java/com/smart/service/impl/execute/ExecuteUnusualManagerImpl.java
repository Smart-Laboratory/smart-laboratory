package com.smart.service.impl.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.execute.ExecuteUnusualDao;
import com.smart.model.execute.ExecuteUnusual;
import com.smart.service.execute.ExecuteUnusualManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("executeUnusualManager")
public class ExecuteUnusualManagerImpl extends GenericManagerImpl<ExecuteUnusual, Long> implements ExecuteUnusualManager {

	private ExecuteUnusualDao executeUnusualDao;
	
	@Autowired
	public void setExecuteUnusualDao(ExecuteUnusualDao executeUnusualDao){
		this.dao = executeUnusualDao;
		this.executeUnusualDao = executeUnusualDao;
	}
}
