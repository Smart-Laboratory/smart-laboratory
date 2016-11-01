package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.In;

public interface InDao extends GenericDao<In, Long> {

	List<In> saveAll(List<In> needSaveIn);

	List<In> getByInDate(String indate);

	List<In> getByLab(String lab);
}
