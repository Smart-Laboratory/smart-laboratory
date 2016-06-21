package com.smart.dao.hibernate.lis;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SectionCodeDao;
import com.smart.model.lis.SectionCode;

@Repository("sectionCodeDao")
public class SectionCodeDaoHibernate extends GenericDaoHibernate<SectionCode, Long> implements SectionCodeDao {
	
	public SectionCodeDaoHibernate(){
		super(SectionCode.class);
	}

	@SuppressWarnings("unchecked")
	public List<SectionCode> getCode(String codeId, int start, int end) {
		String sql = "from SectionCode where code in (" + codeId + ")";
		Query q =  getSession().createQuery(sql);
		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}

	@SuppressWarnings("unchecked")
	public List<SectionCode> searchCode(String name) {
		String sql = "from SectionCode where code like '%" + name + "%' or describe like '%" + name + "%'";
		return getSession().createQuery(sql).list();
	}
}
