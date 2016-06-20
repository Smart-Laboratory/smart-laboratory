package com.smart.dao.reagent;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Reagent;

public interface ReagentDao extends GenericDao<Reagent, Long> {

	@Transactional
	List<Reagent> getReagents(String name, String lab);

	@Transactional
	Reagent getByname(String name);

	@Transactional
	List<Reagent> getByIds(String ids);

	@Transactional
	List<Reagent> getByLab(String lab);

	@Transactional
	List<Reagent> getByTestId(String testid);

	@Transactional
	List<Reagent> getByProduct(String productcode, String lab);
}
