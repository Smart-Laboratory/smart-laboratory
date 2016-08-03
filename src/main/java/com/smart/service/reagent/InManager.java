package com.smart.service.reagent;

import java.util.Date;
import java.util.List;

import com.smart.model.reagent.In;
import com.smart.service.GenericManager;

public interface InManager extends GenericManager<In, Long> {

	void saveAll(List<In> needSaveIn);

	List<In> getByInDate(String indate);

	List<In> getByLab(String lab);
}
