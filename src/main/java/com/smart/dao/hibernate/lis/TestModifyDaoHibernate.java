package com.smart.dao.hibernate.lis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.lis.TestModifyDao;
import com.smart.model.lis.TestModify;
import com.smart.dao.hibernate.GenericDaoHibernate;

@Repository("testModifyDao")
public class TestModifyDaoHibernate extends GenericDaoHibernate<TestModify, Long> implements TestModifyDao {

	public TestModifyDaoHibernate() {
		super(TestModify.class);
	}

	@SuppressWarnings("unchecked")
	public List<TestModify> getBySampleNo(String sampleNo) {
		return getSession().createQuery("from TestModify where sampleNo=? order by modifyTime desc").setString(0, sampleNo).list();
	}
}
