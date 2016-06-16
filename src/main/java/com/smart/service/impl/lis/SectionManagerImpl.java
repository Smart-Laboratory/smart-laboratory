package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SectionDao;
import com.smart.model.lis.Section;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SectionManager;

@Service("sectionManager")
public class SectionManagerImpl extends GenericManagerImpl<Section, Long> implements SectionManager {

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

	@Override
	public int getSectionCount(String query, String hospitalId) {
		return sectionDao.getSectionCount(query,hospitalId);
	}

	@Override
	public List<Section> getSectionList(String query, String hospitalId, int start, int end,String sidx,String sord) {
		return sectionDao.getSectionList(query,hospitalId,start,end,sidx,sord);
	}

	@Override
	public List<Section> getSectionList(String name) {
		return sectionDao.getSectionList(name);
	}

	@Override
	public void batchRemove(long[] ids) {
		sectionDao.batchRemove(ids);
	}


}
