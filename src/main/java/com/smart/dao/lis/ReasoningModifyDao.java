package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ReasoningModify;

public interface ReasoningModifyDao extends GenericDao<ReasoningModify, Long> {
	
	@Transactional
	List<ReasoningModify> getBySampleId(String sampleId);

	@Transactional
	int getAddNumber();

	@Transactional
	int getDragNumber();

}
