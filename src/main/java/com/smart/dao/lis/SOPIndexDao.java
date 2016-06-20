package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SOPIndex;

public interface SOPIndexDao extends GenericDao<SOPIndex, Long>{

	@Transactional
	List<SOPIndex> getByLab(String lab);

	@Transactional
	List<SOPIndex> getByType(String lab, int type);
}
