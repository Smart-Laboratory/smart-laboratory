package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SectionDao;
import com.smart.model.lis.Section;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SectionManager;

@Service("sectionManager")
public class SectionManagerImple extends GenericManagerImpl<Section, Long> implements SectionManager {

	private SectionDao sectionDao;
	
	@Autowired
	public void setSectionDao(SectionDao sectionDao) {
		this.dao = sectionDao;
		this.sectionDao = sectionDao;
	}
	
    public List<Section> search(final String searchTerm) {
        return super.search(searchTerm, Section.class);
    }

	public Section getByCode(String sectionId) {
		return sectionDao.getByCode(sectionId);
	}
}
