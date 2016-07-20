package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Index;

public interface IndexDao extends GenericDao<Index, Long> {

	/**
	 *  搜索一页的指标
	 * @param pageNum 页号
	 * @param field
	 * @param isAsc
	 * @return
	 */
	@Transactional
	List<Index> getIndexs(int pageNum, String field, boolean isAsc);

	/**
	 * 分页查询
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
     * @param sord
     * @return
     */
	@Transactional
	List<Index> getIndexs(String query, int start, int end, String sidx, String sord);
	
	/**
	 * 获取分页记录数
	 * @param query
	 * @param type
	 * @return
	 */
	@Transactional
	int getIndexsCount(String query, int start, int end, String sidx, String sord);
	
	/**
	 *  指标数
	 * @return
	 */
	@Transactional
	int getIndexsCount();
	
	/**
	 *  
	 * @param sample
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	@Transactional
	List<Index> getIndexsByCategory(String sample, int pageNum, String field, boolean isAsc);
	
	/**
	 * 
	 * @param indexId
	 * @param labDepartment
	 * @return
	 */
	@Transactional
	List<Index> getIndexsByIdandLab(String indexId ,String labDepartment);
	
	/**
	 *  该样本来源下的指标数
	 * @param sample
	 * @return
	 */
	@Transactional
	int getIndexsCount(String sample);
	
	/**
	 *   根据指标的部分名称，模糊搜索匹配的指标
	 * @param index
	 * @return
	 */
	@Transactional
	List<Index> getIndexs(String indexName);
	
	/**
	 *  通过指标编号获得指标
	 * @param indexId
	 * @return
	 */
	@Transactional
	Index getIndex(String indexId);
	
	/**
	 *  根据名称模糊搜索
	 * @param name
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	@Transactional
	List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc);

	@Transactional
	List<Index> getIndexsByQuery(String query);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@Transactional
	int getIndexsByNameCount(String name);

	/**
	 * 细菌信息
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	List<Index> getBacteriaList(String query,int start, int end, String sidx, String sord);

	/**
	 * 细菌记录
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	int getBacteriaListCount(String query,int start, int end, String sidx, String sord);

	/**
	 * 细菌信息
	 * @param id
	 * @return
     */
	Index getBacteriaById(String id);

	/**
	 * 抗生素信息
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
	 * @return
	 */
	List<Index> getAntibioticList(String query,int start, int end, String sidx, String sord);

	/**
	 * 抗生素记录
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
	 * @return
	 */
	int getAntibioticCount(String query,int start, int end, String sidx, String sord);

	/**
	 * 抗生素信息
	 * @param id
	 * @return
	 */
	Index getAntibioticById(String id);
}
