package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.SOPIndex;
import com.smart.service.GenericManager;

public interface SOPIndexManager extends GenericManager<SOPIndex, Long> {

	List<SOPIndex> getByLab(String lab);

	List<SOPIndex> getByType(String lab, int type);

}
