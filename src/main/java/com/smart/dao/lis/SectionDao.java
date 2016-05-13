package com.smart.dao.lis;

import com.smart.model.lis.Section;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

public interface SectionDao extends GenericDao<Section,Long>{

	Section getByCode(String sectionId);

	/**
	 * 获取记录数
	 * @param hospitalId
	 * @return
     */
	int getSectionCount(String code,String name,String hospitalId);

	/**
	 * 查询科室列表
	 * @param code
	 * @param name
	 * @param hospitalId
	 * @param start
	 * @param end
     * @return
     */
	List<Section> getSectionList(String code,String name,String hospitalId, int start,int end);
}
