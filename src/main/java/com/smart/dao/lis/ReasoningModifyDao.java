package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ReasoningModify;

public interface ReasoningModifyDao extends GenericDao<ReasoningModify, Long> {
	List<ReasoningModify> getBySampleId(String docNo);

	int getAddNumber();

	int getDragNumber();

}
