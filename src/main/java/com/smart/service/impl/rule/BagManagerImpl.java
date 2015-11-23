package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.BagDao;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Rule;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.rule.BagManager;

@Service("bagManager")
public class BagManagerImpl extends GenericManagerImpl<Bag, Long> implements BagManager {
	private BagDao bagDao;
	
    @Autowired
    public BagManagerImpl(BagDao bagDao) {
        super(bagDao);
        this.bagDao = bagDao;
    }
    
	public List<Bag> getBagByName(String name) {
		return bagDao.getBag(name);
	}

	public List<Bag> getBagByHospital(Long hospital) {
		return bagDao.getBagByHospital(hospital);
	}

	public List<Bag> getBag(Long parentId) {
		return bagDao.getByParentId(parentId);
	}

	public List<Rule> getRuleByBag(String hid) {
		return bagDao.getRuleByBag(hid);
	}
}
