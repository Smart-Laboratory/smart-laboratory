package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Section;
import com.smart.service.GenericManager;

public interface SectionManager extends GenericManager<Section, Long> {
	

	List<Section> search(String searchTerm);
}
