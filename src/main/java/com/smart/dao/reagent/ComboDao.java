package com.smart.dao.reagent;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Combo;

public interface ComboDao extends GenericDao<Combo, Long> {

	@Transactional
	List<Combo> getCombos(String name);

	@Transactional
	List<Combo> getByLab(String lab);

}
