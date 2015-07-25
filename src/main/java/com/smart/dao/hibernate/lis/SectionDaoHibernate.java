package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.model.lis.Section;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SectionDao;

@Repository("sectionDao")
public class SectionDaoHibernate extends GenericDaoHibernate<Section, Long> implements SectionDao {
	
	public SectionDaoHibernate(){
		super(Section.class);
	}

}
