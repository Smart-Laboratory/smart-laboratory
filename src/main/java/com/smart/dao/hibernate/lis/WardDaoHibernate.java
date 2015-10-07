package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.WardDao;
import com.smart.model.lis.Ward;

@Repository("wardDao")
public class WardDaoHibernate extends GenericDaoHibernate<Ward, String> implements WardDao {

	public WardDaoHibernate() {
		super(Ward.class);
	}

	@SuppressWarnings("unchecked")
	public List<Ward> getByWard(String ward) {
		return getSession().createQuery("from Ward where code='"+ ward +"' and type like '%护%'").list();
	}

}
