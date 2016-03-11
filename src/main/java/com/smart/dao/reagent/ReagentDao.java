package com.smart.dao.reagent;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Reagent;

public interface ReagentDao extends GenericDao<Reagent, Long> {

	List<Reagent> getReagents(String name, String lab);

	Reagent getByname(String name);

	List<Reagent> getByIds(String ids);

	List<Reagent> getByLab(String lab);

	List<Reagent> getByTestId(String testid);
}
