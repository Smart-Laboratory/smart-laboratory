package com.smart.dao.hibernate.request;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.request.SFXMTCDao;
import com.smart.model.request.SFXMTC;

@Repository("sfxmtcDao")
public class SFXMTCDaoHibernate extends GenericDaoHibernate<SFXMTC, Long> implements SFXMTCDao {

	public SFXMTCDaoHibernate() {
		super(SFXMTC.class);
	}

}
