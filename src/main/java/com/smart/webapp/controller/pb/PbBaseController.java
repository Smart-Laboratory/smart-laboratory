package com.smart.webapp.controller.pb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.Section;
import com.smart.service.lis.SectionManager;

public class PbBaseController {

	protected Map<String, String> labMap = new HashMap<String, String>();
	
	protected synchronized void initLabMap() {
		labMap.clear();
		List<Section> list = sectionManager.getAll();
		for (Section s : list) {
			if(s.getIspb()==1)
				labMap.put(s.getCode(), s.getName());
		}
	}
	
	
	@Autowired
	protected SectionManager sectionManager;

}
