package com.smart.service.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.lis.ReceivePoint;
import com.smart.service.GenericManager;

public interface ReceivePointManager extends GenericManager<ReceivePoint, Long> {

	@Transactional
	List<ReceivePoint> getByType(int type);

	@Transactional
	List<ReceivePoint> getByName(String name);

	@Transactional
    int getPointCount(String query, String type);

	@Transactional
    List<ReceivePoint> getList(String query, String type, int start, int end, String sidx, String sord);
}
