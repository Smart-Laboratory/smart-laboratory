package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ReceivePoint;

public interface ReceivePointDao extends GenericDao<ReceivePoint, Long> {

	List<ReceivePoint> getByType(int type);

}
