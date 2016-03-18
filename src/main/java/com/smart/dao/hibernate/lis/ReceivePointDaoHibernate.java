package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ReceivePointDao;
import com.smart.model.lis.ReceivePoint;

@Repository("receivePointDao")
public class ReceivePointDaoHibernate extends GenericDaoHibernate<ReceivePoint, Long> implements ReceivePointDao {

	public ReceivePointDaoHibernate() {
		super(ReceivePoint.class);
	}

	@SuppressWarnings("unchecked")
	public List<ReceivePoint> getByType(int type) {
		return getSession().createQuery("from ReceivePoint where type=" + type).list();
	}

}
