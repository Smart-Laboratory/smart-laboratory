package com.smart.service.reagent;

import java.util.List;

import com.smart.model.reagent.Combo;
import com.smart.service.GenericManager;

public interface ComboManager extends GenericManager<Combo, Long> {

	List<Combo> getCombos(String name);

	List<Combo> getByLab(String lab);

}
