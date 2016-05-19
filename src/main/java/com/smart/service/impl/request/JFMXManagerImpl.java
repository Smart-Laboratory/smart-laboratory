package com.smart.service.impl.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.request.JFMXDao;
import com.smart.model.request.JFMX;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.request.JFMXManager;

@Service("jfmxManager")
public class JFMXManagerImpl extends GenericManagerImpl<JFMX, Long> implements JFMXManager {

	@SuppressWarnings("unused")
	private JFMXDao jfmxDao;

	@Autowired
	public void setJfmxDao(JFMXDao jfmxDao) {
		this.dao = jfmxDao;
		this.jfmxDao = jfmxDao;
	}
	
	
}
