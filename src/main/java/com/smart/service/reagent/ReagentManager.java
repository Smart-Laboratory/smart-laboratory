package com.smart.service.reagent;

import java.util.List;

import com.smart.model.reagent.Reagent;
import com.smart.service.GenericManager;

public interface ReagentManager extends GenericManager<Reagent, Long> {

	List<Reagent> getReagents(String name, String lab);

	Reagent getByname(String name);

	List<Reagent> getByIds(String s);

	List<Reagent> getByLab(String lab);

	List<Reagent> getByTestId(String testid);

	List<Reagent> getByProduct(String productcode, String lab);
}
