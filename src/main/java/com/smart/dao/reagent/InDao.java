package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.In;

public interface InDao extends GenericDao<In, Long> {

	void saveAll(List<In> needSaveIn);

	@Transactional
	List<In> getByInDate(String indate);

	@Transactional
	List<In> getByLab(String lab);
}
