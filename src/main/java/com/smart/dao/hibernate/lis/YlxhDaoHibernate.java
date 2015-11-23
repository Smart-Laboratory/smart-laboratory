package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.YlxhDao;
import com.smart.model.lis.Ylxh;

@Repository("ylxhDao")
public class YlxhDaoHibernate extends GenericDaoHibernate<Ylxh, Long> implements YlxhDao {

	public YlxhDaoHibernate() {
		super(Ylxh.class);
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> getYlxh() {
		return getSession().createQuery("from Ylxh order by profiletest").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Ylxh> getTest(String lab){
		return getSession().createQuery("from Ylxh where ksdm='"+lab+"' order by profiletest").list();
	}

	@SuppressWarnings("unchecked")
	public List<Ylxh> getSearchData(String text){
		return getSession().createQuery("from Ylxh where ylmc like '%"+text+"%'").list();
	}
}
