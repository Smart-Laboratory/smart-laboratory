package com.smart.service.reagent;

import java.util.List;

import com.smart.model.reagent.Out;
import com.smart.service.GenericManager;

public interface OutManager extends GenericManager<Out, Long> {

	void saveAll(List<Out> needSaveOut);
}
