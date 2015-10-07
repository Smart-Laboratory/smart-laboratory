package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.TestModify;


public interface TestModifyDao extends GenericDao<TestModify, Long> {

	List<TestModify> getBySampleNo(String sampleNo);

}
