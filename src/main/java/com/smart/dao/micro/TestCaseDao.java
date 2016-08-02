package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.TestCase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 17:44
 * @Version:
 */
public interface TestCaseDao extends GenericDao<TestCase, Long> {
	
	@Transactional
	int getTestCaseCount(String query, int start, int end, String sidx, String sord);

	@Transactional
	List<TestCase> getTestCaseList(String query, int start, int end, String sidx, String sord);
}
