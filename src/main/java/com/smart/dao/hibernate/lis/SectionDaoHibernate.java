package com.smart.dao.hibernate.lis;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.model.lis.Section;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SectionDao;

import java.util.List;

@Repository("sectionDao")
public class SectionDaoHibernate extends GenericDaoHibernate<Section, Long> implements SectionDao {
	
	public SectionDaoHibernate(){
		super(Section.class);
	}

	public Section getByCode(String sectionId) {
		return (Section) getSession().createQuery("from Section where code=" + sectionId).uniqueResult();
	}

	@Override
	public int getSectionCount(String code, String name, String hospitalId) {
		String sql = "select count(1) cnt from l_depart where 1=1";
		if(code != null && !code.equals(""))
			sql += " and  code like  '%" +code +"%'";
		if(name != null && name.equals(""))
			sql += " and name like  '%" +name +"%'";
		if(hospitalId != null && hospitalId.equals(""))
			sql += " and hospital_id = " +hospitalId;
		System.out.println("sql1==>" +sql);
		Query q =  getSession().createSQLQuery(sql);
		System.out.println("===1"+q.uniqueResult() + "");
		return new Integer(q.uniqueResult() + "");
	}

	@Override
	public List<Section> getSectionList(String code, String name, String hospitalId, int start, int end) {
		String sql = "from Section s where 1=1 ";
		if(code != null && !code.equals(""))
			sql += " and  s.code like  '%" +code +"%'";
		if(name != null && name.equals(""))
			sql += " and s.name like  '%" +name +"%'";
		if(hospitalId != null && hospitalId.equals(""))
			sql += " and s.hospital_id = " +hospitalId;

		sql +=" order by id asc";
		Query q =  getSession().createQuery(sql);

		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}


}
