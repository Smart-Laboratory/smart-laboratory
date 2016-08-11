package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.ReceivePointDao;
import com.smart.model.lis.ReceivePoint;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ReceivePointManager;

@Service("receivePointManager")
public class ReceivePointManagerImpl extends GenericManagerImpl<ReceivePoint, Long> implements ReceivePointManager {

	private ReceivePointDao receivePointDao;
	
	@Autowired
	public void setReceivePointDao(ReceivePointDao receivePointDao) {
		this.dao = receivePointDao;
		this.receivePointDao = receivePointDao;
	}

	public List<ReceivePoint> getByType(int type) {
		return receivePointDao.getByType(type);
	}
	
	public List<ReceivePoint> getByName(String name){
		return receivePointDao.getByName(name);
	}
}
