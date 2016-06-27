package com.smart.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.smart.dao.UserLevelDao;
import com.smart.model.user.UserLevel;

@Repository("userLevelDao")
public class UserLevelDaoHibernate extends GenericDaoHibernate<UserLevel, Long> implements UserLevelDao{

	public UserLevelDaoHibernate(){
		super(UserLevel.class);
	}
}
