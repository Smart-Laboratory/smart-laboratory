package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.model.lis.Section;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SectionDao;

@Repository("sectionDao")
public class SectionDaoHibernate extends GenericDaoHibernate<Section, Long> implements SectionDao{
	
	public SectionDaoHibernate(){
		super(Section.class);
	}
	
	public void edit(Section section){
		if (log.isDebugEnabled()) {
            log.debug("user's id: " + section.getId());
        }
		getSession().saveOrUpdate(section);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
	}

}
