package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Section;
import com.smart.service.GenericManager;

public interface SectionManager extends GenericManager<Section, Long> {
	List<Section> search(String searchTerm);

	Section getByCode(String sectionId);

	int getSectionCount(String query,String hospitalId);

	List<Section> getSectionList(String query,String hospitalId,int start,int end,String sidx,String sord);
	List<Section> getSectionList(String name);
	boolean batchRemove(long[] ids);
	
	List<Section> getPbSection(String hospitalId);
}
