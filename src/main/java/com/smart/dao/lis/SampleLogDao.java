package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SampleLog;

public interface SampleLogDao extends GenericDao<SampleLog, Long> {

	List<SampleLog> getBySampleId(Long id);

}
