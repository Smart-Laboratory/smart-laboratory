package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.WardDao;
import com.smart.model.lis.Ward;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.WardManager;

@Service("wardManager")
public class WardManagerImpl extends GenericManagerImpl<Ward, String> implements WardManager {

	private WardDao wardDao;

	@Autowired
	public void setWardDao(WardDao wardDao) {
		this.dao = wardDao;
		this.wardDao = wardDao;
	}

	public List<Ward> getByWard(String ward) {
		return wardDao.getByWard(ward);
	}
	
}
