package com.smart.dao.hibernate.request;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.request.JFMXDao;
import com.smart.model.request.JFMX;

@Repository("jfmxDao")
public class JFMXDaoHibernate extends GenericDaoHibernate<JFMX, Long> implements JFMXDao {

	public JFMXDaoHibernate() {
		super(JFMX.class);
	}

}
