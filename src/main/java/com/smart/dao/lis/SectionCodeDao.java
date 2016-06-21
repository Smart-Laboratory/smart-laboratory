package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.SectionCode;

public interface SectionCodeDao extends GenericDao<SectionCode,Long> {

	List<SectionCode> getCode(String codeId, int start, int end);

	List<SectionCode> searchCode(String name);
}
