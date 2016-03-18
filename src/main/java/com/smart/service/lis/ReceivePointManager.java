package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.ReceivePoint;
import com.smart.service.GenericManager;

public interface ReceivePointManager extends GenericManager<ReceivePoint, Long> {

	List<ReceivePoint> getByType(int type);

}
