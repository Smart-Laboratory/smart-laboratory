package com.smart.service.impl.reagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.reagent.ReagentDao;
import com.smart.model.reagent.Reagent;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.reagent.ReagentManager;

@Service("reagentManager")
public class ReagentManagerImpl extends GenericManagerImpl<Reagent, Long> implements ReagentManager {
	
	private ReagentDao reagentDao;
	
    @Autowired
    public ReagentManagerImpl(ReagentDao reagentDao) {
        this.dao = reagentDao;
        this.reagentDao = reagentDao;
    }
}
