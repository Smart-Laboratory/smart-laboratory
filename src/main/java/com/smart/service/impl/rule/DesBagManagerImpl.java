package com.smart.service.impl.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.DesBagDao;
import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.rule.DesBagManager;

@Service("desBagManager")
public class DesBagManagerImpl extends GenericManagerImpl<DesBag, Long> implements DesBagManager{

	private DesBagDao desBagDao;
	
	@Autowired
	public void setDesBagDao(DesBagDao dao){
		this.dao = dao;
		this.desBagDao = dao;
	}
	
	public List<DesBag> getByParentId(Long parentId){
		return desBagDao.getByParentId(parentId);
	}

	public List<DesBag> getBagByHospital(Long hospital){
		return desBagDao.getBagByHospital(hospital);
	}
	
	public List<DesBag> getBag(String name){
		return desBagDao.getBag(name);
	}

	public List<Description> getDescriptionByBag(String did){
		List<DesBag> desBags = desBagDao.getAll();
		List<Long> ids = new ArrayList<Long>();
		for(DesBag desBag: desBags){
			ids.add(desBag.getId());
		}
//		List<Description> descriptions = descriptionManager.getDescriptionsByBagID(bagId);
		return null;
	}
	
//	@Autowired
//	private DescriptionManager descriptionManager;
}
