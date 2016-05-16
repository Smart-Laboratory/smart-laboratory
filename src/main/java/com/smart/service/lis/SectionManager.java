package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Section;
import com.smart.service.GenericManager;

public interface SectionManager extends GenericManager<Section, Long> {
	List<Section> search(String searchTerm);

	Section getByCode(String sectionId);

	int getSectionCount(String code,String name,String hospitalId);

	List<Section> getSectionList(String code,String name,String hospitalId,int start,int end);

}
