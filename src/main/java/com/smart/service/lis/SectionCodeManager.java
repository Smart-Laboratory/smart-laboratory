package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.SectionCode;
import com.smart.service.GenericManager;

public interface SectionCodeManager extends GenericManager<SectionCode, Long> {

	List<SectionCode> getCode(String codeId, int start, int end);

	List<SectionCode> searchCode(String name);

}
