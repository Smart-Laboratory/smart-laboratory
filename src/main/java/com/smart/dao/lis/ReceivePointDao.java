package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ReceivePoint;

public interface ReceivePointDao extends GenericDao<ReceivePoint, Long> {

	@Transactional
	List<ReceivePoint> getByType(int type);
	
	@Transactional
	List<ReceivePoint> getByName(String name);
}
