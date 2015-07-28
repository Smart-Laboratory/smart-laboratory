package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Sample;

public interface SampleDao extends GenericDao<Sample, Long> {

	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	List<Sample> getListBySampleNo(String sampleno);

}
