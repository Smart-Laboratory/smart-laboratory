package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.YlxhDao;
import com.smart.model.lis.Ylxh;

@Repository("ylxhDao")
public class YlxhDaoHibernate extends GenericDaoHibernate<Ylxh, Long> implements YlxhDao {

	public YlxhDaoHibernate() {
		super(Ylxh.class);
	}
	
	

}
