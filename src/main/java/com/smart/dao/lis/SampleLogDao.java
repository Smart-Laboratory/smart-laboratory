package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SampleLog;

public interface SampleLogDao extends GenericDao<SampleLog, Long> {

	@Transactional
	List<SampleLog> getBySampleId(Long id);

}
