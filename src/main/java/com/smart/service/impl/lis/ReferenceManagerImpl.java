package com.smart.service.impl.lis;

import org.springframework.stereotype.Service;

import com.smart.dao.lis.ReferenceDao;
import com.smart.model.lis.Reference;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ReferenceManager;

@Service("referenceManager")
public class ReferenceManagerImpl extends GenericManagerImpl<Reference, Long> implements ReferenceManager {

	private ReferenceDao referenceDao;

	public void setReferenceDao(ReferenceDao referenceDao) {
		this.dao = referenceDao;
		this.referenceDao = referenceDao;
	}
	
}
