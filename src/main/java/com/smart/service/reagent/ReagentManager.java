package com.smart.service.reagent;

import java.util.List;

import com.smart.model.reagent.Reagent;
import com.smart.service.GenericManager;

public interface ReagentManager extends GenericManager<Reagent, Long> {

	List<Reagent> getReagents(String name, long id);

	Reagent getByname(String name);

	List<Reagent> getByIds(String s);
}
