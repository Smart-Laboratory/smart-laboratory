package com.smart.dao.reagent;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.In;

public interface InDao extends GenericDao<In, Long> {

	void saveAll(List<In> needSaveIn);

	List<In> getByInDate(String indate);

	List<In> getByLab(String lab);
}
