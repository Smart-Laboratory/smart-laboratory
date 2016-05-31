package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.In;

public interface InDao extends GenericDao<In, Long> {

	void saveAll(List<In> needSaveIn);

	List<In> getByInDate(Date indate);

	List<In> getByLab(String lab);
}
