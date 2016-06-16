package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestModify;


public interface TestModifyDao extends GenericDao<TestModify, Long> {

	@Transactional
	List<TestModify> getBySampleNo(String sampleNo);

}
