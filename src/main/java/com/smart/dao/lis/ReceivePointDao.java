package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ReceivePoint;

public interface ReceivePointDao extends GenericDao<ReceivePoint, Long> {

	List<ReceivePoint> getByType(int type);
	
	List<ReceivePoint> getByName(String name);

    int getPointCount(String query, String type);

    List<ReceivePoint> getList(String query, String type, int start, int end, String sidx, String sord);
}
