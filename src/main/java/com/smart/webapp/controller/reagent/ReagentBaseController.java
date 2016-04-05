package com.smart.webapp.controller.reagent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.Section;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.reagent.BatchManager;
import com.smart.service.reagent.ComboManager;
import com.smart.service.reagent.InManager;
import com.smart.service.reagent.OutManager;
import com.smart.service.reagent.ReagentManager;
import com.zju.api.service.RMIService;

public class ReagentBaseController {
	
	@Autowired
	protected UserManager userManager = null;
	
	@Autowired
	protected SectionManager sectionManager = null;
	
	@Autowired
	protected ComboManager comboManager = null;
	
	@Autowired
	protected InManager inManager = null;
	
	@Autowired
	protected OutManager outManager = null;
	
	@Autowired
	protected BatchManager batchManager = null;
	
	@Autowired
	protected ReagentManager reagentManager = null;
	
	@Autowired
	protected RMIService rmiService = null;
	
	protected Map<String, String> labMap = new HashMap<String, String>();
	
	protected synchronized void initLabMap() {
		List<Section> list = sectionManager.getAll();
		for (Section s : list) {
			labMap.put(s.getCode(), s.getName());
		}
	}

}
