package com.smart.dao.reagent;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Combo;

public interface ComboDao extends GenericDao<Combo, Long> {

	List<Combo> getCombos(String name);

}
