package com.smart.service.impl.reagent;

import java.util.List;

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

	public List<Reagent> getReagents(String name, String lab) {
		return reagentDao.getReagents(name, lab);
	}

	public Reagent getByname(String name) {
		return reagentDao.getByname(name);
	}

	public List<Reagent> getByIds(String ids) {
		return reagentDao.getByIds(ids);
	}

	public List<Reagent> getByLab(String lab) {
		return reagentDao.getByLab(lab);
	}

	public List<Reagent> getByTestId(String testid) {
		return reagentDao.getByTestId(testid);
	}

	public List<Reagent> getByProduct(String productcode, String lab) {
		return reagentDao.getByProduct(productcode, lab);
	}
}
