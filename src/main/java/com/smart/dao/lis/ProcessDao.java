package com.smart.dao.lis;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Process;

public interface ProcessDao extends GenericDao<Process, Long> {

	@Transactional
	void removeBySampleId(long id);

}
