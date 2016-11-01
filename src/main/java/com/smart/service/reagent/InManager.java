package com.smart.service.reagent;

import java.util.Date;
import java.util.List;

import com.smart.model.reagent.In;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

public interface InManager extends GenericManager<In, Long> {

	@Transactional
	List<In> saveAll(List<In> needSaveIn);

	@Transactional
	List<In> getByInDate(String indate);

	@Transactional
	List<In> getByLab(String lab);
}
