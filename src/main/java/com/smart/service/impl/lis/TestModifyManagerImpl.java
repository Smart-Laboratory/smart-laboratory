package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.TestModifyDao;
import com.smart.model.lis.TestModify;
import com.smart.service.lis.TestModifyManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("testModifyManager")
public class TestModifyManagerImpl  extends GenericManagerImpl<TestModify, Long> implements TestModifyManager {

	private TestModifyDao testModifyDao;

	@Autowired
	public void setTestModifyDao(TestModifyDao testModifyDao) {
		this.testModifyDao = testModifyDao;
		this.dao = testModifyDao;
	}

	public List<TestModify> getBySampleNo(String sampleNo) {
		return testModifyDao.getBySampleNo(sampleNo);
	}
}