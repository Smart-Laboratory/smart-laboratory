package com.smart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.UserLevelDao;
import com.smart.model.user.UserLevel;
import com.smart.service.UserLevelManager;

@Service("userLevelManager")
public class UserLevelManagerImpl extends GenericManagerImpl<UserLevel, Long> implements UserLevelManager{
	
	private UserLevelDao userLevelDao;
	
	@Autowired
	public void setUserLevelDao(UserLevelDao userLevelDao){
		this.dao = userLevelDao;
		this.userLevelDao = userLevelDao;
	}

}
