package com.smart.dao.hibernate.lis;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.ctc.wstx.util.StringUtil;
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

	@SuppressWarnings("unchecked")
	public List<ReceivePoint> getByName(String name){
		String hql= "from ReceivePoint where (name like '%"+name+"%' ";
		if(StringUtils.isNumeric(name))
			hql += "or id='"+name+"'";
		hql += ")";
		return getSession().createQuery(hql).list();
	}
}
