package com.smart.dao.lis;

import com.smart.model.lis.Section;
import com.smart.dao.GenericDao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface SectionDao extends GenericDao<Section,Long> {

	@Transactional
	Section getByCode(String sectionId);

	/**
	 * 获取记录数
	 * @param hospitalId
	 * @return
     */
	@Transactional
	int getSectionCount(String query,String hospitalId);

	/**
	 * 查询科室列表
	 * @param query
	 * @param hospitalId
	 * @param start
	 * @param end
     * @return
     */
	@Transactional
	List<Section> getSectionList(String query,String hospitalId, int start,int end,String sidx,String sord);

	/**
	 * 根据名称获取部门
	 * @param name
	 * @return
     */
	@Transactional
	List<Section> getSectionList(String name);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
     */
	boolean batchRemove(long[] ids);
	
	/**
	 * 获取需要排班的科室
	 * @param hospitalId
	 * @return
	 */
	List<Section> getPbSection(String hospitalId);
}
