package com.smart.dao.hibernate.request;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.request.SFXMDao;
import com.smart.model.request.SFXM;

@Repository("sfxmDao")
public class SFXMDaoHibernate extends GenericDaoHibernate<SFXM, Long> implements SFXMDao {

	public SFXMDaoHibernate() {
		super(SFXM.class);
	}

}
