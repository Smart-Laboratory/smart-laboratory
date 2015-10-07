package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SOPIndex;

public interface SOPIndexDao extends GenericDao<SOPIndex, Long>{

	List<SOPIndex> getByLab(String lab);

	List<SOPIndex> getByType(String lab, int type);
}
