package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.LikeLabDao;
import com.smart.model.lis.LikeLab;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.LikeLabManager;

@Service("likeLabManager")
public class LikeLabManagerImpl extends GenericManagerImpl<LikeLab, Long> implements LikeLabManager {

private LikeLabDao likeLabDao;
	
	@Autowired
	public void setLikeLabDao(LikeLabDao likeLabDao){
		this.dao = likeLabDao;
		this.likeLabDao = likeLabDao;
	}
}
