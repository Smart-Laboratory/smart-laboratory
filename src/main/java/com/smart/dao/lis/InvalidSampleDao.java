package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.InvalidSample;

public interface InvalidSampleDao extends GenericDao<InvalidSample, Long> {

	InvalidSample getByEzh(Long id);
}
